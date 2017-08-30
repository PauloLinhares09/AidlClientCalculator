package br.com.packapps.aidlservercalculator;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    IMyAidlInterface aidlInterface;

    private TextView tvResultCalculate;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvResultCalculate = (TextView) findViewById(R.id.tvResultCalculate);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    double result = aidlInterface.soma(10, 7);
                    tvResultCalculate.setText("Result: " + result);
                }catch (Exception e){
                    Log.e("TAG", "error: "+ e.getMessage());
                }
            }
        });

        //Service Connection for comunication App Server
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("TAG", "onServiceConnected");
                aidlInterface = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("TAG", "onServiceDisconnected");
            }
        };

        //Intent initialize service
        Intent intent = new Intent("br.com.packapps.aidlservercalculator.CALCULATE");
        intent.setPackage("br.com.packapps.aidlservercalculator");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }



}
