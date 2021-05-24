package com.pickni.wallpaper.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pickni.wallpaper.R;

import java.util.List;

/**
 * date        : 2021/5/20 10:32
 * author      : JerRay
 * email       : chenjietop@gmail.com
 * description :
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    public interface OnColorItemClickListener {
        void onItemClicked(Drawable drawable);
    }

    private OnColorItemClickListener mOnColorItemClickListener;

    public void setOnColorItemClickListener(OnColorItemClickListener listener) {
        mOnColorItemClickListener = listener;
    }

    private List<Drawable> mDrawableList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView colorSolidImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            colorSolidImage = itemView.findViewById(R.id.iv_color);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position < 0) {
                    return;
                }
                if (mOnColorItemClickListener != null) {
                    mOnColorItemClickListener.onItemClicked(mDrawableList.get(position));
                }
            });
        }
    }

    public void setData(List<Drawable> data){
        mDrawableList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item_static_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
//        int parentWidth = parent.getMeasuredWidth();
//        ViewGroup.LayoutParams params = viewHolder.itemView.getLayoutParams();
//        params.width = (int) (parentWidth / 3.5F);
//        viewHolder.itemView.setLayoutParams(params);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ViewHolder holder, int position) {
        holder.colorSolidImage.setImageDrawable(mDrawableList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDrawableList == null ? 0 : mDrawableList.size();
    }
}
