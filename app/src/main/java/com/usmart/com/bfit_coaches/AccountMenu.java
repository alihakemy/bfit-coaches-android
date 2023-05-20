package com.usmart.com.bfit_coaches;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.usmart.com.bfit_coaches.Coaches.MyOrdersCoaches;

import java.util.ArrayList;
import java.util.Locale;

import constants.Values;
import customLists.CustomListMenuAccount;
import dataInLists.DataInListIcons;
import dataInLists.DataInUser;
import helpers.FaceIdHolder;
import helpers.LangHolder;
import helpers.LoginHolder;
import helpers.NetWork;
import helpers.OnlineHolder;
import helpers.UserHolder;


public class AccountMenu extends Activity {
    SharedPreferences Login;
    Activity activity = AccountMenu.this;
    DataInUser _DataUser;
    ArrayList<DataInListIcons> _Data = new ArrayList<>();
    ExpandableHeightListView lv;
    CustomListMenuAccount adapter2;
    TextView UserName, Mobile;
    ImageView HideAll;
    ProgressBar prog;
    String lang;

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
        getWindow().setStatusBarColor(Color.parseColor("#50BCAF"));
        getWindow().getDecorView().setSystemUiVisibility(0);
        //*******************************************************
        setContentView(R.layout.activity_account_menu);
        init();

