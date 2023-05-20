package com.usmart.com.bfit_coaches;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import constants.Values;
import customLists.Coaches.CustomListCoachesMedia;
import customLists.Coaches.CustomListCoachesRates;
import customLists.Coaches.CustomListCoachesWorkTime;
import dataInLists.DataInCoachesDetails;
import helpers.LangHolder;
import helpers.LoginHolder;
import helpers.NetWork;
import helpers.OnlineHolder;
import helpers.UserHolder;


public class CoachDetails extends FragmentActivity {
    Activity activity = CoachDetails.this;
    DataInCoachesDetails Data = new DataInCoachesDetails();
    CustomListCoachesWorkTime WorkTimesAdapter;
    CustomListCoachesMedia HallsMediaAdapter;
    CustomListCoachesRates RatesAdapter;
    ArrayList<String> Photos = new ArrayList<>();
    TextView MainTitle , WorkTime;
    ImageView HideAll, btn_Favorite, Logo;
    EditText edit_Desc;
    ProgressBar prog;
    Button btn_Edit ;
    TextView tv_Title, tv_Desc, Rate, AllRates, tv_Plans;
    ProgressBar ProgressBar_1, ProgressBar_2, ProgressBar_3, ProgressBar_4, ProgressBar_5;
    ExpandableHeightListView lvRates;
    RecyclerView lvMedia;
    String lang;
    boolean IsFav = false;
    ScrollView Scroll;


    LinearLayout Media;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //  Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(activity, 2));
        setContentView(R.layout.activity_coach_details);

        // ************ Custom Action Bar *****************
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
        getWindow().setStatusBarColor(Color.parseColor("#50BCAF"));
        getWindow().getDecorView().setSystemUiVisibility(0);
        // ***********************************************
        if (LangHolder.getInstance().getData().equals("ar")) {
            lang = "ar";
        } else {
            lang = "en";
        }

        // ******************** Sliding Menu *****************


        HideAll = findViewById(R.id.HideAll);
        prog = findViewById(R.id.progressBar1);
        Scroll = findViewById(R.id.Scroll);
        Logo = findViewById(R.id.Logo);
        edit_Desc = findViewById(R.id.edit_Desc);

        MainTitle = findViewById(R.id.MainTitle);
        Media = findViewById(R.id.Media);

        tv_Title = findViewById(R.id.tv_Title);
        tv_Desc = findViewById(R.id.tv_Desc);
        Rate = findViewById(R.id.Rate);
        AllRates = findViewById(R.id.AllRates);
        tv_Plans = findViewById(R.id.tv_Plans);
        WorkTime = findViewById(R.id.WorkTime);

        ProgressBar_1 = findViewById(R.id.ProgressBar_1);
        ProgressBar_2 = findViewById(R.id.ProgressBar_2);
        ProgressBar_3 = findViewById(R.id.ProgressBar_3);
        ProgressBar_4 = findViewById(R.id.ProgressBar_4);
        ProgressBar_5 = findViewById(R.id.ProgressBar_5);


        btn_Favorite = findViewById(R.id.Fav);
        btn_Edit = findViewById(R.id.btn_Edit);
        lvMedia = findViewById(R.id.lvMedia);
        lvRates = findViewById(R.id.lvRates);

