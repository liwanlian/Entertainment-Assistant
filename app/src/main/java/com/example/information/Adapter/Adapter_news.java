package com.example.information.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.information.Bean.Bean_news;
import com.example.information.Fragment.NewsFragment;
import com.example.information.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;


public class Adapter_news extends BaseAdapter {
    private List<Bean_news.ResultBean.DataBean> list;//访问网页 根据返回的json数据 转成相应的bean
    private Context context;
    //listview 不同的item的内容展示
    private int listitem_01=0;
    private int listitem_02=1;
    private int listitem_03=2;


    public Adapter_news(Context context, List<Bean_news.ResultBean.DataBean> list){
        this.context=context;
        this.list=list;
        //配置lmageloader类
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getViewTypeCount() {
        return 3;
    }
    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getThumbnail_pic_s() != null &&
                list.get(position).getThumbnail_pic_s02() !=null &&
                list.get(position).getThumbnail_pic_s03() !=null){
            return listitem_01;
        }else if (list.get(position).getThumbnail_pic_s() !=null &&
                list.get(position).getThumbnail_pic_s02() !=null &&  list.get(position).getThumbnail_pic_s03()==null){
            return listitem_02;
        }
        else
            return listitem_03;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        System.out.println("type1="+getItemViewType((i)));
        NewsFragment newsFragment=new NewsFragment();
        if (getItemViewType(i)==listitem_01){
            Viewholder_01 viewholder_01;
            if (view==null){
                view=View.inflate(context.getApplicationContext(), R.layout.listview_item,null);
                viewholder_01=new Viewholder_01();
                //控件初始化
                viewholder_01.title=(TextView)view.findViewById(R.id.tv_title);
                viewholder_01.time=(TextView)view.findViewById(R.id.tv_time);
                viewholder_01.author_name=(TextView)view.findViewById(R.id.tv_author);
                viewholder_01.image01=(ImageView)view.findViewById(R.id.image01);
                viewholder_01.image02=(ImageView)view.findViewById(R.id.image02);
                viewholder_01.image03=(ImageView)view.findViewById(R.id.image03);
            }
            else{
                viewholder_01=(Viewholder_01) view.getTag();
            }
            //数据渲染
            viewholder_01.title.setText(list.get(i).getTitle());
            viewholder_01.author_name.setText(list.get(i).getAuthor_name());
            viewholder_01.time.setText(list.get(i).getDate());
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.mipmap.ic_launcher);
//            GlideUtil.load(context,list.get(position).getThumbnail_pic_s(),holder.image,options);
            Glide.with(context).load(list.get(i).getThumbnail_pic_s()).apply(options).into(viewholder_01.image01);
            Glide.with(context).load(list.get(i).getThumbnail_pic_s()).apply(options).into(viewholder_01.image02);
            Glide.with(context).load(list.get(i).getThumbnail_pic_s()).apply(options).into(viewholder_01.image03);
        }
        return view;
    }

    //三种不同的viewholder
    static  class Viewholder_01{
        TextView title,author_name,time;
        ImageView image01,image02,image03;
    }
    static  class Viewholder_02{
        TextView title;
        ImageView image001,image002;
    }
    static  class Viewholder_03{
        TextView title;
        ImageView image01,image02,image03;
    }
}
