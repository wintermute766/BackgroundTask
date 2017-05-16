package ru.sberbank.learning.backgroundtasksample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Handler mWorkerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWorkerHandler = new Handler();
        Log.e("Sample activity", "onCreate() " + Thread.currentThread().getId());
        HandlerThread handlerThread = new HandlerThread("Handler thread");
        handlerThread.start();
        mWorkerHandler = new Handler(handlerThread.getLooper());
        new Thread(new OurTask()).start();
        //mWorkerHandler.post(new OurTask());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Sample activity", "onStart() " + Thread.currentThread().getId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Sample activity", "onResume() " + Thread.currentThread().getId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Sample activity", "onPause() " + Thread.currentThread().getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Sample activity", "onDestroy() " + Thread.currentThread().getId());
    }

    private class OurTask implements Runnable {

        @Override
        public void run() {
            Log.e("Sample activity", "worker thread " + Thread.currentThread().getId());
            //SystemClock.sleep(3000);
            Runnable callback = new Runnable() {
                @Override
                public void run() {
                    Log.e("Sample activity", "callback " + Thread.currentThread().getId());
                    //Toast.makeText(MainActivity.this, "Task finished", Toast.LENGTH_LONG).show();
                }
            };
            mWorkerHandler.postDelayed(callback, 5000);
        }
    }
}
