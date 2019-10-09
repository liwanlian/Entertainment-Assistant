package com.example.information.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.information.Adapter.ItemFragmentAdapter;
import com.example.information.Adapter.ItemMusicFragmentAdapter;
import com.example.information.R;
import com.example.information.SearchmusicActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_music extends Fragment {

    private View rootView;
    private Context context;
    private ViewPager viewPager_music;
    private TabLayout tabLayout_music;
    private ArrayList<Fragment> fragments;
    private TextView tv_search;
    //排行榜的类型分类
   // String[] musictype={"新歌榜","热歌榜","摇滚","爵士","流行歌曲","欧美","经典老歌","情歌对唱榜","影视金曲榜","网络歌曲榜"};
    String[] musictype={"新歌榜","热歌榜","摇滚","欧美","经典老歌","情歌对唱榜","影视金曲榜","网络歌曲榜"};
    //排行榜对应的类型
    int[] typemusic={1,2,11,12,16,21,22,23,24,25};
    private List<String> list_musictypes;
    private ItemMusicFragmentAdapter imAdapter;
    private boolean mIsInit = false;//数据是否加载完成
    private boolean mIsPrepared = false;//UI是否准备完成
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.layout_music, container, false);
        context=getActivity();
//        mIsPrepared = true;
//         lazyLoad();
        tv_search=(TextView)rootView.findViewById(R.id.tv_search);
        viewPager_music=(ViewPager)rootView.findViewById(R.id.music_content);
        tabLayout_music=(TabLayout)rootView.findViewById(R.id.tabLayout_music);
        tabLayout_music.setTabMode(TabLayout.MODE_SCROLLABLE);
        init();
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SearchmusicActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            lazyLoad();
//        }
//    }
//    public void lazyLoad() {
//        if (getUserVisibleHint() && mIsPrepared && !mIsInit) {
//            // 异步初始化，在初始化后显示正常UI
//            loadData();
//        }
//    }
//    public void loadData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
//    }
    private void init(){
        fragments=new ArrayList<>();
        fragments.add(new BaseMusicFragment());
        fragments.add(new BaseMusicFragment());
        fragments.add(new BaseMusicFragment());
        fragments.add(new BaseMusicFragment());
        fragments.add(new BaseMusicFragment());
        fragments.add(new BaseMusicFragment());
        fragments.add(new BaseMusicFragment());
        fragments.add(new BaseMusicFragment());
//        fragments.add(new BaseMusicFragment());
//        fragments.add(new BaseMusicFragment());

        list_musictypes=new ArrayList<String>();
        list_musictypes.add("新歌榜");
        list_musictypes.add("热歌榜");
        list_musictypes.add("摇滚");
//        list_musictypes.add("爵士");
//        list_musictypes.add("流行歌曲");
        list_musictypes.add("欧美");
        list_musictypes.add("经典老歌");
        list_musictypes.add("情歌对唱榜");
        list_musictypes.add("影视金曲榜");
        list_musictypes.add("网络歌曲榜");

        imAdapter = new ItemMusicFragmentAdapter(getChildFragmentManager(), musictype, fragments, context,list_musictypes);
        viewPager_music.setAdapter(imAdapter);
        tabLayout_music.setupWithViewPager(viewPager_music);
    }
}
