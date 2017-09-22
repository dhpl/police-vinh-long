package vn.hoitinhocvinhlong.policevinhlong.controller.fragment;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.adapter.AdapterTinNhan;
import vn.hoitinhocvinhlong.policevinhlong.model.ThanhVien;
import vn.hoitinhocvinhlong.policevinhlong.model.TinNhan;
import vn.hoitinhocvinhlong.policevinhlong.util.JsonResult;

/**
 * Created by Long on 9/18/2017.
 */

public class FragmentChuaChay extends Fragment {

    private List<TinNhan> mTinNhanList;
    private RecyclerView mTinNhanRecyclerView;
    private AdapterTinNhan mAdapterTinNhan;

    public static FragmentChuaChay newInstance() {
        Bundle args = new Bundle();
        FragmentChuaChay fragment = new FragmentChuaChay();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chua_chay, container, false);
        //View
        mTinNhanRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChuaChay);
        //Set recycler view chua chay
        mTinNhanList = new ArrayList<>();
        JsonResult.getTinNhan(getContext(), 1, 1, new JsonResult.GetSuccess() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {

            }

            @Override
            public void onResponse(String response) throws JSONException {

            }

            @Override
            public void onError(VolleyError response) throws JSONException {

            }
        });
        mAdapterTinNhan = new AdapterTinNhan(getActivity(), mTinNhanList);
        mTinNhanRecyclerView.setHasFixedSize(true);
        mTinNhanRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTinNhanRecyclerView.setNestedScrollingEnabled(false);
        mTinNhanRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mTinNhanRecyclerView.setAdapter(mAdapterTinNhan);
        return view;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ThanhVien thanhVien = intent.getParcelableExtra("ThanhVien");
            TinNhan tinNhan = intent.getParcelableExtra("TinNhan");
            System.out.println(thanhVien.toString());
        }
    };

    private void registerBroadcastReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("receiver-chua-chay");
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);
    }
}
