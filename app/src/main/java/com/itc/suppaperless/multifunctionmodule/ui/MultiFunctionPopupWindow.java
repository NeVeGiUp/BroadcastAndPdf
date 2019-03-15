package com.itc.suppaperless.multifunctionmodule.ui;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.multifunctionmodule.adapter.MultiFunctionAdapter;
import com.itc.suppaperless.multifunctionmodule.bean.MultiFunctionItemBean;
import com.itc.suppaperless.multifunctionmodule.util.MeetServerUtil;
import com.itc.suppaperless.switch_conference.event.ChangeFragmentEvent;
import com.itc.suppaperless.utils.ScreenUtil;
import com.itc.suppaperless.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Create by zhengwp on 19-2-25.
 * <p>
 * Define the view item on the popupwindow.
 * It can be used in the moudle such as 'meet server' or 'more function'.
 */
public class MultiFunctionPopupWindow extends PopupWindow {
    private final String TAG = "MultiFunctionPopwindow";
    private LinearLayout ll_root_view;
    private RelativeLayout rl_parent;
    private ImageView iv_close;
    private TextView tv_title;
    private RecyclerView recycler;

    private Context mcontext;
    private ConstraintLayout clParentLayout;
    private MSElseRequirement elseRequirement;
    /**
     * @param initType Define the operation which you choose to new this function.
     * The configure see 'Config.class'(eg:Config.meetserver).
     */
    private int initType;
    private LayoutInflater mInflater;
    private MultiFunctionAdapter adapter;
    private ArrayList<MultiFunctionItemBean> itemList;

    public MultiFunctionPopupWindow(Context context) {
        this.mcontext = context;
        mInflater = LayoutInflater.from(mcontext);
        intPopup();
    }

    private void intPopup() {
        View view = mInflater.inflate(R.layout.popupwindow_multi_function, null);
        setContentView(view);
        ll_root_view = view.findViewById(R.id.ll_root_view);
        rl_parent = view.findViewById(R.id.rl_parent);
        iv_close = view.findViewById(R.id.iv_close);
        tv_title = view.findViewById(R.id.tv_title);
        recycler = view.findViewById(R.id.recycler);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setAnimationStyle(R.style.AnimBottom);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) rl_parent.getLayoutParams();
        lp.width = (int) (ScreenUtil.getScreenWidth(mcontext) * 0.8);
        lp.height = (int) (ScreenUtil.getScreenHeight(mcontext) * 0.7);
        rl_parent.setLayoutParams(lp);

        GridLayoutManager layoutManager = new GridLayoutManager(mcontext, 5);
        recycler.setLayoutManager(layoutManager);

        adapter = new MultiFunctionAdapter(R.layout.item_multi_function);
        recycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (initType == Config.meetserver) {
                    MultiFunctionItemBean bean = ((MultiFunctionAdapter) adapter).getData().get(position);
                    /** Apply the else requirement at meeting server. */
                    if (bean.getTitle().equals(mcontext.getResources().getString(R.string.else_requirement))) {
                        if (elseRequirement == null) {
                            elseRequirement = new MSElseRequirement(mcontext);
                        }
                        MultiFunctionPopupWindow.this.dismiss();
                        elseRequirement.showAtLocation(MultiFunctionPopupWindow.this.clParentLayout, Gravity.CENTER, 0, 0);
                    } else {
                        ToastUtil.show(mcontext, mcontext.getResources().getString(R.string.meetserver_send_success), Toast.LENGTH_SHORT);
                        MeetServerUtil.seneMeetServer2PS(bean.getTitle());
                    }
                } else if (initType == Config.morefunction) {
                    //Judge click events of other functional modules according to position
                    switch (position) {
                        case 0:  //Electronic whiteboard
                            dismiss();
                            EventBus.getDefault().post(new ChangeFragmentEvent(14));
                            break;
                        case 1:  //Meeting Exchange

                            break;
                        case 2:  //Web browsing
                            dismiss();
                            EventBus.getDefault().post(new ChangeFragmentEvent(15));
                            break;
                        case 3:  //Video Services
                            dismiss();
                            EventBus.getDefault().post(new ChangeFragmentEvent(16));
                            break;
                        case 4:  //Personal Center
                            dismiss();
                            EventBus.getDefault().post(new ChangeFragmentEvent(17));
                            break;
                    }

                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ll_root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * @param initType
     */
    public void setInitType(int initType) {
        this.initType = initType;
        generateMeetServerItem(this.initType);
        adapter.setNewData(itemList);
    }

    /**
     * @param clParentLayout The parent layout from activity.
     */
    public void setParentView(ConstraintLayout clParentLayout) {
        this.clParentLayout = clParentLayout;
    }

    private void generateMeetServerItem(int type) {
        if (itemList == null) {
            itemList = new ArrayList<>();
        } else {
            itemList.clear();
        }
        switch (type) {
            case Config.meetserver:
                tv_title.setText(R.string.meeting_service);

                itemList.add(new MultiFunctionItemBean(R.mipmap.hyfw_chashui_n, mcontext.getResources().getString(R.string.tea)));
                itemList.add(new MultiFunctionItemBean(R.mipmap.hyfw_bi_n, mcontext.getResources().getString(R.string.pen)));
                itemList.add(new MultiFunctionItemBean(R.mipmap.hyfw_zhi_n, mcontext.getResources().getString(R.string.paper)));
                itemList.add(new MultiFunctionItemBean(R.mipmap.hyfw_mkf_n, mcontext.getResources().getString(R.string.microphone)));
                itemList.add(new MultiFunctionItemBean(R.mipmap.hyfw_fwry_n, mcontext.getResources().getString(R.string.service_personal)));
                itemList.add(new MultiFunctionItemBean(R.mipmap.hyfw_qt_n, mcontext.getResources().getString(R.string.else_requirement)));
                break;
            case Config.morefunction:
                tv_title.setText(R.string.more_features);

                itemList.add(new MultiFunctionItemBean(R.mipmap.but_dzbb_n, mcontext.getResources().getString(R.string.baiban_option)));
                itemList.add(new MultiFunctionItemBean(R.mipmap.but_hyjl_n, mcontext.getResources().getString(R.string.meeting_chat)));
                itemList.add(new MultiFunctionItemBean(R.mipmap.but_wlll_n, mcontext.getResources().getString(R.string.net_browse)));
                itemList.add(new MultiFunctionItemBean(R.mipmap.but_spfu_n, mcontext.getResources().getString(R.string.video_service)));
                itemList.add(new MultiFunctionItemBean(R.mipmap.but_grzx_n, mcontext.getResources().getString(R.string.personal_center)));
                break;
        }
    }
}
