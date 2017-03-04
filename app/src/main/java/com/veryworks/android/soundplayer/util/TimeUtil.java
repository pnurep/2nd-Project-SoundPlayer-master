package com.veryworks.android.soundplayer.util;

/**
 * Created by Gold on 2017. 3. 2..
 */

public class TimeUtil {

    //시간포맷변경 00:00
    public static String covertMiliToTime(long mili) {
        long min = mili / 1000 / 60;
        long sec = mili / 1000 % 60;
        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

}
