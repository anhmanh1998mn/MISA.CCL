package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.Product;

/**
 * Mục đích method thực hiện những công việc gì + Ngữ cảnh sử dụng khi nào
 *
 *
 * @return giải thích hàm này trả về
 *
 * @created_by cvmanh on 06/13/2021
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Activity mContext;

    private int mDemo = 2;

    private int mLayout;

    private List<Product> mListProduct;

    private int mWidthImgae = 0;

    private ITotalAmount mITotalAmount;

    final float[] total = {0};

    public ITotalAmount getmITotalAmount() {
        return mITotalAmount;
    }

    public void setmITotalAmount(ITotalAmount mITotalAmount) {
        this.mITotalAmount = mITotalAmount;
    }

    public MenuAdapter(Activity mContext, int mLayout, List<Product> mListProduct) {
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
            mWidthImgae = 155;
            holder.tvFootName.setText(mListProduct.get(position).getProductName());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.tvFootPrice.setText(decimalFormat.format(mListProduct.get(position).getProductPrice()));
            Bitmap bitmap = BitmapFactory.decodeByteArray(mListProduct.get(position).getProductImage().getImage(),
                    0, mListProduct.get(position).getProductImage().getImage().length);
            holder.ivItemMenu.setImageBitmap(bitmap);
            holder.cvImage.getBackground().setTint(Color.parseColor(mListProduct.get(position).getColor().getColorName()));
            final int[] quantity = {0};

            if (mListProduct.get(position).getQuantity() > 0) {
                holder.cvImage.getBackground().setTint(mContext.getResources().getColor(R.color.green_tick));
                holder.ivItemMenu.setImageResource(R.drawable.ic_tick);
                holder.ivItemMenu.setColorFilter(Color.WHITE);
                holder.ivItemMenu.getLayoutParams().width = 50;
                holder.tvQuantity.setVisibility(View.VISIBLE);
                holder.cvAdd.setVisibility(View.VISIBLE);
                holder.cvRemove.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.grey_button));
                quantity[0] = mListProduct.get(position).getQuantity();
                mListProduct.get(position).setQuantity(quantity[0]);
                total[0] = total[0] + (mListProduct.get(position).getQuantity() * mListProduct.get(position).getProductPrice());
                holder.tvQuantity.setText(String.valueOf(mListProduct.get(position).getQuantity()));
            }

            holder.clFoodSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Tag", "onClick: parent");
                    holder.cvImage.getBackground().setTint(mContext.getResources().getColor(R.color.green_tick));
                    holder.ivItemMenu.setImageResource(R.drawable.ic_tick);
                    holder.ivItemMenu.setColorFilter(Color.WHITE);
                    holder.ivItemMenu.getLayoutParams().width = 50;
                    quantity[0] = quantity[0] + 1;
                    mListProduct.get(position).setQuantity(quantity[0]);
                    holder.tvQuantity.setText(String.valueOf(mListProduct.get(position).getQuantity()));

                    holder.tvQuantity.setVisibility(View.VISIBLE);
                    holder.cvAdd.setVisibility(View.VISIBLE);
                    holder.cvRemove.setVisibility(View.VISIBLE);
                    holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.grey_button));
                    total[0] = total[0] + mListProduct.get(position).getProductPrice();
                    mITotalAmount.processTotalAmount(formatMoney(total[0]), total[0]);
                }
            });
            holder.cvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Tag", "onClick: add");
                    quantity[0] = quantity[0] + 1;
                    mListProduct.get(position).setQuantity(quantity[0]);
                    holder.tvQuantity.setText(String.valueOf(mListProduct.get(position).getQuantity()));
                    total[0] = total[0] + mListProduct.get(position).getProductPrice();
                    mITotalAmount.processTotalAmount(formatMoney(total[0]), total[0]);
                }
            });
            holder.cvRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Tag", "onClick: remove");
                    if (Integer.parseInt(holder.tvQuantity.getText().toString()) < 2) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(mListProduct.get(position).getProductImage().getImage(),
                                0, mListProduct.get(position).getProductImage().getImage().length);
                        holder.ivItemMenu.setImageBitmap(bitmap);
                        holder.cvImage.getBackground().setTint(Color.parseColor(mListProduct.get(position).getColor().getColorName()));
                        holder.cvAdd.setVisibility(View.GONE);
                        holder.cvRemove.setVisibility(View.GONE);
                        holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        holder.ivItemMenu.setColorFilter(null);
                        holder.tvQuantity.setVisibility(View.GONE);
//                        holder.ivItemMenu.getLayoutParams().width = mWidthImgae;
                        holder.ivItemMenu.getLayoutParams().width = mWidthImgae;
                        total[0] = total[0] - mListProduct.get(position).getProductPrice();
                        mITotalAmount.processTotalAmount(formatMoney(total[0]), total[0]);
                        quantity[0] = 0;
                        mListProduct.get(position).setQuantity(quantity[0]);
                        return;
                    }
                    quantity[0] = quantity[0] - 1;
                    mListProduct.get(position).setQuantity(quantity[0]);
                    holder.tvQuantity.setText(String.valueOf(mListProduct.get(position).getQuantity()));
                    total[0] = total[0] - mListProduct.get(position).getProductPrice();
                    mITotalAmount.processTotalAmount(formatMoney(total[0]), total[0]);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String formatMoney(float money) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            return decimalFormat.format(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFootName, tvFootPrice, tvQuantity;

        private ImageView ivRemove, ivAdd, ivItemMenu;

        private CardView cvImage, cvRemove, cvAdd;

        private ConstraintLayout clFoodSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            try {
                tvFootName = itemView.findViewById(R.id.tvFootName);
                tvFootPrice = itemView.findViewById(R.id.tvFootPrice);
                tvQuantity = itemView.findViewById(R.id.tvQuantity);
                ivRemove = itemView.findViewById(R.id.ivRemove);
                ivAdd = itemView.findViewById(R.id.ivAdd);
                ivItemMenu = itemView.findViewById(R.id.ivItemMenu);
                cvImage = itemView.findViewById(R.id.cvImage);
                cvAdd = itemView.findViewById(R.id.cvAdd);
                cvRemove = itemView.findViewById(R.id.cvRemove);
                clFoodSelect = itemView.findViewById(R.id.clFoodSelect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface ITotalAmount {
        public void processTotalAmount(String totalAmount, float totalMoney);

    }
}
