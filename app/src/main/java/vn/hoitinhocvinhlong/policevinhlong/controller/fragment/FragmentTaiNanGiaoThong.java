package vn.hoitinhocvinhlong.policevinhlong.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.hoitinhocvinhlong.policevinhlong.R;

/**
 * Created by Long on 9/18/2017.
 */

public class FragmentTaiNanGiaoThong extends Fragment {

    public static FragmentTaiNanGiaoThong newInstance() {
        Bundle args = new Bundle();
        FragmentTaiNanGiaoThong fragment = new FragmentTaiNanGiaoThong();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tai_nan_giao_thong, container, false);
        return view;
    }
}
