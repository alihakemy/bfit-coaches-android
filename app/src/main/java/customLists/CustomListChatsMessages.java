package customLists;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;
import com.usmart.com.bfit_coaches.R;

import java.util.ArrayList;

import constants.Values;
import dataInLists.DataInChatsDetails;

@SuppressLint("ResourceAsColor")
public class CustomListChatsMessages extends ArrayAdapter<DataInChatsDetails.Chats> {
    private Activity Activity;
    private ArrayList<DataInChatsDetails.Chats> Data;
    private DisplayImageOptions options;

    public CustomListChatsMessages(Activity context, ArrayList<DataInChatsDetails.Chats> data) {
        super(context, R.layout.singel_chat_details_list, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singel_message_details_list, null, true);
        TextView tv_Time_Left = rowView.findViewById(R.id.tv_Time_Left);
        TextView tv_Left = rowView.findViewById(R.id.tv_Left);
        TextView tv_Time_Right = rowView.findViewById(R.id.tv_Time_Right);
        TextView tv_Right = rowView.findViewById(R.id.tv_Right);
        ImageView iv_Right = rowView.findViewById(R.id.iv_Right);
        ImageView iv_Left = rowView.findViewById(R.id.iv_Left);

        FrameLayout video_layout_Right = rowView.findViewById(R.id.video_layout_Right);
        FrameLayout video_layout_Left = rowView.findViewById(R.id.video_layout_Left);
        UniversalVideoView videoView_Right = rowView.findViewById(R.id.videoView_Right);
        UniversalVideoView videoView_Left = rowView.findViewById(R.id.videoView_Left);
        UniversalMediaController media_controller_Right = rowView.findViewById(R.id.media_controller_Right);
        UniversalMediaController media_controller_Left = rowView.findViewById(R.id.media_controller_Left);


        tv_Time_Right.setText(Data.get(position).time + " ");
        tv_Time_Left.setText(Data.get(position).time + " ");

        if (Data.get(position).position.equals("left")) {
            tv_Time_Left.setVisibility(View.VISIBLE);
            tv_Time_Right.setVisibility(View.GONE);
            tv_Right.setVisibility(View.GONE);
            video_layout_Right.setVisibility(View.GONE);
            videoView_Right.setVisibility(View.GONE);
            media_controller_Right.setVisibility(View.GONE);

            if (Data.get(position).type.equals("text")) {
                tv_Left.setVisibility(View.VISIBLE);
                iv_Left.setVisibility(View.GONE);
                tv_Left.setText(Data.get(position).message + "");
                video_layout_Left.setVisibility(View.GONE);
            } else if (Data.get(position).type.equals("image")) {
                iv_Left.setVisibility(View.VISIBLE);
                tv_Left.setVisibility(View.GONE);
                video_layout_Left.setVisibility(View.GONE);
                DisplayImageOptions options;

                options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.def2)
                        .showImageForEmptyUri(R.mipmap.def2)
                        .showImageOnFail(R.mipmap.def2)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();

                ImageLoader.getInstance().displayImage(Values.Link_Image + Data.get(position).message, iv_Left, options);
            } else if (Data.get(position).type.equals("video")) {
                videoView_Left.setMediaController(media_controller_Right);
                iv_Left.setVisibility(View.GONE);
                video_layout_Left.setVisibility(View.VISIBLE);
                tv_Left.setVisibility(View.GONE);

                videoView_Left.setVideoPath(Values.Link_Video + Data.get(position).message);
            }

        } else {
            tv_Time_Right.setVisibility(View.VISIBLE);
            tv_Time_Left.setVisibility(View.GONE);
            tv_Left.setVisibility(View.GONE);
            video_layout_Left.setVisibility(View.GONE);
            videoView_Left.setVisibility(View.GONE);
            media_controller_Left.setVisibility(View.GONE);

            if (Data.get(position).type.equals("text")) {

                tv_Right.setVisibility(View.VISIBLE);
                iv_Right.setVisibility(View.GONE);
                video_layout_Right.setVisibility(View.GONE);
                tv_Right.setText(Data.get(position).message + "");

            } else if (Data.get(position).type.equals("image")) {
                iv_Right.setVisibility(View.VISIBLE);
                tv_Right.setVisibility(View.GONE);
                video_layout_Right.setVisibility(View.GONE);
                DisplayImageOptions options;

                options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.def2)
                        .showImageForEmptyUri(R.mipmap.def2)
                        .showImageOnFail(R.mipmap.def2)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .considerExifParams(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();

                ImageLoader.getInstance().displayImage(Values.Link_Image + Data.get(position).message, iv_Right, options);

            } else if (Data.get(position).type.equals("video")) {
                videoView_Right.setMediaController(media_controller_Right);
                iv_Right.setVisibility(View.GONE);
                video_layout_Right.setVisibility(View.VISIBLE);
                tv_Right.setVisibility(View.GONE);

                videoView_Right.setVideoPath(Values.Link_Video + Data.get(position).message);

            }else if (Data.get(position).type.equals("file")) {
                tv_Right.setVisibility(View.VISIBLE);
                iv_Right.setVisibility(View.GONE);
                video_layout_Right.setVisibility(View.GONE);
                tv_Right.setText(Data.get(position).message + "");

            }


        }


        return rowView;
    }


}
