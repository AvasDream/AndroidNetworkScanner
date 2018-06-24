package com.example.elliotalderson.networkscanner;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class ManualCommandActivity extends AppCompatActivity {
    private TextView cmdOutputView = null;
    private Button executeBtn = null;
    private Spinner cmdSpinner = null;
    private EditText argsInput = null;
    private String[] command = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_command);
        cmdOutputView = findViewById(R.id.cmdOutputView);
        cmdOutputView.setBackgroundColor(getResources().getColor(R.color.textViewBg));
        executeBtn = findViewById(R.id.executeBtn);
        argsInput = findViewById(R.id.cmdArgsInput);
        cmdSpinner = findViewById(R.id.cmdSpinner);


    }

    @Override
    protected void onStart() {
        super.onStart();
        cmdOutputView = findViewById(R.id.cmdOutputView);
        executeBtn = findViewById(R.id.executeBtn);
        argsInput = findViewById(R.id.cmdArgsInput);
        cmdSpinner = findViewById(R.id.cmdSpinner);
        cmdOutputView.setMovementMethod(new ScrollingMovementMethod());
        argsInput.clearFocus();
        cmdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (cmdSpinner.getSelectedItem().toString()) {
                    case "ifconfig": command = new String[]{"ifconfig"};
                        break;
                    case "ping": Toast.makeText(getApplicationContext(),"ping",Toast.LENGTH_LONG).show();
                        break;
                    case "route": command = new String[]{"route"};
                        break;
                    case "netstat": command = new String[]{"netstat"};
                        break;
                    case "ps": command = new String[]{"ps"};
                        break;
                    case "top": command = new String[]{"top","-n","1"};
                        break;
                    default: Toast.makeText(getApplicationContext(),getString(R.string.no_cmd_warning),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        executeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stdout = null;
                try {
                    stdout = new ExecuteAsync().execute(command).get();
                } catch (InterruptedException e) {
                    Log.e("Error  AsyncExec",e.toString());
                } catch (ExecutionException e) {
                    Log.e("Error  AsyncExec",e.toString());
                }
                Log.i("execute output", stdout);
                cmdOutputView.setText(stdout);
            }
        });



    }

    private String execute(String[] command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader reader = new InputStreamReader(process.getInputStream());
            BufferedReader stdoutReader = new BufferedReader(reader);
            int numRead;
            char[] buffer = new char[5000];
            StringBuffer commandOutput = new StringBuffer();
            while ((numRead = stdoutReader.read(buffer)) > 0) {
                commandOutput.append(buffer, 0, numRead);
            }
            stdoutReader.close();
            process.waitFor();
            return commandOutput.toString();
        } catch (IOException e) {
            Log.e("execute IOException", e.toString());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Log.e("execute Exception", e.toString());
            throw new RuntimeException(e);
        }

    }
    private class ExecuteAsync extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String ret;
            ManualCommandActivity cA = new ManualCommandActivity();
            ret = cA.execute(params);
            return ret;
        }

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Do something like display a progress bar
        }
        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Do things like update the progress bar
        }
        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Do things like hide the progress bar or change a TextView
        }
    }


}
