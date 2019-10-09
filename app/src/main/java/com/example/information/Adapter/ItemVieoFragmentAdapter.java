package com.example.information.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ItemVieoFragmentAdapter extends FragmentPagerAdapter {
    private String[] videotype;//
    private List<Fragment> fragments;
    private Context context;
    private List<String> list_videotypes;
    public ItemVieoFragmentAdapter(FragmentManager fm, String[] videotype, List<Fragment> fragments, Context context, List<String> lvn) {
        super(fm);
        this.videotype = videotype;
        this.fragments = fragments;
        this.context = context;
        this.list_videotypes=lvn;
    }
    @Override
    public Fragment getItem(int i) {
        Fragment fragment=fragments.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("name","top");
                if (list_videotypes.get(i).equals("正在热映")){
                    String url="https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&count=50&client=&udid=";
                    String videotype="正在热映";
                    bundle.putString("url",url);
                    bundle.putString("videotype",videotype);
                }else if (list_videotypes.get(i).equals("即将上映")){
                    String url="https://api.douban.com/v2/movie/coming_soon?apikey=0b2bdeda43b5688921839c8ecb20399b&count=50&client=&udid=";
                    String videotype="即将上映";
                    bundle.putString("url",url);
                    bundle.putString("videotype",videotype);
                }else if (list_videotypes.get(i).equals("top100")){
                    String url="https://api.douban.com/v2/movie/top250?apikey=0b2bdeda43b5688921839c8ecb20399b&count=100&client=&udid=";
                    String videotype="top100";
                    bundle.putString("url",url);
                    bundle.putString("videotype",videotype);
                }
                else if (list_videotypes.get(i).equals("新片榜")){
                    String url="https://api.douban.com/v2/movie/new_movies?apikey=0b2bdeda43b5688921839c8ecb20399b&client=&udid=";
                    String videotype="新片榜";
                    bundle.putString("url",url);
                    bundle.putString("videotype",videotype);
                }else if (list_videotypes.get(i).equals("口碑榜")){
                    String url="https://api.douban.com/v2/movie/weekly?apikey=0b2bdeda43b5688921839c8ecb20399b&client=&udid=";
                    String videotype="口碑榜";
                    bundle.putString("url",url);
                    bundle.putString("videotype",videotype);
                }else if (list_videotypes.get(i).equals("北美榜")){
                    String url="https://api.douban.com/v2/movie/us_box?apikey=0b2bdeda43b5688921839c8ecb20399b&client=&udid=";
                    String videotype="北美榜";
                    bundle.putString("url",url);
                    bundle.putString("videotype",videotype);
                }else if (list_videotypes.get(i).equals("影视搜索")){
                    bundle.putString("tag","影视搜索");
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
        return videotype[position];
    }
}
