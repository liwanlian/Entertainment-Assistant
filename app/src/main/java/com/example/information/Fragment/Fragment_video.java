package com.example.information.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.information.Adapter.ItemVieoFragmentAdapter;
import com.example.information.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_video extends Fragment {
    private View rootview;
    //界面控件
    private ViewPager viewPager_video;
    private TabLayout tabLayout_video;

    Context context;
    List<Fragment> fragments;
    List<String> videos_tag;
    String[] strings_video={"正在热映","即将上映","新片榜","口碑榜","北美榜","top100","影视搜索"};
    ItemVieoFragmentAdapter imAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.layout_video, container, false);
        context=getActivity();
        initView();
        initData();
        return rootview;
    }
    private void initView(){
        viewPager_video=(ViewPager)rootview.findViewById(R.id.video_content);
        tabLayout_video=(TabLayout)rootview.findViewById(R.id.tabLayout_video);
        tabLayout_video.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
    private void initData(){
        fragments=new ArrayList<>();
        fragments.add(new BasevideoFragment());
        fragments.add(new BasevideoFragment());
        fragments.add(new BasevideoFragment());
        fragments.add(new BasevideoFragment());
        fragments.add(new BasevideoFragment());
        fragments.add(new BasevideoFragment());
        fragments.add(new BasevideoFragment());

//  String[] strings_video={"正在热映","即将上映","新片榜","口碑榜","北美榜","top100","影视搜索"};
        videos_tag=new ArrayList<String>();
        videos_tag.add("正在热映");
        videos_tag.add("即将上映");
        videos_tag.add("新片榜");

        videos_tag.add("口碑榜");
        videos_tag.add("北美榜");
        videos_tag.add("top100");
        videos_tag.add("影视搜索");

        imAdapter = new ItemVieoFragmentAdapter(getChildFragmentManager(), strings_video, fragments, context,videos_tag);
        viewPager_video.setAdapter(imAdapter);
        tabLayout_video.setupWithViewPager(viewPager_video);
    }
}
