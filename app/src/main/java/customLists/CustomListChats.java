package customLists;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.usmart.com.bfit_coaches.Home;
import com.usmart.com.bfit_coaches.Login;
import com.usmart.com.bfit_coaches.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import constants.Values;
import dataInLists.DataInAddress;
import dataInLists.DataInChats;
import dataInLists.DataInGlobal;
import dataInLists.DataInGlobal2;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.LangHolder;
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
public class CustomListChats extends ArrayAdapter<DataInChats.Chats> {
    private Activity Activity;
    private ArrayList<DataInChats.Chats> Data;
    private DisplayImageOptions options;

    public CustomListChats(Activity context, ArrayList<DataInChats.Chats> data) {
        super(context, R.layout.singel_chat_list, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singel_chat_list, null, true);
        TextView txtName = rowView.findViewById(R.id.tv_Name);
        TextView txtText = rowView.findViewById(R.id.tv_Text);
        TextView tv_status = rowView.findViewById(R.id.tv_status);
        CircleImageView imageView = rowView.findViewById(R.id.iv_Feeds);
        ImageView Action = rowView.findViewById(R.id.Action);


        txtName.setText(Data.get(position).user_name + " ");
        txtText.setText(Data.get(position).last_message + " ");

        if (Data.get(position).conversation_type.equals("empty")) {
            tv_status.setText(R.string.ClosedChat);
            tv_status.setBackgroundResource(R.drawable.btn_yellow_gradient_50);
        } else {
            tv_status.setText(R.string.AvailableChat);
            tv_status.setBackgroundResource(R.drawable.btn_green_gradient_50);
        }

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.def)
                .showImageForEmptyUri(R.mipmap.def)
                .showImageOnFail(R.mipmap.def)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(Values.Link_Image + Data.get(position).image, imageView, options);

        Action.setOnClickListener(view -> selectِAction(position));

        return rowView;
    }


    private void selectِAction(int Position) {
        final Dialog dialog = new Dialog(Activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_noads_options);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        TextView Yes = dialog.findViewById(R.id.btn_2);
        TextView No = dialog.findViewById(R.id.btn_1);
        TextView Cancel = dialog.findViewById(R.id.btn_3);

        Yes.setText(R.string.CloseChat);
        No.setText(R.string.DeleteChat);

        if (Data.get(Position).free_ask == false) {
            Yes.setVisibility(View.GONE);
        }
        Yes.setOnClickListener(v -> {
            /*AddressID = Integer.toString(_Data.data.get(Position).id);
            AddressName = _Data.data.get(Position).title;
            for (int i = 0; i < _Data.data.size(); i++) {
                _Data.data.get(i).is_default = false;
            }
            _Data.data.get(Position).is_default = true;
            adapter2.notifyDataSetChanged();

            setDefAddress(_Data.data.get(Position).id);*/
            //setOnBack(Position);
            loadCloseChat(Data.get(Position).other_user_id);
            dialog.dismiss();
        });
        No.setOnClickListener(v -> {
            loadDeleteChat(Data.get(Position).conversation_id);
            remove(Data.get(Position));
            notifyDataSetChanged();
            dialog.dismiss();
        });
        Cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void loadDeleteChat(int ID) {

        OkHttpClient client = new OkHttpClient();
        String Url;
        Url = Values.Link_service + "chat/remove_conversation/coach/" + ID + "/" + LangHolder.getInstance().getData() + "/v1/";
      /*  String json = new StringBuilder()
                .append("{")
                .append("\"unique_id\":\"" + Settings.Secure.getString(Activity.getContentResolver(),
                        Settings.Secure.ANDROID_ID) + "\",")
                .append("\"product_id\":\"" + ID + "\"")
                .append("}").toString();

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                json
        );*/
        Request request = new Request.Builder()
                .url(Url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                        + " " + UserHolder.getInstance().getData().token.access_token)
                .get()
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
                    //Log.i("TestApp", response.body().string());


                    Activity.runOnUiThread(() -> {
                        if (!Result.success) {
                            if (Result.code == 401) {
                                Intent i = new Intent(Activity, Login.class);
                                Activity.startActivity(i);
                            } else {
                                //  loadMsg(Result.message);
                            }
                        } else {
                            //CartNum.setText(Result.data.count + "");
                            // loadMsg(Activity.getResources().getString(R.string.AddedToCart));
                            Activity.finish();
                            Activity.overridePendingTransition(0, 0);
                            Activity.startActivity(Activity.getIntent());
                            Activity.overridePendingTransition(0, 0);
                        }
                    });

                } catch (Exception e) {
                }
            }

        });

    }

    private void loadCloseChat(int ID) {

        OkHttpClient client = new OkHttpClient();
        String Url;
        Url = Values.Link_service + "coach/chat/end_consultation/" + LangHolder.getInstance().getData() + "/v1";
        Log.i("TestApp", Url);
        MultipartBody.Builder body1 = new MultipartBody.Builder().setType(MultipartBody.FORM);
        body1.addFormDataPart("user_id", ID + "");

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
                String Res = response.body().string();
                Log.i("TestApp", Res);
                try {

                    DataInGlobal2 Result;
                    Gson g = new Gson();
                    Type t = new TypeToken<DataInGlobal2>() {
                    }.getType();

                    Result = g.fromJson(Res, t);


                    Activity.runOnUiThread(() -> {
                        if (!Result.success) {
                            if (Result.code == 401) {
                                Intent i = new Intent(Activity, Login.class);
                                Activity.startActivity(i);
                            } else {
                                loadMsg(Result.message);
                            }
                        } else {
                            //CartNum.setText(Result.data.count + "");
                            loadMsg(Result.message);
                           /* Activity.finish();
                            Activity.overridePendingTransition(0, 0);
                            Activity.startActivity(Activity.getIntent());
                            Activity.overridePendingTransition(0, 0);*/
                        }
                    });

                } catch (Exception e) {
                    Log.i("TestApp", e.getMessage());
                }
            }

        });

    }

    private void loadMsg(String MSG) {
        final Dialog dialog = new Dialog(Activity);
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
            dialog.dismiss();
        });
        dialog.show();
    }

}
