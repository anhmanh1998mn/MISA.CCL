package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.Color;

/**
‐ Mục đích Class thực hiện việc quy định số lượng,cách hiển thị dữ liệu lên view
*
‐ {@link Color}
‐ {@link vn.com.misa.ccl.view.restaurantsetup.ActivityFoodUpdate}
*
‐ @created_by cvmanh on 01/19/2021
*/

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private Activity mContext;

    private int mLayout;

    private List<Color> mListColor;

    private IColorSelection mIColorSelection;

    public IColorSelection getmIColorSelection() {
        return mIColorSelection;
    }

    public void setmIColorSelection(IColorSelection mIColorSelection) {
        this.mIColorSelection = mIColorSelection;
    }

    public ColorAdapter(Activity mContext, int mLayout, List<Color> mListColor) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListColor = mListColor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(mLayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.cvColor.getBackground().setTint(android.graphics.Color.parseColor(mListColor.get(position).getColorName()));
            holder.cvColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mIColorSelection!=null){
                        mIColorSelection.onClickListener(mListColor.get(position).getColorName(),mListColor.get(position).getColorID());
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListColor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvColor;

        TextView tvTick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                cvColor=itemView.findViewById(R.id.cvColor);
                tvTick=itemView.findViewById(R.id.tvTick);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public interface IColorSelection{
        public void onClickListener(String keyColor,int colorID);
    }
}
