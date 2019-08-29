package com.example.wjddu.layout1;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.provider.ContactsContract;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Ref;

import java.util.ArrayList;
import java.util.List;

public class myPage extends AppCompatActivity {

    TextView user;

    GridView gv1;

//    private ArrayAdapter<String> adapter;
//    List<Object> Array = new ArrayList<Object>();
//
//    List<String> list = new ArrayList<>();
//    List<String> date = new ArrayList<>();
//    List<String> urId = new ArrayList<>();
//    List<URL> url = new ArrayList<>();
//    List<Bitmap> bitmap = new ArrayList<>();

    //List<String> date = new ArrayList<String>();
    //String urId[] = null;
    //URL[] url = null;
    //Bitmap[] bitmap =null;

//    private FirebaseDatabase mDatabase;
//    private DatabaseReference mReference;


//    String[] date = {"2019-08-27","2019-28-27","2019-08-28"};  //DB에서 받아올 동영상의 시청 날짜
//    String[] uId ={"https://www.youtube.com/watch?v=72v7c8mxBQQ",
//            "https://www.youtube.com/watch?v=yIADPHu2QJE",
//            "https://www.youtube.com/watch?v=TpLXayxZ98g"};  //DB에서 받아올 동영상의 주소
//    Bitmap[] bitmap = new Bitmap[uId.length];
//    URL[] url = new URL[uId.length];


    private ListView listView;
    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    //private ChildEventListener mChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        user = findViewById(R.id.user);

        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("email");
        String userName = userEmail.substring(0, userEmail.indexOf("@"));
        user.setText(userName + "님");

        // 1. 파이어베이스 연결 - DB Connection
        mDatabase = FirebaseDatabase.getInstance();

        // 2. CRUD 작업의 기준이 되는 노드를 레퍼러느로 가져온다.
        mReference = mDatabase.getReference("users");

        // 4. 리스트뷰에 목록 세팅
       /* MyGridAdapter adapter = new  MyGridAdapter(this);
        gv1.setAdapter(adapter);
        */
        // 4. 리스트뷰에 목록 세팅
        gv1 = findViewById(R.id.gv1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        gv1.setAdapter(adapter);


//    데이터베이스 읽기 #3. ChildEventListener
        mReference.orderByChild("userEmail").equalTo(userEmail).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String msg2 = snapshot.getValue().toString();
                    //                        Array.add(msg2);
                    adapter.add(msg2);

                }
                adapter.notifyDataSetChanged();
                gv1.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
/*
        String flag = "date";

        for(int i=0;i<list.size();i++){
            if(flag == "date"){
                date.add(list.get(i));
                flag ="urId";
            }else if(flag == "urId"){
                urId.add(list.get(i));
                flag ="email";
            }else if(flag == "email"){
                flag ="date";
            }
        }


        Thread mThread = new Thread(){
            @Override
            public void run() {
                for(int i=0;i<urId.size();i++){
                    String uId = "https://img.youtube.com/vi/"+ urId.get(i) + "/" + "sddefault.jpg";  //유튜브 썸네일 불러오는 방법

                    try {
                        url.add(new URL(uId));

                        HttpURLConnection conn = (HttpURLConnection) url.get(i).openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bitmap.add(BitmapFactory.decodeStream(is));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        setTitle("My Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
//        final
        String userEmail = intent.getStringExtra("email");
        user.setText(userEmail);


        // 1. 파이어베이스 연결 - DB Connection
        mDatabase = FirebaseDatabase.getInstance();


 */

        setTitle("My Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}

//        // 2. CRUD 작업의 기준이 되는 노드를 레퍼러느로 가져온다.
//        mReference = mDatabase.getReference("users");
//
//
//        // 4. 리스트뷰에 목록 세팅
//        listView = findViewById(R.id.listviewmsg);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
//        listView.setAdapter(adapter);


//


/*
    public class MyGridAdapter extends BaseAdapter{
        Context context;


        MyGridAdapter(Context c){
            context = c;
        };

        public int getCount() {return urId.size(); }

//    데이터베이스 읽기 #3. ChildEventListener
        mReference.orderByChild("userEmail").equalTo(userEmail).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        String msg2 = snapshot.getValue().toString();
//                        Array.add(msg2);
                        adapter.add(msg2);


                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);

            }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LinearLayout layout = new LinearLayout(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layout.setLayoutParams(params);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView iView = new ImageView(context);
            iView.setLayoutParams(new LinearLayout.LayoutParams(400, 300));
            iView.setImageBitmap(bitmap.get(urId.size() - (i + 1)));
            iView.setPadding(20,5,25,5);

            TextView tView = new TextView(context);
            tView.setLayoutParams(new LinearLayout.LayoutParams(400,300));
            tView.setText(date.get(urId.size() - (i + 1)));
            tView.setTextSize(20);
            tView.setPadding(5,200,5,5);

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            final int pos = i;
            iView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),vlearn.class);
                    intent.putExtra("value",urId.get(urId.size() - (pos + 1)));
                    startActivity(intent);
                }
            });
            return layout;

            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;

        }
        return (super.onOptionsItemSelected(item));
    }

} */
