package ru.sberbank.learning.backgroundtasksample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private static String TAG = "Sample activity";

    private AsyncTaskImp1 mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAsyncTask = new AsyncTaskImp1();
        mAsyncTask.execute("test1", "test444");
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
        mAsyncTask.cancel(false);
    }

    private class AsyncTaskImp1 extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            SystemClock.sleep(10000);

            List<String> listParams = Arrays.asList(params);
            Log.e(TAG, "params:" + listParams);
            for (int i = 0; i < 10; i++) {
                publishProgress(i, i+1);
            }
            Log.e(TAG, "do in background " + Thread.currentThread().getId());
            Random random = new Random();
            return random.nextDouble();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "on pre execute: " + Thread.currentThread().getId());
        }

        @Override
        protected void onPostExecute(Double result) {
            super.onPostExecute(result);
            Log.e(TAG, "result = " + result);
            Log.e(TAG, "on post execute: " + Thread.currentThread().getId());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e(TAG, "progress = " + values[0] + " " + values[1]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.e(TAG, "onCancelled ");
        }
    }
}
