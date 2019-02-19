package com.example.testapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnSeekCompleteListener {
    MediaPlayer mediaPlayer;

    SeekBar skb;

    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skb = findViewById(R.id.skb);

        Uri uri = Uri.parse("http://assets.calm.com/e7f113b62c6ed74f3c71a634f9e126c3.oga");
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this,uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    skb.setMax(195000);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Handler handler1 = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                skb.setProgress(mediaPlayer.getCurrentPosition());
                handler1.post(runnable);
            }
        };

        runnable.run();


        skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                }

                mediaPlayer.seekTo(skb.getProgress());
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        Toast.makeText(this, "aaaaaaaaaaaa", Toast.LENGTH_SHORT).show();
    }
}
