package com.e.runnablesthreadsandhandlers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

 SwitchMaterial switchMaterial;
 ProgressBar progressBar;
 Button taskButton;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchMaterial =findViewById(R.id.switch1);
        progressBar =findViewById(R.id.progressBar);
        taskButton =findViewById(R.id.button);

        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (switchMaterial.isChecked()){
                    progressBar.setVisibility(View.VISIBLE);
                    //Handlers allow you to update the ui thread from another thread
                    Handler handler =new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            //main thread
                            //This runs on background thread
                            Log.d(TAG, "Thread name 1:"+ Thread.currentThread().getName());
                            // long running task, make a wait function for x time

                            synchronized (this) {
                                //wait for 5 seconds
                                try {
                                    wait(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Download Finished", Toast.LENGTH_SHORT).show();
                    }
                });

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "10 seconds after download has finished", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }, 10000);

                            Log.d(TAG, "run: Download finished");
                        }
                    };
                    //runnable.run();
                    // the above code freeze the ui because the runnable is running on the main thread with is
                    //the ui thread
                    Log.d(TAG, "Thread name 1:"+ Thread.currentThread().getName());
                    Thread thread = new Thread(runnable);
                    thread.start();


                    // it will start a new ui thread separate from the main ui thread
                }
                else {
                    Toast.makeText(MainActivity.this, "Cannot run task switch not activated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}