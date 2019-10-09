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
import com.example.information.Bean.Bean_ubox;
import com.example.information.Bean.Bean_video;
import com.example.information.PlayMusicActivity;
import com.example.information.PlayvideooneActivity;
import com.example.information.R;

import java.util.List;

public class VideoInfoAdapter2 extends RecyclerView.Adapter<VideoInfoAdapter2.ViewHolder> {

    private List<Bean_ubox.subjectsdatauxbox> subjectdata;
    Context context;


    public VideoInfoAdapter2(List<Bean_ubox.subjectsdatauxbox> subjectdata) {
        this.subjectdata = subjectdata;
    }
    @NonNull
    @Override
    public VideoInfoAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
      //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_videoplay, parent, false));

    }



    @Override
    public void onBindViewHolder(@NonNull  VideoInfoAdapter2.ViewHolder holder, int position) {

        holder.video_title.setText(subjectdata.get(position).getSubject().getTitle()+"("+subjectdata.get(position).getSubject().getOriginal_title()+")");
        String[] pubdate=subjectdata.get(position).getSubject().getPubdates();
       holder.video_publishtime.setText("上映时间："+pubdate[0]);
        StringBuffer sb_director=new StringBuffer();
        for (int i=0;i<subjectdata.get(position).getSubject().getDirectors().size();i++){
            sb_director.append(subjectdata.get(position).getSubject().getDirectors().get(i).getName());
            if (i==subjectdata.get(position).getSubject().getDirectors().size()-1){

            }
            else{
                sb_director.append(",");
            }
        }
        String string_director=sb_director.toString();
        holder.video_daoyan.setText("导演："+string_director);

        StringBuffer sb_cast=new StringBuffer();
        for (int y=0;y<subjectdata.get(position).getSubject().getCasts().size();y++){
            sb_cast.append(subjectdata.get(position).getSubject().getCasts().get(y).getName());
            if (y==subjectdata.get(position).getSubject().getCasts().size()-1){

            }
            else{
                sb_cast.append(",");
            }
        }
        String string_cast=sb_cast.toString();
        holder.video_zhuyan.setText("主演："+string_cast);

        String pinfen=String.valueOf(subjectdata.get(position).getSubject().getRating().getAverage());
        holder.video_pingfen.setText("评分："+pinfen);
        StringBuffer sb_genere=new StringBuffer();
        String[] arr_genere=subjectdata.get(position).getSubject().getGenres();
        for (int y=0;y<arr_genere.length;y++){
            sb_genere.append(arr_genere[y]);
            if (y==arr_genere.length-1){

            }
            else{
                sb_genere.append(",");
            }
        }
        String string_genere=sb_genere.toString();
        holder.video_type.setText("类型："+string_genere);
        Glide.with(context).load(subjectdata.get(position).getSubject().getImages().getSmall()).into(holder.iv_videoavator);

        String videoid=subjectdata.get(position).getSubject().getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                Intent intent=new Intent(context, PlayvideooneActivity.class);
                bundle.putString("videoid",videoid);
                bundle.putString("photourl",subjectdata.get(position).getSubject().getImages().getSmall());
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
        return subjectdata.size();
    }

    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView video_title,video_publishtime,video_daoyan,video_zhuyan,video_pingfen,video_type;
        ImageView iv_videoavator;

        ViewHolder(View itemView) {
            super(itemView);
            video_title=(TextView)itemView.findViewById(R.id.video_title);
            video_publishtime=(TextView)itemView.findViewById(R.id.video_publishtime);
           video_daoyan=(TextView)itemView.findViewById(R.id.video_daoyan);
            video_zhuyan=(TextView) itemView.findViewById(R.id.video_zhuyan);
            video_pingfen=(TextView)itemView.findViewById(R.id.video_pingfen);
            video_type=(TextView)itemView.findViewById(R.id.video_type);
            iv_videoavator=(ImageView)itemView.findViewById(R.id.iv_videoavator);
        }
    }


}
