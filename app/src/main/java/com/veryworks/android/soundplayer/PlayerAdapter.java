package com.veryworks.android.soundplayer;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.veryworks.android.soundplayer.domain.Sound;

import java.util.List;

/**
 * Created by Gold on 2017. 2. 2..
 */

public class PlayerAdapter extends PagerAdapter {

    List<Sound> datas;
    Context context;
    LayoutInflater inflater;

     public PlayerAdapter(List<Sound> datas, Context context){
         this.datas = datas;
         this.context = context;
         inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     }

    //데이터 총개수
    @Override
    public int getCount() {
        return datas.size();
    }


    //listview의 getView와 같은역할 = 화면 하나하나를 이녀석이 만들어 주는 것
    @Override //제너레이트로 만들어준다
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);

        View view = inflater.inflate(R.layout.player_card_item,null);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtArtist = (TextView) view.findViewById(R.id.txtArtist);

        //실제 음악 데이터 가져오기
        Sound sound = datas.get(position);
        txtTitle.setText(sound.title);
        txtArtist.setText(sound.artist);

        Glide.with(context)
                .load(sound.getImageUri())
                .placeholder(R.mipmap.ic_launcher) //이미지가 없을경우 대체이미지
                .into(imageView);

        //생성한 뷰를 컨테이너에 담아준다. 컨테이너 = 뷰페이저를 생성한 최외곽 레이아웃 개념
        container.addView(view);

        return view;
    }


    //화면에서 사라진 뷰를 메모리에서 제거하기 위한 함수
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        //이 슈퍼가 그역할을 할 걸..? // ㄴㄴ 슈퍼만 쓰면 다운됨 아래꺼 써야함
        container.removeView((View) object); //이거 굳이 안써줘도..?
    }


    //instantiateItem에서 리턴된 object가 그 View가 맞는지 확인.
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
