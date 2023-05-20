package customLists;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usmart.com.bfit_coaches.Home;
import com.usmart.com.bfit_coaches.R;
import com.usmart.com.bfit_coaches.SelectPlans;
import com.usmart.com.bfit_coaches.UpdateRequest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import constants.Values;
import dataInLists.DataInCart;
import dataInLists.DataInGlobal2;
import dataInLists.DataInLogin;
import dataInLists.DataInPackages;
import dataInLists.DataInPlanDetails;
import helpers.FaceIdHolder;
import helpers.LangHolder;
import helpers.LoginHolder;
import helpers.NetWork;
import helpers.UserHolder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("ResourceAsColor")
public class CustomListPackage extends ArrayAdapter<DataInPackages.PackagesDetails> {
    private Activity Activity;
    private ArrayList<DataInPackages.PackagesDetails> Data;
    String lang;
    DataInGlobal2 _Data = new DataInGlobal2();
    PopupWindow BidWindow;

    public CustomListPackage(Activity context, ArrayList<DataInPackages.PackagesDetails> data) {
        super(context, R.layout.singel_plan_list, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

        if (LangHolder.getInstance().getData().equals("ar")) {
            lang = "ar";
        } else {
            lang = "en";
        }
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singel_plan_list, null, true);
        TextView tv_Title = rowView.findViewById(R.id.tv_Name);
        TextView tv_Desc = rowView.findViewById(R.id.tv_Text);
        TextView tv_Price = rowView.findViewById(R.id.tv_Price);
        TextView tv_Price_Before = rowView.findViewById(R.id.tv_Price_Before);
        ImageView ivDialogue = rowView.findViewById(R.id.ivDialogue);
        LinearLayout List = rowView.findViewById(R.id.List);


        tv_Title.setText(Data.get(position).name + "");
        tv_Desc.setText(Data.get(position).title + "");
        tv_Price.setText(Data.get(position).price + " " + Activity.getResources().getString(R.string.DK3));

        if (Data.get(position).discount == 0) {
            tv_Price_Before.setVisibility(View.INVISIBLE);
            tv_Price.setText(Data.get(position).price + " " + Activity.getString(R.string.DK3));
            tv_Price_Before.setText("");

        } else {
            tv_Price_Before.setPaintFlags(tv_Price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_Price_Before.setVisibility(View.VISIBLE);
            tv_Price.setText(Data.get(position).discount_price + " " + Activity.getString(R.string.DK3));
            tv_Price_Before.setText(Data.get(position).price + " " + Activity.getString(R.string.DK3));

        }

        ivDialogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDeleteDialog(Data.get(position).id);
            }
        });

        List.setOnClickListener(view -> Activity.startActivity(new Intent(Activity, UpdateRequest.class)
                .putExtra("ID", Data.get(position).id)
        ));

        return rowView;
    }

    public void editDeleteDialog(int planId) {
        final Dialog dialog = new Dialog(Activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.edit_delete_plan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);


        TextView tvDelete = dialog.findViewById(R.id.tvDelete);
        TextView tvEdit = dialog.findViewById(R.id.tvEdit);

        tvDelete.setOnClickListener(v -> {
            selectAction(planId);
            dialog.dismiss();
        });
        tvEdit.setOnClickListener(v -> {
            Intent i = new Intent(Activity, UpdateRequest.class);
            i.putExtra("ID", planId);
            Activity.startActivity(i);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void removePlan(int id) {
        OkHttpClient client = new OkHttpClient();
        String Url = Values.Link_service + "coach/plan/delete/" + lang + "/v1";
        String json = new StringBuilder()
                .append("{")
                .append("\"id\":" + id)
                .append("}").toString();
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json
        );
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
                try {
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInGlobal2>() {
                    }.getType();
                    String XX = response.body().string();
                    Log.i("TestApp_3", XX);
                    _Data = g.fromJson(XX, t);

                    Activity.runOnUiThread(() -> {
                        if (!_Data.success) {
                            Toast.makeText(Activity, Activity.getResources().getString(R.string.Error), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Activity, Activity.getResources().getString(R.string.deleteSuccessfully), Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((SelectPlans) Activity).loadData();
                                }
                            }, 500);
                        }
                    });

                } catch (Exception e) {
                    Log.i("TestApp_5", e.getMessage());
                }
            }

        });

        /*OkHttpClient client = new OkHttpClient();
        String Url = Values.Link_service + "coach/plan/delete/" + lang + "/v1";
        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body1.addFormDataPart("id", id + "");
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

                    Activity.runOnUiThread(() -> {
                        if (!_Data.success) {
                            Toast.makeText(Activity, Activity.getResources().getString(R.string.Error), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Activity, Activity.getResources().getString(R.string.deleteSuccessfully), Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    Log.i("TestApp_3_Error", e.getMessage() + "");
                }
            }

        });*/
    }

    public void selectAction(int id) {
        final Dialog dialog2 = new Dialog(Activity);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.delete_plan);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView Yes = dialog2.findViewById(R.id.Yes);
        TextView No = dialog2.findViewById(R.id.No);

        Yes.setOnClickListener(v -> {
            removePlan(id);
            dialog2.dismiss();
        });
        No.setOnClickListener(v -> {
            dialog2.dismiss();
        });
        dialog2.show();
    }

}
