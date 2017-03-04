package com.veryworks.android.soundplayer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gold on 2017. 3. 3..
 */

    public class Controller {
        private static Controller instance = null;
        List<ControlInterface> targets;

        private Controller(){
            targets = new ArrayList<>();
        }

        public static Controller getInstance(){
            if(instance == null){
                instance = new Controller();
            }
            return instance;
        }

        public void addObserver(ControlInterface target) {
            targets.add(target);
        }

        public void play(){
            for(ControlInterface target : targets){
                Log.i("컨트롤러의 스타트 플레이어","=====================" + target);
                target.startPlayer();
            }
        }

        public void pause(){
            for(ControlInterface target : targets){
                target.pausePlayer();
            }
        }

        public void next(){
            for(ControlInterface target : targets){
                target.nextPlayer();
            }
        }

        public void prev(){
            for(ControlInterface target : targets){
                target.prevPlayer();
            }
        }

        public void stop(){
            for(ControlInterface target : targets){
                target.stopPlayer();
            }
        }


        public void remove(ControlInterface target){
            targets.remove(target);
        }
    }

