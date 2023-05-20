package com.usmart.com.bfit_coaches;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;

import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hbb20.CountryCodePicker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import constants.Values;

import dataInLists.DataInCheckUser;

import helpers.LangHolder;
import helpers.LoginHolder;
import helpers.NetWork;
import helpers.OnlineHolder;
import okhttp3.*;


public class SetRegister extends FragmentActivity {
    Activity activity = SetRegister.this;
    EditText edit_Password, edit_Email, edit_Name, edit_Name_en, edit_Mobile, edit_Age,
            edit_Exp, edit_Desc, edit_Desc_en, edit_Type;
    Button btn_register;
    TextView MainTitle;
    CountryCodePicker ed_Key;
    ImageView HideAll, MainPhoto;
    ProgressBar prog;
    ArrayList<String> MainupImages = new ArrayList<>();
    private static final int PICK_IMAGE_Studio_Main = 1002;
    String uploadedMainImg = "";
    DataInCheckUser LoginData;
    String lang;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(activity, 2));
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
        getWindow().setStatusBarColor(Color.WHITE);
        //  getWindow().getDecorView().setSystemUiVisibility(0);
        //*******************************************************
        setContentView(R.layout.activity_register);
        if (LangHolder.getInstance().getData().equals("ar")) {
            lang = "ar";
        } else {
            lang = "en";
        }

        // ***********************************************

        HideAll = findViewById(R.id.HideAll);
        prog = findViewById(R.id.progressBar1);

        edit_Name = findViewById(R.id.edit_Name);
        edit_Name_en = findViewById(R.id.edit_Name_en);
        edit_Password = findViewById(R.id.edit_Password);
        edit_Email = findViewById(R.id.edit_Email);
        edit_Mobile = findViewById(R.id.edit_Mobile);
        edit_Age = findViewById(R.id.edit_Age);
        edit_Exp = findViewById(R.id.edit_Exp);
        edit_Desc = findViewById(R.id.edit_Desc);
        edit_Desc_en = findViewById(R.id.edit_Desc_en);
        edit_Type = findViewById(R.id.edit_Type);
        MainPhoto = findViewById(R.id.MainPhoto);
        ed_Key = findViewById(R.id.ed_Key);
        btn_register = findViewById(R.id.btn_Set_Register);

        MainTitle = findViewById(R.id.MainTitle);

        MainTitle.setText(R.string.Register);

        MainPhoto.setOnClickListener(v -> {

            Options options = Options.init()
                    .setRequestCode(PICK_IMAGE_Studio_Main)
                    .setCount(1)
                    .setPreSelectedUrls(MainupImages)
                    .setSpanCount(4)
                    .setMode(Options.Mode.Picture)
                    .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
                    .setPath("/pix/images");

            Pix.start(SetRegister.this, options);

        });

        edit_Type.setOnClickListener(view -> loadSelectType());


        btn_register.setOnClickListener(v -> {
            Log.i("TestApp", ed_Key.getSelectedCountryCode());
            Log.i("TestApp", ed_Key.getSelectedCountryCodeWithPlus());
            /*if (!Chk.isChecked()) {
                loadMsg(getResources().getString(R.string.ConditionField));
                return;
            }*/
            if (edit_Name.getText().toString().matches("") || edit_Name_en.getText().toString().matches("")
                    || edit_Password.getText().toString().matches("") || edit_Email.getText().toString().matches("")
                    || edit_Exp.getText().toString().matches("")
                    || edit_Mobile.getText().toString().matches("") || edit_Age.getText().toString().matches("")) {
                loadMsg(getResources().getString(R.string.EmptyField));
                return;
            }
            if (Patterns.EMAIL_ADDRESS.matcher(edit_Email.getText().toString()).matches() == false) {
                loadMsg(getResources().getString(R.string.ValidMail));
                return;
            }
            if (Patterns.PHONE.matcher(edit_Mobile.getText().toString()).matches() == false) {
                loadMsg(getResources().getString(R.string.ValidMobile));
                return;
            }

            HideAll.setVisibility(View.VISIBLE);
            prog.setVisibility(View.VISIBLE);
            OkHttpClient client = new OkHttpClient();
            String Url = Values.Link_service + "coach/register/" + lang + "/v1";
            String json = new StringBuilder()
                    .append("{")
                    .append("\"phone\":\"" + ed_Key.getSelectedCountryCodeWithPlus() + edit_Mobile.getText().toString() + "\",")
                    .append("\"name\":\"" + edit_Name.getText().toString() + "\",")
                    .append("\"name_en\":\"" + edit_Name_en.getText().toString() + "\",")
                    .append("\"email\":\"" + edit_Email.getText().toString() + "\",")
                    .append("\"password\":\"" + edit_Password.getText().toString() + "\",")
                    .append("\"age\":\"" + edit_Age.getText().toString() + "\",")
                    .append("\"exp\":\"" + edit_Exp.getText().toString() + "\",")
                    .append("\"fcm_token\":\"" + FirebaseInstanceId.getInstance().getToken() + "\",")
                    .append("\"unique_id\":\"" + Settings.Secure.getString(activity.getContentResolver(),
                            Settings.Secure.ANDROID_ID) + "\",")
                    .append("\"type\":\"" + 2 + "\",")
                    .append("\"gender\":\"" + gender + "\",")
                    .append("\"about_coach\":\"" + edit_Desc.getText().toString() + "\",")
                    .append("\"about_coach_en\":\"" + edit_Desc_en.getText().toString() + "\",")
                    .append("\"image\":\"" + uploadedMainImg + "\"")
                    .append("}").toString();

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    json
            );
            Request request = new Request.Builder()
                    .url(Url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", Values.Authorization_User + "")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("TestApp_5", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        Gson g = new Gson();
                        Type t = new TypeToken<DataInCheckUser>() {
                        }.getType();
                        String Res = response.body().string();
                        Log.i("TestApp_5", Res);
                        LoginData = g.fromJson(Res, t);
                        //  LoginData = g.fromJson(response.body().string(), t);

                        activity.runOnUiThread(() -> {
                            if (!LoginData.success) {
                                loadMsg(LoginData.message);
                            } else {
                                if (!LoginData.phone) {
                                    if (!LoginData.email) {
                                        HideAll.setVisibility(View.GONE);
                                        prog.setVisibility(View.GONE);
                                        loadMsg();
                                      /*  Intent i = new Intent(activity, Activation.class);
                                        i.putExtra("ID", 0);
                                        i.putExtra("Mobile", ed_Key.getSelectedCountryCodeWithPlus() + ed_Mobile.getText().toString());
                                        i.putExtra("Name", ed_Name.getText().toString());
                                        i.putExtra("Email", ed_Email.getText().toString());
                                        i.putExtra("Password", ed_Pass.getText().toString());
                                        i.putExtra("ProdID", ProdID);
                                        i.putExtra("IsHome", true);

                                        startActivity(i);*/
                                    } else {
                                        loadMsg(getResources().getString(R.string.ExistEmail));
                                    }

                                } else {
                                    loadMsg(getResources().getString(R.string.ExistPhone));
                                }

                            }
                        });

                    } catch (Exception e) {
                        Log.i("TestApp_3_Error", e.getMessage() + "");
                    }
                }

            });




        });
    }

    private void loadSelectType() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_noads3);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView Title = dialog.findViewById(R.id.MsgTitle);
        TextView Text = dialog.findViewById(R.id.MsgText);

        Button Yes = dialog.findViewById(R.id.btn_Yes);
        Button No = dialog.findViewById(R.id.btn_No);

        Title.setText(R.string.Type);
        Text.setText(R.string.SelectGender);
        Yes.setText(R.string.Male);
        No.setText(R.string.FeMale);


        Yes.setOnClickListener(v -> {
            gender = "male";
            edit_Type.setText(R.string.Male);
            dialog.dismiss();
        });
        No.setOnClickListener(v -> {
            gender = "female";
            edit_Type.setText(R.string.FeMale);
            dialog.dismiss();
        });
        dialog.show();
    }



    private void loadMsg() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_finish);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        Button btn_Continue = dialog.findViewById(R.id.btn_Continue);


        btn_Continue.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(activity, Login.class));
        });
        dialog.show();
    }

    private void loadMsg(String MSG) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_noads3);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView Title = dialog.findViewById(R.id.MsgTitle);
        TextView Text = dialog.findViewById(R.id.MsgText);

        Button Yes = dialog.findViewById(R.id.btn_Yes);
        Button No = dialog.findViewById(R.id.btn_No);

        No.setText(R.string.OK);
        Text.setText(MSG + "");
        Yes.setVisibility(View.GONE);


        No.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        try {
            if (OnlineHolder.getInstance().getData().equals("1")) {
                OnlineHolder.getInstance().setData("0");
                finish();
                startActivity(getIntent());

            } else if (NetWork.isNetworkAvailable(activity) == false) {
                NetWork.gotoError(activity);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            if (LoginHolder.getInstance().getData().equals("login")) {

                // startActivity(new Intent(activity, Home.class));

            }
        } catch (Exception e) {

        }
        try {
        } catch (Exception e) {
            NetWork.gotoError(activity);
        }
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_Studio_Main && data != null && resultCode != RESULT_CANCELED) {
          //  MainPhoto.setVisibility(View.GONE);
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

                        ImageLoader.getInstance().displayImage("file://" + im, MainPhoto, options);
                        //   PhotosFrame.addView(view);
                        //  MaxMain--;
                    }
                }
            }
        }

    }

   /* public String UploadPhotos(final String uploaded, byte i) {
        final String[] XYZ = new String[1];
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                Bitmap resized, bitmap, bitmap2;

                bitmap2 = decodeFile(uploaded);
                bitmap = imageOreintationValidator(bitmap2, uploaded);
                resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.75),
                        (int) (bitmap.getHeight() * 0.75), true);
                String X = UploadImage(resized, i);
                Log.i("TestApp_22", bitmap2.toString() + "");
                XYZ[0] = X;
                msg = X;

                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                XYZ[0] = msg;
            }
        }.execute(null, null, null);
        return XYZ[0];
    }*/

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
           // upImages2.add(bitmap2);
        } else {
           // upImages2.remove(bitmap2);
        }
    }
   /* public String UploadImage(Bitmap bit, byte i) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 90, stream); // compress to
        byte[] byte_arr = stream.toByteArray();
        String YY = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        YY = YY.replace(" ", "").replace("\n", "");
        if (i == 1) {
            uploadedMainImg = YY;
        } else if (i == 2) {
            upImages.add(YY);
        } else {
            upImages.remove(YY);
        }
        Log.i("TestApp_2233_Size", upImages.size() + "");
        Log.i("TestApp_2233", YY + "");
        return YY;
    }*/

    public String UploadImage(Bitmap bit) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 90, stream); // compress to
        byte[] byte_arr = stream.toByteArray();
        String YY = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        YY = YY.replace(" ", "").replace("\n", "");
        //  YY = "data:image/png;base64," + YY;
       /* if (i == 1) {
            uploadedMainImg = YY;
        } else if (i == 2) {
            upImages2.add(bit);
        } else {
            upImages2.remove(bit);
        }*/
      //  Log.i("TestApp_2233_Size", upImages.size() + "");
        Log.i("TestApp_2233", YY + "");
        return YY;
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
        super.onBackPressed();
    }
}

