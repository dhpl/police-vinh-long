package vn.hoitinhocvinhlong.policevinhlong.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vn.hoitinhocvinhlong.policevinhlong.controller.fragment.FragmentChuaChay;
import vn.hoitinhocvinhlong.policevinhlong.controller.fragment.FragmentTaiNanGiaoThong;
import vn.hoitinhocvinhlong.policevinhlong.controller.fragment.FragmentTeNanXaHoi;

/**
 * Created by Long on 9/18/2017.
 */

public class AdapterViewPager extends FragmentStatePagerAdapter {

    private Fragment[] fragments = {new FragmentChuaChay().newInstance(),
            new FragmentTaiNanGiaoThong().newInstance(), new FragmentTeNanXaHoi().newInstance()};
    private String[] titles = {"Chữa cháy", "Tai nạn giao thông", "Tệ nạn xã hội"};

    public AdapterViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
