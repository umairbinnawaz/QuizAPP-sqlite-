package com.example.project_002quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    TextView txt1, txt2, txt3;
    Button btn_start;
    DBase db;
    int myscore;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        btn_start = findViewById(R.id.btn_start);
        pieChart = findViewById(R.id.pieChart);


        SharedPreferences sp = getSharedPreferences("mydata", MODE_PRIVATE);
        myscore = sp.getInt("score", 0);
        txt3.setText("Score :" + myscore);

        getEntries();
        if(myscore!=0)
        {
            pieDataSet = new PieDataSet(pieEntries, "");
            pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            pieDataSet.setSliceSpace(2f);
            pieDataSet.setValueTextColor(Color.WHITE);
            pieDataSet.setValueTextSize(10f);
            pieDataSet.setSliceSpace(5f);
        }

        btn_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
                finish();

                db = new DBase(MainActivity.this);
                db.saveData(db);
            }
        });
    }

    private void getEntries() {
        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(myscore, "Right"));
        pieEntries.add(new PieEntry(5-myscore, "Wrong"));
//        pieEntries.add(new PieEntry(6f, 2));
//        pieEntries.add(new PieEntry(8f, 3));
//        pieEntries.add(new PieEntry(7f, 4));
//        pieEntries.add(new PieEntry(3f, 5));
    }
}