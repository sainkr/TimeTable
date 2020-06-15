package com.example.timetable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textview_day[][];
    TextView semester;

    static final int REQ_ADD_CONTACT = 1 ;
    static final int REQ_ADD_CONTACT2 = 2;

    ImageView button_plus;

    String[] data = new String[50];

    int count= 0;
    int deletenum = 0;

    int color[];
    int color_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 로딩화면면
        Intent loading_intent = new Intent(this, LoadingActivity.class);
        startActivity(loading_intent);

        // 학기 출력
        semester = (TextView)findViewById(R.id.semester);

        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        String year = yearFormat.format(currentTime);
        int month = Integer.parseInt(monthFormat.format(currentTime));

        if(month >= 9){
            semester.setText(year +"년 2학기 시간표");
        }
        else{
            semester.setText(year +"년 1학기 시간표");
        }

        textview_day = new TextView[][]{
                // 월요일
                {(TextView) findViewById(R.id.mon_1), (TextView) findViewById(R.id.mon_2),
                (TextView) findViewById(R.id.mon_3), (TextView) findViewById(R.id.mon_4),
                (TextView) findViewById(R.id.mon_5), (TextView) findViewById(R.id.mon_6),
                (TextView) findViewById(R.id.mon_7), (TextView) findViewById(R.id.mon_8),
                (TextView) findViewById(R.id.mon_9), (TextView) findViewById(R.id.mon_10)},
                // 화요일
                {(TextView) findViewById(R.id.tue_1), (TextView) findViewById(R.id.tue_2),
                        (TextView) findViewById(R.id.tue_3), (TextView) findViewById(R.id.tue_4),
                        (TextView) findViewById(R.id.tue_5), (TextView) findViewById(R.id.tue_6),
                        (TextView) findViewById(R.id.tue_7), (TextView) findViewById(R.id.tue_8),
                        (TextView) findViewById(R.id.tue_9), (TextView) findViewById(R.id.tue_10)},
                // 수요일
                {(TextView) findViewById(R.id.wed_1), (TextView) findViewById(R.id.wed_2),
                        (TextView) findViewById(R.id.wed_3), (TextView) findViewById(R.id.wed_4),
                        (TextView) findViewById(R.id.wed_5), (TextView) findViewById(R.id.wed_6),
                        (TextView) findViewById(R.id.wed_7), (TextView) findViewById(R.id.wed_8),
                        (TextView) findViewById(R.id.wed_9), (TextView) findViewById(R.id.wed_10)},
                // 목요일
                {(TextView) findViewById(R.id.thu_1), (TextView) findViewById(R.id.thu_2),
                        (TextView) findViewById(R.id.thu_3), (TextView) findViewById(R.id.thu_4),
                        (TextView) findViewById(R.id.thu_5), (TextView) findViewById(R.id.thu_6),
                        (TextView) findViewById(R.id.thu_7), (TextView) findViewById(R.id.thu_8),
                        (TextView) findViewById(R.id.thu_9), (TextView) findViewById(R.id.thu_10)},
                // 금요일
                {(TextView) findViewById(R.id.fri_1), (TextView) findViewById(R.id.fri_2),
                        (TextView) findViewById(R.id.fri_3), (TextView) findViewById(R.id.fri_4),
                        (TextView) findViewById(R.id.fri_5), (TextView) findViewById(R.id.fri_6),
                        (TextView) findViewById(R.id.fri_7), (TextView) findViewById(R.id.fri_8),
                        (TextView) findViewById(R.id.fri_9), (TextView) findViewById(R.id.fri_10)}
        };

        color = new int[]{
                ContextCompat.getColor(this, R.color.c1),ContextCompat.getColor(this, R.color.c2),
                ContextCompat.getColor(this, R.color.c3),ContextCompat.getColor(this, R.color.c4),
                ContextCompat.getColor(this, R.color.c5),ContextCompat.getColor(this, R.color.c6),
                ContextCompat.getColor(this, R.color.c7),ContextCompat.getColor(this, R.color.c8),
                ContextCompat.getColor(this, R.color.c9)
        };


        //저장된 값을 불러오기 위해 같은 네임파일을 찾음.
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        //text라는 key에 저장된 값이 있는지 확인. 아무값도 들어있지 않으면 ""를 반환
        count = sf.getInt("count",0);
        if(count != 0) {
            for (int j = 0; j <count; j++) {
                data[j] = sf.getString("data" + j, "");
                if(!data[j].equals("")) {
                    String[] words = data[j].split(",");
                    String day = words[0];
                    int shour = Integer.parseInt(words[3]);
                    String class_name = words[1];
                    String place_name = words[2];
                    int ehour = Integer.parseInt(words[4]);

                    int daynum = 0;

                    if (day.equals("월")) {
                        daynum = 0;
                    } else if (day.equals("화")) {
                        daynum = 1;
                    } else if (day.equals("수")) {
                        daynum =2;
                    } else if (day.equals("목")) {
                        daynum = 3;
                    } else if (day.equals("금")) {
                        daynum = 4;
                    }

                    this.textview_day[daynum][shour].setText(class_name + "\n" + place_name);
                    for (int i = shour; i < ehour; i++)
                        this.textview_day[daynum][i].setBackgroundColor(color[color_count]);

                }
                if(color_count == 7){
                    color_count=0;
                }
                else {
                    color_count++;
                }
            }
        }

        // 각 텍스트뷰 별 클릭리스너
        View.OnClickListener dayListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (v.getId() == textview_day[i][j].getId()) {
                            for (int k = 0; k < count; k++) {
                                String[] words = data[k].split(",");
                                String day = words[0];
                                int shour = Integer.parseInt(words[3]);
                                /*String class_name = words[1];
                                String place_name = words[2];
                                String prof_name = words[5];*/
                                int ehour = Integer.parseInt(words[4]);
                                String click_day = null;
                                if (i == 0) {
                                    click_day = "월";
                                } else if (i == 1) {
                                    click_day = "화";
                                } else if (i == 2) {
                                    click_day = "수";
                                } else if (i == 3) {
                                    click_day = "목";
                                } else if (i == 4) {
                                    click_day = "금";
                                }
                                if (day.equals(click_day)) {
                                    if (shour <= j) {
                                        if (j < ehour) {
                                             deletenum = k;
                                            Intent intent = new Intent(MainActivity.this, DeleteActivity.class);

                                            intent.putExtra("class_name", words[1]);
                                            intent.putExtra("shour",shour);
                                            intent.putExtra("ehour",ehour);
                                            intent.putExtra("place_name", words[2]);
                                            intent.putExtra("prof_name", words[5]);

                                            startActivityForResult(intent, REQ_ADD_CONTACT2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 10; j++){
                this.textview_day[i][j].setOnClickListener(dayListener);
            }
        }


        button_plus  = (ImageView)findViewById(R.id.button_plus);

        button_plus.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlusActivity.class) ;
                intent.putExtra("data",data);
                intent.putExtra("count",count);
                startActivityForResult(intent, REQ_ADD_CONTACT);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_ADD_CONTACT) {
            if (resultCode == RESULT_OK) {

                String class_name = intent.getStringExtra("class_name");
                String prof_name = intent.getStringExtra("prof_name");
                String place_name = intent.getStringExtra("place_name");
                String day = intent.getStringExtra("day");
                int shour = intent.getIntExtra("start_hour", 0);
                int ehour = intent.getIntExtra("end_hour", 0);

                if(prof_name.equals("")) {
                    prof_name = " ";
                }
                data[count] = day + "," + class_name + "," + place_name + "," + shour + "," + ehour + "," + prof_name;
                int daynum = 0;

                if (day.equals("월")) {
                    daynum = 0;
                } else if (day.equals("화")) {
                    daynum = 1;
                } else if (day.equals("수")) {
                    daynum =2;
                } else if (day.equals("목")) {
                    daynum = 3;
                } else if (day.equals("금")) {
                    daynum = 4;
                }

                for (int i = shour; i < ehour; i++)
                    this.textview_day[daynum][i].setBackgroundColor(color[color_count]);

                this.textview_day[daynum][shour].setText(class_name + "\n" + place_name);

                count++;

                if(color_count == 8){
                    color_count = 0;
                }
                else {
                    color_count++;
                }
            }
        }
        else if (requestCode ==  REQ_ADD_CONTACT2) {
            if (resultCode == RESULT_OK) {
                Log.v("삭제 번호 ", String.valueOf(deletenum));
                String words[] = data[deletenum].split(",");
                String day = words[0];
                int shour = Integer.parseInt(words[3]);
                String class_name = words[1];
                String place_name = words[2];
                String prof_name = words[5];
                int ehour = Integer.parseInt(words[4]);

                int daynum = 0;

                if (day.equals("월")) {
                   daynum = 0;
                } else if (day.equals("화")) {
                   daynum = 1;
                } else if (day.equals("수")) {
                    daynum =2;
                } else if (day.equals("목")) {
                   daynum = 3;
                } else if (day.equals("금")) {
                    daynum = 4;
                }

                textview_day[daynum][shour].setText("");
                for (int i = shour; i < ehour; i++) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        this.textview_day[daynum][i].setBackground(ContextCompat.getDrawable(this, R.drawable.border_vh));
                    } else {
                        this.textview_day[daynum][i].setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.border_vh));
                    }
                }

                for(int i = deletenum ; i < count-1 ; i++){
                    data[i] = data[i+1];
                }

                count--;

            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Activity가 종료되기 전에 저장한다.
        //SharedPreferences를 sFile이름, 기본모드로 설정
        SharedPreferences sharedPreferences = getSharedPreferences("sFile",MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();

        if(count!= 0) {
            for (int i = 0; i < count; i++) {
                editor.putString("data" + i, data[i]);
            }

            editor.putInt("count", count);

            //다양한 형태의 변수값을 저장할 수 있다.
            //editor.putString();
            //editor.putBoolean();
            //editor.putFloat();
            //editor.putLong();
            //editor.putInt();
            //editor.putStringSet();

            //최종 커밋
            editor.commit();
        }


    }

}
