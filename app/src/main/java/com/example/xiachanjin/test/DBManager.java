package com.example.xiachanjin.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiachanjin on 15-7-15.
 */
public class DBManager {
    private MyDBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new MyDBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void add(List<Person> persons) {
        db.beginTransaction();
        try {
            for(Person p : persons) {
                db.execSQL("INSERT INTO person VALUES(null, ?, ?)", new Object[] {p.name, p.age});
            }
            db.setTransactionSuccessful();
        }  finally {
            db.endTransaction();
        }
    }

    public void deleteOldPerson(Person p) {
        db.delete("person", "age >= ?", new String[]{String.valueOf(p.age)});
    }

    public void updateAge(Person p) {
        ContentValues cv = new ContentValues();
        cv.put("age", p.age);
        db.update("person", cv, "name = ?", new String[]{p.name});
    }

    public List<Person> query() {
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Person person = new Person();
            person.name = c.getString(c.getColumnIndex("name"));
            person.age = c.getInt(c.getColumnIndex("age"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        return c;
    }

    public void closeDB() {
        db.close();
    }
}
