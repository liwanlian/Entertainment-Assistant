package com.example.information.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.information.Adapter.ItemFragmentAdapter;
import com.example.information.R;


import java.util.ArrayList;
import java.util.List;

public class Fragment_news extends Fragment {
    private View rootView;

    private boolean mIsInit = false;//数据是否加载完成
    private boolean mIsPrepared = false;//UI是否准备完成
    //界面控件
    private ViewPager viewPager_news;
   private TabLayout tabLayout_news;
    private List<String> list_newstypes;
    private ArrayList<Fragment> fragments;
    private ItemFragmentAdapter imAdapter;
    private Context context;
    //新闻类型分类
  final   String[] newstypes={"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.layout_news, container, false);
        context=getActivity();
        viewPager_news=(ViewPager)rootView.findViewById(R.id.vp_content);
        tabLayout_news=(TabLayout)rootView.findViewById(R.id.tabLayout_news);
        tabLayout_news.setTabMode(TabLayout.MODE_SCROLLABLE);
        init();
        return rootView;
    }
    private void init(){
        fragments=new ArrayList<>();
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());
        fragments.add(new BaseFragment());

        list_newstypes=new ArrayList<String>();
        list_newstypes.add("头条");
        list_newstypes.add("社会");
        list_newstypes.add("国内");
        list_newstypes.add("国际");
        list_newstypes.add("娱乐");
        list_newstypes.add("体育");
        list_newstypes.add("军事");
        list_newstypes.add("科技");
        list_newstypes.add("财经");
        list_newstypes.add("时尚");

        imAdapter = new ItemFragmentAdapter(getChildFragmentManager(), newstypes, fragments, context,list_newstypes);
        viewPager_news.setAdapter(imAdapter);
        tabLayout_news.setupWithViewPager(viewPager_news);
    }
}
