package vn.hoitinhocvinhlong.policevinhlong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;

/**
 * Created by Long on 9/25/2017.
 */

public class AdapterHinh extends RecyclerView.Adapter<AdapterHinh.ViewHolderHinh>{

    private Context mContext;
    private List<String> mHinhList;

    public AdapterHinh(Context context, List<String> hinhList) {
        mContext = context;
        mHinhList = hinhList;
    }

    @Override
    public ViewHolderHinh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hinh, parent, false);
        return new ViewHolderHinh(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderHinh holder, int position) {
        String url = mHinhList.get(position);
        holder.bind(url);
    }

    @Override
    public int getItemCount() {
        return mHinhList.size();
    }

    public class ViewHolderHinh extends RecyclerView.ViewHolder{

        private ImageView imageViewHinh;

        public ViewHolderHinh(View itemView) {
            super(itemView);
            imageViewHinh = (ImageView) itemView.findViewById(R.id.imageViewHinhAnh);
        }

        public void bind(String url){
            System.out.println("Hinh: " + url.split("\\.jpg")[0]);
            Picasso.with(mContext)
                    .load(url.split("\\.jpg")[0])
                    .fit()
                    .placeholder(R.drawable.fire)
                    .into(imageViewHinh);
        }
    }
}
