package com.example.xiachanjin.test;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.*;
import android.os.Process;
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

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


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
    private MyService.MyBinder binder;
    private IMyAidlInterface aidlInterface;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.MyBinder)service;
            Log.d(TAG, "onServiceConnected " + name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceConnected " + name);
        }
    };

    private ServiceConnection aidlConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                int sum = aidlInterface.plus(1, 2);
                String str = aidlInterface.toUpperCase("abcdefg");
                Log.d(TAG, "aidl: 1+2=" + sum + " ,abcdefg=" + str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

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

    public void startFragment(View view) {
        Intent intent = new Intent(MainActivity.this, MainFragmentActivity.class);
        startActivity(intent);
    }

    public void startMyService(View view) {
        Log.d(TAG, "startService");
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    public void stopMyService(View view) {
        Log.d(TAG, "stopService");
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    public void bindMyService(View view) {
        Log.d(TAG, "bindService");
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, aidlConnection, BIND_AUTO_CREATE);
    }

    public void unbindMyService(View view) {
        //binder.onMyBinderExec();
        Log.d(TAG, "unbindService");
        unbindService(aidlConnection);
    }

    public void openActionBar(View view) {
        Log.d(TAG, "openActionBar");
        Intent intent = new Intent(this, MyActionBarActivity.class);
        startActivity(intent);
    }

    public class Foo {
        public int id;
        public String body;
        public float number;
        public String created_at;
    }

    public void gsonTest() {
        String str = "{\n" +
                "    \"id\": 100,\n" +
                "    \"body\": \"It is my post\",\n" +
                "    \"number\": 0.13,\n" +
                "    \"created_at\": \"2014-05-22 19:12:38\"\n" +
                "}";
        Foo foo = new Gson().fromJson(str, Foo.class);
        Log.d(TAG, "foo.id=" + foo.id + " ,body=" + foo.body + " , number=" + foo.number
                + " ,created at" + foo.created_at);

        String str2 = "[{\n" +
                "    \"id\": 100,\n" +
                "    \"body\": \"It is my post1\",\n" +
                "    \"number\": 0.13,\n" +
                "    \"created_at\": \"2014-05-20 19:12:38\"\n" +
                "},\n" +
                "{\n" +
                "    \"id\": 101,\n" +
                "    \"body\": \"It is my post2\",\n" +
                "    \"number\": 0.14,\n" +
                "    \"created_at\": \"2014-05-22 19:12:38\"\n" +
                "}]";
        Foo[] foos = new Gson().fromJson(str2, Foo[].class);
    }

    private void threadTest() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onResume: thread id=" + Thread.currentThread().getId() + "; process id="
                        + android.os.Process.myPid());
            }
        };

        Thread thread = new Thread(runnable, "xiacj");
        thread.start();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                super.run();
                Log.d(TAG, "onResume: thread1 id=" + Thread.currentThread().getId() + "; process id="
                        + android.os.Process.myPid());

            }
        };
        thread1.start();
    }
    public class WorkerThread implements Runnable {
        private String command;

        public WorkerThread(String s){
            this.command=s;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
            processCommand();
            System.out.println(Thread.currentThread().getName()+" End.");
        }

        private void processCommand() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString(){
            return this.command;
        }
    }

    class MyRunnable implements Runnable {
        private ThreadPoolExecutor mExecutor;
        private int mSeconds;
        private boolean mRun = true;

        public  MyRunnable(ThreadPoolExecutor executor, int delay) {
            mExecutor = executor;
            mSeconds = delay;
        }

        public  void exitRun() {
            mRun = false;
        }

        @Override
        public void run() {
            while(mRun) {
                System.out.println(
                        String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                                this.mExecutor.getPoolSize(),
                                this.mExecutor.getCorePoolSize(),
                                this.mExecutor.getActiveCount(),
                                this.mExecutor.getCompletedTaskCount(),
                                this.mExecutor.getTaskCount(),
                                this.mExecutor.isShutdown(),
                                this.mExecutor.isTerminated()));
                try {
                    Thread.sleep(mSeconds * 4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void threadsTest()  {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2), threadFactory );
        Runnable runnable = new MyRunnable(threadPoolExecutor, 3);
        Thread thread = new Thread(runnable);
        thread.start();
        for (int i=0; i<3; i++) {
            threadPoolExecutor.execute(new WorkerThread("cmd" + i));
        }

//        try {
//            Thread.sleep(30000);
//            threadPoolExecutor.shutdown();
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
    public void startMyIntentService(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
        startService(intent);
        startService(intent);
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

        //listView = (ListView) this.findViewById(R.id.listview);
        //initViews4();
        Log.d(TAG, "onCreate: thread id=" + Thread.currentThread().getId() + "; process id="
                + android.os.Process.myPid());
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
//        threadsTest();
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
