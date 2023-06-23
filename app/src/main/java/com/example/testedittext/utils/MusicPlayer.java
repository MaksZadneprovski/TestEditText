package com.example.testedittext.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.example.testedittext.R;

import java.io.IOException;

public class MusicPlayer {

    MediaPlayer mPlayer;
    Context context;
    private SharedPreferences sharedPreferences;
    public static final String APP_PREFERENCES = "mysettings";

    public MusicPlayer(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String music = sharedPreferences.getString("music", "Без сопровождения");
        switch (music) {
            case "Без сопровождения":
                mPlayer = null;
                break;
            case "Татарская":
                mPlayer = MediaPlayer.create(context, R.raw.musictatar);
                break;
            case "Божественная симфония Бориса":
                mPlayer = MediaPlayer.create(context, R.raw.music);
                break;
            case "Бандитская":
                mPlayer = MediaPlayer.create(context, R.raw.band);
                break;
            case "Рэп":
                mPlayer = MediaPlayer.create(context, R.raw.rap);
                break;
            case "Добрая":
                mPlayer = MediaPlayer.create(context, R.raw.kind);
                break;
            case "Бодрящая":
                mPlayer = MediaPlayer.create(context, R.raw.bodr);
                break;
        }
        if (mPlayer!=null){
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlay();
                }
            });
        }

    }

    private void stopPlay(){
        if (mPlayer!=null) {
            mPlayer.stop();
            try {
                mPlayer.prepare();
                mPlayer.seekTo(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void play(){
        if (mPlayer!=null) {
            mPlayer.start();
        }
    }
    public void pause(){
        if (mPlayer!=null) {
            mPlayer.pause();
        }
    }
    public void stop(){
        stopPlay();
    }


}
