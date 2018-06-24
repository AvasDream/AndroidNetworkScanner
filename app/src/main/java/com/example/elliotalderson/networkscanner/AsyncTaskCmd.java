package com.example.elliotalderson.networkscanner;

import android.os.AsyncTask;

public class AsyncTaskCmd extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate = null;
    private ManualCommandActivity mc = new ManualCommandActivity();


    protected String doInBackground(String... params) {
        String ret;
        ret = mc.execute(params);
        return ret;
    }

    // Runs in UI before background thread is called
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Do something like display a progress bar
    }

    // This runs in UI when background thread finishes
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Do things like hide the progress bar or change a TextView
        mc.cmdOutputView.setText(result);
    }

}
