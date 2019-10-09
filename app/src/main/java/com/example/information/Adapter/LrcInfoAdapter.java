package com.example.information.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.information.R;

import java.util.List;

public class LrcInfoAdapter extends RecyclerView.Adapter<LrcInfoAdapter.ViewHolder> {

    List<String> list_lrc;

    public LrcInfoAdapter( List<String> list_lrc) {
        this.list_lrc = list_lrc;


    }
    @NonNull
    @Override
    public LrcInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.music_lrcitem, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull  LrcInfoAdapter.ViewHolder holder, int position) {

                String geci=list_lrc.get(position);
                String[] lastgeci=geci.split("]");
              if (lastgeci.length>1)
                holder.tv_lrc.setText(lastgeci[1]);
              else
                  holder.tv_lrc.setText(" ");
    }

    /**
     * 数量
     * @return
     */
    @Override
    public int getItemCount() {
        return list_lrc.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_lrc;
        private ViewHolder(View itemView) {
            super(itemView);
            tv_lrc=(TextView)itemView.findViewById(R.id.tv_musiclrc);

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
