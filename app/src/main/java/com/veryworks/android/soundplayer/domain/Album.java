package com.veryworks.android.soundplayer.domain;

import android.net.Uri;

/**
 * Created by Gold on 2017. 2. 28..
 */

public class Album extends Common{

    public int id;
    public String artist;
    public String artist_key;
    public int album_id;
    public int album;
    public Uri album_image_uri;
    public int number_of_tracks;

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getArtist() {
        return null;
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public String getDurationText() {
        return null;
    }

    @Override
    public Uri getImageUri() {
        return null;
    }
}
