package in.techware.lataxidriverapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.techware.lataxidriverapp.fragments.AccountsFragment;
import in.techware.lataxidriverapp.fragments.EarningsFragment;
import in.techware.lataxidriverapp.fragments.HomeFragment;
import in.techware.lataxidriverapp.fragments.RatingsFragment;


public class HomePagerAdapter extends FragmentPagerAdapter {

    private HomeFragment homeFragment;
    private EarningsFragment earningsFragment;
    private RatingsFragment ratingsFragment;
    private AccountsFragment accountsFragment;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                return homeFragment;
            case 1:
                if (earningsFragment == null) {
                    earningsFragment = new EarningsFragment();
                }
                return earningsFragment;
            case 2:
                if (ratingsFragment == null) {
                    ratingsFragment = new RatingsFragment();
                }
                return ratingsFragment;
            case 3:
                if (accountsFragment == null) {
                    accountsFragment = new AccountsFragment();
                }
                return accountsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
