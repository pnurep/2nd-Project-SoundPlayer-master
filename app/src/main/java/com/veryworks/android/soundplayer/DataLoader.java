package com.veryworks.android.soundplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.veryworks.android.soundplayer.domain.Album;
import com.veryworks.android.soundplayer.domain.Artist;
import com.veryworks.android.soundplayer.domain.Database;
import com.veryworks.android.soundplayer.domain.Genre;
import com.veryworks.android.soundplayer.domain.Sound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gold on 2017. 2. 28..
 */

public class DataLoader {

    // datas 를 두개의 activity에서 공유하기 위해 static 형태로 변경
    private static List<Sound> soundDatas = new ArrayList<>();

    private static List<Artist> artistDatas = new ArrayList<>();

    private static List<Album> albumDatas = new ArrayList<>();

    private static List<Genre> genreDatas = new ArrayList<>();


    // static 변수인 datas 를 체크해서 널이면 load 를 실행
    public static List<Sound> getSounds(Context context){
        if(soundDatas == null || soundDatas.size() == 0){
            loadSound(context);
        }
        return soundDatas;
    }

    public static List<Artist> getArtist(Context context){
        if(artistDatas == null || artistDatas.size() == 0){
            loadArtist(context);
        }
        return artistDatas;
    }

    public static List<Album> getAlbum(Context context){
        if(albumDatas == null || albumDatas.size() == 0){
            loadAlbum(context);
        }
        return albumDatas;
    }

    public static List<Genre> getGenre(Context context) {
        if(genreDatas == null || genreDatas.size() == 0){
            loadGenre(context);
        }
        return genreDatas;
    }


    // load 함수는 get 함수를 통해서만 접근한다.
    private static void loadSound(Context context) {
        // 1. 데이터 컨테츠 URI 정의
        final Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // 2. 데이터에서 가져올 데이터 컬럼명을 String 배열에 담는다.
        //    데이터컬럼명은 Content Uri 의 패키지에 들어있다.
        final String PROJ[] = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ARTIST_KEY,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.IS_MUSIC,
                MediaStore.Audio.Media.COMPOSER,
                MediaStore.Audio.Media.YEAR,
        };

        // 1. 데이터에 접근하기위해 ContentResolver 를 불러온다.
        ContentResolver resolver = context.getContentResolver();

        // 2. Content Resolver 로 쿼리한 데이터를 Cursor 에 담는다.
        Cursor cursor = resolver.query(URI, PROJ, null, null, null);

        // 3. Cursor 에 담긴 데이터를 반복문을 돌면서 꺼낸다
        if(cursor != null) {
            while(cursor.moveToNext()) {
                Sound sound = new Sound();
                // 데이터
                sound.id           = getInt(   cursor, PROJ[0]);
                sound.album_id     = getInt(   cursor, PROJ[1]);
                sound.title        = getString(cursor, PROJ[2]);
                sound.artist_id    = getInt(   cursor, PROJ[3]);
                sound.artist       = getString(cursor, PROJ[4]);
                sound.artist_key   = getString(cursor, PROJ[5]);
                sound.duration     = getInt(   cursor, PROJ[6]);
                sound.is_music     = getString(cursor, PROJ[7]);
                sound.composer     = getString(cursor, PROJ[8]);
                sound.year         = getString(cursor, PROJ[9]);

                sound.music_uri       = getMusicUri(sound.id);
                sound.album_image_uri = getAlbumImageSimple(sound.album_id);

                soundDatas.add(sound);
                Database.addSound(sound);
            }
            // 처리 후 커서를 닫아준다
            cursor.close();
        }
    }

    // load 함수는 get 함수를 통해서만 접근한다.
    private static void loadArtist(Context context) {

        // 1. 데이터 컨테츠 URI 정의
        final Uri URI = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

        // 2. 데이터에서 가져올 데이터 컬럼명을 String 배열에 담는다.
        //    데이터컬럼명은 Content Uri 의 패키지에 들어있다.
        final String PROJ[] = {
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.ARTIST_KEY,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
        };

        // 1. 데이터에 접근하기위해 ContentResolver 를 불러온다.
        ContentResolver resolver = context.getContentResolver();

        // 2. Content Resolver 로 쿼리한 데이터를 Cursor 에 담는다.
        Cursor cursor = resolver.query(URI, PROJ, null, null, null);

        // 3. Cursor 에 담긴 데이터를 반복문을 돌면서 꺼낸다
        if(cursor != null) {
            while(cursor.moveToNext()) {
                Artist artist = new Artist();
                // 데이터
                artist.id = getInt(cursor, PROJ[0]);
                artist.artist = getString(cursor, PROJ[1]);
                artist.artist_key = getString(cursor, PROJ[2]);
                artist.number_of_albums = getInt(cursor, PROJ[3]);
                artist.number_of_tracks = getInt(cursor, PROJ[4]);

                artist.album_id = getAlbumIdByArtistId(artist.id);
                artist.album_image_uri = getAlbumUriByArtistId(artist.id);

                artistDatas.add(artist);
                Database.addArtist(artist);
            }
            // 처리 후 커서를 닫아준다
            cursor.close();
        }
    }


    private static void loadAlbum(Context context) {
//        final Uri URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
//
//        final String PROJ[] = {
//                MediaStore.Audio.Albums._ID,
//                MediaStore.Audio.Albums.ALBUM_ID,
//                MediaStore.Audio.Albums.ALBUM,
//                MediaStore.Audio.Albums.ARTIST,
//                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
//        };
//        ContentResolver resolver = context.getContentResolver();
//        Cursor cursor = resolver.query(URI, PROJ, null, null, null);
//
//        if(cursor != null){
//            while(cursor.moveToNext()){
//                Album album = new Album();
//
//                album.id = getInt(cursor, PROJ[0]);
//                album.album_id = getInt(cursor, PROJ[1]);
//                album.artist = getString(cursor, PROJ[2]);
//                album.album = getInt(cursor, PROJ[3]);
//                album.number_of_tracks = getInt(cursor, PROJ[4]);
//
//
//                album.album_image_uri = getAlbumImageSimple(album.album_id);
//                albumDatas.add(album);
//            }
//            cursor.close();
//        }
    }


    private static void loadGenre(Context context) {

    }


    public static int getAlbumIdByArtistId(int artist_id){
        for(Sound sound : soundDatas){
            if(sound.artist_id == artist_id){
                return sound.album_id;
            }
        }
        return -1;
    }

    public static Uri getAlbumUriByArtistId(int artist_id){
        for(Sound sound : soundDatas){
            if(sound.artist_id == artist_id){
                return sound.album_image_uri;
            }
        }
        return null;
    }


    private static String getGenre(){
        // MediaStore.Audio.Genres.getContentUriForAudioId();
        return "";
    }


    private static String getString(Cursor cursor, String columnName){
        int idx = cursor.getColumnIndex(columnName);
        return cursor.getString(idx);
    }

    private static int getInt(Cursor cursor, String columnName){
        int idx = cursor.getColumnIndex(columnName);
        return cursor.getInt(idx);
    }

    // 음악 id로 uri 를 가져오는 함수
    private static Uri getMusicUri(int music_id){
        Uri content_uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(content_uri, music_id+"");
    }

    // 앨범 Uri 생성
    private static Uri getAlbumImageSimple(int album_id){
        return Uri.parse("content://media/external/audio/albumart/" + album_id);
    }
}
