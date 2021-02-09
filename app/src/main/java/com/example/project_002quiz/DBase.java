package com.example.project_002quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBase extends SQLiteOpenHelper
{
    List<MD_Class> list = new ArrayList<>();
    Context context;

    public static final String database_name = "DataBase_Name";
    public static final String table_name = "Quiz";
    public static final int version = 1;
    public static final String id = "ID";
    public static final String q1 = "Question_1";
    public static final String o1 = "Option_1";
    public static final String o2 = "Option_2";
    public static final String o3 = "Option_3";
    public static final String co = "Correct_Option";
    public static final String query = " CREATE TABLE " + table_name + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT," + q1 + " TEXT," + o1 + " TEXT, " + o2 + " TEXT, " + o3 + " TEXT, " + co + " TEXT " + ")";

    public DBase(@Nullable Context context)
    {
        super(context, database_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(query);
        quizQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public  void saveData(DBase db)
    {
        SQLiteDatabase dob = db.getWritableDatabase();
        for (int i = 0; i < list.size(); i++)
        {
            ContentValues cv = new ContentValues();
            cv.put(q1, list.get(i).getQ1());
            cv.put(o1, list.get(i).getO1());
            cv.put(o2, list.get(i).getO2());
            cv.put(o3, list.get(i).getO3());
            cv.put(co, list.get(i).getCo());
            dob.insert(table_name, null, cv);
        }
    }

    public Cursor readData(DBase db)
    {
        SQLiteDatabase dob = db.getReadableDatabase();
        String[] col = {id, q1, o1, o2, o3, co};
        Cursor cr = dob.query(false, table_name, col, null, null, null, null, null, null);
        return cr;
    }

    public void quizQuestions()
    {

//        Question1
        MD_Class md1 = new MD_Class();
        md1.setQ1("Which is the world's first Trillion $Dollar Company?");
        md1.setO1("Samsung");
        md1.setO2("Apple");
        md1.setO3("Huawei");
        md1.setCo("Apple");
        list.add(md1);

//        Question2
        MD_Class md2 = new MD_Class();
        md2.setQ1("Who is the owner of Amazon?");
        md2.setO1("Bill Gates");
        md2.setO2("Mark ZingerBurger");
        md2.setO3("Jeff Bezos");
        md2.setCo("Jeff Bezos");
        list.add(md2);

//        Question3
        MD_Class md3 = new MD_Class();
        md3.setQ1("Which one stores more data then DVD?");
        md3.setO1("Blue Ray Disk");
        md3.setO2("CD");
        md3.setO3("Red Ray Disk");
        md3.setCo("Blue Ray Disk");
        list.add(md3);

//        Question4
        MD_Class md4 = new MD_Class();
        md4.setQ1("The Operation System is a:");
        md4.setO1("Application Software");
        md4.setO2("Device Software");
        md4.setO3("System Software");
        md4.setCo("System Software");
        list.add(md4);

//        Question5
        MD_Class md5 = new MD_Class();
        md5.setQ1("'R' in RAM stands for?");
        md5.setO1("Rewrite");
        md5.setO2("Random");
        md5.setO3("Readable");
        md5.setCo("Random");
        list.add(md5);
    }
}
