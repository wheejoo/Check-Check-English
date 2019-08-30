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
