package com.veryworks.android.soundplayer.domain;

import com.veryworks.android.soundplayer.DataLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gold on 2017. 2. 28..
 */

public class Database {

    private static List<Sound> musicList = new ArrayList<>();
    private static List<Artist> artistList = new ArrayList<>();

    public static void addSound(Sound sound) {
        musicList.add(sound);
    }

    public static void addArtist(Artist artist){
        artistList.add(artist);
    }

    public static Sound getSound(int idx){
        return musicList.get(idx);
    }

    public static Artist getArtist(){
        return null;
    }

    public static int getSoundListSize(){
        return musicList.size();
    }

    public static int getArtistListSize(){
        return artistList.size();
    }


}
