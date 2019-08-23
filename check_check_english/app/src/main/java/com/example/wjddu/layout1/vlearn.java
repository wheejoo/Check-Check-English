package com.example.wjddu.layout1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Locale;



public class vlearn extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    TextToSpeech tts;
    Button input, sambtn;
    public static Context c;
    TextView txt;


    YouTubePlayer.OnInitializedListener listener;
    String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlearn);

        c = this;

        input = findViewById(R.id.input);
        txt = findViewById(R.id.txt);
        sambtn = findViewById(R.id.sambtn);




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



        Intent intent = getIntent();
        String uId = intent.getStringExtra("value");
        url = uId.substring(uId.lastIndexOf("?")+3);

        YouTubePlayerView youtubeView = (YouTubePlayerView)findViewById(R.id.youtubeView);

        youtubeView.initialize("AIzaSyCyxRKghwzdwtpTgX0DKBCK_JLKE3_eG78", this);


        sambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (url){
                    case "2jxR4eydqKI":
                    case "APD3FQLiME0":
                    case "COlB70FGG04":
                    case "pBI2JVH8vbw" :
                        AlertDialog.Builder dialog = new AlertDialog.Builder(vlearn.this);
                        dialog.setTitle("Example - First greeting")
                                .setMessage("-  Oh My god!! I'm so happy to see you. \n" +
                                        "-  I feel like I'm gonna get along with you.")
                                .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();
                        break;


                    case "BgKh0WlLtS0":
                    case "wvtA-8yDxrU":
                    case "kRQcE30Jmew":
                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(vlearn.this);
                        dialog2.setTitle("Example - Funny")
                                .setMessage("-  Are you kidding me? \n" +
                                        "-  Is what I heard true?")
                                .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();
                        break;


                    case "72v7c8mxBQQ":
                    case "yIADPHu2QJE":
                    case "TpLXayxZ98g":
                        AlertDialog.Builder dialog3 = new AlertDialog.Builder(vlearn.this);
                        dialog3.setTitle("Example - Apologizing")
                                .setMessage("-  I'll forgive you. \n" +
                                        "-  If you're sorry to me, dance for me.")
                                .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();
                        break;


                    case "1vddtic0mpI":
                    case "rVJXGScKzPI":
                    case "lAN_3PvHEjA":
                        AlertDialog.Builder dialog4 = new AlertDialog.Builder(vlearn.this);
                        dialog4.setTitle("Example - Fight")
                                .setMessage("-  Take it easy \n" +
                                        "-  Relax. I don't want to fight you")
                                .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();
                        break;


                    case "F1PxuuUvHM4":
                    case "9wkcc0dKI4I":
                    case "4TuiTjmeZRw":
                        AlertDialog.Builder dialog5 = new AlertDialog.Builder(vlearn.this);
                        dialog5.setTitle("Example - Love Love Love")
                                .setMessage("-  I'm glad to hear that from you \n" +
                                        "-  You talk so pretty")
                                .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();
                        break;


                }
            }
        });

    }




    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(url);
    }

    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        // 초기화를 실패한 경우에 처리한다
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

                    txt.append("[나]" + result.get(0)+"\n");
                    replyAnswer(result.get(0), txt);
                    stt.destroy();

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


    private void replyAnswer(String input, TextView txt){
        try{
            if(input.equals("안녕")){
                txt.append("[병아리] 누구세요?\n");
                tts.speak("누구세요?", TextToSpeech.QUEUE_ADD, null);
            }
            else if(input.equals("너는 누구니")){
                txt.append("[병아리] 나는 짭스티라고 해.\n");
                tts.speak("나는 짭스티라고 해.", TextToSpeech.QUEUE_ADD, null);
            }
            else if(input.equals("종료")){
                finish();
            }
            else {
                txt.append("[병아리] 뭐라는거야?\n");
                tts.speak("뭐라는거야?", TextToSpeech.QUEUE_ADD, null);
            }
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }




}