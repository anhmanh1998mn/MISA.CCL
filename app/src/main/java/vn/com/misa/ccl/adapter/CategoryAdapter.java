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
import vn.com.misa.ccl.entity.Category;

/**
‐ Mục đích Class thực hiện việc quy định dữ liệu và cách hiển thị dữ liệu category lên view
*
‐ {@link Category}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Activity mContext;

    private int mLayout;

    private List<Category> mListCategory;

    private Boolean mCheckVisible=false;

    private IItemClickListener mIItemClickListener;

    public IItemClickListener getmIItemClickListener() {
        return mIItemClickListener;
    }

    public void setmIItemClickListener(IItemClickListener mIItemClickListener) {
        this.mIItemClickListener = mIItemClickListener;
    }

    public CategoryAdapter(Activity mContext, int mLayout, List<Category> mListCategory) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListCategory = mListCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(mLayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCategoryName.setText(mListCategory.get(position).getmCategoryName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIItemClickListener!=null){
                    mIItemClickListener.onClickListener(mListCategory.get(position).getmCategoryID());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategoryName;

        ImageView ivTick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName=itemView.findViewById(R.id.tvCategoryName);
            ivTick=itemView.findViewById(R.id.ivTick);

        }
    }

    public interface IItemClickListener{
        public void onClickListener(int position);
    }
}
