package com.example.elliotalderson.networkscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class ManualCommandActivity extends AppCompatActivity {
    private TextView cmdOutputView = null;
    private Button executeBtn = null;
    private Spinner cmdSpinner = null;
    private EditText argsInput = null;
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
        executeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stdout = execute("/system/bin/netstat -löd");
                cmdOutputView.setText(stdout);
            }
        });
    }

    private String execute(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);

            InputStreamReader reader = new InputStreamReader(process.getInputStream());
            InputStreamReader errReader = new InputStreamReader(process.getErrorStream());

            BufferedReader stdoutReader = new BufferedReader(reader);
            BufferedReader stderrReader = new BufferedReader(errReader);

            int numRead;
            char[] buffer = new char[5000];
            char[] buffer2 = new char[5000];
            StringBuffer commandOutput = new StringBuffer();
            while ((numRead = stdoutReader.read(buffer)) > 0) {
                commandOutput.append(buffer, 0, numRead);
            }
            while ((numRead = stderrReader.read(buffer2)) > 0) {
                commandOutput.append(buffer, 0, numRead);
            }
            stdoutReader.close();
            process.waitFor();
            Log.i("execute output", commandOutput.toString());
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
