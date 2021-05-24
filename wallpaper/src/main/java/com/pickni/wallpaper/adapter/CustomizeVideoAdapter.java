package com.pickni.wallpaper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pickni.wallpaper.R;

import java.util.List;

/**
 * date        : 2021/5/20 10:32
 * author      : JerRay
 * email       : chenjietop@gmail.com
 * description :
 */
public class CustomizeVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CustomizeAdapter";
    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private static final int ITEM_TYPE_FOOTER = 2;
    private static final int HEADER_COUNT = 1;
    private static final int FOOTER_COUNT = 0;

    private List<String> mVideoPathList;

    public int getContentItemCount() {
        return mVideoPathList == null ? 0 : mVideoPathList.size();
    }

    public boolean isHeaderView(int position) {
        //noinspection ConstantConditions
        return HEADER_COUNT != 0 && position < HEADER_COUNT;
    }

    public boolean isFooterView(int position) {
        //noinspection ConstantConditions
        return FOOTER_COUNT != 0 && position >= (HEADER_COUNT + getContentItemCount());
    }

    public interface OnCustomItemClickListener {
        void onHeaderClicked();

        void onItemClicked(String videoPath);

        void onFooterClicked();
    }

    private OnCustomItemClickListener mOnCustomItemClickListener;

    public void setOnCustomItemClickListener(OnCustomItemClickListener listener) {
        mOnCustomItemClickListener = listener;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (mOnCustomItemClickListener != null) {
                    mOnCustomItemClickListener.onHeaderClicked();
                }
            });
        }
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder {
        private final ImageView coverDynamicImage;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            coverDynamicImage = itemView.findViewById(R.id.iv_dynamic_cover);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position < 0) {
                    return;
                }
                if (mOnCustomItemClickListener != null) {
                    mOnCustomItemClickListener.onItemClicked(mVideoPathList.get(getDataAdapterPosition(position)));
                }
            });
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (mOnCustomItemClickListener != null) {
                    mOnCustomItemClickListener.onFooterClicked();
                }
            });
        }
    }

    public void setData(List<String> data) {
        mVideoPathList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ITEM_TYPE_HEADER) {
            View header = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item_dynamic_header, parent, false);
            viewHolder = new HeaderViewHolder(header);
        } else if (viewType == ITEM_TYPE_FOOTER) {
            // ITEM_TYPE_FOOTER
            View footer = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item_dynamic_footer, parent, false);
            viewHolder = new FooterViewHolder(footer);
        } else {
            // ITEM_TYPE_CONTENT
            View content = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item_dynamic_content, parent, false);
            viewHolder = new ContentViewHolder(content);
        }
//        int parentWidth = parent.getMeasuredWidth();
//        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();
//        params.width = (int) (parentWidth / 3.5F);
//        viewHolder.itemView.setLayoutParams(params);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int dataPosition = getDataAdapterPosition(position);
        if (holder instanceof ContentViewHolder) {
            ContentViewHolder viewHolder = (ContentViewHolder) holder;
            Glide.with(viewHolder.coverDynamicImage)
                    .load(mVideoPathList.get(dataPosition))
                    .centerCrop()
                    .into(viewHolder.coverDynamicImage);
        }
    }

    @Override
    public int getItemCount() {
        return HEADER_COUNT + getContentItemCount() + FOOTER_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (HEADER_COUNT != 0 && position < HEADER_COUNT) {
            return ITEM_TYPE_HEADER;
        } else if (FOOTER_COUNT != 0 && position >= (HEADER_COUNT + dataItemCount)) {
            return ITEM_TYPE_FOOTER;
        } else {
            return ITEM_TYPE_CONTENT;
        }
    }

    private int getDataAdapterPosition(int position) {
        return position - HEADER_COUNT;
    }
}
