package com.veryworks.android.soundplayer;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by Gold on 2017. 3. 3..
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    String title = "";
    String artist = "";


    public SearchListAdapter(String title, String artist){
        this.title = title;
        this.artist = artist;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_adapter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.position = position;
        holder.textTitle1.setText(title);
        holder.textArtist1.setText(artist);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public int position;
        ImageView imageView1;
        TextView textTitle1, textArtist1, textDuration1;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.imageView1);
            textTitle1 = (TextView) itemView.findViewById(R.id.textTitle1);
            textArtist1 = (TextView) itemView.findViewById(R.id.textArtist1);
            textDuration1 = (TextView) itemView.findViewById(R.id.textDuration1);
        }
    }
}
