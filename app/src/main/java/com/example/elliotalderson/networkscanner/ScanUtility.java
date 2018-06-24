package com.example.elliotalderson.networkscanner;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
public class ScanUtility {

    public List<String> scanNetwork(String ip) {
        List<String> reachableIPs = new ArrayList<>();

        if (validIP(ip)) {
            String netIp = cutIP(ip);
            for (int i = 0; i <= 255; i++) {
                String tmpIP = netIp + i;
                doPing(tmpIP);
            }
        } else {
            reachableIPs.add("127.0.0.1");
        }
        return reachableIPs;
    }

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

    public String cutIP(String ip) {
        String IpWithNoFinalPart  = ip.replaceAll("(.*\\.)\\d+$", "$1");
        return IpWithNoFinalPart;
    }

    public static boolean doPing(String host) {

        try {
            ProcessBuilder processBuilder;
            processBuilder = new ProcessBuilder("ping","-c","3", host);
            Process process = processBuilder.start();
            String ret = process.getOutputStream().toString();
            System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
