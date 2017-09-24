package vn.hoitinhocvinhlong.policevinhlong.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.controller.activity.map.MapActivity;
import vn.hoitinhocvinhlong.policevinhlong.model.ThanhVien;
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
                iMapActivity.putExtra("NhiemVu", tinNhan.getNhiemvu());
                mContext.startActivity(iMapActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTinNhanList.size();
    }

    public class ViewHolderTinNhan extends RecyclerView.ViewHolder{

        private TextView mTenTextView, mDiaChiTextView, mThoiGianDangTextView, mNoiDungTextView;
        private ImageView mHinhAnhImageView;
        private CardView mTinNhanCardView;

        public ViewHolderTinNhan(View itemView) {
            super(itemView);
            mTenTextView = (TextView) itemView.findViewById(R.id.textViewTen);
            mDiaChiTextView = (TextView) itemView.findViewById(R.id.textViewDiaChi);
            mHinhAnhImageView = (ImageView) itemView.findViewById(R.id.imageViewHinhAnh);
            mTinNhanCardView = (CardView) itemView.findViewById(R.id.cardViewTinNhan);
            mThoiGianDangTextView = (TextView) itemView.findViewById(R.id.textViewThoiGianDang);
            mNoiDungTextView = (TextView) itemView.findViewById(R.id.textViewNoiDung);
        }

        public void bind(TinNhan tinNhan){

            ThanhVien thanhVien = new Gson().fromJson(tinNhan.getUser(), ThanhVien.class);
            List<Address> addressList = null;
            try {
                addressList = new Geocoder(mContext, Locale.getDefault())
                        .getFromLocation(tinNhan.getLat(), tinNhan.getLng(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            String addressTN = address.getAddressLine(0);
            mTenTextView.setText(thanhVien.getTen());
            mDiaChiTextView.setText("Địa chỉ: " + addressTN);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm a");
            mThoiGianDangTextView.setText(simpleDateFormat.format(new Date(tinNhan.getThoigiantao())));

            if(tinNhan.getHinhanh().size() > 0){
                mHinhAnhImageView.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(tinNhan.getHinhanh().get(0).split("\\.jpg")[0])
                        .fit()
                        .placeholder(R.drawable.fire)
                        .into(mHinhAnhImageView);
            }else{
                mHinhAnhImageView.setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(tinNhan.getNoidung())){
                mNoiDungTextView.setText("Nội dung: " + tinNhan.getNoidung());
                mNoiDungTextView.setVisibility(View.VISIBLE);
            }else{
                mNoiDungTextView.setVisibility(View.GONE);
            }
        }
    }

}
