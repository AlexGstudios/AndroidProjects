package com.example.toserver;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends AsyncTask<String, Void, Void> {

    private String ip, message;
    private int port, num;

    private String response = "";

    public Client(String serverIp, String message, int port, int num){

        this.ip = serverIp;
        this.message = message;
        this.port = port;
        this.num = num;
    }

    protected Void doInBackground(String... params){

        Socket socket = null;
        PrintWriter fromClient = null;
        DataInputStream fromServer = null;

        try{

            socket = new Socket(ip, port);

            if(!socket.isConnected()){

                response = "Bad Ip";
            }else{

                fromClient = new PrintWriter(socket.getOutputStream());
            }
        }catch(Exception e){

            Log.d("TAG", "doInBackground: " + e);
        }

        try{

            fromClient.print(message);
            Log.d("TAG2", "doInBackground: we get here");
            fromClient.flush();

            fromServer = new DataInputStream(socket.getInputStream());

            response = fromServer.readUTF();

            Log.d("TAG2", "doInBackground: " + response);

            socket.close();

        }catch(Exception e){

            Log.d("TAG2", "doInBackground: " + e);
        }

        return null;
    }

    public String getResponse(){

        int i = 0;

        while(i < 50){

            Log.d("alexander", "getResponse: inne i loopen " + response);
            if (!response.isEmpty()){
                return response;
            }
            i++;
        }
        return "No connection";
    }
}