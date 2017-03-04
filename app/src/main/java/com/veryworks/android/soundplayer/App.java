package com.veryworks.android.soundplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by Gold on 2017. 2. 28..
 */

public class App {

    //서비스 플레이 액션 정의
    public static final String ACTION_PLAY = "com.veryworks.android.soundplayer.action.play";
    public static final String ACTION_PAUSE = "com.veryworks.android.soundplayer.action.pause";
    public static final String ACTION_RESTART = "com.veryworks.android.soundplayer.action.restart";
    public static final String ACTION_STOP = "com.veryworks.android.soundplayer.action.stop";


    //플레이어
    public static MediaPlayer soundPlayer;

    //음악을 제어하려면 어떤음악인지 플레이어가 알 필요가 있다.
    public static void initSound(Context context){
        Uri soundUri = null;
        soundPlayer = MediaPlayer.create(context, soundUri);
    }

    public static void playSound(){
        soundPlayer.start();

    }


    public static void pauseSound(){
        soundPlayer.pause();

    }


    public static void restartSound(){
        soundPlayer.start();

    }


    public static void stop(){
        if(soundPlayer!=null){
            soundPlayer.release();
            soundPlayer=null;
        }
    }
}
