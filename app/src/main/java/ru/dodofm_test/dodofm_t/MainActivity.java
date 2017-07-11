package ru.dodofm_test.dodofm_t;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView mImPlay;
    private ImageView mImPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        List<View> pages = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(this);

        View page = inflater.inflate(R.layout.banner_page, null);
        ((ImageView) page.findViewById(R.id.imBanner)).setImageResource(R.drawable.slide_one);
        pages.add(page);

        page = inflater.inflate(R.layout.banner_page, null);
        ((ImageView) page.findViewById(R.id.imBanner)).setImageResource(R.drawable.slide_two);
        pages.add(page);

        page = inflater.inflate(R.layout.banner_page, null);
        ((ImageView) page.findViewById(R.id.imBanner)).setImageResource(R.drawable.slide_three);
        pages.add(page);

        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(pages);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.setCurrentItem(0);

        LinePageIndicator linePageIndicator = (LinePageIndicator) findViewById(R.id.linePageIndicator);
        linePageIndicator.setViewPager(viewPager);

        mImPlay = (ImageView) findViewById(R.id.imPlay);
        mImPause = (ImageView) findViewById(R.id.imPause);
        mImPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });
        mImPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pause();
            }
        });

//        int color = ContextCompat.getColor(getContext(), R.color.my_color);
//        seekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP); // полоска
//        seekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP); // кругляшок

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        //seekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        seekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);


    }

    private void play() {
        mImPlay.setVisibility(View.INVISIBLE);
        mImPause.setVisibility(View.VISIBLE);
    }

    private void pause() {
        mImPause.setVisibility(View.INVISIBLE);
        mImPlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {

        } else if (id == R.id.nav_star) {

        } else if (id == R.id.nav_offline) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.nav_vk) {

        } else if (id == R.id.nav_fb) {

        } else if (id == R.id.nav_site_dodo_fm) {

        } else if (id == R.id.nav_site_dodo_pizza) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
