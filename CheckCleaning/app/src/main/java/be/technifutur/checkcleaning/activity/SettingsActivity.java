package be.technifutur.checkcleaning.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.fragment.SettingsAccountFragment;
import be.technifutur.checkcleaning.fragment.SettingsNotificationFragment;
import be.technifutur.checkcleaning.presenter.SettingsAccountPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.settings_viewPager)
    ViewPager settingsViewPager;

    private User mUser;
    private Building mBuilding;
    private MenuItem prevMenuItem;
    private SettingsAccountFragment accountFragment;
    private SettingsNotificationFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setTitle("Paramètres");
        mUser = getIntent().getExtras().getParcelable("user");
        mBuilding = getIntent().getExtras().getParcelable("building");
        setupViewPager();
    }

    public void setupViewPager() {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), mUser, mBuilding);
        settingsViewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        /*if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        else
        {
            bottomNavigation.getMenu().getItem(0).setChecked(false);
        }
        bottomNavigation.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigation.getMenu().getItem(position);*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;
        private User mUser;
        private Building mBuilding;
        private SettingsAccountFragment accountFragment;
        private SettingsNotificationFragment mapFragment;

        public MyPagerAdapter(FragmentManager fragmentManager, User user, Building building) {
            super(fragmentManager);
            mUser = user;
            mBuilding = building;
            accountFragment = SettingsAccountFragment.newInstance(mUser);
            mapFragment = SettingsNotificationFragment.newInstance(mBuilding);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return accountFragment;
                default:
                    return mapFragment;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {

            String title = "";

            switch (position){
                case 0:
                    title = "Compte";
                    break;
                case 1:
                    title = "Notifications";
                    break;
            }

            return title;
        }

    }
}
