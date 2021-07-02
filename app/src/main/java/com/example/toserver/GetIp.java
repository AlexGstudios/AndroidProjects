package com.example.toserver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.net.InetAddress;

import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class GetIp extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "nstask";

    private final int PORT = 13000;
    private static String num = "";

    private DbHelper dbHelper = MainActivity.getDbHelper();

    private final WeakReference<Context> mContextRef;

    public GetIp(Context context){

        mContextRef = new WeakReference<Context>(context);
    }

    protected Void doInBackground(Void... voids) {

        Log.d(TAG, "Let's sniff the network");

        try {

            Context context = mContextRef.get();

            if(context != null){

                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                // wifiInfo only supports the IPv4-addresses
                // so to implement another way of formatting the string is not prio
                // i could do it with getHostById(ipAddress); but that will also result in
                // an IPv4-address due to wifiInfo.
                WifiInfo connectionInfo = wm.getConnectionInfo();
                int ipAddress = connectionInfo.getIpAddress();
                String ipString = Formatter.formatIpAddress(ipAddress); // deprecated change this one

                Log.d(TAG, "activeNetwork: " + String.valueOf(activeNetwork));
                Log.d(TAG, "ipString: " + ipString);

                String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);
                Log.d(TAG, "prefix: " + prefix);

                dbHelper.delete();

                for(int i = 0; i < 255; i++){

                    String testIp = prefix + String.valueOf(i);

                    Log.d(TAG, "doInBackground: in the for loop" + i);

                    InetAddress address = InetAddress.getByName(testIp);
                    boolean reachable = address.isReachable(100);
                    String hostName = address.getCanonicalHostName();

                    if(reachable){
                        //send a message to all reachable and receive a response
                        //the response is a number of witch the device is located
                        //ie 192.1168.0.110 "msg: " 4, 192.168.0.223 "msg: " 1,
                        //then take all ip's that have a msg num and sort by that number then save to db

                        Client client = new Client(testIp, "Num", PORT, 1);
                        client.doInBackground();

                        setNum(client.getResponse());

                        int x = Integer.parseInt(num);

                        if(x != 0){

                            dbHelper.add(x, testIp);
                        }

                        Log.d(TAG, "Host: " + String.valueOf(hostName) + "(" + String.valueOf(testIp) + ") is reachable!");
                    }
                }
            }
        }
        catch (Throwable T){

            Log.d(TAG, "Something went wrong " + T);
        }

        return null;
    }

    public static void setNum(String fromServer){
        num = fromServer;
    }
}