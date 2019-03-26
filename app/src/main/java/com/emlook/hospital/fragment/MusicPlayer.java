package com.emlook.hospital.fragment;

import android.content.Context;
import android.media.MediaPlayer;

import com.emlook.hospital.R;

public class MusicPlayer {

    MediaPlayer mMediaPlayer;
    Context mContext;
    static MusicPlayer obj;
    public static MusicPlayer newInstance() {
        if(obj == null){
            obj = new MusicPlayer();
        }
        return obj;
    }

    public void playMusic(Context context){
        if(mMediaPlayer == null){
            mMediaPlayer = MediaPlayer.create(context, R.raw.love_scenario);
        }
        
        if(mMediaPlayer.isPlaying() == false){
            mMediaPlayer.start();
        }
    }

    public void stopMusic(){
        if(mMediaPlayer == null){
            return;
        }

        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.stop();
        }
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
