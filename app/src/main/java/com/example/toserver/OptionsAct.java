package com.example.toserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class OptionsAct extends AppCompatActivity {

    private EditText edtIp, edtValue;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        dbHelper = MainActivity.getDbHelper();

        findAll();

        //TODO: get all ip's from database, put in list
        //iterate list.length to dynamically create
        //TextView's and EditText's

        //This code:
//        public void Add_Line() {
//            LinearLayout ll = (LinearLayout)findViewById(R.id.linearLayoutDecisions);
//            // add edittext
//            EditText et = new EditText(this);
//            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            et.setLayoutParams(p);
//            et.setText("Text");
//            et.setId(numberOfLines + 1);
//            ll.addView(et);
//            numberOfLines++;
//        }
    }

    public void findAll(){

        edtIp = findViewById(R.id.edtOptionsIp);
        edtValue = findViewById(R.id.edtOptionsValue);
    }

    public void delete(){
        dbHelper.delete();
    }

    public void saveToDb(EditText toSave, EditText toSaveValue){

        String strSave = toSave.getText().toString();
        int intSave = Integer.parseInt(toSaveValue.getText().toString());

        dbHelper.add(intSave, strSave);
    }

    public void optionsOnClick(View v){

        switch(v.getId()){

            case R.id.btnOptionsBack:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btnOptionsSave:
                saveToDb(edtIp, edtValue);
                break;
            case R.id.btnOptionsAuto:
                GetIp getIp = new GetIp(getApplicationContext());
                getIp.execute();
                break;
            case R.id.btnOptionsDelete:
                delete();
                break;

        }
    }
}