package com.usmart.com.bfit_coaches;

import static android.graphics.BitmapFactory.decodeFile;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.ViewSkeletonScreen;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import constants.Values;
import customLists.CustomListChatsDetails;
import dataInLists.DataInChatsCheck;
import dataInLists.DataInChatsDetails;
import dataInLists.DataInGlobal;
import helpers.LangHolder;
import helpers.LoginHolder;
import helpers.NetWork;
import helpers.OnlineHolder;
import helpers.UserHolder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.ExpandableHeightGridView;

public class Chats_Details extends FragmentActivity {
    Activity activity = Chats_Details.this;
    DataInChatsDetails _Data = new DataInChatsDetails();
    ViewSkeletonScreen skeletonScreen;
    ImageView HideAll, iv_send, iv_Meida, iv_Video;
    ProgressBar prog;
    ExpandableHeightGridView lvOffers;
  //  ScrollView Scroll;
    ImageView User_Image;
    private static final int PICK_IMAGE_Studio_Main = 1002;
    private static final int PICK_IMAGE_Studio_List_Video = 1008;
    TextView UserName, Mobile;
    ArrayList<String> MainupImages = new ArrayList<>();
    EditText ed_write;
    CustomListChatsDetails adapterOffers;
    String lang;
    int ConverID, UserID;
    String uploadedMainImg = "";
    String uploadedListImg = "";
    boolean CanSend;
    byte MediaType = 1;
    LinearLayout Footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
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
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        );
        getWindow().setStatusBarColor(Color.parseColor("#23293F"));
        getWindow().getDecorView().setSystemUiVisibility(0);
        // ***********************************************
        setContentView(R.layout.activity_chat_details);
        if (LangHolder.getInstance().getData().equals("ar")) {
            lang = "ar";
        } else {
            lang = "en";
        }
        ConverID = getIntent().getExtras().getInt("ConverID");
        UserID = getIntent().getExtras().getInt("UserID");
        CanSend = getIntent().getExtras().getBoolean("CanSend");

        HideAll = findViewById(R.id.HideAll);
        prog = findViewById(R.id.progressBar1);

        ed_write = findViewById(R.id.ed_write);
        iv_send = findViewById(R.id.iv_send);
        iv_Meida = findViewById(R.id.iv_Meida);
        iv_Video = findViewById(R.id.iv_Video);
        User_Image = findViewById(R.id.User_Image);
        UserName = findViewById(R.id.UserName);
        Mobile = findViewById(R.id.Mobile);
        Footer = findViewById(R.id.Footer);
        lvOffers = findViewById(R.id.listViewOrders);
     //   Scroll = findViewById(R.id.Scroll);
       lvOffers.setExpanded(true);

        adapterOffers = new CustomListChatsDetails(activity, new ArrayList<>());
        lvOffers.setAdapter(adapterOffers);
        iv_Meida.setOnClickListener(view -> {
            if (CanSend) {
                Options options = Options.init()
                        .setRequestCode(PICK_IMAGE_Studio_Main)                                           //Request code for activity results
                        .setCount(1)                                                   //Number of images to restict selection count
                        .setPreSelectedUrls(MainupImages)                               //Pre selected Image Urls
                        .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                        .setMode(Options.Mode.Picture)                                     //Option to select only pictures or videos or both
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                        .setPath("/pix/images");
                Pix.start(Chats_Details.this, options);
            } else {
                loadMsg(getString(R.string.CannotSend));
            }
        });
        iv_Video.setOnClickListener(view -> {
            if (CanSend) {
                Options options = Options.init()
                        .setRequestCode(PICK_IMAGE_Studio_List_Video)                                           //Request code for activity results
                        .setCount(1)                                                   //Number of images to restict selection count
                        .setPreSelectedUrls(MainupImages)                               //Pre selected Image Urls
                        .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                        .setMode(Options.Mode.Video)                                     //Option to select only pictures or videos or both
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                        .setPath("/pix/images");
                Pix.start(Chats_Details.this, options);
            } else {
                loadMsg(getString(R.string.CannotSend));
            }
        });
        iv_send.setOnClickListener(view -> {
            if (CanSend) {
                if (ed_write.getText().toString().equals("") || ed_write.getText().toString().equals(" ")) {
                    Toast.makeText(activity, R.string.MustEnterMessage, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // checkChattingExistent();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
              /*  postMessageUrl = Values.Link_service + "chat/send_message/en/v1";
                postMessageBody = "{\n" + "\"ad_product_id\" : " + ad_product_id + ",\n"
                        + "\"message\" : " + "\"" + et_write.getText().toString() + "\"" + ",\n"
                        + "\"type\" : " + "\"text\"" + ",\n"
                        + "\"conversation_id\" : " + conversation_id + "\n"
                        + "}";
                Log.i(TAG, "Post Message Url: " + postMessageUrl + "\n"
                        + "Post Message Body: " + postMessageBody);*/
                    sendMessage(ConverID, ed_write.getText().toString(), "text", "");
                    ed_write.setText("");
                    //iv_send.setEnabled(false);
                }
            } else {
                loadMsg(getString(R.string.CannotSend));
            }
        });
        if (!CanSend) {
            //  iv_send.setEnabled(false);
            ed_write.setEnabled(false);
            Footer.setEnabled(false);
        } else {
            //  iv_send.setEnabled(true);
            ed_write.setEnabled(true);
            Footer.setEnabled(true);
        }

        checkChattingExistent();
    }


    private void loadData(int ConID) {
        AsyncHttpClient client = new AsyncHttpClient();
        String Url;
        Url = Values.Link_service + "coach/chat/get_ad_message/" + UserID + "/" + ConID +
                "/" + lang + "/v1/";
        Log.i("TestApp", Url);
        client.addHeader("Content-Type", "application/json");
        if (LoginHolder.getInstance().getData().equals("login")) {
            client.addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                    + " " + UserHolder.getInstance().getData().token.access_token);
        } else {
            client.addHeader("Authorization", "" + Values.Authorization_User);
        }

        client.get(Url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

              /*  skeletonScreen = Skeleton.bind(lvOffers)
                        .load(R.layout.singel_load_list)
                        .show();*/
            }

            @Override
            public void onSuccess(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.i("TestApp", arg1);
                super.onSuccess(arg0, arg1);
                try {
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInChatsDetails>() {
                    }.getType();
                    _Data = g.fromJson(arg1, t);

                    UserName.setText(_Data.data.ad_user_data.name + "");
                    Mobile.setText(_Data.data.ad_user_data.email + "");
                    DisplayImageOptions options;
                    options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.mipmap.ic_avatar)
                            .showImageForEmptyUri(R.mipmap.ic_avatar)
                            .showImageOnFail(R.mipmap.ic_avatar)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .considerExifParams(true)
                            .bitmapConfig(Bitmap.Config.RGB_565)
                            .build();
                    ImageLoader.getInstance().displayImage(Values.Link_Image + _Data.data.ad_user_data.image, User_Image, options);

                    adapterOffers.clear();
                    adapterOffers.notifyDataSetChanged();
                    // lvOffers.smoothScrollToPosition(adapterOffers.getCount());
                    adapterOffers.addAll(_Data.data.messages);
                    adapterOffers.notifyDataSetChanged();
                    //lvOffers.smoothScrollToPosition(adapterOffers.getCount());
                   /* lvOffers.post(() -> {
                        // Select the last row so it will scroll into view...
                        Log.i("TestApp_Error", adapterOffers.getCount() + "");
                        lvOffers.setSelection(adapterOffers.getCount());
                    });
                    lvOffers.setStackFromBottom(true);*/

                    //Scroll.smoothScrollTo(0, lvOffers.getBottom());


                } catch (Exception e) {
                    Log.i("TestApp_Error", e.getMessage());
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
                //  lvOffers.smoothScrollToPosition(adapterOffers.getCount());
                lvOffers.post(new Runnable() {
                    @Override
                    public void run() {
                        // Select the last row so it will scroll into view...
                        lvOffers.setSelection(adapterOffers.getCount());
                    }
                });
                lvOffers.setStackFromBottom(true);
               // Scroll.smoothScrollTo(0, lvOffers.getBottom());
                //   skeletonScreen.hide();
            }
        });

    }

    public void checkChattingExistent() {
        String url = Values.Link_service + "coach/chat/test_exists_conversation/" + UserID + "/en/v1";
        Log.i("TestApp", "Check Chatting Existent url: " + url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                        + " " + UserHolder.getInstance().getData().token.access_token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //
                DataInChatsCheck _DataCheck;
                String mMessage = response.body().string();
                Log.i("TestAppResponse", mMessage);
                try {
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInChatsCheck>() {
                    }.getType();
                    _DataCheck = g.fromJson(mMessage, t);
                    runOnUiThread(() -> {
                        Log.i("TestApp1", _DataCheck.toString());
                        Log.i("TestApp1", _DataCheck.success + "");
                        if (!_DataCheck.success) {
                            if (_DataCheck.code == 401) {
                                Intent i = new Intent(activity, Login.class);
                                startActivity(i);
                            } else {
                                loadMsg(_DataCheck.message);
                            }
                        } else {
                            if (_DataCheck.data.exist) {
                                Log.i("TestApp4", mMessage);
                                ConverID = _DataCheck.data.conversation_id;
                                loadData(_DataCheck.data.conversation_id);
                            }
                        }
                    });


                } catch (Exception e) {
                    Log.i("TestApp_Error", e.getMessage());
                }

            }
        });
    }


    private void sendMessage(int Conv, String Body, String Type, String VideoImg) {

        HideAll.setVisibility(View.VISIBLE);
        prog.setVisibility(View.VISIBLE);
        // btn_Set_Checkout.setEnabled(false);
        if (Type.equals("image")) {
            DataInChatsDetails.Chats Chats = new DataInChatsDetails().
                    new Chats(UserID, Conv, _Data.data.ad_user_data.coach_id, "", "image");
            ArrayList<DataInChatsDetails.Chats> Ar = new ArrayList();
            Ar.add(Chats);
            DataInChatsDetails.Messages Obj = new DataInChatsDetails()
                    .new Messages("", Ar);
            adapterOffers.add(Obj);
            adapterOffers.notifyDataSetChanged();
        }
        if (Type.equals("video")) {
            DataInChatsDetails.Chats Chats = new DataInChatsDetails().
                    new Chats(UserID, Conv, _Data.data.ad_user_data.coach_id, "", "image");
            ArrayList<DataInChatsDetails.Chats> Ar = new ArrayList();
            Ar.add(Chats);
            DataInChatsDetails.Messages Obj = new DataInChatsDetails()
                    .new Messages("", Ar);
            adapterOffers.add(Obj);
            adapterOffers.notifyDataSetChanged();
        }

        OkHttpClient client = new OkHttpClient();

        String Url = Values.Link_service + "coach/chat/send_message/" + lang + "/v1";
        Log.i("TestApp_12", Url);
        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body1.addFormDataPart("type", Type);
        body1.addFormDataPart("user_id", UserID + "");
        body1.addFormDataPart("conversation_id", Conv + "");
        body1.addFormDataPart("message", Body + "");

        if (Type.equals("image"))
            body1.addFormDataPart("message", Body + "");
        else if (Type.equals("video")) {
            File tempFile = new File(Body);
            body1.addFormDataPart("message", tempFile.getName(),
                    RequestBody.create(okhttp3.MediaType.parse("media/type"), new File(Body)));
        } else
            body1.addFormDataPart("message", Body + "");
        RequestBody body = body1.build();

        //     Log.i("TestApp_12", json);
        String Auth;
        Auth = UserHolder.getInstance().getData().token.token_type
                + " " + UserHolder.getInstance().getData().token.access_token;

        // Log.i("TestApp_12", Auth);
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
                DataInGlobal Res;
                try {
                  /*  String XX = response.body().string();
                    Log.i("TestApp_5", XX);
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInGlobal>() {
                    }.getType();
                    Res = g.fromJson(XX, t);*/
                    runOnUiThread(() -> {
                      /*  if (!Res.success) {
                            loadMsg(Res.message + "");
                        } else*/
                        checkChattingExistent();
                    });


                } catch (Exception e) {
                    Log.i("TestApp_5", e.getMessage());
                }
            }

        });
        //btn_Set_Checkout.setEnabled(true);

        HideAll.setVisibility(View.GONE);
        prog.setVisibility(View.GONE);

    }



    /* private void sendMessage(int Conv, String Body) {

        HideAll.setVisibility(View.VISIBLE);
        prog.setVisibility(View.VISIBLE);
        // btn_Set_Checkout.setEnabled(false);

        OkHttpClient client = new OkHttpClient();

        String Url = Values.Link_service + "coach/chat/send_message/" + lang + "/v1";
        Log.i("TestApp_12", Url);
        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body1.addFormDataPart("user_id", UserID + "");
        body1.addFormDataPart("type", "text");
        body1.addFormDataPart("conversation_id", Conv + "");
        body1.addFormDataPart("message", Body + "");

        RequestBody body = body1.build();

        //     Log.i("TestApp_12", json);
        String Auth;
        Auth = UserHolder.getInstance().getData().token.token_type
                + " " + UserHolder.getInstance().getData().token.access_token;

        // Log.i("TestApp_12", Auth);
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
                try {
                    String XX = response.body().string();
                    Log.i("TestApp_5", XX);
                    runOnUiThread(() -> {
                        checkChattingExistent();
                    });


                } catch (Exception e) {
                }
            }

        });
        //btn_Set_Checkout.setEnabled(true);

        HideAll.setVisibility(View.GONE);
        prog.setVisibility(View.GONE);

    }*/

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("TestApp_Noti000", "Test");
            try {
                checkChattingExistent();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // lvOffers.smoothScrollToPosition(adapterOffers.getCount());
           lvOffers.post(new Runnable() {
                @Override
                public void run() {
                    // Select the last row so it will scroll into view...
                    lvOffers.setSelection(adapterOffers.getCount());
                }
            });
            lvOffers.setStackFromBottom(true);
          //  Scroll.smoothScrollTo(0, lvOffers.getBottom());
        }
    };

    private void loadMsg(String MSG) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_noads3);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView Title = dialog.findViewById(R.id.MsgTitle);
        TextView Text = dialog.findViewById(R.id.MsgText);

        Button Yes = dialog.findViewById(R.id.btn_Yes);
        Button No = dialog.findViewById(R.id.btn_No);

        Yes.setText(R.string.OK);
        Text.setText(MSG + "");
        No.setVisibility(View.GONE);


        Yes.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver), new IntentFilter("productData"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }


    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    protected void onResume() {
       /* CartCount();
        BottomNavigationViewEx navigation = findViewById(R.id.navigation);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_offer);*/
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver), new IntentFilter("productData"));
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
                        sendMessage(ConverID, uploadedMainImg, "image", "");
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
                        UploadVideos(im, (byte) 2);


                        String path = im;
                        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
                                MediaStore.Images.Thumbnails.MINI_KIND);

                        sendMessage(ConverID, im, "video", getRealPathFromURI(getImageUri(thumb)));

                        Log.i("TestApp_Main", getRealPathFromURI(getImageUri(thumb)) + "");
                    /*    DataInCoachesDetails HoCls = new DataInCoachesDetails();
                        DataInCoachesDetails.Media Obj = HoCls.new Media(999,  "file://" +getRealPathFromURI(getImageUri(thumb)), "image_temp");
                        HallsVideoAdapter.add(Obj);
                        HallsVideoAdapter.notifyDataSetChanged();

                        updateMediaVideo(im);*/

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

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, null, null);
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



    public void gotoBack(View v) {
        super.onBackPressed();
    }

    public void gotoMenu(View v) {

    }

    public void gotoCart(View v) {

    }

    public void gotoSearch(View v) {
        startActivity(new Intent(activity, Search.class));
    }


}
