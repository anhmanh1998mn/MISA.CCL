package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.OrderDetail;
import vn.com.misa.ccl.entity.Setting;

/**
 * ‐ Mục đích Class thực hiện việc quy định dữ liệu và cách hiển thị lên view
 * <p>
 * ‐ {@link vn.com.misa.ccl.view.manage.ActivityRestaurantManage}
 * ‐ {@link Setting}
 * <p>
 * ‐ @created_by cvmanh on 01/25/2021
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {

    private Activity mContext;

    private int mLayout;

    private List<Setting> mListSetting;

    private IOnCLickViewListener mIOnCLickViewListener;

    public IOnCLickViewListener getmIOnCLickViewListener() {
        return mIOnCLickViewListener;
    }

    public void setmIOnCLickViewListener(IOnCLickViewListener mIOnCLickViewListener) {
        this.mIOnCLickViewListener = mIOnCLickViewListener;
    }

    public SettingAdapter(Activity mContext, int mLayout, List<Setting> mListSetting) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListSetting = mListSetting;
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
            if (position == 3 || position == 6) { //3: Item là Thiết lập, 6: Item là trợ giúp
                holder.tvSettingName.setText(mListSetting.get(position).getSettingName());
                holder.line.setVisibility(View.VISIBLE);
                holder.tvSettingName.setTextSize(13);
                holder.tvSettingName.setTextColor(mContext.getResources().getColor(R.color.grey));
                return;
            }
            Drawable drawableLeft = mContext.getResources().getDrawable(mListSetting.get(position).getIconSetting());
            holder.tvSettingName.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
            holder.tvSettingName.setText(mListSetting.get(position).getSettingName());

            holder.clItemSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIOnCLickViewListener.onCLickListener(mListSetting.get(position).getSettingName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListSetting.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSettingName;

        private View line;

        private ConstraintLayout clItemSetting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                tvSettingName = itemView.findViewById(R.id.tvSettingName);
                line = itemView.findViewById(R.id.line);
                clItemSetting=itemView.findViewById(R.id.clItemSetting);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface IOnCLickViewListener{
        public void onCLickListener(String settingName);
    }
}
