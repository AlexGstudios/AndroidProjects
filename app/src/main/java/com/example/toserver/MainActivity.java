package com.example.toserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView ip1, ip2, ip3, ip4, hostname1, hostname2, hostname3, hostname4;
    private ArrayList<TextView> arrText;
    private ArrayList<DbObject> arrIp;

    private TextView status;

    private static String con = "";

//    private Button btnOpen, btnClose, btnStop, btnOptions;

    private final String serverIp = "";

    private final int port = 13000;
    private final int num = 1;

    private static DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

        findBy();

        showTextView();

        sortArray();

    }

    private void sortArray() {

        try {

            Collections.sort(arrIp);
        }catch (Exception e){

            Log.d("TAG", "sortArray: " + e);
        }
    }

    private void status(String str) {

        Log.d("alexander", "status: " + str);

        status.setText(str);
    }

    private void findBy(){

        ip1 = findViewById(R.id.txtIp1);
        ip2 = findViewById(R.id.txtIp2);
        ip3 = findViewById(R.id.txtIp3);
        ip4 = findViewById(R.id.txtIp4);

        status = findViewById(R.id.txtStatus);
    }

    public void onClick(View v){

        switch(v.getId()){

            case R.id.btnOpen:
                if (arrIp.isEmpty()){

                    status("No Ip found!");
                }else{

                    for (int i = 0; i < arrIp.size(); i++){

                        Client clientOpen = new Client(arrIp.get(i).getContent(), "Open", port, num);
                        clientOpen.execute();
                        // fix a switch in this method below
                        // in gui switch between green and red image
                        status(clientOpen.getResponse());
                    }
                }
                break;
            case R.id.btnStop:
                if (arrIp.isEmpty()){

                    status("No Ip found!");
                }else{

                    for (int i = 0; i < arrIp.size(); i++){

                        Client clientStop = new Client(arrIp.get(i).getContent(), "Stop", port, num);
                        clientStop.execute();
                        // fix a switch in this method below
                        // in gui switch between green and red image
                        status(clientStop.getResponse());
                    }
                }
                break;
            case R.id.btnClose:
                if (arrIp.isEmpty()){

                    status("No Ip found!");
                }else{

                    for (int i = 0; i < arrIp.size(); i++){

                        Client clientClose = new Client(serverIp, "Close", port, num);
                        clientClose.execute();
                        // fix a switch in this method below
                        // in gui switch between green and red image
                        status(clientClose.getResponse());
                    }
                }
                break;
            case R.id.btnOptions:
                Intent intent = new Intent(this, OptionsAct.class);
                startActivity(intent);
                break;
        }
    }

    public void showTextView(){

        try{
            // get table from db to put in arrObj then set to the different textviews

            ArrayList<DbObject> arrObj = new ArrayList<>();

            arrObj = dbHelper.table(arrObj);

            arrIp = arrObj;

            arrText = new ArrayList<>();

            arrText.add(ip1);
            arrText.add(ip2);
            arrText.add(ip3);
            arrText.add(ip4);

            for (int i = 0; i < arrObj.size(); i++){

                arrText.get(i).setText(String.valueOf(arrObj.get(i).getContent()));
            }
        }catch(Exception e){

            Log.d("TAG", "getTextView: " + e);
        }
    }

    public static DbHelper getDbHelper() {

        return dbHelper;
    }
}