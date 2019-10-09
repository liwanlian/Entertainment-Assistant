package com.example.information.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ItemFragmentAdapter extends FragmentPagerAdapter {
    private String[] names;
    private List<Fragment> fragments;
    private Context context;
    private List<String> list_newstypes;
    public ItemFragmentAdapter(FragmentManager fm, String[] names, List<Fragment> fragments, Context context,List<String> lvn) {
        super(fm);
        this.names = names;
        this.fragments = fragments;
        this.context = context;
        this.list_newstypes=lvn;
    }
    @Override
    public Fragment getItem(int i) {
        Fragment fragment=fragments.get(i);
                Bundle bundle = new Bundle();
                bundle.putString("name","top");
                if (list_newstypes.get(i).equals("头条")){
                    bundle.putString("name","top");

                }else if (list_newstypes.get(i).equals("社会")){
                    bundle.putString("name","shehui");
                }else if (list_newstypes.get(i).equals("国内")){
                    bundle.putString("name","guonei");
                }else if (list_newstypes.get(i).equals("国际")){
                    bundle.putString("name","guoji");
                }else if (list_newstypes.get(i).equals("娱乐")){
                    bundle.putString("name","yule");
                }else if (list_newstypes.get(i).equals("体育")){
                    bundle.putString("name","tiyu");
                }else if (list_newstypes.get(i).equals("军事")){
                    bundle.putString("name","junshi");
                }else if (list_newstypes.get(i).equals("科技")){
                    bundle.putString("name","keji");
                }else if (list_newstypes.get(i).equals("财经")){
                    bundle.putString("name","caijing");
                }else if (list_newstypes.get(i).equals("时尚")){
                    bundle.putString("name","shishang");
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
        return names[position];
    }
}
