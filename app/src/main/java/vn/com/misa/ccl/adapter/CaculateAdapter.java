package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.OrderDetail;

/**
 * ‐ Mục đích Class thực hiện việc quy định dữ liệu và cách hiển thị lên view
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.restaurantsetup.ActivityFoodUpdate}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class CaculateAdapter extends RecyclerView.Adapter<CaculateAdapter.ViewHolder> {

    private IResultClickItem mIResultClickItem;

    public IResultClickItem getmIResultClickItem() {
        return mIResultClickItem;
    }

    public void setmIResultClickItem(IResultClickItem mIResultClickItem) {
        this.mIResultClickItem = mIResultClickItem;
    }

    private Activity mContext;

    private int mLayout;

    private List<String> mListCaculate;

    public CaculateAdapter(Activity mContext, int mLayout, List<String> mListCaculate) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListCaculate = mListCaculate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvItemCaculate.setText(mListCaculate.get(position));
            if (position == mListCaculate.size() - 1) {
                holder.tvItemCaculate.setBackgroundColor(mContext.getResources().getColor(R.color.purple_500));
                holder.tvItemCaculate.setTextColor(Color.WHITE);
            }
            if (position == 3) {
                holder.ivBackKeyboard.setVisibility(View.VISIBLE);
            }

            holder.tvItemCaculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIResultClickItem != null) {
                        mIResultClickItem.resultClickItem(mListCaculate.get(position));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListCaculate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemCaculate;

        private ImageView ivBackKeyboard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                tvItemCaculate = itemView.findViewById(R.id.tvItemCaculate);
                ivBackKeyboard = itemView.findViewById(R.id.ivBackKeyboard);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface IResultClickItem {
        public void resultClickItem(String nameClick);
    }
}
