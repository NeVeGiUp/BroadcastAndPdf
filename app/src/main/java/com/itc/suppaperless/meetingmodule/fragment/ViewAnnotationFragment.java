package com.itc.suppaperless.meetingmodule.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.itc.suppaperless.R;
import com.itc.suppaperless.base.BaseFragment;
import com.itc.suppaperless.global.Config;
import com.itc.suppaperless.loginmodule.adapter.listener.IAdapterClickListener;
import com.itc.suppaperless.meetingmodule.adapter.CailiaoAdapter;
import com.itc.suppaperless.meetingmodule.adapter.ViewAnnotationImageAdapter;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.meetingmodule.mvp.contract.ViewAnnotaionContract;
import com.itc.suppaperless.meetingmodule.mvp.presenter.ViewAnnotaionPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by zhengwp on 19-3-7.
 */
public class ViewAnnotationFragment extends BaseFragment<ViewAnnotaionPresenter> implements ViewAnnotaionContract.annotationView {
    @BindView(R.id.rg_annotation)
    RadioGroup rgAnnotation;
    @BindView(R.id.rb_document_annotation)
    RadioButton rbDocument;
    @BindView(R.id.rb_handwriten_annotation)
    RadioButton rbHandwriten;
    @BindView(R.id.rb_electron_annotation)
    RadioButton rbElectron;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.rl_filecycler)
    RecyclerView recycler;

    private LinearLayoutManager linearManager;
    private GridLayoutManager gridManager;
    private CailiaoAdapter fileAdapter;
    private ViewAnnotationImageAdapter imageAdapter;
    private ViewAnnotaionPresenter annotaionPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_view_annotation;
    }

    @Override
    public ViewAnnotaionPresenter createPresenter() {
        return new ViewAnnotaionPresenter(this);
    }

    @Override
    public void init() {
        //初始化 P
        annotaionPresenter = (ViewAnnotaionPresenter) getPresenter();
        /** init two layout managers for recyclerview. */
        gridManager = new GridLayoutManager(mContext, 3);
        linearManager = new LinearLayoutManager(mContext);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);

        /** Init the view. */
        recycler.setLayoutManager(linearManager);
        annotaionPresenter.generateFileItem(Config.DocAnnotationType);
    }

    //////////////// click listener by using bind. ///////////////////
    @OnClick({R.id.rb_document_annotation, R.id.rb_handwriten_annotation, R.id.rb_electron_annotation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_document_annotation:
                annotaionPresenter.generateFileItem(Config.DocAnnotationType);
                break;
            case R.id.rb_handwriten_annotation:
                annotaionPresenter.generateFileItem(Config.HandwriteAnnotationType);
                break;
            case R.id.rb_electron_annotation:
                annotaionPresenter.generateFileItem(Config.ElectronAnnotationType);
                break;
            default:
                break;
        }
    }

    ////////// Override some method of view from interface. /////////
    @Override
    public void changeFileAdapter(List<CommentUploadListInfo.LstFileBean> data) {
        /** Set the data of file item. */
        nullDataCheck(data);
        if (fileAdapter == null){
            fileAdapter = new CailiaoAdapter(R.layout.item_cailiao);
        }
        recycler.setLayoutManager(linearManager);
        recycler.setAdapter(fileAdapter);
        fileAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                annotaionPresenter.clickCallback(position);
            }
        });

        fileAdapter.setNewData(data);
    }

    @Override
    public void notifyFileDataOnly(List<CommentUploadListInfo.LstFileBean> data) {
        fileAdapter.setNewData(data);
    }

    @Override
    public void changeImageAdapter(List<CommentUploadListInfo.LstFileBean> data) {
        /** Set the data of image(annotation) item. */
        nullDataCheck(data);
        if (imageAdapter == null){
            imageAdapter = new ViewAnnotationImageAdapter(getActivity(), data);
        }
        recycler.setLayoutManager(gridManager);
        recycler.setAdapter(imageAdapter);
        imageAdapter.setAdpterClick(new IAdapterClickListener() {
            @Override
            public void adapterClick(int id, int position) {
                annotaionPresenter.clickCallback(position);
            }
        });

        imageAdapter.setItemData(data);
    }

    @Override
    public void notifyImageDataOnly(List<CommentUploadListInfo.LstFileBean> data) {
        imageAdapter.setItemData(data);
    }

    ///////////////// Some private method of view. /////////////////
    private void nullDataCheck(List<CommentUploadListInfo.LstFileBean> data){
        if (data == null || data.size() == 0) {
            rlNoData.setVisibility(View.VISIBLE);
            return;
        } else {
            rlNoData.setVisibility(View.GONE);
        }
    }
}
