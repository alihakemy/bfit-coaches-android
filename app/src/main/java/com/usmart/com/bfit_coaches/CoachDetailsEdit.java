package com.usmart.com.bfit_coaches;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import constants.Values;
import customLists.Coaches.CustomListCoachesRates;
import customLists.CustomListCoachesMedia2;
import customLists.CustomListDialogs;
import dataInLists.DataInAdEditCoach;
import dataInLists.DataInCoachesDetails;
import dataInLists.DataInDialog;
import dataInLists.DataInGlobal;
import dataInLists.DataInGlobal2;
import helpers.LangHolder;
import helpers.LoginHolder;
import helpers.NetWork;
import helpers.OnlineHolder;
import helpers.UpdateCoachHolder;
import helpers.UserHolder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.ExpandableHeightGridView;

public class CoachDetailsEdit extends FragmentActivity {
    Activity activity = CoachDetailsEdit.this;
    DataInCoachesDetails Data = new DataInCoachesDetails();
    CustomListCoachesMedia2 HallsMediaAdapter;
    CustomListCoachesMedia2 HallsVideoAdapter;
    CustomListCoachesRates RatesAdapter;
    ArrayList<String> Photos = new ArrayList<>();
    TextView MainTitle, tv_Day, tv_Time;
    ImageView HideAll, Logo;
    TextView tv_Desc, tv_Title, tv_Email;
    ProgressBar prog;
    Button btn_Buy;
    TabLayout MainTap;
    ExpandableHeightGridView lvMedia;
    String lang;
    DataInAdEditCoach CoachDetails = new DataInAdEditCoach();
    ArrayList<DataInDialog> Arrs = new ArrayList<>();
    LinearLayout WorkDays;
    byte MediaType = 1;

    private static final int PICK_IMAGE_Studio_Main = 1002;
    private static final int PICK_IMAGE_Studio_List = 1004;

    private static final int PICK_IMAGE_Studio_List_Video = 1008;

    String uploadedMainImg = "";
    String uploadedListImg = "";
    ArrayList<String> MainupImages = new ArrayList<>();
    private static final int INITIAL_REQUEST = 1337;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;
    private static final int READ_REQUEST = INITIAL_REQUEST + 2;
    private static final int WRITE_REQUEST = INITIAL_REQUEST + 3;
    private static final String[] CAMERA_PERMS = {
            android.Manifest.permission.CAMERA
    };
    private static final String[] READ_PERMS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static final String[] WRITE_PERMS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //  Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(activity, 2));
        setContentView(R.layout.activity_coach_details_edit);

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

        UpdateCoachHolder.getInstance().setData();
        HideAll = findViewById(R.id.HideAll);
        prog = findViewById(R.id.progressBar1);
        Logo = findViewById(R.id.Logo);
        tv_Desc = findViewById(R.id.tv_Desc);
        MainTap = findViewById(R.id.Tab);

        MainTitle = findViewById(R.id.MainTitle);
        tv_Day = findViewById(R.id.tv_Day);
        tv_Time = findViewById(R.id.tv_Time);
        WorkDays = findViewById(R.id.WorkDays);

        tv_Title = findViewById(R.id.tv_Title);
        tv_Email = findViewById(R.id.tv_Email);

        btn_Buy = findViewById(R.id.btn_Buy);
        lvMedia = findViewById(R.id.lvMedia);
        lvMedia.setExpanded(true);

        tv_Title.setOnClickListener(view -> {
            loadEditName();
        });
        tv_Desc.setOnClickListener(view -> {
            loadEditDesc();
        });
        WorkDays.setOnClickListener(view -> {
            loadDays();
        });
        tv_Time.setOnClickListener(view -> {
            loadTime();
        });
        TabLayout.Tab fTab;
        LinearLayout List;
        TextView CatTitle;
        ImageView ImgTap;

        fTab = MainTap.newTab();
        fTab.setCustomView(R.layout.singel_tap2);
        List = fTab.getCustomView().findViewById(R.id.List);
        CatTitle = fTab.getCustomView().findViewById(R.id.tv_Title);
        ImgTap = fTab.getCustomView().findViewById(R.id.ImgTap);
        ImgTap.setImageResource(R.mipmap.tap_img2);
        CatTitle.setText(R.string.Images);
        fTab.select();
        MainTap.addTab(fTab, 0);

