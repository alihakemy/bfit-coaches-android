package customLists;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;
import com.usmart.com.bfit_coaches.PhotoGallery;
import com.usmart.com.bfit_coaches.R;
import com.usmart.com.bfit_coaches.WatchVideo;

import java.util.ArrayList;

import constants.Values;
import dataInLists.DataInCartCheckout;
import dataInLists.DataInCoachesDetails;

@SuppressLint("ResourceAsColor")
public class CustomListCoachesMedia2 extends ArrayAdapter<DataInCoachesDetails.Media> {
    private Activity Activity;
    private ArrayList<DataInCoachesDetails.Media> Data;
    private DisplayImageOptions options;
    ArrayList<String> Photos = new ArrayList<>();

    public CustomListCoachesMedia2(Activity context, ArrayList<DataInCoachesDetails.Media> data) {
        super(context, R.layout.singel_media_list2, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singel_media_list2, null, true);

        ImageView Image, VidIcon;
        LinearLayout List;
        FrameLayout Photo , More;


        Image = rowView.findViewById(R.id.iv_Feeds);
        VidIcon = rowView.findViewById(R.id.VidIcon);
        List = rowView.findViewById(R.id.List);
        More = rowView.findViewById(R.id.More);
        Photo = rowView.findViewById(R.id.Photo);

        if(Data.get(position).id == 0){
            More.setVisibility(View.VISIBLE);
            Photo.setVisibility(View.GONE);
        }else {
            More.setVisibility(View.GONE);
            Photo.setVisibility(View.VISIBLE);
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


        if (Data.get(position).type.equals("image")) {
            VidIcon.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(Values.Link_Image + Data.get(position).image, Image, options);
        }
        else if (Data.get(position).type.equals("image_temp")) {
            VidIcon.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(Data.get(position).image, Image, options);
        }
        else {

            ImageLoader.getInstance().displayImage(Values.Link_Image + Data.get(position).thumbnail, Image, options);
        }
       /* List.setOnClickListener(v -> {
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
        });*/


        return rowView;
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
