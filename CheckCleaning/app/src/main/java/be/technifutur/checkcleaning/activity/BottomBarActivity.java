package be.technifutur.checkcleaning.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.Util.CustomViewPager;
import be.technifutur.checkcleaning.adapter.ViewPagerAdapter;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.TaskData;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.fragment.ControlFragment;
import be.technifutur.checkcleaning.fragment.HomeFragment;
import be.technifutur.checkcleaning.fragment.ReportFragment;
import be.technifutur.checkcleaning.fragment.RootTodoFragment;
import be.technifutur.checkcleaning.fragment.TeamFragment;
import be.technifutur.checkcleaning.presenter.BottomBarPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomBarActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigation;

    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    private User mUser;
    private Building mBuilding;
    private FragmentManager manager = getSupportFragmentManager();
    private HomeFragment homeFragment;
    private RootTodoFragment rootTodoFragment;
    private ControlFragment controlFragment;
    private ReportFragment reportFragment;
    private TeamFragment teamFragment;
    private boolean wouldLikeDC;
    private MenuItem prevMenuItem;
    public static String mBuildingName;
    private boolean fragmentIsOpen;
    private BottomBarPresenter mPresenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                System.out.println("BACKSTACK COUNT = " + getFragmentManager().getBackStackEntryCount());
            }
            if (fragmentIsOpen){
                getSupportFragmentManager().popBackStackImmediate();
                fragmentIsOpen = false;
                viewPager.setEnable(true);
            }

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("Accueil");
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_todo:
                    setTitle("Notes");
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_control:
                    setTitle("Contrôle périodique");
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_report:
                    setTitle("Radenopport journalier");
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.navigation_team:
                    setTitle("Mon équipe");
                    viewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }

    };

    private BottomNavigationItemView.OnFocusChangeListener mOnFocusChangeListener
            = new BottomNavigationView.OnFocusChangeListener(){

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            System.out.println("ON FOCUS CHANGE");
            System.out.println(v.getClass());
            System.out.println("hasFocus = " + hasFocus);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        ButterKnife.bind(this);

        setTitle("Accueil");

        wouldLikeDC = false;
        fragmentIsOpen = false;
        mUser = getIntent().getExtras().getParcelable("user");
        mBuilding = getIntent().getExtras().getParcelable("building");
        mBuildingName = mBuilding.getName();
        mPresenter = new BottomBarPresenter(this, mBuilding.getUsers_id());
        Collections.sort(mUser.getTasks());
        viewPager.addOnPageChangeListener(this);
        mPresenter.loadTeam();
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * Gestion du double back pour quitter
     */

    @Override
    public void onBackPressed() {

        if (fragmentIsOpen) {
            viewPager.setEnable(true);
            fragmentIsOpen = false;
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            if (wouldLikeDC) {
                super.onBackPressed();
            }

            wouldLikeDC = true;
            Toast.makeText(this, "Appuyez sur retour une deuxième fois pour quitter.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    wouldLikeDC = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if(fragmentIsOpen){
            viewPager.setEnable(false);
        }
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        }
        else
        {
            bottomNavigation.getMenu().getItem(0).setChecked(false);
        }
        bottomNavigation.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigation.getMenu().getItem(position);
        setTitleForFragment(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setupViewPager(List<User> team) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        rootTodoFragment = RootTodoFragment.newInstance(this, mUser, mBuilding);
        controlFragment = new ControlFragment();
        reportFragment = new ReportFragment();
        teamFragment = TeamFragment.newInstance(mUser, team);
        adapter.addFragment(homeFragment);
        adapter.addFragment(rootTodoFragment);
        adapter.addFragment(controlFragment);
        adapter.addFragment(reportFragment);
        adapter.addFragment(teamFragment);
        viewPager.setAdapter(adapter);
    }

    public void setTitleForFragment(int position){

        switch (position) {
            case 0:
                setTitle("Accueil");
                break;
            case 1:
                setTitle("Notes");
                break;
            case 2:
                setTitle("Contrôle périodique");
                break;
            case 3:
                setTitle("Rapport journalier");
                break;
            case 4:
                setTitle("Mon équipe");
                break;
        }
    }

    public void setFragmentIsOpen(boolean isEnable){
        fragmentIsOpen = isEnable;
    }

    public void createViewPager(List<User> team) {
        setupViewPager(team);
    }

    public CustomViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(CustomViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
