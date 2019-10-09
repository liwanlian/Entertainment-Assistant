package com.example.information.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ItemMusicFragmentAdapter extends FragmentPagerAdapter {
    private String[] musictype;
    private List<Fragment> fragments;
    private Context context;
    private List<String> list_musictypes;
    public ItemMusicFragmentAdapter(FragmentManager fm, String[] musictype, List<Fragment> fragments, Context context, List<String> lvn) {
        super(fm);
        this.musictype = musictype;
        this.fragments = fragments;
        this.context = context;
        this.list_musictypes=lvn;
    }
    @Override
    public Fragment getItem(int i) {
        Fragment fragment=fragments.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("name","1");
                if (list_musictypes.get(i).equals("新歌榜")){
                    bundle.putString("name","1");//1

                }else if (list_musictypes.get(i).equals("热歌榜")){
                    bundle.putString("name","2");
                }else if (list_musictypes.get(i).equals("摇滚")){
                    bundle.putString("name","11");
                }
//                else if (list_musictypes.get(i).equals("爵士")){
//                    bundle.putString("name","17");//
//                }else if (list_musictypes.get(i).equals("流行歌曲")){
//                    bundle.putString("name","16");//16
//                }
                else if (list_musictypes.get(i).equals("欧美")){
                    bundle.putString("name","21");
                }else if (list_musictypes.get(i).equals("经典老歌")){
                    bundle.putString("name","22");
                }else if (list_musictypes.get(i).equals("情歌对唱榜")){
                    bundle.putString("name","23");
                }else if (list_musictypes.get(i).equals("影视金曲榜")){
                    bundle.putString("name","24");
                }else if (list_musictypes.get(i).equals("网络歌曲榜")){
                    bundle.putString("name","25");
                }
                fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return musictype[position];
    }
}
