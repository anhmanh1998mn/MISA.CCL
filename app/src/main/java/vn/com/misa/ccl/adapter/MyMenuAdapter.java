package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.Product;
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.view.restaurantsetup.ActivityFoodUpdate;

/**
 * ‐ Mục đích Class thực hiện việc khởi tạo, quy định dữ liệu và cách hiển thị lên view
 * <p>
 * ‐ @created_by cvmanh on 01/30/2021
 */

public class MyMenuAdapter extends RecyclerView.Adapter<MyMenuAdapter.ViewHolder> {

    private Activity mContext;

    private int mLayout;

    private List<Product> mListProduct;

    public MyMenuAdapter(Activity mContext, int mLayout, List<Product> mListProduct) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListProduct = mListProduct;
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
            Bitmap bitmap = BitmapFactory.decodeByteArray(mListProduct.get(position).getmProductImage().getmImage(), 0,
                    mListProduct.get(position).getmProductImage().getmImage().length);
            holder.ivItemMenu.setImageBitmap(bitmap);
            holder.tvFootName.setText(mListProduct.get(position).getmProductName());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.tvFootPrice.setText(String.valueOf(decimalFormat.format(mListProduct.get(position).getmProductPrice())));
            holder.cvImage.getBackground().setTint(Color.parseColor(mListProduct.get(position).getmColor().getColorName()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivityFoodUpdate.class);
                    Product product = mListProduct.get(position);
                    intent.putExtra("TypeIntent", "Menu");
                    intent.putExtra("Object", product);
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivItemMenu;

        TextView tvFootName, tvFootPrice;

        CardView cvImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            try {
                ivItemMenu = itemView.findViewById(R.id.ivItemMenu);
                tvFootName = itemView.findViewById(R.id.tvFootName);
                tvFootPrice = itemView.findViewById(R.id.tvFootPrice);
                cvImage = itemView.findViewById(R.id.cvImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
