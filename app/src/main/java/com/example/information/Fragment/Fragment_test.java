package com.example.information.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.example.information.R;

public class Fragment_test extends Fragment {
    private View rootView;
    private boolean mIsInit = false;//数据是否加载完成
    private boolean mIsPrepared = false;//UI是否准备完成
    TextView t1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.layout_test, container, false);
        mIsPrepared = true;
        lazyLoad();
        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }
    public void lazyLoad() {
        if (getUserVisibleHint() && mIsPrepared && !mIsInit) {
            // 异步初始化，在初始化后显示正常UI
            loadData();
        }
    }
    public void loadData() {
        new Thread() {
            public void run() {
               t1=(TextView)rootView.findViewById(R.id.tv_test);
                Bundle bundle = getArguments();
                 String data = bundle.getString("name");
//                t1.setText(data);
            }
        }.start();
    }

}
