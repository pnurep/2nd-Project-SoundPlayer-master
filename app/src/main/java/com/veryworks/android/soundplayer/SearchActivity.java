package com.veryworks.android.soundplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchListAdapter searchListAdapter;

    public static String title = "";
    public static String artists = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.re1);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        artists = intent.getStringExtra("artist");


        searchListAdapter = new SearchListAdapter(title, artists);

        recyclerView.setAdapter(searchListAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

    }
}
