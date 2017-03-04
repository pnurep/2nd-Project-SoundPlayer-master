package com.veryworks.android.soundplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veryworks.android.soundplayer.dummy.DummyContent;
import com.veryworks.android.soundplayer.dummy.DummyContent.DummyItem;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ListFragment extends Fragment{

    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARG_LIST_TYPE = "list-type";
    public static final String ARG_POSITION = "position";

    public static final String TYPE_SONG = "SONG";
    public static final String TYPE_ARTIST = "ARTIST";
    public static final String TYPE_ALBUM = "ALBUM";
    public static final String TYPE_GENRE = "GENRE";

    private int mColumnCount = 1;
    private String mListType = "";

    public static int position; // 어댑터의 포지션

    private List<?> datas;

    RecyclerView recyclerView;
    ListAdapter listAdapter;

    public ListFragment() {

    }

    public static ListFragment newInstance(int columnCount, String flag) {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_LIST_TYPE, flag);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mListType = getArguments().getString(ARG_LIST_TYPE);

            if(TYPE_SONG.equals(mListType))
                datas = DataLoader.getSounds(getContext());
            else if(TYPE_ARTIST.equals(mListType))
                datas = DataLoader.getArtist(getContext());
            else if(TYPE_ALBUM.equals(mListType))
                datas = DataLoader.getAlbum(getContext());
            else if(TYPE_ARTIST.equals(mListType))
                datas = DataLoader.getGenre(getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //recyclerView.onScrollStateChanged();

            listAdapter = new ListAdapter(getContext(),datas,mListType);
            position = listAdapter.getPostion();
            recyclerView.setAdapter(listAdapter);

        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
