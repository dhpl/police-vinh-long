package vn.hoitinhocvinhlong.policevinhlong.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private Activity mActivity;
    private List<TinNhan> mTinNhanList;

    public AdapterTinNhan(Context context, Activity activity, List<TinNhan> tinNhanList) {
        mContext = context;
        mActivity = activity;
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
        private Button mButtonCall;
        private RecyclerView mRecyclerViewHinh, mRecyclerViewVideo;
        private AdapterHinh mAdapterHinh;
        private AdapterVideo mAdapterVideo;
        private String[] mPermissionCall = {android.Manifest.permission.CALL_PHONE};

        public ViewHolderTinNhan(View itemView) {
            super(itemView);
            mTenTextView = (TextView) itemView.findViewById(R.id.textViewTen);
            mDiaChiTextView = (TextView) itemView.findViewById(R.id.textViewDiaChi);
            mHinhAnhImageView = (ImageView) itemView.findViewById(R.id.imageViewHinhAnh);
            mTinNhanCardView = (CardView) itemView.findViewById(R.id.cardViewTinNhan);
            mThoiGianDangTextView = (TextView) itemView.findViewById(R.id.textViewThoiGianDang);
            mNoiDungTextView = (TextView) itemView.findViewById(R.id.textViewNoiDung);
            mRecyclerViewHinh = (RecyclerView) itemView.findViewById(R.id.recyclerViewHinhAnh);
            mRecyclerViewVideo = (RecyclerView) itemView.findViewById(R.id.recyclerViewVideo);
            mButtonCall = (Button) itemView.findViewById(R.id.buttonCall);
        }

        public void bind(final TinNhan tinNhan){
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
            if(!TextUtils.isEmpty(tinNhan.getNoidung())){
                mNoiDungTextView.setText("Nội dung: " + tinNhan.getNoidung());
                mNoiDungTextView.setVisibility(View.VISIBLE);
            }else{
                mNoiDungTextView.setVisibility(View.GONE);
            }
            //Set Hinh
            if(tinNhan.getHinhanh().size() > 0){
                mRecyclerViewHinh.setVisibility(View.VISIBLE);
                mAdapterHinh = new AdapterHinh(mContext, tinNhan.getHinhanh());
                mRecyclerViewHinh.setItemAnimator(new DefaultItemAnimator());
                mRecyclerViewHinh.setNestedScrollingEnabled(false);
                mRecyclerViewHinh.setHasFixedSize(true);
                mRecyclerViewHinh.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                mRecyclerViewHinh.setAdapter(mAdapterHinh);
            }else{
                mRecyclerViewHinh.setVisibility(View.GONE);
            }
            //Set video
            if(tinNhan.getVideo().size() > 0){
                mRecyclerViewVideo.setVisibility(View.VISIBLE);
                mAdapterVideo = new AdapterVideo(mContext, tinNhan.getVideo());
                mRecyclerViewVideo.setItemAnimator(new DefaultItemAnimator());
                mRecyclerViewVideo.setNestedScrollingEnabled(false);
                mRecyclerViewVideo.setHasFixedSize(true);
                mRecyclerViewVideo.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                mRecyclerViewVideo.setAdapter(mAdapterVideo);
            }else{
                mRecyclerViewVideo.setVisibility(View.GONE);
            }
            //Set Call
            mButtonCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkPermission()){
                        ThanhVien thanhVien = new Gson().fromJson(tinNhan.getUser(), ThanhVien.class);
                        Intent iCall = new Intent(Intent.ACTION_CALL);
                        iCall.setData(Uri.parse("tel:" + thanhVien.getSdt()));
                        mContext.startActivity(iCall);
                    }else{
                        Toast.makeText(mContext, "Ứng dụng cần quyền truy cập.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        private boolean checkPermission(){
            List<String> permissions = new ArrayList<>();
            if(ContextCompat.checkSelfPermission(mContext, mPermissionCall[0]) != PackageManager.PERMISSION_GRANTED){
                permissions.add(mPermissionCall[0]);
            }
            if(!permissions.isEmpty()){
                ActivityCompat.requestPermissions(mActivity, permissions.toArray(new String[permissions.size()]), 100);
                return false;
            }
            return true;
        }
    }

}
