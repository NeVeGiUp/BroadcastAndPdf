package com.itc.suppaperless.switch_conference.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.base.mvp.IBaseXPresenter;
import com.itc.suppaperless.switch_conference.adapter.FeaturesOneAdapter;
import com.itc.suppaperless.switch_conference.event.ChangeFragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

public class FeaturesOneFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_features_one)
    RecyclerView rvFeaturesOne;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Integer> strings;

    public FeaturesOneFragment() {}

    public static FeaturesOneFragment newInstance(String param1, String param2) {
        FeaturesOneFragment fragment = new FeaturesOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_features_one;
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
        strings.add(R.string.jizhong_message);
        strings.add(R.string.issue_material);
        strings.add(R.string.meeting_person);
        strings.add(R.string.meeting_material);
        strings.add(R.string.meeting_voting);
        strings.add(R.string.meeting_service);
        strings.add(R.string.view_comments);
        strings.add(R.string.more_features);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        FeaturesOneAdapter featuresOneAdapter = new FeaturesOneAdapter(R.layout.layout_features_item);
        featuresOneAdapter.setNewData(strings);
        featuresOneAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new ChangeFragmentEvent(position));
            }
        });
        rvFeaturesOne.setLayoutManager(gridLayoutManager);
        rvFeaturesOne.setAdapter(featuresOneAdapter);
    }



}
