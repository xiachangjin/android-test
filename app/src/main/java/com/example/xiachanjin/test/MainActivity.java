package com.example.xiachanjin.test;

import android.app.Activity;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {
    public static final String TAG = "xiacj";
    public static final String EDITKEY = "EDIT";

    private EditText editText;
    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayList<HashMap<String, Object>> arrayList2;
    private ArrayList<HashMap<String, Object>> arrayList3;
    private DBManager dbMgr;
    private Button queryBtn;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        //outState.putString(EDITKEY, editText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
        if (savedInstanceState != null) {
            String str = savedInstanceState.getString(EDITKEY);
            Log.d(TAG, "onCreate with text " + str);
        }
    }

    private void initViews1() {
        arrayList = new ArrayList<String>();
        arrayList.add("one");
        arrayList.add("two");
        arrayList.add("three");
        arrayList.add("four");

        ListAdapter la = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(la);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "click listview line " + i);
            }
        });
    }

    private void initViews2() {
        arrayList2 = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("title", "title" + i);
            map.put("name", "name" + i);
            arrayList2.add(map);
        }

        listView = (ListView) this.findViewById(R.id.listview);
        ListAdapter la = new SimpleAdapter(this, arrayList2, android.R.layout.simple_list_item_2,
                new String[] {"title", "name"}, new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(la);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "click listview line " + i);
            }
        });
    }

    private void initViews3() {
        arrayList3 = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("title", "title" + i);
            map.put("name", "name" + i);
            map.put("image", R.drawable.icon);
            arrayList3.add(map);
        }

        listView = (ListView) this.findViewById(R.id.listview);

        ListAdapter la = new SimpleAdapter(this, arrayList3, R.layout.custom_list,
                new String[] {"title", "name","image"}, new int[] {R.id.title, R.id.name,
                 R.id.imageview});
        listView.setAdapter(la);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "click listview line " + i);
            }
        });
    }

    private void initViews4() {
        arrayList3 = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<30; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("title", "title" + i);
            map.put("name", "name" + i);
            map.put("button", "test" + i);
            map.put("image", R.drawable.icon);
            arrayList3.add(map);
        }

        listView = (ListView) this.findViewById(R.id.listview);

//        ListAdapter la = new SimpleAdapter(this, arrayList3, R.layout.custom_list,
//                new String[] {"title", "name", "button","image"}, new int[] {R.id.title, R.id.name,
//                R.id.button, R.id.imageview});

        ListAdapter la = new MyListAdapter(this, arrayList3);

        listView.setAdapter(la);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "click listview line " + i);
            }
        });
    }

    public void add(View view) {
        ArrayList<Person> persons = new ArrayList<Person>();
        Person p1= new Person("aaa", 10);
        Person p2= new Person("bbb", 20);
        Person p3= new Person("ccc", 30);
        Person p4= new Person("ddd", 40);
        Person p5= new Person("eee", 50);
        persons.add(p1);
        persons.add(p2);
        persons.add(p3);
        persons.add(p4);
        persons.add(p5);
        dbMgr.add(persons);
    }

    public void delete(View view) {
        Person p = new Person();
        p.age = 30;
        dbMgr.deleteOldPerson(p);
    }


    public  void update(View view) {
        Person p = new Person("aaa", 22);
        dbMgr.updateAge(p);
    }

    public void query() {
        List<Person> persons = dbMgr.query();
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(Person p : persons) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", p.name);
            map.put("age", p.age + " years old");
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,list,android.R.layout.simple_list_item_2,
                new String[] {"name", "age"}, new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
    }

    public void queryTheCursor() {
        Cursor c = dbMgr.queryTheCursor();
        startManagingCursor(c);
        CursorWrapper cw = new CursorWrapper(c) {
            @Override
            public String getString(int columnIndex) {
                return super.getString(columnIndex);
            }
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cw,
                new String[] {"name", "age"}, new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbMgr = new DBManager(this);
        queryBtn = (Button) this.findViewById(R.id.btn_query);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //query();
                queryTheCursor();
            }
        });
        listView = (ListView) this.findViewById(R.id.listview);
        //initViews4();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        dbMgr.closeDB();
    }
}
