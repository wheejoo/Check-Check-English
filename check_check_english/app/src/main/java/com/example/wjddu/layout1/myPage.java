package com.example.wjddu.layout1;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class myPage extends AppCompatActivity {

    TextView user;
    GridView gv1;
    GridView gv2;

    String[] date = {"2019-08-27","2019-28-27","2019-08-28"};  //DB에서 받아올 동영상의 시청 날짜
    String[] uId ={"https://www.youtube.com/watch?v=72v7c8mxBQQ",
            "https://www.youtube.com/watch?v=yIADPHu2QJE",
            "https://www.youtube.com/watch?v=TpLXayxZ98g"};  //DB에서 받아올 동영상의 주소
    Bitmap[] bitmap = new Bitmap[uId.length];
    URL[] url = new URL[uId.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        user = findViewById(R.id.user);
        gv1 = findViewById(R.id.gv1);
        //gv2 = findViewById(R.id.gv2);

        Thread mThread = new Thread(){
            @Override
            public void run() {
                for(int i=0;i<uId.length;i++){
                    String id = uId[i].substring(uId[i].lastIndexOf("?")+3);  //맨마지막 '/'뒤에 id가있으므로 그것만 파싱해줌
                    String uId = "https://img.youtube.com/vi/"+ id+ "/" + "sddefault.jpg";  //유튜브 썸네일 불러오는 방법

                    try {
                        url[i] = new URL(uId);

                        HttpURLConnection conn = (HttpURLConnection) url[i].openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bitmap[i] = BitmapFactory.decodeStream(is);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        mThread.start();

        try{
            mThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTitle("My Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Intent intent = getIntent();
//        String userEmail = intent.getStringExtra("email");
//        String userNmae = userEmail.substring(0,userEmail.indexOf("@"));
//        user.setText(userNmae + "님의 시청기록");

//        MyImageGridAdapter iAdater = new MyImageGridAdapter(this);
//        MyTextGridAdapter tAdater = new MyTextGridAdapter(this);
//
//        gv1.setAdapter(iAdater);
//        gv2.setAdapter(tAdater);

        MyGridAdapter adapter = new  MyGridAdapter(this);
        gv1.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

//    public class MyImageGridAdapter extends BaseAdapter {
//        Context context;
//
//        MyImageGridAdapter(Context c){context = c;};
//
//        public int getCount(){return uId.length;}
//
//        @Override
//        public Object getItem(int i) {return null;}
//
//        @Override
//        public long getItemId(int i) {return 0;}
//
//        @Override
//        public View getView(int position, View view, ViewGroup viewGroup) {
//            ImageView iView = new ImageView(context);
//            iView.setPadding(5,50,5,50);
//            iView.setImageBitmap(bitmap[position]);
//
//            final int pos = position;
//            iView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(),vlearn.class);
//                    intent.putExtra("value",uId[pos]);
//                    startActivity(intent);
//                }
//            });
//            return iView;
//        }
//    }
//
//    public class MyTextGridAdapter extends BaseAdapter {
//        Context context;
//
//        MyTextGridAdapter(Context c){context = c;};
//
//        public int getCount(){return uId.length;}
//
//        @Override
//        public Object getItem(int i) {return null;}
//
//        @Override
//        public long getItemId(int i) {return 0;}
//
//        @Override
//        public View getView(int position, View view, ViewGroup viewGroup) {
//            TextView tView = new TextView(context);
//            tView.setPadding(5,50,5,50);
//            tView.setText(date[position]);
//            tView.setTextSize(50);
//            tView.setTextColor(Color.BLACK);
//
//            return tView;
//        }
//    }

    public class MyGridAdapter extends BaseAdapter{
        Context context;

        MyGridAdapter(Context c){context = c;};

        public int getCount() {return uId.length; }

        @Override
        public Object getItem(int i) {return null;}

        @Override
        public long getItemId(int i) {return 0;}

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
 //           ImageView iView = new ImageView(context);
//            iView.setPadding(5,50,5,50);
//            iView.setImageBitmap(bitmap[position]);

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setLayoutParams(new LinearLayout.LayoutParams(-2,-2));

            ImageView iView = new ImageView(context);
            iView.setLayoutParams(new LinearLayout.LayoutParams(-2,-2));
            iView.setImageBitmap(bitmap[i]);
            iView.setPadding(5,5,5,5);

            TextView tView = new TextView(context);
            tView.setLayoutParams(new LinearLayout.LayoutParams(-2,-2));
            tView.setText(date[i]);
            tView.setGravity(Gravity.BOTTOM);
            tView.setTextSize(25);
            tView.setPadding(5,350,5,5);


            layout.addView(iView);
            layout.addView(tView);

            final int pos = i;
            iView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),vlearn.class);
                    intent.putExtra("value",uId[pos]);
                    startActivity(intent);
                }
            });

            return layout;
        }
    }
}
