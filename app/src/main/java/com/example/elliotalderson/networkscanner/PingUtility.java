package com.example.elliotalderson.networkscanner;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
public class PingUtility {



    private static boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    public static boolean doPing(final String  host) {
        final ManualCommandActivity mc = new ManualCommandActivity();
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                String[] command = new String[] {"ping","-c","1", host};
                String stdout = mc.execute(command);
                Log.i("CMD:", stdout);
            } catch (Exception e) {
                Log.e("doPing():", e.toString());
            }

        }
    }).start();
        return true;
    }
}
