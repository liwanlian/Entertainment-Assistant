package com.example.information.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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
//        mIsPrepared = true;
////        lazyLoad();

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
//        new Thread() {
//            public void run() {
//
//                initView();
//                initData();
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////
////                        control_funcion();
////                    }
////                }).start();
//
//            }
//        }.start();
//    }
//    //控件初始化
//    private void initView(){
//        viewPager_news=(ViewPager)rootView.findViewById(R.id.vp_content);
//        tabLayout_news=(TabLayout)rootView.findViewById(R.id.tabLayout_news);
//        fragments=new ArrayList<Fragment>();
//        fragments.add(new Fragment_test());
//        fragments.add(new Fragment_test());
//        fragments.add(new Fragment_test());
//        fragments.add(new Fragment_test());
//        fragments.add(new Fragment_test());
//        fragments.add(new Fragment_test());
//        fragments.add(new Fragment_test());
//        fragments.add(new Fragment_test());
//        fragments.add(new Fragment_test());
//        fragments.add(new Fragment_test());
//    }
//    //数据初始化
//    private void initData(){
//        //新闻界面的头部导航栏
//        list_newstypes=new ArrayList<String>();
//        list_newstypes.add("头条");
//        list_newstypes.add("社会");
//        list_newstypes.add("国内");
//        list_newstypes.add("国际");
//        list_newstypes.add("娱乐");
//        list_newstypes.add("体育");
//        list_newstypes.add("军事");
//        list_newstypes.add("科技");
//        list_newstypes.add("财经");
//        list_newstypes.add("时尚");
//
////        Fragment_test fragment_test=new Fragment_test();
////        //判断所选的标题，进行传值显示
////        Bundle bundle = new Bundle();
////        bundle.putString("name","top");
////        if (list_newstypes.get(position).equals("头条")){
////            bundle.putString("name","top");
////        }else if (list_newstypes.get(position).equals("社会")){
////            bundle.putString("name","shehui");
////        }else if (list_newstypes.get(position).equals("国内")){
////            bundle.putString("name","guonei");
////        }else if (list_newstypes.get(position).equals("国际")){
////            bundle.putString("name","guoji");
////        }else if (list_newstypes.get(position).equals("娱乐")){
////            bundle.putString("name","yule");
////        }else if (list_newstypes.get(position).equals("体育")){
////            bundle.putString("name","tiyu");
////        }else if (list_newstypes.get(position).equals("军事")){
////            bundle.putString("name","junshi");
////        }else if (list_newstypes.get(position).equals("科技")){
////            bundle.putString("name","keji");
////        }else if (list_newstypes.get(position).equals("财经")){
////            bundle.putString("name","caijing");
////        }else if (list_newstypes.get(position).equals("时尚")){
////            bundle.putString("name","shishang");
////        }
//    }
//    //控件功能
//    private void control_funcion() {
////        viewPager_news.setAdapter(new FragmentPagerAdapter(Fragment_news.this.getChildFragmentManager()) {
////            //得到当前页的标题，也就是设置当前页面显示的标题是tabLayout对应标题
////
////            @Nullable
////            @Override
////            public CharSequence getPageTitle(int position) {
////                return list_newstypes.get(position);
////            }
////            @Override
////            public Fragment getItem(int position) {
////                        Fragment_test fragment_test=new Fragment_test();
////                //判断所选的标题，进行传值显示
////                Bundle bundle = new Bundle();
////                bundle.putString("name","top");
////                if (list_newstypes.get(position).equals("头条")){
////                    bundle.putString("name","top");
////                }else if (list_newstypes.get(position).equals("社会")){
////                    bundle.putString("name","shehui");
////                }else if (list_newstypes.get(position).equals("国内")){
////                    bundle.putString("name","guonei");
////                }else if (list_newstypes.get(position).equals("国际")){
////                    bundle.putString("name","guoji");
////                }else if (list_newstypes.get(position).equals("娱乐")){
////                    bundle.putString("name","yule");
////                }else if (list_newstypes.get(position).equals("体育")){
////                    bundle.putString("name","tiyu");
////                }else if (list_newstypes.get(position).equals("军事")){
////                    bundle.putString("name","junshi");
////                }else if (list_newstypes.get(position).equals("科技")){
////                    bundle.putString("name","keji");
////                }else if (list_newstypes.get(position).equals("财经")){
////                    bundle.putString("name","caijing");
////                }else if (list_newstypes.get(position).equals("时尚")){
////                    bundle.putString("name","shishang");
////                }
////                fragment_test.setArguments(bundle);
////                return fragment_test;
////            }
////
////            @NonNull
////            @Override
////            public Object instantiateItem(@NonNull ViewGroup container, int position) {
////                Fragment_test fragment_test = (Fragment_test)  super.instantiateItem(container, position);
////
////                return fragment_test;
////            }
////
////            @Override
////            public int getItemPosition(@NonNull Object object) {
////                return FragmentPagerAdapter.POSITION_NONE;
////            }
////
////            @Override
////            public int getCount() {
////                return list_newstypes.size();
////            }
////        });
//        viewPager_news.setAdapter(new FragmentPagerAdapter(this.getChildFragmentManager()) {
//            @Override
//            public Fragment getItem(int i) {
//                return fragments.get(i);
//            }
//
//            @Override
//            public int getCount() {
//                return fragments.size();
//            }
//            @Nullable
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return newstypes[position];
//            }
//        });
        //TabLayout要与ViewPAger关联显示
      //  tabLayout_news.setupWithViewPager(viewPager_news);
 //   }
}
//com.android.builder.dexing.DexArchiveException:Failed to process