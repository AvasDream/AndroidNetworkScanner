package com.example.elliotalderson.networkscanner;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    private View welcomeView;

    private static final String fileAddressMac = "/sys/class/net/wlan0/address";
    private TextView wlanView = null;// = findViewById(R.id.view_wlan);
    private TextView ipv4View = null;// = findViewById(R.id.view_ipv4);
    private Button scanBtn = null;// = findViewById(R.id.scan_btn);
    private Button showBtn = null;// = findViewById(R.id.show_scans_btn);
    private Button cmdBtn = null;// = findViewById(R.id.cmd_btn);
    WifiManager wifiManager = null;
    WifiInfo wifiInfo = null;
    private boolean wifiOn = false;
    ProgressBar pgsBar = null;
    String ipv4 = null;
    /*
        Callback fires on first creation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        // Set Backgroundcolor
        welcomeView = findViewById(android.R.id.content);
        welcomeView.setBackgroundResource(R.color.viewBg);
        this.setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    /*
        onStart makes activity visible for user
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Wifi connected?
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        scanBtn =  findViewById(R.id.scan_btn);
        showBtn = findViewById(R.id.show_scans_btn);
        cmdBtn = findViewById(R.id.cmd_btn);

        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            wifiOn = true;
            wlanView=findViewById(R.id.view_wlan);
            ipv4View=findViewById(R.id.view_ipv4);
            //Set SSID
            wlanView.setText(wifiInfo.getSSID().replace("\"", ""));
            // Set IP
            int ip = wifiInfo.getIpAddress();
            ipv4 = String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));
            ipv4View.setText(ipv4);

        } else {
            Toast.makeText(getApplicationContext(),getString(R.string.wifi_warning),Toast.LENGTH_LONG).show();
        }
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wifiOn) {
                    changeToPingActivity();
                } else {
                    Toast.makeText(getApplicationContext(),getString(R.string.wifi_warning),Toast.LENGTH_LONG).show();
                }
            }
        });
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToShowScans();
            }
        });
        cmdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToManualCMD();
            }
        });
    }

    public void changeToShowScans() {
        Intent intent = new Intent(this, ShowShodanActivity.class);
        intent.putExtra("ip",ipv4);
        startActivity(intent);
    }

    public void changeToManualCMD() {
        Intent intent = new Intent(this, ManualCommandActivity.class);
        startActivity(intent);
    }
    public void changeToPingActivity() {
        Intent intent = new Intent(this, PingActivity.class);
        startActivity(intent);
    }







    /*
        When activity was pushed to background
        and returns to foregrpund onResume is called
     */
    @Override
    protected void onResume() {
        super.onResume();
    }
    /*
        onPause is called at the first indication the user is leaving the activity
     */
    @Override
    protected void onPause() {
        super.onPause();
    }
    /*
        stopped is called when the activity is no longer visible for the user
     */
    @Override
    protected void onStop() {
        super.onStop();
    }
    /*
        Cut my activity in two pieces this is my last callback
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