       /* WorkTimesAdapter = new CustomListCoachesWorkTime(activity, new ArrayList<>());

        lvRates.setExpanded(true);
        WorkTimes.setExpanded(true);
        WorkTimes.setAdapter(WorkTimesAdapter);*/
        Scroll.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == 0) {
                // ActionBar.setBackgroundColor(Color.TRANSPARENT);
                // Log.i("TestApp_Pos", "Top");
                //  getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                //  Log.i("TestApp_Pos", "Not Top");
                //   getWindow().setStatusBarColor(Color.parseColor("#a9d44e"));

                // ActionBar.setBackgroundColor(Color.parseColor("#2699FB"));
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 1);
        gridLayoutManager.setSpanCount(1);
        RecyclerView.LayoutManager layoutManager = new
                GridLayoutManager(activity, 1, LinearLayoutManager.HORIZONTAL, false);

        lvMedia.setLayoutManager(layoutManager);


      /*  btn_Favorite.setOnClickListener(view -> {
            if (IsFav) {
                RemoveFave(Data.data.basic.id, btn_Favorite);
            } else {
                AddFave(Data.data.basic.id, btn_Favorite);
            }
        });*/
        btn_Edit.setOnClickListener(view -> {
            startActivity(new Intent(activity , CoachDetailsEdit.class));
        });
        /*iv_Add.setOnClickListener(v -> {
            int n = Integer.parseInt(tv_amount.getText().toString());

            if (n <= Remain) {
                n++;
                tv_amount.setText(Integer.toString(n));
                float x = XPrice * n;
                tv_Price.setText(x + " " + getResources().getString(R.string.DK));
            } else {
                if (n > Remain) {
                    if (Remain > 0)
                        tv_amount.setText(Integer.toString(Remain));
                }
                loadMsg(getResources().getString(R.string.NotEnough), false);
            }

        });

        iv_Remove.setOnClickListener(v -> {
            int n = Integer.parseInt(tv_amount.getText().toString());
            if (n > 1) {
                n--;
                tv_amount.setText(Integer.toString(n));
                float x = XPrice * n;
                tv_Price.setText(x + " " + getResources().getString(R.string.DK));
            }

        });*/



    }

    public void loadData() {
        AsyncHttpClient client = new AsyncHttpClient();
        String Url;

        Url = Values.Link_service + "coach/my_data/" + lang + "/v1/";
        client.addHeader("Content-Type", "application/json");
        if (LoginHolder.getInstance().getData().equals("login")) {
            client.addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                    + " " + UserHolder.getInstance().getData().token.access_token);
        } else {
            client.addHeader("Authorization", "" + Values.Authorization_User);
        }

        Log.i("TestApp", Url);
        client.get(Url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // TODO Auto-generated method stub
                super.onStart();
                HideAll.setVisibility(View.VISIBLE);
                prog.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int arg0, String arg1) {
                // TODO Auto-generated method stub
                super.onSuccess(arg0, arg1);
                Log.i("TestApp", arg1);
                try {
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInCoachesDetails>() {
                    }.getType();
                    Data = g.fromJson(arg1, t);
                    DisplayImageOptions options, options2;
                    options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.mipmap.def)
                            .showImageForEmptyUri(R.mipmap.def)
                            .showImageOnFail(R.mipmap.def)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .considerExifParams(true)
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .build();

                    options2 = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.mipmap.def_icon)
                            .showImageForEmptyUri(R.mipmap.def_icon)
                            .showImageOnFail(R.mipmap.def_icon)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .considerExifParams(true)
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .build();

                    ImageLoader.getInstance().displayImage(Values.Link_ImageHomeCats + Data.data.basic.image, Logo, options2);

                    Logo.setOnClickListener(v -> {
                        if (Data.data.basic.story != null) {
                            if (!Data.data.basic.story.isEmpty()) {
                                if (!Data.data.basic.story.equals("0")) {
                                    startActivity(new Intent(activity, Stories.class)
                                            .putExtra("Stories", Data.data.basic.story)
                                    );
                                }
                            }
                        }
                    });

                    ProgressBar_1.setMax(Data.data.rates_count);
                    ProgressBar_2.setMax(Data.data.rates_count);
                    ProgressBar_3.setMax(Data.data.rates_count);
                    ProgressBar_4.setMax(Data.data.rates_count);
                    ProgressBar_5.setMax(Data.data.rates_count);

                    ProgressBar_1.setProgress(Data.data.stars_count.one);
                    ProgressBar_2.setProgress(Data.data.stars_count.tow);
                    ProgressBar_3.setProgress(Data.data.stars_count.three);
                    ProgressBar_4.setProgress(Data.data.stars_count.four);
                    ProgressBar_5.setProgress(Data.data.stars_count.five);

                    WorkTime.setText(
                            getString(R.string.WorkTimes2) + " : " + getString(R.string.From) + " " + Data.data.basic.day_from.name + " "
                                    + getString(R.string.To) + " " + Data.data.basic.day_to.name + " " + Data.data.work_times.time_from + " - " +
                                    Data.data.work_times.time_to
                    );


                    if (Data.data.media_image.size() > 0) {
                        Media.setVisibility(View.VISIBLE);
                        HallsMediaAdapter = new CustomListCoachesMedia(activity, Data.data.media_image);
                        lvMedia.setAdapter(HallsMediaAdapter);
                    }

                    RatesAdapter = new CustomListCoachesRates(activity, Data.data.all_rates);


                    lvRates.setAdapter(RatesAdapter);
                    tv_Title.setText(Data.data.basic.name);
                    Rate.setText(Data.data.basic.rate + "");
                    tv_Plans.setText(Data.data.plans_num + " " + getResources().getString(R.string.Plans));


                    int x = Data.data.basic.about.length();
                    if (x >= 250) {
                        tv_Desc.setText(Html.fromHtml(Data.data.basic.about.substring(0, 250)) + " ...");
                    } else {
                        tv_Desc.setText(Html.fromHtml(Data.data.basic.about) + " ");
                    }
                    //  tv_Desc.setText(Html.fromHtml(Data.data.product.description));


                    tv_Desc.setOnClickListener(v -> {
                       /* Intent i = new Intent(activity, ProductDesc.class);
                        i.putExtra("Description", Data.data.basic.about);
                        startActivity(i);*/
                    });
                    AllRates.setOnClickListener(v -> {
                        loadRates();
                    });
                    IsFav = Data.data.favorite;
                   /* if (Data.data.favorite) {

                        btn_Favorite.setImageResource(R.mipmap.like);
                    } else {
                        btn_Favorite.setImageResource(R.mipmap.non_like);
                    }*/

                } catch (Exception e) {
                    Log.i("TestApp", e.getMessage());
                }

            }

            @SuppressWarnings("deprecation")
            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                super.onFailure(arg0);
                NetWork.gotoError(activity);
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();

                HideAll.setVisibility(View.GONE);
                prog.setVisibility(View.GONE);

            }
        });

    }



    private void loadPhoto() {
        Intent i = new Intent(activity, PhotoGallery.class);
        i.putExtra("Photos", Photos);
        startActivity(i);
        //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }


    private void loadMsg(String MSG, boolean cart) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_noads3);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView Title = dialog.findViewById(R.id.MsgTitle);
        TextView Text = dialog.findViewById(R.id.MsgText);

        Button Yes = dialog.findViewById(R.id.btn_Yes);
        Button No = dialog.findViewById(R.id.btn_No);

        No.setText(R.string.OK);
        Yes.setText(R.string.Login);
        Text.setText(MSG + "");
        Yes.setVisibility(View.GONE);


        No.setOnClickListener(v -> {
            dialog.dismiss();
            if (cart) {
            }
        });
        /*No.setOnClickListener(v -> {
            dialog.dismiss();

            Intent i = new Intent(activity, Login.class);
            i.putExtra("ProdID", ID);
            startActivity(i);

        });*/
        dialog.show();
    }


    private void loadRates() {
        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_rates);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        ListView lisPlans = dialog.findViewById(R.id.lisPlans);
        TextView Rate = dialog.findViewById(R.id.Rate);
        ProgressBar ProgressBar_1 = dialog.findViewById(R.id.ProgressBar_1);
        ProgressBar ProgressBar_2 = dialog.findViewById(R.id.ProgressBar_2);
        ProgressBar ProgressBar_3 = dialog.findViewById(R.id.ProgressBar_3);
        ProgressBar ProgressBar_4 = dialog.findViewById(R.id.ProgressBar_4);
        ProgressBar ProgressBar_5 = dialog.findViewById(R.id.ProgressBar_5);

        Rate.setText(Data.data.basic.rate + "");

        ProgressBar_1.setMax(Data.data.rates_count);
        ProgressBar_2.setMax(Data.data.rates_count);
        ProgressBar_3.setMax(Data.data.rates_count);
        ProgressBar_4.setMax(Data.data.rates_count);
        ProgressBar_5.setMax(Data.data.rates_count);

        ProgressBar_1.setProgress(Data.data.stars_count.one);
        ProgressBar_2.setProgress(Data.data.stars_count.tow);
        ProgressBar_3.setProgress(Data.data.stars_count.three);
        ProgressBar_4.setProgress(Data.data.stars_count.four);
        ProgressBar_5.setProgress(Data.data.stars_count.five);


        CustomListCoachesRates adapter2;
        Data.data.all_rates.addAll(Data.data.all_rates);
        Data.data.all_rates.addAll(Data.data.all_rates);
        Data.data.all_rates.addAll(Data.data.all_rates);
        Data.data.all_rates.addAll(Data.data.all_rates);
        Data.data.all_rates.addAll(Data.data.all_rates);
        Data.data.all_rates.addAll(Data.data.all_rates);
        Data.data.all_rates.addAll(Data.data.all_rates);
        adapter2 = new CustomListCoachesRates(activity, Data.data.all_rates);
        lisPlans.setAdapter(adapter2);

        /*No.setOnClickListener(v -> {
            dialog.dismiss();

            Intent i = new Intent(activity, Login.class);
            i.putExtra("ProdID", ID);
            startActivity(i);

        });*/
        dialog.show();
    }

    @Override
    protected void onResume() {
        loadData();
        try {
            if (OnlineHolder.getInstance().getData().equals("1")) {
                OnlineHolder.getInstance().setData("0");
                finish();
                startActivity(getIntent());
                //overridePendingTransition(R.anim.fadein_fast, R.anim.fadeout_fast);

            } else if (NetWork.isNetworkAvailable(activity) == false) {
                NetWork.gotoError(activity);
            }
        } catch (Exception e) {

        }

        super.onResume();

    }

    public void gotoBack(View v) {
        onBackPressed();
    }

    public void gotoBack() {
        onBackPressed();

    }

    public void gotoSetting(View v) {
        startActivity(new Intent(activity, AccountMenu.class));
    }

    public void gotoShare(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "http://onelink.to/mooda");
        intent.putExtra(Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.app_name));
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share"));
    }


}
