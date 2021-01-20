package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.view.restaurantsetup.ActivityFoodUpdate;

/**
‐ Mục đích Class thực hiện việc quy định dữ liệu và cách thức hiển thị dữ liệu ProductCategory lên view
*
‐ {@link ProductCategory}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {
    private Activity mContext;

    private int mLayout;

    private List<ProductCategory> mListProductCategory;

    public ProductCategoryAdapter(Activity mContext, int mLayout, List<ProductCategory> mListProductCategory) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListProductCategory = mListProductCategory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(mLayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap bitmap= BitmapFactory.decodeByteArray(mListProductCategory.get(position).getmProduct().getmProductImage().getmImage(),0,
                mListProductCategory.get(position).getmProduct().getmProductImage().getmImage().length);
        holder.ivItemMenu.setImageBitmap(bitmap);
        holder.tvFootName.setText(mListProductCategory.get(position).getmProduct().getmProductName());
        holder.tvFootPrice.setText(String.valueOf(mListProductCategory.get(position).getmProduct().getmProductPrice()));
        holder.cvImage.getBackground().setTint(Color.parseColor(mListProductCategory.get(position).getmProduct().getmColor().getColorName()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ActivityFoodUpdate.class);
                ProductCategory productCategory=mListProductCategory.get(position);
                intent.putExtra("Object",productCategory);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListProductCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivItemMenu;

        TextView tvFootName,tvFootPrice;

        CardView cvImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItemMenu=itemView.findViewById(R.id.ivItemMenu);
            tvFootName=itemView.findViewById(R.id.tvFootName);
            tvFootPrice=itemView.findViewById(R.id.tvFootPrice);
            cvImage=itemView.findViewById(R.id.cvImage);
        }
    }
}
