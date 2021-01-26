package vn.com.misa.ccl.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.com.misa.ccl.R;
import vn.com.misa.ccl.entity.ProductImage;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ViewHolder> {

    private IOnClickItemListener mClickItemListener;

    public IOnClickItemListener getmClickItemListener() {
        return mClickItemListener;
    }

    public void setmClickItemListener(IOnClickItemListener mClickItemListener) {
        this.mClickItemListener = mClickItemListener;
    }

    private Activity mContext;

    private int mLayout;

    private List<ProductImage> mListProductImage;

    public ProductImageAdapter(Activity mContext, int mLayout, List<ProductImage> mListProductImage) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.mListProductImage = mListProductImage;
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
            Bitmap bitmap= BitmapFactory.decodeByteArray(mListProductImage.get(position).getmImage(),0,
                    mListProductImage.get(position).getmImage().length);
            holder.ivProductImage.setImageBitmap(bitmap);
            holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mClickItemListener!=null){
                        mClickItemListener.selectionItem(mListProductImage.get(position).getmImage(),mListProductImage.get(position).getmProductImageID());
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListProductImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProductImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                ivProductImage=itemView.findViewById(R.id.ivProductImage);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public interface IOnClickItemListener{
        public void selectionItem(byte[] productImage,int imageID);
    }
}
