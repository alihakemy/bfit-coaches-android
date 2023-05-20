package com.usmart.com.bfit_coaches;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bolaware.viewstimerstory.Momentz;
import com.bolaware.viewstimerstory.MomentzCallback;
import com.bolaware.viewstimerstory.MomentzView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

import constants.Values;
import helpers.LangHolder;

public class Stories extends Activity {
    Activity activity = Stories.this;
    ConstraintLayout container;
    String Stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //*******************************************************
        Resources activityRes = getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale(LangHolder.getInstance().getData());
        activityConf.setLocale(newLocale);
        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics());

        Resources applicationRes = getApplicationContext().getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(newLocale);
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.getDisplayMetrics());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //*******************************************************
        setContentView(R.layout.activity_stories);
        Stories = getIntent().getExtras().getString("Stories");
        container = findViewById(R.id.container);

        VideoView locallyLoadedVideo = new VideoView(activity);

        ArrayList<MomentzView> listOfViews = new ArrayList<>();
        listOfViews.add(new MomentzView(locallyLoadedVideo, 10));
        new Momentz(activity, listOfViews, container, new MomentzCallback() {
            @Override
            public void onNextCalled(@NotNull View view, @NotNull Momentz momentz, int i) {
                    if (view instanceof VideoView) {
                        momentz.pause(true);
                        playVideo((VideoView) view, i, momentz);
                    }
            }

            @Override
            public void done() {
                Stories.super.onBackPressed();
            }

        }, R.drawable.green_lightgrey_drawable).start();
    }

    public void playVideo(VideoView VideoView, int index, Momentz momentz) {
        // String str = "http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4";
        Uri uri = Uri.parse(Values.Link_Video + Stories);
        VideoView.setVideoURI(uri);

        VideoView.requestFocus();
        VideoView.start();

        VideoView.setOnInfoListener((mp, what, extra) -> {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                // Here the video starts
                momentz.editDurationAndResume(index, (VideoView.getDuration()) / 1000);
                //  Toast.makeText(this@MainActivity, "Video loaded from the internet", Toast.LENGTH_LONG).show()
                return true;
            }
            return false;
        });
    }

    public void gotoBack(View v) {
        super.onBackPressed();
    }

}
