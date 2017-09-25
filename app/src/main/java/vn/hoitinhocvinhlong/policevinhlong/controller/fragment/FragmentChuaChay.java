package vn.hoitinhocvinhlong.policevinhlong.controller.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.adapter.AdapterTinNhan;
import vn.hoitinhocvinhlong.policevinhlong.model.TinNhan;
import vn.hoitinhocvinhlong.policevinhlong.util.JsonResult;

/**
 * Created by Long on 9/18/2017.
 */

public class FragmentChuaChay extends Fragment {

    private List<TinNhan> mTinNhanList;
    private RecyclerView mTinNhanRecyclerView;
    private AdapterTinNhan mAdapterTinNhan;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mIsLastPage = false;
    private static int mPage;
    private int mItems = 10;
    private int mItemLoad = 5;


    public static FragmentChuaChay newInstance() {
        Bundle args = new Bundle();
        FragmentChuaChay fragment = new FragmentChuaChay();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcastTinNhan();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mPage = savedInstanceState.getInt("Page");
        }
        View view = inflater.inflate(R.layout.fragment_chua_chay, container, false);
        //View
        mTinNhanRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChuaChay);
        //Set recycler view chua chay
        mTinNhanList = new ArrayList<>();
        mAdapterTinNhan = new AdapterTinNhan(getActivity(), getActivity(), mTinNhanList);
        mTinNhanRecyclerView.setHasFixedSize(true);
        mTinNhanRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTinNhanRecyclerView.setNestedScrollingEnabled(false);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mTinNhanRecyclerView.setLayoutManager(mLinearLayoutManager);
        mTinNhanRecyclerView.addOnScrollListener(mOnScrollListenerChuaChay);
        mTinNhanRecyclerView.setAdapter(mAdapterTinNhan);
        loadMore(mPage);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Page", mPage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterBroadcastTinNhan();
    }

    private void loadMore(int page){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Đang tải thêm");
        progressDialog.setMessage("Vui lòng chờ");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JsonResult.getTinNhan(getContext(), 1, page, new JsonResult.GetSuccess() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                JSONObject jsonObjectData = response.getJSONObject("data");
                JSONArray jsonArrayChuaChay = jsonObjectData.getJSONArray("items");
                int size = jsonArrayChuaChay.length();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObjectTinNhan = jsonArrayChuaChay.getJSONObject(i);
                    int id = jsonObjectTinNhan.getInt("id");
                    int iduser = jsonObjectTinNhan.getInt("iduser");
                    String noidung = jsonObjectTinNhan.getString("noidung");
                    String hinhanh = jsonObjectTinNhan.getString("hinhanh");
                    String video = jsonObjectTinNhan.getString("video");
                    List<String> hinhanhList = new Gson().fromJson(hinhanh, List.class);
                    List<String> videoList = new Gson().fromJson(video, List.class);
                    double lat = jsonObjectTinNhan.getDouble("lat");
                    double lng = jsonObjectTinNhan.getDouble("lng");
                    int nhiemvu = jsonObjectTinNhan.getInt("nhiemvu");
                    long thoigiantao = jsonObjectTinNhan.getLong("thoigiantao");
                    String jsonUser = String.valueOf(jsonObjectTinNhan.getString("user"));
                    TinNhan tinNhan = new TinNhan();
                    tinNhan.setId(id);
                    tinNhan.setIduser(iduser);
                    tinNhan.setNoidung(noidung);
                    tinNhan.setHinhanh(hinhanhList);
                    tinNhan.setVideo(videoList);
                    tinNhan.setLat(lat);
                    tinNhan.setLng(lng);
                    tinNhan.setNhiemvu(nhiemvu);
                    tinNhan.setThoigiantao(thoigiantao);
                    tinNhan.setUser(jsonUser);
                    mTinNhanList.add(tinNhan);
                    mAdapterTinNhan.notifyDataSetChanged();
                }
                if(size < mItems){
                    mIsLastPage = true;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(String response) throws JSONException {

            }

            @Override
            public void onError(VolleyError response) throws JSONException {

            }
        });
    }

    private RecyclerView.OnScrollListener mOnScrollListenerChuaChay = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int first = mLinearLayoutManager.findFirstVisibleItemPosition();
            int total = mLinearLayoutManager.getItemCount();
            int visible = mLinearLayoutManager.getChildCount();
            if(!mIsLastPage){
                if((first + mItemLoad) >= (total - visible)){
                    mPage += 1;
                    loadMore(mPage);
                }
            }
        }
    };

    private BroadcastReceiver mBroadcastReceiverTinNhan = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mTinNhanRecyclerView.smoothScrollToPosition(0);
            System.out.println("HiHi");
            TinNhan tinNhan = intent.getParcelableExtra("TinNhan");
            mTinNhanList.add(0, tinNhan);
            mAdapterTinNhan.notifyItemInserted(0);
        }
    };

    private void registerBroadcastTinNhan(){
        IntentFilter intentFilter = new IntentFilter("vn.hoitinhocvinhlong.broadcast.tinnhan");
        getActivity().registerReceiver(mBroadcastReceiverTinNhan, intentFilter);
    }

    private void unregisterBroadcastTinNhan(){
        getActivity().unregisterReceiver(mBroadcastReceiverTinNhan);
    }
}
