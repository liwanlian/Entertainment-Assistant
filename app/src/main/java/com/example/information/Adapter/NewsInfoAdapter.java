package com.example.information.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.information.Bean.Bean_news;
import com.example.information.R;
import com.example.information.WebActivity;

import java.util.List;

public class NewsInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 传入的解析后的新闻数据集合
     */
    private List<Bean_news.ResultBean.DataBean> news;
    Context context;
    //recycleview 不同的item的内容展示
    private int listitem_01=0;
    private int listitem_02=1;
    private int listitem_03=2;
    
    public NewsInfoAdapter(List<Bean_news.ResultBean.DataBean> news) {
        this.news = news;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
      //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        if (viewType==listitem_01)
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false));
        else if (viewType==listitem_02)
            return  new ViewHolderTwo(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_itemtwo, parent, false));
        else
            return  new ViewHolderThree(LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_itemthree, parent, false));
    }
    @Override
    public int getItemViewType(int position) {
        if (news.get(position).getThumbnail_pic_s() != null &&
                news.get(position).getThumbnail_pic_s02() !=null &&
                news.get(position).getThumbnail_pic_s03() !=null){
            return listitem_01;
        }else if (news.get(position).getThumbnail_pic_s() !=null &&
                news.get(position).getThumbnail_pic_s02() !=null &&  news.get(position).getThumbnail_pic_s03()==null){
            return listitem_02;
        }
        else
            return listitem_03;
      //  return super.getItemViewType(position);
    }
    
    
    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            Glide.with(context).load(news.get(position).getThumbnail_pic_s()).into(((ViewHolder)holder).image01);
            Glide.with(context).load(news.get(position).getThumbnail_pic_s02()).into(((ViewHolder)holder).image02);
            Glide.with(context).load(news.get(position).getThumbnail_pic_s03()).into(((ViewHolder)holder).image03);
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.mipmap.ic_launcher);
            ((ViewHolder)holder).title.setText(news.get(position).getTitle());
            ((ViewHolder)holder).time.setText(news.get(position).getDate());
            ((ViewHolder)holder).author_name.setText(news.get(position).getAuthor_name());

            ((ViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, WebActivity.class);
                in.putExtra("url", news.get(position).getUrl());
                 context.startActivity(in);
            }
        });
        }
        else if (holder instanceof ViewHolderTwo){
            Glide.with(context).load(news.get(position).getThumbnail_pic_s()).into(((ViewHolderTwo)holder).image01);
            Glide.with(context).load(news.get(position).getThumbnail_pic_s02()).into(((ViewHolderTwo)holder).image02);


            ((ViewHolderTwo)holder).title.setText(news.get(position).getTitle());
            ((ViewHolderTwo)holder).time.setText(news.get(position).getDate());
            ((ViewHolderTwo)holder).author_name.setText(news.get(position).getAuthor_name());
            ((ViewHolderTwo)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context, WebActivity.class);
                    in.putExtra("url", news.get(position).getUrl());
                    context.startActivity(in);
                }
            });
        }
        else {
            Glide.with(context).load(news.get(position).getThumbnail_pic_s()).into(((ViewHolderThree)holder).image01);

            ((ViewHolderThree)holder).title.setText(news.get(position).getTitle());
            ((ViewHolderThree)holder).time.setText(news.get(position).getDate());
            ((ViewHolderThree)holder).author_name.setText(news.get(position).getAuthor_name());
            ((ViewHolderThree)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context, WebActivity.class);
                    in.putExtra("url", news.get(position).getUrl());
                    context.startActivity(in);
                }
            });
        }
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
    }

    /**
     * 新闻的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return news.size();
    }

    
    static  class  ViewHolderzhong extends  RecyclerView.ViewHolder{
        private ViewHolderzhong(View itemView) {
            super(itemView);
            
        }
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,author_name,time;
        ImageView image01,image02,image03;

        private ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tv_title);
            time=(TextView)itemView.findViewById(R.id.tv_time);
           author_name=(TextView)itemView.findViewById(R.id.tv_author);
            image01=(ImageView)itemView.findViewById(R.id.image01);
           image02=(ImageView)itemView.findViewById(R.id.image02);
           image03=(ImageView)itemView.findViewById(R.id.image03);
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
    static class ViewHolderTwo extends RecyclerView.ViewHolder {
        TextView title,author_name,time;
        ImageView image01,image02;

        private ViewHolderTwo(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tv_title2);
            time=(TextView)itemView.findViewById(R.id.tv_time2);
            author_name=(TextView)itemView.findViewById(R.id.tv_author2);
            image01=(ImageView)itemView.findViewById(R.id.image012);
            image02=(ImageView)itemView.findViewById(R.id.image022);

        }

    }

    static class ViewHolderThree extends RecyclerView.ViewHolder {
        TextView title,author_name,time;
        ImageView image01;

        private ViewHolderThree(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tv_title3);
            time=(TextView)itemView.findViewById(R.id.tv_time3);
            author_name=(TextView)itemView.findViewById(R.id.tv_author3);
            image01=(ImageView)itemView.findViewById(R.id.image013);
        }

    }

}
