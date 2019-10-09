package com.example.information.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.information.R;

import java.util.List;

public class Adapter_historyitem extends ArrayAdapter<String> {

    Context context;
    int resourceid;
    List<String> datas;
    public Adapter_historyitem(@NonNull Context context, int resource, @NonNull List<String> objects){
        super(context,resource,objects);
        this.context=context;
        this.resourceid=resource;
        this.datas=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        Viewholder viewholder;
//        if (convertView!=null){
//            view=convertView;
//            viewholder=(Viewholder)view.getTag();
//        }
//        else{
//
//
//        }
        view= LayoutInflater.from(context).inflate(resourceid,parent,false);//获取布局
        viewholder=new Viewholder();
        viewholder.textView=(TextView)view.findViewById(R.id.tv_lable);
        viewholder.textView.setText(datas.get(position));
        return view;
    }

    class Viewholder{
        TextView textView;
    }
}
