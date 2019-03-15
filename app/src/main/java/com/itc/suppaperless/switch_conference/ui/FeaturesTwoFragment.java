package com.itc.suppaperless.switch_conference.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.switch_conference.adapter.FeaturesTwoAdapter;
import com.itc.suppaperless.switch_conference.event.ChangeFragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FeaturesTwoFragment extends BaseFragment {

    @BindView(R.id.rv_features_two)
    RecyclerView rvFeaturesTwo;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;
    private ArrayList<Integer> strings;

    public FeaturesTwoFragment() {
    }

    public static FeaturesTwoFragment newInstance(String param1, String param2) {
        FeaturesTwoFragment fragment = new FeaturesTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_features_two;
    }

    @Override
    public IBaseXPresenter createPresenter() {
        return null;
    }

    @Override
    public void init() {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        strings = new ArrayList<>();
        strings.add(R.string.sign_in_management);
        strings.add(R.string.voting_management);
        strings.add(R.string.topic_management);
        strings.add(R.string.meeting_slogan);
        strings.add(R.string.centralized_control);
        strings.add(R.string.screen_broad);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        FeaturesTwoAdapter featuresTwoAdapter = new FeaturesTwoAdapter(R.layout.layout_features_item);
        featuresTwoAdapter.setNewData(strings);
        rvFeaturesTwo.setLayoutManager(gridLayoutManager);
        rvFeaturesTwo.setAdapter(featuresTwoAdapter);
        featuresTwoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new ChangeFragmentEvent(position+8));
            }
        });
    }

}
