package com.example.testedittext.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class Secure {

    public static String getKey(Context context) throws UnsupportedEncodingException {
        SharedPreferences sharedPreferences;
        String APP_PREFERENCES = "mysettings";

        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        String login = sharedPreferences.getString("login", null);
        String pass = sharedPreferences.getString("pass", null);
        String text = login+pass;
        return Base64.encodeToString(text.getBytes("UTF-8"),Base64.DEFAULT) ;
    }

    public static String getId() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

}
