package customLists.Coaches;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.usmart.com.bfit_coaches.AccountMenu;
import com.usmart.com.bfit_coaches.CoachDetails;
import com.usmart.com.bfit_coaches.Login;
import com.usmart.com.bfit_coaches.R;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import constants.Values;
import dataInLists.DataInCoachesSubscribe;
import dataInLists.DataInGlobal;
import helpers.LangHolder;
import helpers.UserHolder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("ResourceAsColor")
public class CustomListCoachSubscribe extends ArrayAdapter<DataInCoachesSubscribe.CoachesSubscribes> {
    private Activity Activity;
    private ArrayList<DataInCoachesSubscribe.CoachesSubscribes> Data;
    private DisplayImageOptions options;

    public CustomListCoachSubscribe(Activity context, ArrayList<DataInCoachesSubscribe.CoachesSubscribes> data) {
        super(context, R.layout.singel_hall_subscribe_list, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singel_hall_subscribe_list, null, true);
        TextView tv_Price = rowView.findViewById(R.id.tv_Price);
        TextView tv_Title = rowView.findViewById(R.id.tv_Title);
        TextView tv_PlanDate = rowView.findViewById(R.id.tv_PlanDate);
        TextView tv_Rate = rowView.findViewById(R.id.tv_Rate);
        TextView tv_EndDate = rowView.findViewById(R.id.tv_EndDate);
        TextView tv_ID = rowView.findViewById(R.id.tv_ID);
        TextView tv_Home = rowView.findViewById(R.id.tv_Home);
        TextView tv_UserPage = rowView.findViewById(R.id.tv_UserPage);
        ImageView Logo = rowView.findViewById(R.id.Logo);
        LinearLayout List = rowView.findViewById(R.id.List);
        LinearLayout Top = rowView.findViewById(R.id.Top);


        tv_Title.setText(Data.get(position).user_name);
        tv_PlanDate.setText(Data.get(position).reserve_name);

        tv_EndDate.setText(" : " + Data.get(position).expire_date);

        tv_ID.setText(" : " + Data.get(position).id);


        tv_Price.setText(Data.get(position).price + "  " + Activity.getResources().getString(R.string.DK));
        if (Data.get(position).status.equals("start")) {
            Top.setBackgroundResource(R.drawable.btn_green_gradient);
        } else {
            Top.setBackgroundResource(R.drawable.btn_gray_radius);
        }

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.def_icon)
                .showImageForEmptyUri(R.mipmap.def_icon)
                .showImageOnFail(R.mipmap.def_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(Values.Link_ImageHomeCats + Data.get(position).user_logo, Logo, options);


        tv_Home.setOnClickListener(v -> {
            Intent i = new Intent(Activity, CoachDetails.class);
            i.putExtra("ID", Data.get(position).id);
            Activity.startActivity(i);
        });

        tv_UserPage.setOnClickListener(v -> {
            Intent i = new Intent(Activity, AccountMenu.class);
            i.putExtra("ID", Data.get(position).id);
            Activity.startActivity(i);
        });

       /* tv_Rate.setOnClickListener(v -> {
            if (!Data.get(position).isRated)
                loadPlans(Data.get(position).coach_name + " / " + Data.get(position).reserve_name,
                        position);
        });*/


        return rowView;
    }

    private void loadPlans(String HallName, int Pos) {
        final BottomSheetDialog dialog = new BottomSheetDialog(Activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rate_item);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);

        TextView Desc = dialog.findViewById(R.id.MsgDesc);
        EditText Comment = dialog.findViewById(R.id.Comment);
        Button btn_Add = dialog.findViewById(R.id.btn_Add);
        RatingBar rateBar = dialog.findViewById(R.id.rateBar);

        Desc.setText(HallName + "");
        btn_Add.setOnClickListener(v -> {
            dialog.dismiss();
            addRate(rateBar.getRating(), Comment.getText().toString(), Data.get(Pos).user_id);

        });
        dialog.show();
    }

    private void addRate(float rate, String Comment, int hall_id) {

        OkHttpClient client = new OkHttpClient();

        String Url = Values.Link_service + "rates/coach/" + LangHolder.getInstance().getData() + "/v1";
        Log.i("TestApp_12", Url);
        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body1.addFormDataPart("rate", rate + "");
        body1.addFormDataPart("text", Comment + "");
        body1.addFormDataPart("target_id", hall_id + "");

        RequestBody body = body1.build();

        //     Log.i("TestApp_12", json);
        String Auth;
        Auth = UserHolder.getInstance().getData().token.token_type
                + " " + UserHolder.getInstance().getData().token.access_token;

        Log.i("TestApp_12", Auth);
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
            public void onResponse(Call call, Response response) {
                try {
                    DataInGlobal _DataRes ;
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInGlobal>() {
                    }.getType();

                    String XX = response.body().string();
                    Log.i("TestApp_5", XX);
                    _DataRes = g.fromJson(XX, t);
                    // Log.i("TestApp55666",response.body().string()+"");
                    // _DataRes = g.fromJson(response.body().string(), t);


                    Activity.runOnUiThread(() -> {
                        if (!_DataRes.success) {
                            if (_DataRes.code == 401) {
                                Intent i = new Intent(Activity, Login.class);
                                Activity.startActivity(i);
                            } else {
                                loadMsg(_DataRes.message);
                            }
                        } else {
                            loadMsg(_DataRes.message);
                            Activity.finish();
                            Activity.startActivity(Activity.getIntent());
                        }
                    });

                } catch (Exception e) {
                }
            }

        });

    }

    private void loadMsg(String MSG) {
        final Dialog dialog = new Dialog(Activity);
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
}
