package com.example.wjddu.layout1;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.VideoView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class fgreet extends AppCompatActivity {
    GridView gv;
    String[] uId ={"https://www.youtube.com/watch?v=pBI2JVH8vbw",
            "https://www.youtube.com/watch?v=2jxR4eydqKI",
            "https://www.youtube.com/watch?v=APD3FQLiME0",
            "https://www.youtube.com/watch?v=COlB70FGG04"};
    URL[] url = new URL[uId.length];
    Bitmap[] bitmap = new Bitmap[uId.length];

    String movieName = "";

    long now = System.currentTimeMillis();
    Date date = new Date(now);

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun);

        gv = findViewById(R.id.gv);

        firebaseAuth = FirebaseAuth.getInstance();

        Thread mThread = new Thread(){
            @Override
            public void run() {
                for(int i=0;i<uId.length;i++){
                    String id = uId[i].substring(uId[i].lastIndexOf("?")+3);  //맨마지막 '/'뒤에 id가있으므로 그것만 파싱해줌
                    Log.d("파싱한 아이디id 값", id);
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
        MyGridAdapter adater = new MyGridAdapter(this);

        gv.setAdapter(adater);
        setTitle("First greeting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    public class UserData {
        private String userEmail;
        private String date;
        private String moviename;


        public UserData(String userEmail, String date, String moviename) {

            this.userEmail = userEmail;
            this.date  = date;
            this.moviename = moviename;

        }

        public String getUserEmail() {

            return userEmail;

        }

        public void setUserEmail(String userEmail) {

            this.userEmail = userEmail;

        }


        public String getDate() {

            return date;

        }


        public void setDate(String date) {

            this.date = date;

        }


        public String getMoviename() {

            return moviename;

        }


        public void setMoviename(String moviename) {

            this.moviename = moviename;

        }

    }


    public class MyGridAdapter extends BaseAdapter{
        Context context;

        MyGridAdapter(Context c){context = c;};

        public int getCount(){return uId.length;}

        @Override
        public Object getItem(int i) {return null;}

        @Override
        public long getItemId(int i) {return 0;}

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ImageView iView = new ImageView(context);
            iView.setPadding(5,150,5,150);
            iView.setImageBitmap(bitmap[position]);

            final int pos = position;
            iView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(pos == 0)
                    {
                        movieName = "Frozen";
                    }
                    else if(pos == 1)
                    {
                        movieName = "About Time";
                    }
                    else if(pos == 2)
                    {
                        movieName = "Toy Story";
                    }
                    else if(pos == 3)
                    {
                        movieName = "Monsters, Inc";
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String getTime = sdf.format(date);

                    FirebaseUser user = firebaseAuth.getCurrentUser();


                    String email = user.getEmail();

                    String mname = movieName;

                    UserData userdata = new UserData(email, getTime, mname);

                    databaseReference.child("users").push().setValue(userdata);




                    Intent intent = new Intent(getApplicationContext(),vlearn.class);
                    intent.putExtra("value",uId[pos]);
                    startActivity(intent);

                }
            });
            return iView;
        }
    }
}
