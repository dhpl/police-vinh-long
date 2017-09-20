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

public class FragmentTeNanXaHoi extends Fragment {

    public static FragmentTeNanXaHoi newInstance() {
        Bundle args = new Bundle();
        FragmentTeNanXaHoi fragment = new FragmentTeNanXaHoi();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_te_nan_xa_hoi, container, false);
        return view;
    }
}
