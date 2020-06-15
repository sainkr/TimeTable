package com.example.timetable;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DeleteActivity extends Activity {
    Intent intent;

    TextView class_name,class_time,prof_name,place_name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delete);

        class_name = (TextView) findViewById(R.id.dclass_name);
        class_time = (TextView) findViewById(R.id.dclass_time);
        prof_name = (TextView) findViewById(R.id.dprof_name);
        place_name = (TextView) findViewById(R.id.dplace_name);

        intent = getIntent();

        class_name.setText(intent.getStringExtra("class_name"));
        class_time.setText((intent.getIntExtra("shour",0)+9)+":00 - "+(intent.getIntExtra("ehour",0)+9)+":00");
        prof_name.setText(intent.getStringExtra("prof_name"));
        place_name.setText(intent.getStringExtra("place_name"));

    }

    public void onClickDialog(View v){
        switch(v.getId()){
            case R.id.bt_delete:
                    //intent.putExtra("delete_num",intent.getIntExtra("delete_num",0));
                    Toast.makeText(getApplicationContext(),"삭제 되었습니다.",Toast.LENGTH_LONG).show();

                     setResult(RESULT_OK, intent);

                    finish();
                break;
            case R.id.bt_ok:
                this.finish();
                break;
        }
    }


}
