package customLists;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.usmart.com.bfit_coaches.NewPassProfile;
import com.usmart.com.bfit_coaches.R;

import java.util.ArrayList;

import dataInLists.DataInListIcons;

@SuppressLint("ResourceAsColor")
public class CustomListMenuAccount extends ArrayAdapter<DataInListIcons> {
    private Activity Activity;
    private ArrayList<DataInListIcons> Data;
    private DisplayImageOptions options;
    SharedPreferences Login;

    public CustomListMenuAccount(Activity context, ArrayList<DataInListIcons> data) {
        super(context, R.layout.drawer_list_item, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.def)
                .showImageForEmptyUri(R.mipmap.def).showImageOnFail(R.mipmap.def)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView;

        rowView = inflater.inflate(R.layout.drawer_list_item, null, true);

        if (position >= 0) {
            TextView txtTitle = rowView.findViewById(R.id.Menutitle);
            TextView tv_Password = rowView.findViewById(R.id.tv_Password);
            TextView tv_Date = rowView.findViewById(R.id.tv_Date);
            ImageView imageView = rowView.findViewById(R.id.Menuicon);
            ImageView imageView2 = rowView.findViewById(R.id.Menuicon2);
            txtTitle.setText(Data.get(position).name);
            imageView.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(Data.get(position).photo, imageView2, options);
            if (Data.get(position).type == 1) {
                tv_Password.setVisibility(View.VISIBLE);
                tv_Date.setVisibility(View.GONE);

                tv_Password.setOnClickListener(v -> Activity.startActivity(new Intent(Activity, NewPassProfile.class)));
            }
            if (Data.get(position).type == 2) {
                tv_Password.setVisibility(View.GONE);
                tv_Date.setVisibility(View.VISIBLE);
                tv_Date.setText(Data.get(position).created_at + "");
            }
        }
      /*  if (position == 6) {
            TextView txtTitle = rowView.findViewById(R.id.Menutitle);
            txtTitle.setTextColor(Color.parseColor("#DB3022"));
        }*/


        return rowView;
    }


}
