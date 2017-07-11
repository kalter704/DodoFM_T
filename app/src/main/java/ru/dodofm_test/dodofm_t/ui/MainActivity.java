package ru.dodofm_test.dodofm_t.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.dodofm_test.dodofm_t.Const;
import ru.dodofm_test.dodofm_t.adapters.BannerPagerAdapter;
import ru.dodofm_test.dodofm_t.R;
import ru.dodofm_test.dodofm_t.classes.Player;
import ru.dodofm_test.dodofm_t.classes.RadioChannels;
import ru.dodofm_test.dodofm_t.classes.RadioState;
import ru.dodofm_test.dodofm_t.listners.OnRadioListener;
import ru.dodofm_test.dodofm_t.services.NotificationService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnRadioListener{

    private AudioManager mAudioManager;

    private ImageView mImPlay;
    private ImageView mImPause;

    ImageView mImUpOn;
    ImageView mImUpOff;
    ImageView mImDownOn;
    ImageView mImDownOff;

    ImageView mImAlbum;

    TextView mTvSongName;

    BannerPagerAdapter mBannerPagerAdapter;
    ViewPager mViewPager;
    LinePageIndicator mLinePageIndicator;

    private int mID;

    private RadioChannels mRadioChannels;

    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RadioState.context = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mID = 0;

        mRadioChannels = RadioChannels.getInstance();

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

        mBannerPagerAdapter = new BannerPagerAdapter(pages);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mBannerPagerAdapter);
        mViewPager.setCurrentItem(0);

//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        mLinePageIndicator = (LinePageIndicator) findViewById(R.id.linePageIndicator);
        mLinePageIndicator.setViewPager(mViewPager);

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

        mImUpOn = (ImageView) findViewById(R.id.imUpOn);
        mImUpOff = (ImageView) findViewById(R.id.imUpOff);
        mImDownOn = (ImageView) findViewById(R.id.imDownOn);
        mImDownOff = (ImageView) findViewById(R.id.imDownOff);
        mImUpOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeOff();
            }
        });
        mImUpOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeOn();
            }
        });
        mImDownOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dislikeOff();
            }
        });
        mImDownOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dislikeOn();
            }
        });

        mImAlbum = (ImageView) findViewById(R.id.imAlbum);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        //seekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        seekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int volume = (int) (1.0 * i / 100 * mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                mAudioManager.setStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        volume,
                        AudioManager.FLAG_PLAY_SOUND);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mTvSongName = (TextView) findViewById(R.id.tvSongName);

        changeBannerTimer.start();
        changeAlbumImageTimer.start();

        RadioState.addRadioListener(this);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRadioChannels.mPlayRadioWithId == mID) {
            if (RadioState.isPlaying()) {
                showPause();
            } else {
                showPlay();
            }
        } else {
            showPlay();
        }
        seekBar.setProgress((int) ((1.0 * mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)) * 100));
    }

    private void play() {
        if (RadioState.hasConnectionToNetwork()) {
            showPause();
            //showProgressBar();
            RadioChannels.getInstance().mPlayRadioWithId = mID;
            mRadioChannels.mMetaDataNameSongPlayingRadio = null;
            startRadio();
        } else {
            pushSnack(getString(R.string.error_network));
        }
    }

    private void pause() {
        Intent pauseIntent = new Intent(this, NotificationService.class);
        pauseIntent.setAction(Const.ACTION.PLAY_ACTION);
        PendingIntent ppauseIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            ppauseIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
        showPlay();
    }

    private void showPlay() {
        mImPause.setVisibility(View.INVISIBLE);
        mImPlay.setVisibility(View.VISIBLE);
    }

    private void showPause() {
        mImPlay.setVisibility(View.INVISIBLE);
        mImPause.setVisibility(View.VISIBLE);
    }

    private void likeOn() {
        mImUpOff.setVisibility(View.INVISIBLE);
        mImUpOn.setVisibility(View.VISIBLE);
    }

    private void likeOff() {
        mImUpOn.setVisibility(View.INVISIBLE);
        mImUpOff.setVisibility(View.VISIBLE);
    }

    private void dislikeOn() {
        mImDownOff.setVisibility(View.INVISIBLE);
        mImDownOn.setVisibility(View.VISIBLE);
    }

    private void dislikeOff() {
        mImDownOn.setVisibility(View.INVISIBLE);
        mImDownOff.setVisibility(View.VISIBLE);
    }

    private void startRadio() {
        Intent serviceIntent = new Intent(MainActivity.this, NotificationService.class);
        serviceIntent.setAction(Const.ACTION.STARTFOREGROUND_ACTION);
        startService(serviceIntent);
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

    private Thread changeBannerTimer = new Thread() {
        @Override
        public void run() {
            try {
                while (true) {
                    sleep(10000);
                    int current = mViewPager.getCurrentItem();
                    current++;
                    if (current >= mBannerPagerAdapter.getCount()) {
                        current = 0;
                    }
                    final int currentItem = current;
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(currentItem);
                            //mLinePageIndicator.setCurrentItem(currentItem);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Thread changeAlbumImageTimer = new Thread() {
        @Override
        public void run() {
            AssetManager am = MainActivity.this.getAssets();
            try {
                while (true) {
                    String[] albums = am.list("albums");
                    Random r = new Random();
                    int current = r.nextInt(albums.length);
                    InputStream stream = am.open("albums/" + albums[current]);
                    final Drawable image = Drawable.createFromStream(stream, "temp");
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImAlbum.setImageDrawable(image);
                        }
                    });
                    sleep(10000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        RadioState.removeRadioListener(this);
        Player.offRadio();
        super.onDestroy();
    }

    @Override
    public void onRadioStarted() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPause();
                //debugToast("onRadioStarted");
            }
        });
    }

    @Override
    public void onRadioPaused() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPlay();
                //debugToast("onRadioPaused");
            }
        });
    }

    @Override
    public void onRadioStopped() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPlay();
                //debugToast("onRadioStopped");
            }
        });
    }

    @Override
    public void onRadioLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //showProgressBar();
                //debugToast("onRadioLoading");
            }
        });
    }

    @Override
    public void onRadioMetadata(final String s, final String s2) {
        //final String ss = s;
        //final String ss2 = s2;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO Do UI works here
                Log.d("MetaDataDebug", "s = " + s);
                Log.d("MetaDataDebug", "s2 = " + s2);
                Log.d("MetaDataDebug", "\n");
                if ("StreamTitle".equals(s)) {
                    mTvSongName.setText(s2);
                    mRadioChannels.mMetaDataNameSongPlayingRadio = s2;
                }
            }
        });
    }

    @Override
    public void onRadioError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPlay();
                //mRadioChannels.mPlayRadioWithId = -1;
                //debugToast("onRadioError");
            }
        });
    }

    private void pushSnack(String str) {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.playCoordLayout),
                str,
                Snackbar.LENGTH_SHORT);
        View snackView = snackbar.getView();
        snackView.setBackgroundColor(getResources().getColor(R.color.snackColor));
        TextView snackTV = (TextView) snackView.findViewById(android.support.design.R.id.snackbar_text);
        snackTV.setTextColor(getResources().getColor(R.color.snackTextColor));
        snackTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }

}
