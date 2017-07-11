package ru.dodofm_test.dodofm_t.classes;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class RadioChannels {

    private static RadioChannels mInstance = new RadioChannels();

    public static RadioChannels getInstance() {
        return mInstance;
    }

    private RadioChannels() {
    }

    public List<Integer> mIds = new ArrayList<Integer>() {{
        add(0);
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(6);
        add(7);
        add(8);
        add(9);
    }};

    public String mRadioNames[] = {
            // 0
            "DODO FM",
            // 1
            "DODO FM",
            // 2
            "DODO FM",
            // 3
            "DODO FM",
            // 4
            "DODO FM",
            // 5
            "DODO FM",
            // 6
            "DODO FM",
            // 7
            "DODO FM",
            // 8
            "DODO FM",
            // 9
            "DODO FM",

    };

    public String mLocations[] = {
            // 0
            "Moscow, Russia",
            // 1
            "Moscow, Russia",
            // 2
            "Moscow, Russia",
            // 3
            "Moscow, Russia",
            // 4
            "Moscow, Russia",
            // 5
            "Moscow, Russia",
            // 6
            "Moscow, Russia",
            // 7
            "Moscow, Russia",
            // 8
            "Gomel, Belarus",
            // 9
            "Moscow, Russia",

    };

    public String mLinks[] = {
            // 0
            "http://dodofm.ru:8000/radio",
            // 1
            "http://dodofm.ru:8000/radio",
            // 2
            "http://dodofm.ru:8000/radio",
            // 3
            "http://dodofm.ru:8000/radio",
            // 4
            "http://dodofm.ru:8000/radio",
            // 5
            "http://dodofm.ru:8000/radio",
            // 6
            "http://dodofm.ru:8000/radio",
            // 7
            "http://dodofm.ru:8000/radio",
            // 8
            "http://dodofm.ru:8000/radio",
            // 9
            "http://dodofm.ru:8000/radio",

    };

    // Указать индекс (i - 1)
    public List<Integer> mLikes = new ArrayList<>();

    public static int mPlayRadioWithId = -1;
    public String mMetaDataNameSongPlayingRadio = null;

//    public void saveLike(Context context, int n) {
//        if(!mLikes.contains(n)) {
//            mLikes.add(n);
//            Collections.sort(mLikes);
//        }
//        SharedPreferences sPref = context.getSharedPreferences(context.getString(R.string.file_name), MODE_PRIVATE);
//        SharedPreferences.Editor ed = sPref.edit();
//        ed.putString(String.valueOf(n), "like");
//        ed.apply();
//    }
//
//    public void saveDislike(Context context, int n) {
//        if(mLikes.contains(n)) {
//            mLikes.remove(Integer.valueOf(n));
//        }
//        SharedPreferences sPref = context.getSharedPreferences(context.getString(R.string.file_name), MODE_PRIVATE);
//        SharedPreferences.Editor ed = sPref.edit();
//        ed.putString(String.valueOf(n), "dislike");
//        ed.apply();
//    }
//
//    public void loadLikes(Context context) {
//        SharedPreferences sPref = context.getSharedPreferences(context.getString(R.string.file_name), MODE_PRIVATE);
//        mLikes.clear();
//        for(int i = 0; i < mIds.size(); i++) {
//            if(sPref.getString(String.valueOf(mIds.get(i)), "").equals("like")) {
//                mLikes.add(mIds.get(i));
//            }
//        }
//    }


}
