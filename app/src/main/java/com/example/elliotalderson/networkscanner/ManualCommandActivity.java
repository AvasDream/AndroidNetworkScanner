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

public class ManualCommandActivity extends AppCompatActivity  {
    public TextView cmdOutputView = null;
    private Button executeBtn = null;
    private Spinner cmdSpinner = null;
    private String[] command = null;
    public String cmdOutput = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_command);
        cmdOutputView = findViewById(R.id.cmdOutputView);
        cmdOutputView.setBackgroundColor(getResources().getColor(R.color.textViewBg));
        executeBtn = findViewById(R.id.executeBtn);
        cmdSpinner = findViewById(R.id.cmdSpinner);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cmdOutputView = findViewById(R.id.cmdOutputView);
        executeBtn = findViewById(R.id.executeBtn);
        cmdSpinner = findViewById(R.id.cmdSpinner);
        cmdOutputView.setMovementMethod(new ScrollingMovementMethod());
        cmdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (cmdSpinner.getSelectedItem().toString()) {
                    case "ifconfig": command = new String[]{"ifconfig"};
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
                cmdOutputView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnPressed();
                    }
                }, 2000);
            }
        });



    }
    /*
    Capsulation for Networking Thread an UI
     */
    private void btnPressed() {
        try {
            setView(command);
        } catch (InterruptedException e) {
            Log.e("Thread Exception",e.toString());
        }
        cmdOutputView.setText(cmdOutput);
        cmdOutput ="";
    }
    private void setView(final String[] cmd) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                cmdOutput = execute(cmd);
            }
        });
        t.start();
        t.join();
    }
    public String execute(String[] command) {
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





}
