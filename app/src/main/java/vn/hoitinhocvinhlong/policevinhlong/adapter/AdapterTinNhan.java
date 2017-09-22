package vn.hoitinhocvinhlong.policevinhlong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.controller.activity.map.MapActivity;
import vn.hoitinhocvinhlong.policevinhlong.model.TinNhan;

/**
 * Created by Long on 9/18/2017.
 */

public class AdapterTinNhan extends RecyclerView.Adapter<AdapterTinNhan.ViewHolderTinNhan>{

    private Context mContext;
    private List<TinNhan> mTinNhanList;

    public AdapterTinNhan(Context context, List<TinNhan> tinNhanList) {
        mContext = context;
        mTinNhanList = tinNhanList;
    }

    @Override
    public ViewHolderTinNhan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_custom_tin_nhan, parent, false);
        return new ViewHolderTinNhan(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderTinNhan holder, int position) {
        final TinNhan tinNhan = mTinNhanList.get(position);
        holder.bind(tinNhan);
        holder.mTinNhanCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMapActivity = new Intent(mContext, MapActivity.class);
                iMapActivity.putExtra("LatitudeTinNhan", tinNhan.getLat());
                iMapActivity.putExtra("LongitudeTinNhan", tinNhan.getLng());
                mContext.startActivity(iMapActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTinNhanList.size();
    }

    public class ViewHolderTinNhan extends RecyclerView.ViewHolder{

        private TextView mTenTextView, mDiaChiTextView;
        private ImageView mHinhAnhImageView;
        private CardView mTinNhanCardView;

        public ViewHolderTinNhan(View itemView) {
            super(itemView);
            mTenTextView = (TextView) itemView.findViewById(R.id.textViewTen);
            mDiaChiTextView = (TextView) itemView.findViewById(R.id.textViewDiaChi);
            mHinhAnhImageView = (ImageView) itemView.findViewById(R.id.imageViewHinhAnh);
            mTinNhanCardView = (CardView) itemView.findViewById(R.id.cardViewTinNhan);
        }

        public void bind(TinNhan tinNhan){
//            mTenTextView.setText(tinNhan.getTen());
//            mDiaChiTextView.setText(tinNhan.getDiaChi());
//            Picasso.with(mContext).load(tinNhan.getHinhAnh()).fit().into(mHinhAnhImageView);
        }
    }

}
