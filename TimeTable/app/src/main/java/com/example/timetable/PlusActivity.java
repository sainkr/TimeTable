package com.example.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class PlusActivity extends Activity {
    Spinner spinner_day, spinner_starthour,spinner_endhour;
    EditText class_name, prof_name,place_name;
    String put_day;
    int put_shour,put_ehour;
    int pass = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_plus);

        final String [] day = {"월","화","수","목","금"};
        final String [] start_hour = {"9","10","11","12","1","2","3","4","5","6"};
        final String [] end_hour = {"9","10","11","12","1","2","3","4","5","6"};

        //Spinner 객체
        spinner_day = (Spinner) findViewById(R.id.spinner_day);
        spinner_starthour = (Spinner) findViewById(R.id.spinner_starthour);
        spinner_endhour = (Spinner) findViewById(R.id.spinner_endhour);

        // spinner_day 어댑터 연결
        ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                day);
        spinner_day = (Spinner)findViewById(R.id.spinner_day);
        spinner_day.setAdapter(adapter);

        spinner_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                put_day = day[position];

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // spinner_starthour 어댑터 연결
        ArrayAdapter adapter1 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                start_hour);
        spinner_starthour = (Spinner)findViewById(R.id.spinner_starthour);
        spinner_starthour.setAdapter(adapter1);

        spinner_starthour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                put_shour = position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // spinner_endhour어댑터 연결
        ArrayAdapter adapter2 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                end_hour);
        spinner_endhour = (Spinner)findViewById(R.id.spinner_endhour);
        spinner_endhour.setAdapter(adapter2);

        spinner_endhour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               put_ehour = position;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }

    public void onClickDialog(View v){
        switch(v.getId()){
            case R.id.btn_ok:
                if(put_shour >= put_ehour){
                    Toast.makeText(getApplicationContext(),"시간을 다시 선택하세요.",Toast.LENGTH_LONG).show();
                }
                else {

                    // Intent 객체 생성.
                    Intent intent = getIntent();

                    String[] data = intent.getStringArrayExtra("data");
                    int count = intent.getIntExtra("count",0);

                    loop:
                    for (int i = 0; i < count; i++) {
                        String[] words = data[i].split(",");
                        if(put_day.equals(words[0])){
                            for(int j = put_shour ; j <put_ehour ;j++){
                                for(int m = Integer.parseInt(words[3]) ; m <Integer.parseInt(words[4]) ;m++){
                                    if(j == m){
                                        Toast.makeText(getApplicationContext(),"겹치는 수업이 있습니다",Toast.LENGTH_LONG).show();
                                        pass = 1;
                                        break loop;
                                    }
                                }
                            }
                        }
                    }

                    if(pass ==0){
                            class_name = (EditText) findViewById(R.id.class_name);
                            prof_name = (EditText) findViewById(R.id.prof_name);
                            place_name = (EditText) findViewById(R.id.place_name);

                            intent.putExtra("class_name", class_name.getText().toString());
                            intent.putExtra("prof_name", prof_name.getText().toString());
                            intent.putExtra("place_name", place_name.getText().toString());
                            intent.putExtra("day", put_day);
                            intent.putExtra("start_hour", put_shour);
                            intent.putExtra("end_hour", put_ehour);

                            setResult(RESULT_OK, intent);

                            finish();
                    }

                    pass=0;


                }
                break;
            case R.id.btn_cancle:
                this.finish();
                break;
        }
    }
}
