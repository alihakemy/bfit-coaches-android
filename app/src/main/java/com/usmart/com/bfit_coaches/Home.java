package com.usmart.com.bfit_coaches;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import constants.Values;
import customLists.Coaches.CustomListCoachesRates;
import customLists.CustomListChats;
import customLists.CustomListCoachesMedia2;
import customLists.CustomListDialogs;
import dataInLists.DataInChats;
import dataInLists.DataInCoachesDetails;
import dataInLists.DataInDialog;
import dataInLists.DataInGlobal;
import dataInLists.DataInGlobal2;
import helpers.ForceUpgradeManager;
import helpers.LangHolder;
import helpers.LoginHolder;
import helpers.NetWork;
import helpers.UpdateCoachHolder;
import helpers.UpdateHolder;
import helpers.UserHolder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import utils.ExpandableHeightGridView;

public class Home extends FragmentActivity {
    Activity activity = Home.this;
    SharedPreferences Login;
    DataInChats _Data = new DataInChats();
    DataInCoachesDetails Data = new DataInCoachesDetails();

    ImageView HideAll, User_Image;
    ProgressBar prog;
    String lang;
    TextView UserName, tv_CartNum, tv_viewStories;
    ExpandableHeightListView lvMain;
    CustomListChats adapterChats;
    private static final int PICK_IMAGE_Studio_List_Video = 1008;
    ArrayList<String> MainupImages = new ArrayList<>();
    private ForceUpgradeManager forceUpgradeManager;
    //byte MediaType = 1;
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
    private PopupWindow BidWindow;
    //CustomListCoachesMedia2 HallsVideoAdapter;
    String thumbnailBase64 = "";

    @SuppressLint("ResourceType")
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
        getWindow().setStatusBarColor(Color.parseColor("#50BCAF"));
        getWindow().getDecorView().setSystemUiVisibility(0);
        // ***********************************************
        setContentView(R.layout.activity_home);

        if (LangHolder.getInstance().getData().equals("ar")) {
            lang = "ar";
        } else {
            lang = "en";
        }

        HideAll = findViewById(R.id.HideAll);
        prog = findViewById(R.id.progressBar1);
        UserName = findViewById(R.id.UserName);
        User_Image = findViewById(R.id.User_Image);
        lvMain = findViewById(R.id.lvMain);
        tv_viewStories = findViewById(R.id.tv_viewStories);

        lvMain.setExpanded(true);

        UserName.setText(UserHolder.getInstance().getData().name + "");
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
        ImageLoader.getInstance().displayImage(Values.Link_ImageHomeCats + UserHolder.getInstance().getData().image, User_Image, options);

        tv_CartNum = findViewById(R.id.tv_CartNum);

