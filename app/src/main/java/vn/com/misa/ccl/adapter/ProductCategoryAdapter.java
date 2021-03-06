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
import vn.com.misa.ccl.entity.ProductCategory;
import vn.com.misa.ccl.view.restaurantsetup.ActivityFoodUpdate;

/**
 * ‐ Mục đích Class thực hiện việc quy định dữ liệu và cách thức hiển thị dữ liệu ProductCategory lên view
 * <p>
 * ‐ {@link ProductCategory}
 * <p>
 * ‐ @created_by cvmanh on 01/19/2021
 */

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder> {
    private Activity mContext;

    private int mLayout;

    private List<ProductCategory> mListProductCategory;

    private String TYPE_INTENT="TypeIntent";

    private String OBJECT_INTENT="Object";

    private String TYPE_INTENT_DATA="Setup";

    public ProductCategoryAdapter(Activity mContext, int mLayout, List<ProductCategory> mListProductCategory) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListProductCategory = mListProductCategory;
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
            Bitmap bitmap = BitmapFactory.decodeByteArray(mListProductCategory.get(position).getProduct().getProductImage().getImage(), 0,
                    mListProductCategory.get(position).getProduct().getProductImage().getImage().length);
            holder.ivItemMenu.setImageBitmap(bitmap);
            holder.tvFootName.setText(mListProductCategory.get(position).getProduct().getProductName());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.tvFootPrice.setText(String.valueOf(decimalFormat.format(mListProductCategory.get(position).getProduct().getProductPrice())));
            holder.cvImage.getBackground().setTint(Color.parseColor(mListProductCategory.get(position).getProduct().getColor().getColorName()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ActivityFoodUpdate.class);
                    ProductCategory productCategory = mListProductCategory.get(position);
                    intent.putExtra(TYPE_INTENT, TYPE_INTENT_DATA);
                    intent.putExtra(OBJECT_INTENT, productCategory);
                    mContext.startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListProductCategory.size();
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
