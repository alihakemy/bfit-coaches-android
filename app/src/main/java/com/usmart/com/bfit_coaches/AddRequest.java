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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import constants.Values;
import dataInLists.DataInGlobal2;
import dataInLists.DataInPlanDetails;
import dataInLists.DataInRequest;
import helpers.FaceIdHolder;
import helpers.LangHolder;
import helpers.LoginHolder;
import helpers.UserHolder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddRequest extends FragmentActivity {
    Activity activity = AddRequest.this;
    SharedPreferences Login;
    EditText edit_Name_ar, edit_Name_en, edit_Package_ar, edit_Package_en, edit_Package_period,
            edit_Package_price, edit_Package_discount, edit_Package_After_discount;

    Switch Chk;
    TextInputLayout After_discount, Discount;
    LinearLayout OptionsItems;
    ArrayList<DataInPlanDetails> OptionData = new ArrayList<>();
    DataInGlobal2 _Data = new DataInGlobal2();
    Button btn_Add;
    TextView MainTitle;
    ImageView HideAll;
    ImageView Add_Details;
    ProgressBar prog;
    String lang;
    byte IsDiscount = 0;
    int details = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(activity, 2));
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
        // ***********************************************
        //*******************************************************
        setContentView(R.layout.activity_add_request);
        // ***********************************************
        if (LangHolder.getInstance().getData().equals("ar")) {
            lang = "ar";
        } else {
            lang = "en";
        }


        // Test = findViewById(R.id.Test);
        HideAll = findViewById(R.id.HideAll);
        prog = findViewById(R.id.progressBar1);

        MainTitle = findViewById(R.id.MainTitle);

        edit_Name_ar = findViewById(R.id.edit_Name_ar);
        edit_Name_en = findViewById(R.id.edit_Name_en);
        edit_Package_ar = findViewById(R.id.edit_Package_ar);
        edit_Package_en = findViewById(R.id.edit_Package_en);
        edit_Package_period = findViewById(R.id.edit_Package_period);
        edit_Package_price = findViewById(R.id.edit_Package_price);
        edit_Package_discount = findViewById(R.id.edit_Package_discount);
        edit_Package_After_discount = findViewById(R.id.edit_Package_After_discount);
        Discount = findViewById(R.id.Discount);
        After_discount = findViewById(R.id.After_discount);
        OptionsItems = findViewById(R.id.OptionsItems);
        Chk = findViewById(R.id.Chk);

        btn_Add = findViewById(R.id.btn_Add);
        Add_Details = findViewById(R.id.Add_Details);

        Chk.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                IsDiscount = 1;
                Discount.setVisibility(View.VISIBLE);
                After_discount.setVisibility(View.VISIBLE);
            } else {
                IsDiscount = 0;
                Discount.setVisibility(View.GONE);
                After_discount.setVisibility(View.GONE);
            }
        });
        edit_Package_discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {
                    float Main = Float.parseFloat(edit_Package_price.getText().toString());
                    float persent = Float.parseFloat(edit_Package_discount.getText().toString());
                    float Last = Main - (Main * (persent / 100));
                    edit_Package_After_discount.setText(Last + "");
                } catch (Exception e) {
                    edit_Package_After_discount.setText(edit_Package_price.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        MainTitle.setText(R.string.AddSubscribe);


        loadDetails(details);

        Add_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details++;
                loadDetails(details);
            }
        });

        btn_Add.setOnClickListener(v -> {
           /* if (edit_Name_ar.getText().toString().matches("")
                    || edit_Name_en.getText().toString().matches("") || edit_Package_ar.getText().toString().matches("")
                    || edit_Package_en.getText().toString().matches("") || edit_Package_period.getText().toString().matches("")
                    || edit_Package_price.getText().toString().matches("")) {
                loadMsg(getResources().getString(R.string.EmptyField), false);
                return;
            }*/


            HideAll.setVisibility(View.VISIBLE);
            prog.setVisibility(View.VISIBLE);
            btn_Add.setEnabled(false);

            OkHttpClient client = new OkHttpClient();
            String Url = Values.Link_service + "coach/plan/store/" + lang + "/v1";

            MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
            body1.addFormDataPart("name_ar", edit_Name_ar.getText().toString() + "");
            body1.addFormDataPart("name_en", edit_Name_en.getText().toString() + "");
            body1.addFormDataPart("title_ar", edit_Package_ar.getText().toString() + "");
            body1.addFormDataPart("title_en", edit_Package_en.getText().toString() + "");
            body1.addFormDataPart("months_num", edit_Package_period.getText().toString() + "");
            body1.addFormDataPart("price", edit_Package_price.getText().toString() + "");
            body1.addFormDataPart("discount", edit_Package_discount.getText().toString() + "");
            body1.addFormDataPart("is_discount", IsDiscount + "");
            body1.addFormDataPart("discount_price", edit_Package_After_discount.getText().toString() + "");

            int x = 0;
            for (DataInPlanDetails item : OptionData) {
                body1.addFormDataPart("details[" + x + "][name_ar]", item.getName_ar() + "");
                body1.addFormDataPart("details[" + x + "][name_en]", item.getName_en() + "");
                x++;
            }

            RequestBody body = body1.build();
            String Auth;
            if (LoginHolder.getInstance().getData().equals("login")) {
                Auth = UserHolder.getInstance().getData().token.token_type
                        + " " + UserHolder.getInstance().getData().token.access_token;
            } else {
                Auth = Values.Authorization_User;
            }
            Request request = new Request.Builder()
                    .url(Url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "" + Auth)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("TestApp_5", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //
                    try {
                        Gson g = new Gson();
                        Type t = new TypeToken<DataInGlobal2>() {
                        }.getType();
                        String XX = response.body().string();
                        Log.i("TestApp_3", XX);
                        _Data = g.fromJson(XX, t);

                        activity.runOnUiThread(() -> {
                            if (!_Data.success) {
                                loadMsg(_Data.message, false);
                            } else {
                                loadMsg(_Data.message, true);
                            }
                        });

                    } catch (Exception e) {
                        Log.i("TestApp_3_Error", e.getMessage() + "");
                    }
                }

            });

            btn_Add.setEnabled(true);
            HideAll.setVisibility(View.GONE);
            prog.setVisibility(View.GONE);
        });
    }

    private void loadDetails(int det) {
        OptionsItems.removeAllViews();
        for (int i = 0; i < det; i++) {
            int j = i + 1;
            if (OptionData.size() < j)
                OptionData.add(i, new DataInPlanDetails(i, "", ""));
            View NewView = LayoutInflater.from(activity).inflate(R.layout.layout_plan_input, null);
            Button btn_Delete = NewView.findViewById(R.id.btn_Delete);
            EditText edit_Name_ar = NewView.findViewById(R.id.edit_Name_ar);
            EditText edit_Name_en = NewView.findViewById(R.id.edit_Name_en);

            TextInputLayout edit_Name_ar_Lay = NewView.findViewById(R.id.edit_Name_ar_Lay);
            TextInputLayout edit_Name_en_Lay = NewView.findViewById(R.id.edit_Name_en_Lay);

            String Req = "";
            boolean Re = true;

            edit_Name_ar_Lay.setHint(getString(R.string.Extra_Details_ar).replace("%s", "( " + j + " )"));
            edit_Name_en_Lay.setHint(getString(R.string.Extra_Details_en).replace("%s", "( " + j + " )"));
            edit_Name_ar.setText(OptionData.get(i).getName_ar());
            edit_Name_en.setText(OptionData.get(i).getName_en());
            /* Title.setText(_Option.title + " " + Req);
            Input.setHint(_Option.title + " " + Req);
            OptionData.put(_Option.option_id, new DataInOptions(_Option.option_id, _Option.title, R));*/
            // Select.setOnClickListener(v -> loadOptions(_Option.values, _Option.title));

            boolean finalR = Re;
            int ID = i;
            edit_Name_ar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(final CharSequence s, int start, int before, int count) {
                    //You need to remove this to run only once
                    //handler.removeCallbacks(input_finish_checker);

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    //avoid triggering event when text is empty
                    // if (s.length() > 0) {
                    //  Log.i("TestApp", s.toString());

                    OptionData.set(ID, new DataInPlanDetails(ID, s.toString(), edit_Name_en.getText().toString()));

                }
            });
            edit_Name_en.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(final CharSequence s, int start, int before, int count) {
                    //You need to remove this to run only once
                    //handler.removeCallbacks(input_finish_checker);

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    //avoid triggering event when text is empty
                    // if (s.length() > 0) {
                    // Log.i("TestApp", s.toString());
                    OptionData.set(ID, new DataInPlanDetails(ID, edit_Name_ar.getText().toString(), s.toString()));

                }
            });

            btn_Delete.setOnClickListener(view -> {
                details--;
                OptionsItems.removeView(NewView);
                OptionData.remove(details);
            });

            OptionsItems.addView(NewView);
        }

    }

    private void loadMsg(String MSG, boolean t) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_noads3);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView Title = dialog.findViewById(R.id.MsgTitle);
        TextView Text = dialog.findViewById(R.id.MsgText);

        Button Yes = dialog.findViewById(R.id.btn_Yes);
        Button No = dialog.findViewById(R.id.btn_No);

        Yes.setText(R.string.OK);
        Text.setText(MSG + "");
        No.setVisibility(View.GONE);


        Yes.setOnClickListener(v -> {
            if (t) {
                startActivity(new Intent(activity, SelectPlans.class));
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    public void setOnBack() {
        super.onBackPressed();
    }



    @Override
    protected void onResume() {

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
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        setOnBack();
    }

    public void gotoBack(View v) {
        setOnBack();
    }

    public void gotoDiscount(View v) {
        if (Chk.isChecked()) {
            Chk.setChecked(false);
        } else {
            Chk.setChecked(true);
        }
    }
}

