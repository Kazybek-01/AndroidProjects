package com.example.androidvectors;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Vectors - это адаптивные картинки или же иконки  (ic_launcher xml - это и есть vector)
    //Asset - активы
    //ImageAsset -> mipmap->ic_launcher (png) (5 размеров)

    //imageAsset - тратит память
    //Vector - тратит процессор

    MediaPlayer player;
    AudioManager audioManager;
    TextView start,end;
    ImageButton imageButton,prev,next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        end = findViewById(R.id.end);
        imageButton = findViewById(R.id.imageButton5);
        prev = findViewById(R.id.imageButton7);
        next = findViewById(R.id.imageButton8);


        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        player = MediaPlayer.create(this,R.raw.john);

        final SeekBar seekBar = findViewById(R.id.seekBar);

        seekBar.setMax(player.getDuration()); //время воспроизв

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!player.isPlaying()){
                    player.start();
                    imageButton.setImageResource(R.drawable.ic_baseline_pause_24);
                }
                else if(player.isPlaying()){
                    player.pause();
                    imageButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(player.getCurrentPosition() - player.getDuration());
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(player.getCurrentPosition() + player.getDuration());
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    player.seekTo(progress);
                }
                int minutes = progress /1000/60;
                int seconds = progress /1000%60;
                int min2 = ((player.getDuration()/1000/60) - (progress/1000/60));
                int sec2= ((player.getDuration()/1000%60) - (progress/1000%60));

                if(sec2 <= -1){
                    min2--;
                    sec2+=60;
                }

                start.setText(String.format("%02d:%02d:%02d",minutes,seconds));
                end.setText(String.format("%02d:%02d:%02d",min2,sec2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(player.getCurrentPosition()); //вызываем текущую позицию
            }
        }, 0,1000);
    }

}
