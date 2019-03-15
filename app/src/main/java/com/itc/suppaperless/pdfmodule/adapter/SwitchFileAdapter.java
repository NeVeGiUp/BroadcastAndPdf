package com.itc.suppaperless.pdfmodule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.barteksc.pdfviewer.PDFView;
import com.itc.suppaperless.R;
import com.itc.suppaperless.loginmodule.adapter.listener.IAdapterClickListener;
import com.itc.suppaperless.meetingmodule.bean.CommentUploadListInfo;
import com.itc.suppaperless.utils.FileOpenUtil;

import java.io.File;
import java.util.List;

import static com.github.barteksc.pdfviewer.util.FitPolicy.HEIGHT;

/**
 * Create by zhengwp on 2/26/19.
 */
public class SwitchFileAdapter extends RecyclerView.Adapter<SwitchFileAdapter.FileViewHolder> {
    private Context mConText;
    private List<CommentUploadListInfo.LstFileBean> itemData;
    private IAdapterClickListener click;

    public SwitchFileAdapter(Context mConText, List<CommentUploadListInfo.LstFileBean> itemData) {
        this.mConText = mConText;
        this.itemData = itemData;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mConText).inflate(R.layout.item_switch_file_list, parent, false);
        return new FileViewHolder(view);
    }

    public void setAdpterClick(IAdapterClickListener click) {
        this.click = click;
    }

    public void setItemData(List<CommentUploadListInfo.LstFileBean> itemData) {
        this.itemData = itemData;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final FileViewHolder holder, final int position) {
        holder.switchfile_name.setText((itemData.get(position).getStrName()));

        if ( !FileOpenUtil.getfileSystemName(itemData.get(position).getiID()).equals("") ) {
            holder.switchfile_image.fromFile(new File(FileOpenUtil.getfilePrefixPath(itemData.get(position).getiID()) + "/"
                                                + FileOpenUtil.getfileSystemName(itemData.get(position).getiID())))
                    .pages(0)
                    .enableSwipe(false) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
                    .pageSnap(false) // snap pages to screen boundaries
                    .pageFling(false) // make a fling change only a single page like ViewPager
                    .enableDoubletap(false)
                    .defaultPage(0)
                    //是否呈现pdf注解，pdf文档上的批注，颜色或者样式
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .enableAntialiasing(false) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    //动态间距适应屏幕
                    .nightMode(false) // toggle night mode
                    //宽度适配
                    .pageFitPolicy(HEIGHT)
                    .load();
        }

        holder.switchfile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.adapterClick(0, position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.adapterClick(v.getId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {
        private PDFView switchfile_image;
        private TextView switchfile_name;

        public FileViewHolder(View itemView) {
            super(itemView);
            switchfile_image =  itemView.findViewById(R.id.iv_switchfile_image);
            switchfile_name =  itemView.findViewById(R.id.tv_switchfile_name);
        }
    }
}
