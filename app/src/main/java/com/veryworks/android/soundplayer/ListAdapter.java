package com.veryworks.android.soundplayer;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veryworks.android.soundplayer.domain.Common;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Position {

    private List<?> datas;
    private String flag;
    private int item_layout_id;
    private Context context;

    int mPosition = 0; //어댑터 포지션

    public ListAdapter(Context context, List<?> datas, String flag) {
        this.context = context;
        this.datas = datas;
        this.flag = flag;
        switch(flag){
            case ListFragment.TYPE_SONG:
                item_layout_id = R.layout.list_fragment_item;
                break;
            case ListFragment.TYPE_ALBUM:
                item_layout_id = R.layout.list_fragment_item_album;
                break;
            case ListFragment.TYPE_GENRE:
                item_layout_id = R.layout.list_fragment_item;
                break;
            case ListFragment.TYPE_ARTIST:
                item_layout_id = R.layout.list_fragment_item_album;
                break;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(item_layout_id, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Common common = (Common) datas.get(position);
        holder.position = position;
        mPosition = position;

        Glide.with(context)
                .load(common.getImageUri())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);

        holder.textTitle.setText(common.getTitle());
        holder.textArtist.setText(common.getArtist());

        switch (flag){
            case ListFragment.TYPE_SONG :
                holder.textDuration.setText(common.getDurationText());
                break;
            case ListFragment.TYPE_ALBUM :
                break;
            case ListFragment.TYPE_GENRE :
                break;
            case ListFragment.TYPE_ARTIST :
                break;
        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    @Override
    public int getPostion() {
        return mPosition;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public int position;
        ImageView imageView;
        TextView textTitle;
        TextView textArtist;
        TextView textDuration;
        ConstraintLayout box;

        public ViewHolder(View view) {
            super(view);

            getAdapterPosition();

            box = (ConstraintLayout) view.findViewById(R.id.list_item);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textTitle = (TextView) view.findViewById(R.id.textTitle);
            textArtist = (TextView) view.findViewById(R.id.textArtist);
            switch(flag){
                case ListFragment.TYPE_SONG:
                    textDuration = (TextView) view.findViewById(R.id.textDuration);

                    //리스너가 온바인드뷰홀더 안에 있으면 리스트를 왔다갔다 할때마다 리스너를 뉴 해주기 때문에 클래스안에 넣어두면
                    //그런것을 방지할 수 있다.
                    box.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, PlayerActivity.class);
                            intent.putExtra(ListFragment.ARG_POSITION, position);
                            intent.putExtra(ListFragment.ARG_LIST_TYPE, flag);
                            context.startActivity(intent);
                        }
                    });
                    break;
                default :
                    // nothing
                    break;
            }
        }
    }
}
