package com.balvinder.pianoguru;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    Button playButton;
    EditText textNote;
    String text;
    String[] splitedText;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        registerListener();
    }

    //Initialize variables
    public void init() {
        player = new MediaPlayer();
        playButton = (Button) findViewById(R.id.playButton);
        textNote = (EditText) findViewById(R.id.textNote);
    }

    //register Listeners
    public void registerListener() {

        //Play button listener
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = textNote.getText().toString();
                splitedText = text.split("\\s+");

                for (int i = 0; i < splitedText.length; i++) {
                    if (splitedText[i].trim().equals(".")) {

                        // if dot (.) found, delay of 100ms
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // play a .wav file
                        AssetFileDescriptor afd = null;
                        try {
                            player.reset();
                            afd = getAssets().openFd(splitedText[i].trim().toLowerCase() + ".wav");
                            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                            player.prepare();
                            player.start();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Invalid sequence entered", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