        //**************** Set Adapter ***********************
        adapter2 = new CustomListMenuAccount(activity, _Data);
        lv.setAdapter(adapter2);
        lv.setExpanded(true);
        //****************************** On Item Click ************
        lv.setOnItemClickListener((parent, view, position, id) -> {

            if (adapter2.getItem(position).id == 1) {
                Intent i = new Intent(activity, SelectPlans.class);
                startActivity(i);
                //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }
            if (adapter2.getItem(position).id == 2) {
                Intent i = new Intent(activity, Notis.class);
                startActivity(i);
                //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }
            if (adapter2.getItem(position).id == 3) {
                Intent i = new Intent(activity, MyOrdersCoaches.class);
                startActivity(i);
                //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }
            if (adapter2.getItem(position).id == 4) {
                Intent i = new Intent(activity, Conditions.class);
                startActivity(i);
                //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }

            if (adapter2.getItem(position).id == 5) {
                Intent i = new Intent(activity, About.class);
                startActivity(i);
                //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }
            if (adapter2.getItem(position).id == 6) {
                //selectLang();
                if (LangHolder.getInstance().getData().equals("ar")) {
                    LangHolder.getInstance().setData("en");
                    SharedPreferences.Editor editor = activity
                            .getSharedPreferences(Values.SharedPreferences_FileNameLangSelect, 0).edit();
                    editor.putString("Lang", "en");
                    editor.commit();

                    Intent intent = new Intent(activity, AccountMenu.class);
                    activity.finish();
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.fadein_fast, R.anim.fadeout_fast);
                } else {
                    LangHolder.getInstance().setData("ar");
                    SharedPreferences.Editor editor = activity
                            .getSharedPreferences(Values.SharedPreferences_FileNameLangSelect, 0).edit();
                    editor.putString("Lang", "ar");
                    editor.commit();

                    Intent intent = new Intent(activity, AccountMenu.class);
                    activity.finish();
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.fadein_fast, R.anim.fadeout_fast);
                }
            }
          /*  if (adapter2.getItem(position).id == 6) {
                Intent i = new Intent(activity, MyOrders.class);
                startActivity(i);
                //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }

            if (adapter2.getItem(position).id == 7) {
                Intent i = new Intent(activity, MyPoints.class);
                startActivity(i);
                //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }

            if (adapter2.getItem(position).id == 8) {
                Intent i = new Intent(activity, MyFav.class);
                startActivity(i);
                //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }*/

         /*   if (adapter2.getItem(position).id == 9) {
                LoginHolder.getInstance().setData("logout");
                FaceIdHolder.getInstance().setData("0");

                Login = getSharedPreferences(Values.SharedPreferences_FileName, 0);
                SharedPreferences.Editor editor = Login.edit();
                editor.putString("isLogin", "logout");
                editor.putString("UserID", "0");
                editor.putString("User", "");
                editor.commit();

                Intent i = new Intent(activity, Home.class);
                startActivity(i);
            }*/


           /* if (adapter2.getItem(position).id == 7) {
                LoginHolder.getInstance().setData("logout");
                FaceIdHolder.getInstance().setData("0");

                Login = getSharedPreferences(Values.SharedPreferences_FileName, 0);
                SharedPreferences.Editor editor = Login.edit();
                editor.putString("isLogin", "logout");
                editor.putString("UserID", "0");
                editor.putString("User", "");
                editor.commit();

                Intent i = new Intent(activity, Home.class);
                startActivity(i);
                //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
            }*/
        });
        //*********************************************************
        loadMenuItems();
    }

    private void init() {
        if (LangHolder.getInstance().getData().equals("ar")) {
            lang = "ar";
        } else {
            lang = "en";
        }
        // ********************* Call Views  *****************
        HideAll = findViewById(R.id.HideAll);
        prog = findViewById(R.id.progressBar1);

        lv = findViewById(R.id.listViewOrders);
        UserName = findViewById(R.id.UserName);
        Mobile = findViewById(R.id.Mobile);

        // *************************************************
    }

    private void loadMenuItems() {
        String[] navMenuTitles = getResources().getStringArray(R.array.nav_account);

        adapter2.add(new DataInListIcons(navMenuTitles[0],
                "drawable://" + R.mipmap.menu_a_01, 1));

        adapter2.add(new DataInListIcons(navMenuTitles[1],
                "drawable://" + R.mipmap.menu_a_02, 2));

        adapter2.add(new DataInListIcons(navMenuTitles[2],
                "drawable://" + R.mipmap.menu_a_03, 3));

        adapter2.add(new DataInListIcons(navMenuTitles[3],
                "drawable://" + R.mipmap.menu_a_04, 4));
        adapter2.add(new DataInListIcons(navMenuTitles[4],
                "drawable://" + R.mipmap.menu_a_05, 5));

        if (LangHolder.getInstance().getData().equals("ar")) {
            adapter2.add(new DataInListIcons(navMenuTitles[6],
                    "drawable://" + R.mipmap.menu_a_05, 6));
        } else {
            adapter2.add(new DataInListIcons(navMenuTitles[5],
                    "drawable://" + R.mipmap.menu_a_05, 6));
        }
        adapter2.notifyDataSetChanged();
    }

    private void loadAccountData() {
        UserName.setText(UserHolder.getInstance().getData().name + "");
        Mobile.setOnClickListener(view -> {
            LoginHolder.getInstance().setData("logout");
            FaceIdHolder.getInstance().setData("0");

            Login = getSharedPreferences(Values.SharedPreferences_FileName, 0);
            SharedPreferences.Editor editor = Login.edit();
            editor.putString("isLogin", "logout");
            editor.putString("UserID", "0");
            editor.putString("User", "");
            editor.commit();

            Intent i = new Intent(activity, Home.class);
            startActivity(i);
        });
        // Mobile.setText(UserHolder.getInstance().getData().email + "");
    }

    public void selectLang() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_noads_options);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView Yes = dialog.findViewById(R.id.btn_2);
        TextView No = dialog.findViewById(R.id.btn_1);
        TextView Cancel = dialog.findViewById(R.id.btn_3);

        Yes.setText(R.string.Arabic);
        No.setText(R.string.English);
        Yes.setOnClickListener(v -> {

            LangHolder.getInstance().setData("ar");
            SharedPreferences.Editor editor = activity
                    .getSharedPreferences(Values.SharedPreferences_FileNameLangSelect, 0).edit();
            editor.putString("Lang", "ar");
            editor.commit();
            Intent intent = new Intent(activity, Home.class);
            activity.finish();
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fadein_fast, R.anim.fadeout_fast);

            dialog.dismiss();
        });
        No.setOnClickListener(v -> {
            LangHolder.getInstance().setData("en");
            SharedPreferences.Editor editor = activity
                    .getSharedPreferences(Values.SharedPreferences_FileNameLangSelect, 0).edit();
            editor.putString("Lang", "en");
            editor.commit();
            Intent intent = new Intent(activity, Home.class);
            activity.finish();
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fadein_fast, R.anim.fadeout_fast);
            dialog.dismiss();
        });
        Cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        loadAccountData();
        //********************** Footer Menu  ********************
        BottomNavigationViewEx navigation = findViewById(R.id.navigation);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.setTextSize(12);
        navigation.enableItemShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_more);

        //************************** Login Mode **********************
        try {
            if (LoginHolder.getInstance().getData().equals("login")) {
                SharedPreferences.Editor editor = Login.edit();
                editor.putString("isLogin", "login");
                editor.putString("UserID", FaceIdHolder.getInstance().getData());
                editor.putString("User", UserHolder.getInstance().getData().toString());

                editor.commit();

            } else {
                Login = getSharedPreferences(Values.SharedPreferences_FileName, 0);
                LoginHolder.getInstance().setData("logout");
                FaceIdHolder.getInstance().setData("0");
                SharedPreferences.Editor editor = Login.edit();
                editor.putString("isLogin", "logout");
                editor.putString("UserID", "0");
                editor.putString("User", "");
                editor.commit();

                startActivity(new Intent(activity, Login.class));
            }
        } catch (Exception e) {

        }
        //******************* Offline Mode **********************
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                startActivity(new Intent(activity, Home.class));
                return true;
            case R.id.navigation_offer:
                startActivity(new Intent(activity, Chats.class));
                return true;
          /*  case R.id.navigation_search:
                startActivity(new Intent(activity, Search.class));
                return true;*/
            case R.id.navigation_account:
                startActivity(new Intent(activity, CoachDetails.class));
                // startActivity(new Intent(activity, StoreCats.class));
                // overridePendingTransition(R.anim.slide_bottom_to_top, R.anim.slide_top_to_bottom);
                return true;
            case R.id.navigation_more:
                //startActivity(new Intent(activity, AccountMenu.class));
                // overridePendingTransition(R.anim.slide_bottom_to_top, R.anim.slide_top_to_bottom);
                return true;
        }
        return false;
    };


    public void gotoBack(View v) {
        onBackPressed();
    }

    public void gotoUpload(View v) {
        Intent i = new Intent(activity, AccountMenu.class);
        startActivity(i);
        //overridePendingTransition(R.anim.fadein_fast, R.anim.fadeout_fast);
    }

    public void gotoLogout(View v) {
        LoginHolder.getInstance().setData("logout");
        FaceIdHolder.getInstance().setData("0");

        Login = getSharedPreferences(Values.SharedPreferences_FileName, 0);
        SharedPreferences.Editor editor = Login.edit();
        editor.putString("isLogin", "logout");
        editor.putString("UserID", "0");
        editor.putString("User", "");
        editor.commit();

        Intent i = new Intent(activity, Home.class);
        startActivity(i);
    }

    public void gotoAction(View v) {

    }

}
