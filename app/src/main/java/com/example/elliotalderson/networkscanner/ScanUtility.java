package com.example.elliotalderson.networkscanner;
import java.util.ArrayList;
import java.util.List;
public class ScanUtility {

    public List<String> scanNetwork(String ip) {
        List<String> reachableIPs = new ArrayList<>();

        if (validIP(ip)) {
            reachableIPs.add(ip);
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
}
