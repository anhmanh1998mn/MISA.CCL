package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

    private IItemClickListener mIItemClickListener;

    public IItemClickListener getmIItemClickListener() {
        return mIItemClickListener;
    }

    public void setmIItemClickListener(IItemClickListener mIItemClickListener) {
        this.mIItemClickListener = mIItemClickListener;
    }

    private Activity mContext;

    private int mLayout;

    private List<Unit> mListUnit;

    private ConstraintLayout clUnit=null;

    private ImageView ivCheck=null;

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
        holder.clUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIItemClickListener!=null){
                    mIItemClickListener.getUnitNameItemClick(mListUnit.get(position).getmUnitName(),
                            mListUnit.get(position).getmUnitID());
                }

                if(clUnit!=null && ivCheck!=null){
                    ivCheck.setVisibility(View.GONE);
                }
                holder.ivItemUnit.setVisibility(View.VISIBLE);
                clUnit= holder.clUnit ;
                ivCheck=holder.ivItemUnit;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListUnit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUnitName;

        private ImageView ivUpdateUnitName,ivItemUnit;

        private ConstraintLayout clUnit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUnitName=itemView.findViewById(R.id.tvUnitName);
            ivUpdateUnitName=itemView.findViewById(R.id.ivUpdateUnitName);
            ivItemUnit=itemView.findViewById(R.id.ivItemUnit);
            clUnit=itemView.findViewById(R.id.clUnit);
        }
    }

    public interface IItemClickListener{
        public void getUnitNameItemClick(String unitName,int unitID);
    }
}
