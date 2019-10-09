package com.example.information.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.information.Bean.Bean_searchmusic;
import com.example.information.PlayMusicActivity;
import com.example.information.R;

import java.util.List;

public class SearchmusicInfoAdapter extends RecyclerView.Adapter<SearchmusicInfoAdapter.ViewHolder> {

    List<Bean_searchmusic.SearchData> list_searchresult;
    Context context;
    public SearchmusicInfoAdapter(List<Bean_searchmusic.SearchData> list_searchresult) {
        this.list_searchresult = list_searchresult;


    }
    @NonNull
    @Override
    public SearchmusicInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        context=parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_searchmusic, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull  SearchmusicInfoAdapter.ViewHolder holder, int position) {

              holder.tv_songname.setText(list_searchresult.get(position).getSongname());
              holder.tv_artistname.setText(list_searchresult.get(position).getArtistname());
              String songid=list_searchresult.get(position).getSongid();
              holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                Intent intent=new Intent(context, PlayMusicActivity.class);
                // intent.putExtra("songid",songsid);
                bundle.putString("songid",songid);

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
        return list_searchresult.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_songname,tv_artistname;
        private ViewHolder(View itemView) {
            super(itemView);
            tv_songname=(TextView)itemView.findViewById(R.id.searchmusic_songname);
            tv_artistname=(TextView)itemView.findViewById(R.id.searchmusic_artistname);
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
