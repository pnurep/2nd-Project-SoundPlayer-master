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

    private Controller() {
        targets = new ArrayList<>();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void addObserver(ControlInterface target) {
        targets.add(target);
    }

    // 플레이어 액티비티에서 나갔다 들어오면 onDestroy() 때문에 컨트롤러의 List배열에서 빠지게 되어
    // 배열순서가 액티비티, 서비스 에서 -> 서비스, 액티비티로 바뀌기 때문에 로직이 꼬인다
    // 그걸 방지하기위해 addObserver메소드를 인덱스를 받는 메소드로 오버로딩하여
    // 액티비티가 나갔다가 다시들어올때 강제로 인덱스를 List의 0번째로 고정시켜 로직의 꼬임을 막는다.
    public void addObserver(int idx, ControlInterface target) {targets.add(idx, target);}

    public void play() {
        for (ControlInterface target : targets) {
            Log.i("컨트롤러의 스타트 플레이어", "=====================" + target);
            target.startPlayer();
        }
    }

    public void pause() {
        for (ControlInterface target : targets) {
            Log.i("Controller / pause", "========================" + targets);
            target.pausePlayer();
        }
    }

    public void next() {
        for (ControlInterface target : targets) {
            Log.i("Controller / next", "========================" + targets);
            target.nextPlayer();
        }
    }

    public void prev() {
        for (ControlInterface target : targets) {
            target.prevPlayer();
        }
    }

    public void stop() {
        for (ControlInterface target : targets) {
            target.stopPlayer();
        }
    }


    public void remove(ControlInterface target) {
        targets.remove(target);
    }
}

