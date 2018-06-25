package com.example.elliotalderson.networkscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PingActivity extends AppCompatActivity {
    Button pingBtn = null;
    TextView pingOutputView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);
    }
}
