package com.example.wjddu.layout1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class vlearn extends AppCompatActivity {

    TextToSpeech tts;
    Button input;
    LinearLayout llin;
    Context c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlearn);

        c = this;

        input = findViewById(R.id.input);
        llin = findViewById(R.id.llin);

        final TextView txt = new TextView(c);
        txt.setTextSize(20);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 5);
            toast("순순히 권한을 넘기지 않으면, 음성 인식 기능을 사용할 수 없다!");
        }
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputVoice(txt);
            }
        });
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.KOREAN);
            }
        });
    }

    public void inputVoice(final TextView txt) {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
            final SpeechRecognizer stt = SpeechRecognizer.createSpeechRecognizer(this);
            stt.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    toast("음성 입력 시작...");
                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                }

                @Override
                public void onEndOfSpeech() {
                    toast("음성 입력 종료");
                }

                @Override
                public void onError(int error) {
                    toast("오류 발생 : " + error);
                }


                @SuppressLint("WrongConstant")
                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result = (ArrayList<String>) results.get(SpeechRecognizer.RESULTS_RECOGNITION);

                    //mymy(txt, stt, result);

                    ImageView img1 = new ImageView(c);
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.microphone);
                    Bitmap bMap = Bitmap.createScaledBitmap(bitmap,100,100,true);
                    //img1.setImageResource(R.drawable.microphone);
                    img1.setImageBitmap(bMap);

                    LinearLayout layout = new LinearLayout(c);
                    layout.setOrientation(0);


                    llin.addView(layout);
                    layout.addView(img1);
                    layout.addView(txt);


                    txt.setText(result.get(0)+"\n");
                    replyAnswer(result.get(0), txt);
                    stt.destroy();

                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.activity_vlearn,layout);
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
            stt.startListening(intent);
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    //simg.setImageResource(R.drawable.zicon);

    private void replyAnswer(String input, TextView txt){
        try{
            if(input.equals("안녕")){
                txt.getText();
                txt.setText("hi");
                txt.append("[짭스티] 누구세요?\n");
                tts.speak("누구세요?", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(input.equals("너는 누구니")){
                txt.append("[짭스티] 나는 짭스티라고 해.\n");
                tts.speak("나는 짭스티라고 해.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(input.equals("종료")){
                finish();
            }
            else {
                txt.append("[짭스티] 뭐라는거야?\n");
                tts.speak("뭐라는거야?", TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

//    @SuppressLint("WrongConstant")
//    public LinearLayout zzap(TextView txt,SpeechRecognizer stt,ArrayList result){
//        LinearLayout layout = new LinearLayout( c);
//        layout.setOrientation(0);
//
//        ImageView img1 = new ImageView(c);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.microphone);
//        Bitmap bMap = Bitmap.createScaledBitmap(bitmap,100,100,true);
//        //img1.setImageResource(R.drawable.microphone);
//        img1.setImageBitmap(bMap);
//
//        llin.addView(layout);
//        layout.addView(img1);
//        layout.addView(txt);
//
//        txt.setText(result.get(0)+"\n");
//        replyAnswer((String) result.get(0), txt);
//        stt.destroy();
//
//
//        return layout;
//    }

}