        User_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Options options = Options.init()
                        .setRequestCode(PICK_IMAGE_Studio_List_Video)                                           //Request code for activity results
                        .setCount(1)                                                   //Number of images to restict selection count
                        .setPreSelectedUrls(MainupImages)                               //Pre selected Image Urls
                        .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                        .setMode(Options.Mode.Video)                                     //Option to select only pictures or videos or both
                        .setVideoDurationLimitinSeconds(10)
                        .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                        .setPath("/pix/videos");                                       //Custom Path For media Storage
                Pix.start(Home.this, options);
            }
        });


        tv_viewStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadVideos();
            }
        });


        UserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadVideos();
            }
        });

      /*  lvMain.setOnItemClickListener((parent, view, position, id) ->
                startActivity(new Intent(activity, StoreCats.class)
                        .putExtra("StoreID", _Data.data.content.get(position).id)));*/

        lvMain.setOnItemClickListener((adapterView, view, i, l) -> {
                    if (adapterChats.getItem(i).conversation_type.equals("empty"))
                        startActivity(new Intent(activity, Chats_Details.class)
                                .putExtra("ConverID", adapterChats.getItem(i).conversation_id)
                                .putExtra("UserID", adapterChats.getItem(i).other_user_id)
                                .putExtra("CanSend", false)
                        );
                    else
                        startActivity(new Intent(activity, Chats_Details.class)
                                .putExtra("ConverID", adapterChats.getItem(i).conversation_id)
                                .putExtra("UserID", adapterChats.getItem(i).other_user_id)
                                .putExtra("CanSend", true)
                        );
                }
        );
        loadHome();

    }


    private void loadHome() {
        AsyncHttpClient client = new AsyncHttpClient();
        String Url;

        Url = Values.Link_service + "coach/chat/my_messages/" + lang + "/v1/";
        Log.i("TestApp", Url);
        client.addHeader("Content-Type", "application/json");
        if (LoginHolder.getInstance().getData().equals("login")) {
            client.addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                    + " " + UserHolder.getInstance().getData().token.access_token);
            Log.i("TestApp", "" + UserHolder.getInstance().getData().token.token_type
                    + " " + UserHolder.getInstance().getData().token.access_token);
        } else {
            client.addHeader("Authorization", "" + Values.Authorization_User);
        }

        client.get(Url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                HideAll.setVisibility(View.VISIBLE);
                prog.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.i("TestApp", arg1);
                super.onSuccess(arg0, arg1);
                try {
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInChats>() {
                    }.getType();
                    _Data = g.fromJson(arg1, t);


                    adapterChats = new CustomListChats(activity, _Data.data.conversations);
                    lvMain.setAdapter(adapterChats);
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
                Log.i("TestApp", arg0.getMessage());
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                super.onFinish();
                HideAll.setVisibility(View.GONE);
                prog.setVisibility(View.GONE);
                //loadCats();
            }
        });

    }

    private void CartCount() {

        OkHttpClient client = new OkHttpClient();
        String Url = Values.Link_service + "visitors/cart/count/" + LangHolder.getInstance().getData() + "/v1";
        String json = new StringBuilder()
                .append("{")
                .append("\"unique_id\":\"" + Settings.Secure.getString(getContentResolver(),
                        Settings.Secure.ANDROID_ID) + "\"")
                .append("}").toString();

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json
        );
        Request request = new Request.Builder()
                .url(Url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "" + Values.Authorization_User)
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

                    Result = g.fromJson(response.body().string(), t);


                    activity.runOnUiThread(() -> {
                        if (!Result.success) {
                            if (Result.code == 401) {
                                Intent i = new Intent(activity, Login.class);
                                startActivity(i);
                            } else {
                                loadMsg(Result.message, false);
                            }
                        } else {
                            tv_CartNum.setText(Result.data.count + "");
                        }
                    });

                } catch (Exception e) {
                }
            }

        });

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
        // writeFileOnInternalStorage(activity, "braodcast.txt", "Test");
        //CartCount();
        BottomNavigationViewEx navigation = findViewById(R.id.navigation);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.setTextSize(12);
        navigation.enableItemShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        if (UpdateHolder.getInstance().getData()) {
//            onUpdateNeeded();
        }
        super.onResume();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                //startActivity(new Intent(activity, Home.class));
                return true;
            case R.id.navigation_offer:
                startActivity(new Intent(activity, Chats.class));
                return true;
          /*  case R.id.navigation_search:
                startActivity(new Intent(activity, Search.class));
                return true;*/
            case R.id.navigation_account:
                startActivity(new Intent(activity, CoachDetails.class));
                // overridePendingTransition(R.anim.slide_bottom_to_top, R.anim.slide_top_to_bottom);
                return true;
            case R.id.navigation_more:
                startActivity(new Intent(activity, AccountMenu.class));
                // overridePendingTransition(R.anim.slide_bottom_to_top, R.anim.slide_top_to_bottom);
                return true;
        }
        return false;
    };

    private void onUpdateNeeded() {
        //     if (temp != null) {
        androidx.appcompat.app.AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("New version available")
                .setCancelable(false)
                .setMessage("Please update app for seamless experience.")
                .setPositiveButton("Continue",
                        (dialog1, which) -> redirectStore()).create();
        dialog.show();
        //  }
    }

    private void redirectStore() {
        Uri updateUrl = Uri.parse("market://details?id=" + getPackageName());
        final Intent intent = new Intent(Intent.ACTION_VIEW, updateUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
                CartCount();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void gotoBack(View v) {
        onBackPressed();
    }


    private void loadClose() {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_noads3);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView Title = dialog.findViewById(R.id.MsgTitle);
        TextView Text = dialog.findViewById(R.id.MsgText);

        Button Yes = dialog.findViewById(R.id.btn_Yes);
        Button No = dialog.findViewById(R.id.btn_No);


        Title.setText(R.string.ExitTitle);
        Text.setText(R.string.ExitMeg);

        Yes.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            finish();
            startActivity(intent);
        });
        No.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }


    public void gotoCart(View v) {
        startActivity(new Intent(activity, Notis.class));
    }

    public void gotoSearch(View v) {
        startActivity(new Intent(activity, Search.class));
    }

    public void gotoAccount(View v) {
        startActivity(new Intent(activity, AccountMenu.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                        Log.i("TestApp_Main", getRealPathFromURI(getImageUri(thumb)) + "");
                        DataInCoachesDetails HoCls = new DataInCoachesDetails();
                        DataInCoachesDetails.Media Obj = HoCls.new Media(999, "file://" + getRealPathFromURI(getImageUri(thumb)), "image_temp");
                        Log.d("TAG", "Created thumbnail image: " + getRealPathFromURI(getImageUri(thumb)));

                        try {
                            String imageUri = getRealPathFromURI(getImageUri(thumb));
                            Uri mImageUri = Uri.fromFile(new File(imageUri));
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                            thumbnailBase64 = encodeTobase64(bitmap);
                            Log.d("TAG", "selected image base64: " + thumbnailBase64);
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                        //HallsVideoAdapter.add(Obj);
                        //HallsVideoAdapter.notifyDataSetChanged();
                        //updateMediaVideo(im);
                        try {
                            getVideoFileDuration(im);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        /*if (requestCode == PICK_IMAGE_Studio_List_Video && data != null && resultCode != RESULT_CANCELED) {
            ArrayList<String> selectedImages = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            if (selectedImages.size() > 0) {
                for (String im : selectedImages) {
                    if (!im.isEmpty()) {
                        Log.d("TestApp", "video path url: " + im + "");
                        String path = im;
                        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
                                MediaStore.Images.Thumbnails.MINI_KIND);
                        Log.i("TestApp_Main", getRealPathFromURI(getImageUri(activity, thumb)) + "");
                        DataInCoachesDetails HoCls = new DataInCoachesDetails();
                        DataInCoachesDetails.Media Obj = HoCls.new Media(999, "file://" + getRealPathFromURI(getImageUri(activity, thumb)), "image_temp");
                        getVideoFileDuration(im);

                    }
                }
            }
        }*/
    }

    // method for bitmap to base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap img = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 75, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP);
        return imageEncoded;
    }

    public void getVideoFileDuration(String videoFile) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        //use one of overloaded setDataSource() functions to set your data source
        retriever.setDataSource(activity, Uri.fromFile(new File(videoFile)));
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInMillisec = Long.parseLong(time);
        if (timeInMillisec > 10000) {
            loadMsg(getResources().getString(R.string.durationTime), false);
        } else {
            updateMediaVideo(videoFile);
        }
        retriever.release();
    }

    /*public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, null, null);
        return Uri.parse(path);
    }*/

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    @SuppressWarnings("deprecation")
    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /*
    private void updateMediaFile(String im) throws FileNotFoundException {
        HideAll.setVisibility(View.VISIBLE);
        prog.setVisibility(View.VISIBLE);

        OkHttpClient client = new OkHttpClient();
        String Url = Values.Link_service + "coach/media/store/" + lang + "/v1";
        File tempFile = new File(im);

        ContentResolver contentResolver = activity.getContentResolver();
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        final String contentType = fileNameMap.getContentTypeFor(tempFile.getAbsolutePath());
        final AssetFileDescriptor fd = contentResolver.openAssetFileDescriptor(Uri.parse("file://" + tempFile.getAbsolutePath()), "r");
        if (fd == null) {
            throw new FileNotFoundException("could not open file descriptor");
        }

        RequestBody videoFile = new RequestBody() {
            @Override
            public long contentLength() {
                return fd.getDeclaredLength();
            }

            @Override
            public MediaType contentType() {
                return MediaType.parse(contentType);
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                try (InputStream is = fd.createInputStream()) {
                    sink.writeAll(Okio.buffer(Okio.source(is)));
                }
            }
        };

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", tempFile.getName(), videoFile)
                .addFormDataPart("type", "video")
                .addFormDataPart("thumbnail", "\"" + thumbnailBase64 + "\"")
                .build();

        Request request = new Request.Builder()
                .url(Url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                        + " " + UserHolder.getInstance().getData().token.access_token)
                .post(requestBody)
                .build();

        client.newBuilder().writeTimeout(5, TimeUnit.MINUTES).connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                try {
                    fd.close();
                } catch (IOException ex) {
                    e.addSuppressed(ex);
                }
                Log.e("TAG", "failed", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                fd.close();
                try {
                    DataInGlobal2 Result;
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInGlobal2>() {
                    }.getType();
                    String Res = response.body().string();
                    Log.i("TestApp", Res);
                    Result = g.fromJson(Res, t);

                    activity.runOnUiThread(() -> {
                        HideAll.setVisibility(View.GONE);
                        prog.setVisibility(View.GONE);
                        if (!Result.success) {
                            loadMsg(Result.message, false);
                        } else {
                            loadMsg(Result.message, true);
                            Toast.makeText(activity, Result.message, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    activity.runOnUiThread(() -> {
                        HideAll.setVisibility(View.GONE);
                        prog.setVisibility(View.GONE);
                    });
                }
            }
        });
    }
    */


    private void updateMediaVideo(String im) {
        HideAll.setVisibility(View.VISIBLE);
        prog.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        String Url = Values.Link_service + "coach/media/store/" + lang + "/v1";
        Log.d("TAG", "uploading video url: " + Url);

        File tempFile = new File(im);
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String mimeType = fileNameMap.getContentTypeFor(tempFile.getAbsolutePath());
        Log.d("TAG", "Type of video file: " + mimeType);

        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body1.addFormDataPart("image", tempFile.getName(), RequestBody.create(MediaType.parse("media/type"), tempFile));
        body1.addFormDataPart("type", "video");
        body1.addFormDataPart("thumbnail", thumbnailBase64);
        RequestBody body = body1.build();

        Request request = new Request.Builder()
                .url(Url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                        + " " + UserHolder.getInstance().getData().token.access_token)
                .post(body)
                .build();
        client.newBuilder().writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES).build().newCall(request).enqueue(new Callback() {
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

                    Home.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HideAll.setVisibility(View.GONE);
                            prog.setVisibility(View.GONE);
                            if (!Result.success) {
                                loadMsg(Result.message, false);
                            } else {
                                loadMsg(Result.message, false);
                                Toast.makeText(activity, Result.message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Home.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HideAll.setVisibility(View.GONE);
                            prog.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    /*
    private void updateMediaVideo(String videoFileUrl) {
        HideAll.setVisibility(View.VISIBLE);
        prog.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        String Url = Values.Link_service + "coach/media/store/" + lang + "/v1";
        Log.d("TAG", "Post video url: " + Url);

        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File tempFile = new File(videoFileUrl);
        body1.addFormDataPart("image", tempFile.getName(), RequestBody.create(okhttp3.MediaType.parse("media/type"), tempFile));
        body1.addFormDataPart("type", "video");
        body1.addFormDataPart("thumbnail", "");
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

                    activity.runOnUiThread(() -> {
                        HideAll.setVisibility(View.GONE);
                        prog.setVisibility(View.GONE);
                        if (!Result.success) {
                            loadMsg(Result.message, false);
                        } else {
                            loadMsg(Result.message, true);
                        }
                    });
                } catch (Exception e) {
                    HideAll.setVisibility(View.GONE);
                    prog.setVisibility(View.GONE);
                }
            }
        });
    }
    */

    private void loadVideos() {
        try {
            ImageView ivClose;
            ExpandableHeightGridView lvMedia;
            VideoView videoView;

            LayoutInflater inflater = (LayoutInflater) Home.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.videos_window, null);
            BidWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, 1200, true);

            BidWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            BidWindow.setOutsideTouchable(true);
            BidWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
            BidWindow.setAnimationStyle(R.style.Base_Animation_AppCompat_DropDownUp);

            ivClose = layout.findViewById(R.id.ivClose);
            lvMedia = layout.findViewById(R.id.lvMedia);
            videoView = layout.findViewById(R.id.videoView);

            lvMedia.setExpanded(true);

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BidWindow.dismiss();
                }
            });

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

                        String videoUrl = Values.Link_Video + Data.data.basic.story;
                        //resource from the videoUrl
                        //Uri uri = Uri.parse(videoUrl);
                        //sets the resource from the videoUrl to the videoView
                        videoView.setVideoPath(videoUrl);
                        //creating object of
                        //media controller class
                        MediaController mediaController = new MediaController(Home.this);
                        mediaController.show();
                        //sets the anchor view anchor view for the videoView
                        mediaController.setAnchorView(videoView);
                        //sets the media player to the videoView
                        mediaController.setMediaPlayer(videoView);
                        //sets the media controller to the videoView
                        videoView.setMediaController(mediaController);
                        videoView.setZOrderOnTop(true);
                        //starts the video
                        videoView.start();

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

        } catch (Exception e) {

        }
    }

}