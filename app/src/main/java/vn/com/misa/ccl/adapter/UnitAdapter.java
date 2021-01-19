package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.entity.ProductImage;
import vn.com.misa.ccl.entity.Unit;

/**
 ‐ Mục đích Class thực hiện việc quy định dữ liệu và cách thức hiển thị dữ liệu Unit lên view
 *
 ‐ {@link Unit}
 *
 ‐ @created_by cvmanh on 01/19/2021
 */

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {

    private Activity mContext;

    private int mLayout;

    private List<Unit> mListUnit;

    public UnitAdapter(Activity mContext, int mLayout, List<Unit> mListUnit) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListUnit = mListUnit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(mLayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvUnitName.setText(mListUnit.get(position).getmUnitName());
    }

    @Override
    public int getItemCount() {
        return mListUnit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUnitName;

        private ImageView ivUpdateUnitName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUnitName=itemView.findViewById(R.id.tvUnitName);
            ivUpdateUnitName=itemView.findViewById(R.id.ivUpdateUnitName);
        }
    }
}
