package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.content.Context;
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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Activity mContext;

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
        mWidthImgae = holder.ivItemMenu.getLayoutParams().width;
        holder.tvFootName.setText(mListProduct.get(position).getmProductName());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.tvFootPrice.setText(decimalFormat.format(mListProduct.get(position).getmProductPrice()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(mListProduct.get(position).getmProductImage().getmImage(),
                0, mListProduct.get(position).getmProductImage().getmImage().length);
        holder.ivItemMenu.setImageBitmap(bitmap);
        holder.cvImage.getBackground().setTint(Color.parseColor(mListProduct.get(position).getmColor().getColorName()));
        final int[] quantity = {0};

        holder.clFoodSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tag", "onClick: parent");
                holder.cvImage.getBackground().setTint(mContext.getResources().getColor(R.color.greenn));
                holder.ivItemMenu.setImageResource(R.drawable.ic_tick);
                holder.ivItemMenu.setColorFilter(Color.WHITE);
                holder.ivItemMenu.getLayoutParams().width = 50;
                holder.tvQuantity.setText(String.valueOf(quantity[0]=quantity[0]+1));
                holder.tvQuantity.setVisibility(View.VISIBLE);
                holder.cvAdd.setVisibility(View.VISIBLE);
                holder.cvRemove.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.light_grey));
                total[0] = total[0] +mListProduct.get(position).getmProductPrice();
                mITotalAmount.processTotalAmount(formatMoney(total[0]));
                mITotalAmount.getQuantity(Integer.parseInt(holder.tvQuantity.getText().toString()));
            }
        });
        holder.cvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tag", "onClick: add");
                holder.tvQuantity.setText(String.valueOf(quantity[0]=quantity[0]+1));
                total[0] = total[0] +mListProduct.get(position).getmProductPrice();
                mITotalAmount.processTotalAmount(formatMoney(total[0]));
                mITotalAmount.getQuantity(Integer.parseInt(holder.tvQuantity.getText().toString()));
            }
        });
        holder.cvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tag", "onClick: remove");
                if (Integer.parseInt(holder.tvQuantity.getText().toString()) <1) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(mListProduct.get(position).getmProductImage().getmImage(),
                            0, mListProduct.get(position).getmProductImage().getmImage().length);
                    holder.ivItemMenu.setImageBitmap(bitmap);
                    holder.cvImage.getBackground().setTint(Color.parseColor(mListProduct.get(position).getmColor().getColorName()));
                    holder.cvAdd.setVisibility(View.GONE);
                    holder.cvRemove.setVisibility(View.GONE);
                    holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    holder.ivItemMenu.setColorFilter(null);
                    holder.tvQuantity.setVisibility(View.GONE);
                    holder.ivItemMenu.getLayoutParams().width = mWidthImgae;
                    return;
                }
                holder.tvQuantity.setText(String.valueOf(quantity[0]=quantity[0]-1));
                total[0] = total[0] -mListProduct.get(position).getmProductPrice();
                mITotalAmount.processTotalAmount(formatMoney(total[0]));
                mITotalAmount.getQuantity(Integer.parseInt(holder.tvQuantity.getText().toString()));
            }
        });
    }

    private String formatMoney(float money){
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        return decimalFormat.format(money);
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
        }
    }

    public interface ITotalAmount{
        public void processTotalAmount(String totalAmount);

        public void getQuantity(int quantity);
    }
}
