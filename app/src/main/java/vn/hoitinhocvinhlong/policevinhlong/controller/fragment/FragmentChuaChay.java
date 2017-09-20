package vn.hoitinhocvinhlong.policevinhlong.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.hoitinhocvinhlong.policevinhlong.R;
import vn.hoitinhocvinhlong.policevinhlong.adapter.AdapterTinNhan;
import vn.hoitinhocvinhlong.policevinhlong.model.TinNhan;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chua_chay, container, false);
        //View
        mTinNhanRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChuaChay);
        //Set recycler view chua chay
        mTinNhanList = new ArrayList<>();
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, -34, 151));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mTinNhanList.add(new TinNhan("Long", "Vĩnh Long", R.drawable.fire, 10.1673292, 105.9248945));
        mAdapterTinNhan = new AdapterTinNhan(getActivity(), mTinNhanList);
        mTinNhanRecyclerView.setHasFixedSize(true);
        mTinNhanRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTinNhanRecyclerView.setNestedScrollingEnabled(false);
        mTinNhanRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mTinNhanRecyclerView.setAdapter(mAdapterTinNhan);
        return view;
    }
}
