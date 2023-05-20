package customLists.Coaches;

import android.app.Activity;
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
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;
import com.usmart.com.bfit_coaches.PhotoGallery;
import com.usmart.com.bfit_coaches.R;
import com.usmart.com.bfit_coaches.WatchVideo;

import java.io.IOException;
import java.util.ArrayList;

import constants.Values;
import dataInLists.DataInCoachesDetails;
import helpers.LangHolder;
import helpers.UserHolder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CustomListCoachesMedia extends RecyclerView.Adapter<CustomListCoachesMedia.ViewHolder> {
    private Activity Activity;
    private ArrayList<DataInCoachesDetails.Media> Data;
    private DisplayImageOptions options;
    ArrayList<String> Photos = new ArrayList<>();

    public CustomListCoachesMedia(Activity context, ArrayList<DataInCoachesDetails.Media> data) {
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.singel_media_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.def)
                .showImageForEmptyUri(R.mipmap.def)
                .showImageOnFail(R.mipmap.def)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


        if (Data.get(position).type.equals("image")) {
            holder.VidIcon.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(Values.Link_Image + Data.get(position).image, holder.Image, options);
        } else {

            ImageLoader.getInstance().displayImage(Values.Link_Image + Data.get(position).thumbnail, holder.Image, options);
        }
        holder.List.setOnClickListener(v -> {
            if (Data.get(position).type.equals("image")) {
                for (DataInCoachesDetails.Media _data : Data) {
                    if (_data.type.equals("image"))
                        Photos.add(_data.image + "");
                }
                loadPhoto();
            } else {
                //loadVideo(Values.Link_Video + Data.get(position).image);
                Activity.startActivity(new Intent(Activity, WatchVideo.class)
                        .putExtra("Code", Values.Link_Video + Data.get(position).image)
                );
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lang;
                if (LangHolder.getInstance().getData().equals("ar")) {
                    lang = "ar";
                } else {
                    lang = "en";
                }
                //https://nshapekw.com/api/coach/media/delete/en/v1
                String Url = Values.Link_service + "coach/media/delete/" + lang + "/v1";

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();

                RequestBody formBody = new FormBody.Builder()
                        .add("id", String.valueOf(Data.get(position).id))
                        .build();
                Request request = new Request.Builder()
                        .url(Url)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "" + UserHolder.getInstance().getData().token.token_type
                                + " " + UserHolder.getInstance().getData().token.access_token)
                        .method("POST", formBody)
                        .build();

                    client.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            Activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Data.remove(position);
                                    notifyDataSetChanged();
                                }
                            });

                        }
                    });


            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView Image, VidIcon ,remove;
        ConstraintLayout List;

        public ViewHolder(View rowView) {
            super(rowView);

            // get the reference of item view's
            Image = rowView.findViewById(R.id.iv_Feeds);
            VidIcon = rowView.findViewById(R.id.VidIcon);
            List = rowView.findViewById(R.id.List);
            remove=rowView.findViewById(R.id.remove);
        }
    }

    private void loadVideo(String Path) {
        final BottomSheetDialog dialog = new BottomSheetDialog(Activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.msg_video);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);

        UniversalVideoView mVideoView;
        UniversalMediaController mMediaController;

        mVideoView = dialog.findViewById(R.id.videoView);
        mMediaController = dialog.findViewById(R.id.media_controller);
        mVideoView.setMediaController(mMediaController);
        try {
            Log.i("TestApp_Video", Path);
            mVideoView.setVideoPath(Path);

        } catch (Exception e) {
            Log.i("TestApp_Video", e.getMessage());
        }
        dialog.show();
    }

    private void loadPhoto() {
        Intent i = new Intent(Activity, PhotoGallery.class);
        i.putExtra("Photos", Photos);
        Activity.startActivity(i);
        //overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

}