        fTab = MainTap.newTab();
        fTab.setCustomView(R.layout.singel_tap);
        List = fTab.getCustomView().findViewById(R.id.List);
        CatTitle = fTab.getCustomView().findViewById(R.id.tv_Title);
        ImgTap = fTab.getCustomView().findViewById(R.id.ImgTap);
        ImgTap.setImageResource(R.mipmap.tap_video);
        CatTitle.setText(R.string.Videos);
        MainTap.addTab(fTab, 1);

        MainTap.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                LinearLayout List = tab.getCustomView().findViewById(R.id.List);
                TextView CatTitle = tab.getCustomView().findViewById(R.id.tv_Title);
                ImageView ImgTap = tab.getCustomView().findViewById(R.id.ImgTap);
                List.setBackgroundResource(R.drawable.border_bottom2);
                CatTitle.setTextColor(Color.parseColor("#50bcb4"));
                Log.i("TestApp", tab.getPosition() + "");
                if (tab.getPosition() == 0) {
                    MediaType = 1;
                    ImgTap.setImageResource(R.mipmap.tap_img2);
                    CatTitle.setText(R.string.Images);
                    lvMedia.setAdapter(HallsMediaAdapter);
                } else {
                    MediaType = 2;
                    ImgTap.setImageResource(R.mipmap.tap_video2);
                    CatTitle.setText(R.string.Videos);
                    lvMedia.setAdapter(HallsVideoAdapter);
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout List = tab.getCustomView().findViewById(R.id.List);
                TextView CatTitle = tab.getCustomView().findViewById(R.id.tv_Title);
                ImageView ImgTap = tab.getCustomView().findViewById(R.id.ImgTap);
                List.setBackgroundResource(0);
                CatTitle.setTextColor(Color.parseColor("#000000"));
                Log.i("TestApp", tab.getPosition() + "");
                if (tab.getPosition() == 0) {
                    MediaType = 1;
                    ImgTap.setImageResource(R.mipmap.tap_img);
                    CatTitle.setText(R.string.Images);
                } else {
                    MediaType = 2;
                    ImgTap.setImageResource(R.mipmap.tap_video);
                    CatTitle.setText(R.string.Videos);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btn_Buy.setOnClickListener(view -> {

            CoachDetails.setImage(uploadedMainImg);
            UpdateCoachHolder.getInstance().setData(CoachDetails);
            Log.i("TestApp", UpdateCoachHolder.getInstance().getData().toString());
            updateData();
        });

        lvMedia.setOnItemClickListener((adapterView, view, position, l) -> {
            if (position == 0) {
                if (MediaType == 1) {
                    Options options = Options.init()
                            .setRequestCode(PICK_IMAGE_Studio_List)                                           //Request code for activity results
                            .setCount(1)                                                   //Number of images to restict selection count
                            .setPreSelectedUrls(MainupImages)                               //Pre selected Image Urls
                            .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                            .setMode(Options.Mode.Picture)                                     //Option to select only pictures or videos or both
                            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                            .setPath("/pix/images");                                       //Custom Path For media Storage

                    Pix.start(CoachDetailsEdit.this, options);
                } else {
                    Options options = Options.init()
                            .setRequestCode(PICK_IMAGE_Studio_List_Video)                                           //Request code for activity results
                            .setCount(1)                                                   //Number of images to restict selection count
                            .setPreSelectedUrls(MainupImages)                               //Pre selected Image Urls
                            .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                            .setMode(Options.Mode.Video)                                     //Option to select only pictures or videos or both
                            .setVideoDurationLimitinSeconds(10)
                            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                            .setPath("/pix/videos");                                       //Custom Path For media Storage
                    Pix.start(CoachDetailsEdit.this, options);
                }
            }
        });
        Logo.setOnClickListener(v -> {

            Options options = Options.init()
                    .setRequestCode(PICK_IMAGE_Studio_Main)                                           //Request code for activity results
                    .setCount(1)                                                   //Number of images to restict selection count
                    .setPreSelectedUrls(MainupImages)                               //Pre selected Image Urls
                    .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                    .setMode(Options.Mode.Picture)                                     //Option to select only pictures or videos or both
                    .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                    .setPath("/pix/images");                                       //Custom Path For media Storage

            Pix.start(CoachDetailsEdit.this, options);

        });
        loadData();

    }

    private void loadData() {
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

                    MainTitle.setText(Data.data.basic.name + "");
                    CoachDetails.setName_ar(Data.data.basic.name);
                    CoachDetails.setName_en(Data.data.basic.name_en);
                    CoachDetails.setAbout_coach_ar(Data.data.basic.about_coach);
                    CoachDetails.setAbout_coach_en(Data.data.basic.about_coach_en);
                    CoachDetails.setWork_day_from(Data.data.basic.day_from.id);
                    CoachDetails.setWork_day_to(Data.data.basic.day_to.id);
                    CoachDetails.setWork_day_from_txt(Data.data.basic.day_from.name);
                    CoachDetails.setWork_day_to_txt(Data.data.basic.day_to.name);
                    CoachDetails.setTime_from(Data.data.work_times.time_from);
                    CoachDetails.setTime_to(Data.data.work_times.time_to);
                    UpdateCoachHolder.getInstance().setData(CoachDetails);


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

                  /*  Logo.setOnClickListener(v -> {
                        if (Data.data.basic.story != null) {
                            if (!Data.data.basic.story.isEmpty()) {
                                if (!Data.data.basic.story.equals("0")) {
                                    startActivity(new Intent(activity, Stories.class)
                                            .putExtra("Stories", Data.data.basic.story)
                                    );
                                }
                            }
                        }
                    });*/

                    DataInCoachesDetails HoCls = new DataInCoachesDetails();
                    DataInCoachesDetails.Media Obj = HoCls.new Media(0, "", "");
                    Data.data.media_image.add(0, Obj);
                    Data.data.media_video.add(0, Obj);
                    if (Data.data.media_image.size() > 0) {
                        // Media.setVisibility(View.VISIBLE);
                        HallsMediaAdapter = new CustomListCoachesMedia2(activity, Data.data.media_image);
                    }

                    if (Data.data.media_video.size() > 0) {
                        // Media.setVisibility(View.VISIBLE);
                        HallsVideoAdapter = new CustomListCoachesMedia2(activity, Data.data.media_video);
                    }


                    lvMedia.setAdapter(HallsMediaAdapter);

                    RatesAdapter = new CustomListCoachesRates(activity, Data.data.all_rates);


                    tv_Title.setText(Data.data.basic.name);

                    tv_Day.setText(
                            getString(R.string.From) + " " + Data.data.basic.day_from.name + " "
                                    + getString(R.string.To) + " " + Data.data.basic.day_to.name + " "
                    );
                    tv_Time.setText(
                            getString(R.string.From) + " " + Data.data.work_times.time_from + " "
                                    + getString(R.string.To) + " " + Data.data.work_times.time_to + " "
                    );
                    tv_Email.setText(Html.fromHtml(Data.data.basic.about) + " ");
                    tv_Email.setVisibility(View.GONE);
                    tv_Desc.setText(Html.fromHtml(Data.data.basic.about));


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

    private void updateData() {
        HideAll.setVisibility(View.VISIBLE);
        prog.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        String Url = Values.Link_service + "coach/update/ny_data/" + lang + "/v1";

        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body1.addFormDataPart("name", UpdateCoachHolder.getInstance().getData().getName_ar() + "");
        body1.addFormDataPart("name_en", UpdateCoachHolder.getInstance().getData().getName_en() + "");
        body1.addFormDataPart("about_coach", UpdateCoachHolder.getInstance().getData().getAbout_coach_ar() + "");
        body1.addFormDataPart("about_coach_en", UpdateCoachHolder.getInstance().getData().getAbout_coach_en() + "");
        body1.addFormDataPart("work_day_from", UpdateCoachHolder.getInstance().getData().getWork_day_from() + "");
        body1.addFormDataPart("work_day_to", UpdateCoachHolder.getInstance().getData().getWork_day_to() + "");
        body1.addFormDataPart("time_from", UpdateCoachHolder.getInstance().getData().getTime_from() + "");
        body1.addFormDataPart("time_to", UpdateCoachHolder.getInstance().getData().getTime_to() + "");
        if (!uploadedMainImg.isEmpty())
            body1.addFormDataPart("image", UpdateCoachHolder.getInstance().getData().getImage() + "");
        RequestBody body = body1.build();

        Request request = new Request.Builder()
                .url(Url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                        + " " + UserHolder.getInstance().getData().token.access_token)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TestApp_5", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    DataInGlobal Result;
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInGlobal>() {
                    }.getType();
                    String Res = response.body().string();
                    Log.i("TestApp", Res);
                    Result = g.fromJson(Res, t);

                    runOnUiThread(() -> {
                        HideAll.setVisibility(View.GONE);
                        prog.setVisibility(View.GONE);
                        if (!Result.success) {
                            loadMsg(getString(R.string.UpdateError), false);
                        } else {
                            loadMsg(getString(R.string.UpdateDone), false);
                        }
                    });

                } catch (Exception e) {
                }
            }

        });

    }

    private void updateMediaImage() {
        HideAll.setVisibility(View.VISIBLE);
        prog.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        String Url = Values.Link_service + "coach/media/store/" + lang + "/v1";

        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body1.addFormDataPart("type", "image");
        if (!uploadedListImg.isEmpty())
            body1.addFormDataPart("image", uploadedListImg + "");
        RequestBody body = body1.build();

        Request request = new Request.Builder()
                .url(Url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                        + " " + UserHolder.getInstance().getData().token.access_token)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TestApp_5", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    DataInGlobal2 Result;
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInGlobal2>() {
                    }.getType();
                    String Res = response.body().string();
                    Log.i("TestApp", Res);
                    Result = g.fromJson(Res, t);


                    runOnUiThread(() -> {
                        HideAll.setVisibility(View.GONE);
                        prog.setVisibility(View.GONE);
                        if (!Result.success) {
                            loadMsg(Result.message, false);
                        } else {
                            loadMsg(Result.message, false);
                        }
                    });

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            // Stuff that updates the UI
                            HideAll.setVisibility(View.GONE);
                            prog.setVisibility(View.GONE);
                        }
                    });
                }
            }

        });

    }

    private void updateMediaVideo(String im) {
        HideAll.setVisibility(View.VISIBLE);
        prog.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        String Url = Values.Link_service + "coach/media/store/" + lang + "/v1";
        Log.d("TAG", "uploading video url: " + Url);
        Log.d("TAG", "Post video url: " + Url);
        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body1.addFormDataPart("type", "video");
        //  if (!uploadedListImg.isEmpty()) {
        File tempFile = new File(im);
        //  body1.addFormDataPart("image", tempFile.getName() , RequestBody.);

        body1.addFormDataPart("image", tempFile.getName(), RequestBody.create(okhttp3.MediaType.parse("media/type"), new File(im)));
        // }
        RequestBody body = body1.build();

        Request request = new Request.Builder()
                .url(Url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                        + " " + UserHolder.getInstance().getData().token.access_token)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TestApp_5", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    DataInGlobal2 Result;
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInGlobal2>() {
                    }.getType();
                    String Res = response.body().string();
                    Log.i("TestApp", Res);
                    Result = g.fromJson(Res, t);

                    runOnUiThread(() -> {
                        HideAll.setVisibility(View.GONE);
                        prog.setVisibility(View.GONE);
                        if (!Result.success) {
                            loadMsg(Result.message, false);
                        } else {
                            loadMsg(Result.message, false);
                        }
                    });

                } catch (Exception e) {
                    HideAll.setVisibility(View.GONE);
                    prog.setVisibility(View.GONE);
                }
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

    private void loadEditName() {
        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_edit_name);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        EditText edit_Name_ar = dialog.findViewById(R.id.edit_Name_ar);
        EditText edit_Name_en = dialog.findViewById(R.id.edit_Name_en);
        ImageView MenuButton = dialog.findViewById(R.id.MenuButton);
        RelativeLayout LeftSide = dialog.findViewById(R.id.LeftSide);
        TextView MainTitle = dialog.findViewById(R.id.MainTitle);

        MainTitle.setText(R.string.EditName);
        edit_Name_ar.setText(UpdateCoachHolder.getInstance().getData().getName_ar() + "");
        edit_Name_en.setText(UpdateCoachHolder.getInstance().getData().getName_en() + "");

        LeftSide.setOnClickListener(view -> {
            tv_Title.setText(edit_Name_ar.getText().toString() + "");
            CoachDetails.setName_ar(edit_Name_ar.getText().toString());
            CoachDetails.setName_en(edit_Name_en.getText().toString());
            UpdateCoachHolder.getInstance().setData(CoachDetails);
            // tv_Email.setText(UpdateCoachHolder.getInstance().getData().getName_ar() + "");
            dialog.dismiss();
        });
        MenuButton.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void loadEditDesc() {
        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_edit_desc);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        EditText edit_Desc_ar = dialog.findViewById(R.id.edit_Desc_ar);
        EditText edit_Desc_en = dialog.findViewById(R.id.edit_Desc_en);
        ImageView MenuButton = dialog.findViewById(R.id.MenuButton);
        RelativeLayout LeftSide = dialog.findViewById(R.id.LeftSide);
        TextView MainTitle = dialog.findViewById(R.id.MainTitle);

        MainTitle.setText(R.string.EditDesc);

        edit_Desc_ar.setText(UpdateCoachHolder.getInstance().getData().getAbout_coach_ar() + "");
        edit_Desc_en.setText(UpdateCoachHolder.getInstance().getData().getAbout_coach_en() + "");

        LeftSide.setOnClickListener(view -> {
            tv_Desc.setText(edit_Desc_ar.getText().toString() + "");
            CoachDetails.setAbout_coach_ar(edit_Desc_ar.getText().toString());
            CoachDetails.setAbout_coach_en(edit_Desc_en.getText().toString());
            UpdateCoachHolder.getInstance().setData(CoachDetails);
            //   tv_Email.setText(UpdateCoachHolder.getInstance().getData().getAbout_coach_ar() + "");
            dialog.dismiss();
        });
        MenuButton.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void loadDays() {
        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_edit_work_day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        ImageView MenuButton = dialog.findViewById(R.id.MenuButton);
        RelativeLayout LeftSide = dialog.findViewById(R.id.LeftSide);
        TextView edit_From = dialog.findViewById(R.id.edit_From);
        TextView edit_To = dialog.findViewById(R.id.edit_To);
        TextView MainTitle = dialog.findViewById(R.id.MainTitle);

        MainTitle.setText(R.string.WorkDay);

        edit_From.setText(UpdateCoachHolder.getInstance().getData().getWork_day_from_txt() + "");
        edit_To.setText(UpdateCoachHolder.getInstance().getData().getWork_day_to_txt() + "");

        edit_From.setOnClickListener(view -> {
            Arrs.clear();
            for (DataInCoachesDetails.WorkDays day : Data.data.week_days) {
                Arrs.add(new DataInDialog(day.name, day.id));
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            CustomListDialogs adp;
            adp = new CustomListDialogs(activity, Arrs);
            builder.setAdapter(adp, (dialog1, which) -> {
                edit_From.setText(Arrs.get(which).name + "");
                CoachDetails.setWork_day_from(Arrs.get(which).id);
                CoachDetails.setWork_day_from_txt(Arrs.get(which).name);
                UpdateCoachHolder.getInstance().setData(CoachDetails);
            });
            builder.create().show();
        });

        edit_To.setOnClickListener(view -> {
            Arrs.clear();
            for (DataInCoachesDetails.WorkDays day : Data.data.week_days) {
                Arrs.add(new DataInDialog(day.name, day.id));
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            CustomListDialogs adp;
            adp = new CustomListDialogs(activity, Arrs);
            builder.setAdapter(adp, (dialog1, which) -> {
                edit_To.setText(Arrs.get(which).name + "");
                CoachDetails.setWork_day_to(Arrs.get(which).id);
                CoachDetails.setWork_day_to_txt(Arrs.get(which).name);
                UpdateCoachHolder.getInstance().setData(CoachDetails);
            });
            builder.create().show();
        });

        LeftSide.setOnClickListener(view -> {
            tv_Day.setText(
                    getString(R.string.From) + " " + UpdateCoachHolder.getInstance().getData().getWork_day_from_txt() + " "
                            + getString(R.string.To) + " " + UpdateCoachHolder.getInstance().getData().getWork_day_to_txt() + " "
            );
            dialog.dismiss();
        });
        MenuButton.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void loadTime() {
        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_edit_work_day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
        ImageView MenuButton = dialog.findViewById(R.id.MenuButton);
        RelativeLayout LeftSide = dialog.findViewById(R.id.LeftSide);
        TextView edit_From = dialog.findViewById(R.id.edit_From);
        TextView edit_To = dialog.findViewById(R.id.edit_To);
        TextView MainTitle = dialog.findViewById(R.id.MainTitle);

        MainTitle.setText(R.string.WorkTimes3);

        edit_From.setText(UpdateCoachHolder.getInstance().getData().getTime_from() + "");
        edit_To.setText(UpdateCoachHolder.getInstance().getData().getTime_to() + "");

        edit_From.setOnClickListener(view -> {
            int mHour, mMinute;
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                    (timePicker, hourOfDay, minute) -> {
                        String minuteTxt = "";
                        if (minute < 10)
                            minuteTxt = "0" + minute;
                        else
                            minuteTxt = minute + "";
                        edit_From.setText(hourOfDay + ":" + minuteTxt + ":00");
                    }
                    , mHour, mMinute, false);
            timePickerDialog.show();
        });

        edit_To.setOnClickListener(view -> {
            int mHour, mMinute;
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                    (timePicker, hourOfDay, minute) -> {
                        String minuteTxt = "";
                        if (minute < 10)
                            minuteTxt = "0" + minute;
                        else
                            minuteTxt = minute + "";
                        edit_To.setText(hourOfDay + ":" + minuteTxt + ":00");
                    }
                    , mHour, mMinute, false);
            timePickerDialog.show();
        });

        LeftSide.setOnClickListener(view -> {
            CoachDetails.setTime_from(edit_From.getText().toString() + "");
            CoachDetails.setTime_to(edit_To.getText().toString() + "");
            UpdateCoachHolder.getInstance().setData(CoachDetails);

            tv_Time.setText(
                    getString(R.string.From) + " " + UpdateCoachHolder.getInstance().getData().getTime_from() + " "
                            + getString(R.string.To) + " " + UpdateCoachHolder.getInstance().getData().getTime_to() + " "
            );
            dialog.dismiss();
        });
        MenuButton.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private boolean canAccessCamera() {
        return (hasPermission(android.Manifest.permission.CAMERA));
    }

    private boolean canReadStorage() {
        return (hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE));
    }

    private boolean canWriteStorage() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
        }
        return false;
    }

    @Override
    protected void onResume() {
        if (!canAccessCamera()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
            }
        }
        if (!canReadStorage()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(READ_PERMS, READ_REQUEST);
            }
        }
        if (!canWriteStorage()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(WRITE_PERMS, WRITE_REQUEST);
            }
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_Studio_Main && data != null && resultCode != RESULT_CANCELED) {
            ArrayList<String> selectedImages = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            if (selectedImages.size() > 0) {
                for (String im : selectedImages) {
                    if (!im.isEmpty()) {
                        Log.i("TestApp", im + "");
                        UploadPhotos(im, (byte) 1);
                        Log.i("TestApp_Main", uploadedMainImg + "");
                       /* View view = LayoutInflater.from(activity).inflate(R.layout.single_imageview, null);
                        ImageView NewPhoto = view.findViewById(R.id.newPhoto);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(350,
                                273);
                        lp.leftMargin = 3;
                        lp.rightMargin = 10;
                        NewPhoto.setLayoutParams(lp);*/
                        DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.mipmap.add_photo_main).showImageForEmptyUri(R.mipmap.add_photo_main)
                                .showImageOnFail(R.mipmap.add_photo_main).cacheInMemory(true).cacheOnDisk(true)
                                .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                                .bitmapConfig(Bitmap.Config.RGB_565)
                                .build();

                        ImageLoader.getInstance().displayImage("file://" + im, Logo, options);
                        //   PhotosFrame.addView(view);
                        //  MaxMain--;
                    }
                }
            }
        }


        if (requestCode == PICK_IMAGE_Studio_List && data != null && resultCode != RESULT_CANCELED) {
            ArrayList<String> selectedImages = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            if (selectedImages.size() > 0) {
                for (String im : selectedImages) {
                    if (!im.isEmpty()) {
                        Log.i("TestApp", im + "");
                        UploadPhotos(im, (byte) 2);
                        Log.i("TestApp_Main", im + "");

                        DataInCoachesDetails HoCls = new DataInCoachesDetails();
                        DataInCoachesDetails.Media Obj = HoCls.new Media(999, "file://" + im, "image_temp");
                        HallsMediaAdapter.add(Obj);
                        HallsMediaAdapter.notifyDataSetChanged();
                        updateMediaImage();
                      /*  DisplayImageOptions options = new DisplayImageOptions.Builder()
                                .showImageOnLoading(R.mipmap.add_photo_main).showImageForEmptyUri(R.mipmap.add_photo_main)
                                .showImageOnFail(R.mipmap.add_photo_main).cacheInMemory(true).cacheOnDisk(true)
                                .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                                .bitmapConfig(Bitmap.Config.RGB_565)
                                .build();

                        ImageLoader.getInstance().displayImage("file://" + im, Logo, options);*/
                        //   PhotosFrame.addView(view);
                        //  MaxMain--;
                    }
                }
            }
        }

        if (requestCode == PICK_IMAGE_Studio_List_Video && data != null && resultCode != RESULT_CANCELED) {
            ArrayList<String> selectedImages = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            if (selectedImages.size() > 0) {
                for (String im : selectedImages) {
                    if (!im.isEmpty()) {
                        Log.i("TestApp", im + "");
                        // UploadVideos(im, (byte) 2);


                        String path = im;
                        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
                                MediaStore.Images.Thumbnails.MINI_KIND);
                        Log.i("TestApp_Main", getRealPathFromURI(getImageUri(activity, thumb)) + "");
                        DataInCoachesDetails HoCls = new DataInCoachesDetails();
                        DataInCoachesDetails.Media Obj = HoCls.new Media(999, "file://" + getRealPathFromURI(getImageUri(activity, thumb)), "image_temp");
                        HallsVideoAdapter.add(Obj);
                        HallsVideoAdapter.notifyDataSetChanged();
                        updateMediaVideo(im);

                    }
                }
            }
        }


    }

    public void UploadPhotos(final String uploaded, byte i) {

        Bitmap bitmap2;

        bitmap2 = decodeFile(uploaded);
        // bitmap = imageOreintationValidator(bitmap2, uploaded);
              /*  resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.75),
                        (int) (bitmap.getHeight() * 0.75), true);*/

        Log.i("TestApp_22", bitmap2.toString() + "");

        if (i == 1) {
            String X = UploadImage(bitmap2);
            uploadedMainImg = X;
        } else if (i == 2) {
            String X = UploadImage(bitmap2);
            uploadedListImg = X;
        } else {
            //upImages2.remove(bitmap2);
        }
    }

    public void UploadVideos(String im, byte i) {
        File tempFile = new File(im);
        String encodedString = null;

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(tempFile);
        } catch (Exception e) {
            // TODO: handle exception
        }
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        encodedString = Base64.encodeToString(bytes, 1);
        if (i == 2) {
            uploadedListImg = encodedString;
        }
        // Log.i("Strng", encodedString);
    }

    public String UploadImage(Bitmap bit) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 90, stream); // compress to
        byte[] byte_arr = stream.toByteArray();
        String YY = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        YY = YY.replace(" ", "").replace("\n", "");

        Log.i("TestApp_2233", YY + "");
        return YY;
    }

    /*public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, null, null);
        return Uri.parse(path);
    }*/

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public Bitmap decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 2400;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeFile(filePath, o2);

    }

    public void gotoBack(View v) {
        onBackPressed();
    }

    public void gotoBack() {
        onBackPressed();

    }

    public void gotoCart(View v) {

    }

    public void gotoShare(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "http://onelink.to/mooda");
        intent.putExtra(Intent.EXTRA_SUBJECT, activity.getResources().getString(R.string.app_name));
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share"));
    }


}
