package com.example.project_002quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener
{
    TextView txt_q, txt_score, txt_time;
    RadioButton rb1, rb2, rb3, rb4;
    RadioGroup radioGroup;
    Button btn_confirm, btn_next;
    List<MD_Class> list = new ArrayList<>();
    String q1, o1, o2, o3, co;
    int id;
    int count = 0;
    int total = 0;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt_q = findViewById(R.id.txt_q);
        txt_score = findViewById(R.id.txt_score);
        txt_time = findViewById(R.id.txt_time);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_next = findViewById(R.id.btn_next);

        timer = new CountDownTimer(30000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                txt_time.setText("Time :" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish()
            {
                txt_time.setText("Time up");
            }
        }.start();

        listData();
        txt_q.setText(list.get(count).getQ1());
        rb1.setText(list.get(count).getO1());
        rb2.setText(list.get(count).getO2());
        rb3.setText(list.get(count).getO3());

        btn_confirm.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_confirm:
                int radio = radioGroup.getCheckedRadioButtonId();
                rb4 = findViewById(radio);
                timer.cancel();
                if (!rb1.isChecked() && !rb2.isChecked() && !rb3.isChecked())
                {
                    Toast.makeText(this, "tu baaz aa ja", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (list.get(count).getCo().equals(rb4.getText().toString()))
                    {
                        btn_confirm.setVisibility(View.INVISIBLE);
                        btn_next.setVisibility(View.VISIBLE);
                        total = total + 1;
                        rb4.setTextColor(getResources().getColor(R.color.colorGreen));
                        txt_q.setText("Correct");
                        txt_q.setTextColor(getResources().getColor(R.color.colorGreen));
                        txt_score.setText("Score :" + total);
                    }
                    else
                    {
                        btn_confirm.setVisibility(View.INVISIBLE);
                        btn_next.setVisibility(View.VISIBLE);
                        if (list.get(count).getCo().equals(rb1.getText().toString()))
                        {
                            rb1.setTextColor(getResources().getColor(R.color.colorGreen));
                            rb2.setTextColor(getResources().getColor(R.color.colorRed));
                            rb3.setTextColor(getResources().getColor(R.color.colorRed));
                        }
                        else if (list.get(count).getCo().equals(rb2.getText().toString()))
                        {
                            rb1.setTextColor(getResources().getColor(R.color.colorRed));
                            rb2.setTextColor(getResources().getColor(R.color.colorGreen));
                            rb3.setTextColor(getResources().getColor(R.color.colorRed));
                        }
                        else
                        {
                            rb1.setTextColor(getResources().getColor(R.color.colorRed));
                            rb2.setTextColor(getResources().getColor(R.color.colorRed));
                            rb3.setTextColor(getResources().getColor(R.color.colorGreen));
                        }
                        txt_q.setText("Incorrect");
                        txt_q.setTextColor(getResources().getColor(R.color.colorRed));
                    }
                }
                break;

            case R.id.btn_next:
                btn_confirm.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.INVISIBLE);
                radioGroup.clearCheck();
                timer = new CountDownTimer(30000, 1000)
                {
                    @Override
                    public void onTick(long millisUntilFinished)
                    {
                        txt_time.setText("Time :" + millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish()
                    {
                        txt_time.setText("Time up");
                    }
                }.start();

                count++;
                if (count < 5)
                {
                    txt_q.setText(list.get(count).getQ1());
                    rb1.setText(list.get(count).getO1());
                    rb2.setText(list.get(count).getO2());
                    rb3.setText(list.get(count).getO3());

                    txt_q.setTextColor(getResources().getColor(R.color.colorBlack));
                    rb1.setTextColor(getResources().getColor(R.color.colorBlack));
                    rb2.setTextColor(getResources().getColor(R.color.colorBlack));
                    rb3.setTextColor(getResources().getColor(R.color.colorBlack));
                }

                else if (count == 5)
                {
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    SharedPreferences sp = getSharedPreferences("mydata", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putInt("score", total);
                    ed.apply();
                    startActivity(intent);
                    finish();
                }
                else
                {

                }
                break;
        }
    }

    public void listData()
    {
        list.clear();
        DBase db = new DBase(this);
        Cursor cr = db.readData(db);
        while (cr.moveToNext())
        {
            q1 = cr.getString(cr.getColumnIndex(DBase.q1));
            o1 = cr.getString(cr.getColumnIndex(DBase.o1));
            o2 = cr.getString(cr.getColumnIndex(DBase.o2));
            o3 = cr.getString(cr.getColumnIndex(DBase.o3));
            co = cr.getString(cr.getColumnIndex(DBase.co));
            id =  cr.getInt(cr.getColumnIndex(DBase.id));

            MD_Class md = new MD_Class();
            md.setQ1(q1);
            md.setO1(o1);
            md.setO2(o2);
            md.setO3(o3);
            md.setCo(co);
            list.add(md);
        }
    }
}