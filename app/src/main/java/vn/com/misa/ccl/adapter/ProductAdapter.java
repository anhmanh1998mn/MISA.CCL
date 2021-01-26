package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.Product;

/**
‐ Mục đích Class thực hiện quy định dữ liệu và cách thức hiển thị dữ liệu product lên view
*
‐ {@link Product}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Activity mContext;

    private int mLayout;

    private List<Product> mListProduct;

    public ProductAdapter(Activity mContext, int mLayout, List<Product> mListProduct) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListProduct = mListProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(mLayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Bitmap bitmap= BitmapFactory.decodeByteArray(mListProduct.get(position).getmProductImage().getmImage(),0,
                    mListProduct.get(position).getmProductImage().getmImage().length);
            holder.ivItemMenu.setImageBitmap(bitmap);
            holder.tvFootName.setText(mListProduct.get(position).getmProductName());
            holder.tvFootPrice.setText(String.valueOf(mListProduct.get(position).getmProductPrice()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivItemMenu;

        TextView tvFootName,tvFootPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            try {
                ivItemMenu=itemView.findViewById(R.id.ivItemMenu);
                tvFootName=itemView.findViewById(R.id.tvFootName);
                tvFootPrice=itemView.findViewById(R.id.tvFootPrice);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
