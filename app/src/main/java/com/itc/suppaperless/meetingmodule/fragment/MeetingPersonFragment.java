package com.itc.suppaperless.meetingmodule.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.meetingmodule.adapter.MeetingPersonAdapter;
import com.itc.suppaperless.meetingmodule.bean.JiaoLiuUserInfo;
import com.itc.suppaperless.meetingmodule.mvp.contract.MeetingPersonContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.MeetingPesonPresenter;

import butterknife.BindView;

/**
 * 会议名单
 */
public class MeetingPersonFragment extends BaseFragment<MeetingPesonPresenter> implements MeetingPersonContract.View {

    @BindView(R.id.rv_person)
    RecyclerView rvPerson;
    private MeetingPersonAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_meeting_person;
    }

    @Override
    public MeetingPesonPresenter createPresenter() {
        return new MeetingPesonPresenter(this);
    }

    @Override
    public void init() {
        getPresenter();
        adapter=new MeetingPersonAdapter(R.layout.item_meetingperson);
        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPerson.setLayoutManager(manager);
        rvPerson.setAdapter(adapter);

    }


    @Override
    public void getPerson(JiaoLiuUserInfo jiaoLiuUserInfo) {
        for (int i=0;i<jiaoLiuUserInfo.getLstUser().size();i++){
            if (jiaoLiuUserInfo.getLstUser().get(i).getIUserID()==0){
                jiaoLiuUserInfo.getLstUser().remove(i);
                i--;
            }
        }
        adapter.setNewData(jiaoLiuUserInfo.getLstUser());
    }
}
