package com.example.texttospeech;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    EditText txt;
    Button speak;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=findViewById(R.id.txt);
        speak=findViewById(R.id.speak);
        textToSpeech=new TextToSpeech(this,this);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speaknow();
            }
        });

    }

    @Override
    protected void onDestroy() {
        if(textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onInit(int status) {
        if(status==textToSpeech.SUCCESS){



            int result = textToSpeech.setLanguage(Locale.US);
            if(result==textToSpeech.LANG_NOT_SUPPORTED||result==textToSpeech.LANG_MISSING_DATA){
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                speak.setEnabled(false);
            }else{
                speak.setEnabled(true);
                speaknow();
            }

        }else{
            Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show();
        }

    }

    private void speaknow() {
        String newtext=txt.getText().toString();
        if(!newtext.isEmpty()){
            textToSpeech.speak(newtext,TextToSpeech.QUEUE_FLUSH,null);
        }
    }
}
