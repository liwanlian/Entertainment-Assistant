package com.example.information.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.information.Bean.Bean_rankinglist;
import com.example.information.PlayMusicActivity;
import com.example.information.R;

import java.util.List;

public class MusicInfoAdapter extends RecyclerView.Adapter<MusicInfoAdapter.ViewHolder> {

    private List<Bean_rankinglist.SongList> musics;
    Context context;


    public MusicInfoAdapter(List<Bean_rankinglist.SongList> musics) {
        this.musics = musics;
    }
    @NonNull
    @Override
    public MusicInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
      //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_rankingmusic, parent, false));

    }

    
    
    @Override
    public void onBindViewHolder(@NonNull  MusicInfoAdapter.ViewHolder holder, int position) {


       // Glide.with(context).load(news.get(position).getThumbnail_pic_s()).into(holder.image01);
//        Glide.with(context).load(news.get(position).getThumbnail_pic_s()).apply(options).into(holder.image02);
//        Glide.with(context).load(news.get(position).getThumbnail_pic_s()).apply(options).into(holder.image03);

        //给每个新闻设置单击事件，点击通过给的url路径跳转到详情页面
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(Utils.getContext(), WebActivity.class);
//                in.putExtra("url", news.get(position).url);
//                Utils.getContext().startActivity(in);
//            }
//        });
        String S_titleauthor=musics.get(position).getTitle()+"---"+musics.get(position).getAuthor();
        holder.titleauthor.setText(S_titleauthor);
        holder.languare.setText(musics.get(position).getLanguage());
        holder.publishtime.setText(musics.get(position).getPublishtime());
        Glide.with(context).load(musics.get(position).getPic_s500()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.image01);

        String songsid=musics.get(position).getSong_id();
//        String songlrc=musics.get(position).getLrclink();
         int durction=musics.get(position).getFile_duration();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                Intent intent=new Intent(context, PlayMusicActivity.class);
               // intent.putExtra("songid",songsid);
                bundle.putString("songid",songsid);
                bundle.putInt("durction",durction);
                intent.putExtra("bundle",bundle);
                context.startActivity(intent);
            }
        });
    }

    /**
     * 数量
     * @return
     */
    @Override
    public int getItemCount() {
        return musics.size();
    }

    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleauthor,languare,publishtime;
        ImageView image01;

        private ViewHolder(View itemView) {
            super(itemView);
            titleauthor=(TextView)itemView.findViewById(R.id.ranking_titleauthor);
            languare=(TextView)itemView.findViewById(R.id.ranking_language);
           publishtime=(TextView)itemView.findViewById(R.id.tv_publishtime);
            image01=(ImageView)itemView.findViewById(R.id.iv_rankingmusic);
        }

//           Glide.with(context).load(news.get(position).getThumbnail_pic_s()).into(holder.image01);
//        Glide.with(context).load(news.get(position).getThumbnail_pic_s02()).into(holder.image02);
//        Glide.with(context).load(news.get(position).getThumbnail_pic_s03()).into(holder.image03);
//        RequestOptions options = new RequestOptions()
//                .placeholder(R.mipmap.ic_launcher)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .error(R.mipmap.ic_launcher);
//        holder.title.setText(news.get(position).getTitle());
//        holder.time.setText(news.get(position).getDate());
    }


}
