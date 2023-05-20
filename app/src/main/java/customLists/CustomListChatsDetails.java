package customLists;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.usmart.com.bfit_coaches.R;

import java.util.ArrayList;

import constants.Values;
import dataInLists.DataInChats;
import dataInLists.DataInChatsDetails;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ResourceAsColor")
public class CustomListChatsDetails extends ArrayAdapter<DataInChatsDetails.Messages> {
    private Activity Activity;
    private ArrayList<DataInChatsDetails.Messages> Data;
    private DisplayImageOptions options;

    public CustomListChatsDetails(Activity context, ArrayList<DataInChatsDetails.Messages> data) {
        super(context, R.layout.singel_chat_details_list, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singel_chat_details_list, null, true);
        TextView tv_Day = rowView.findViewById(R.id.tv_Day);
        ExpandableHeightListView lvMessages = rowView.findViewById(R.id.lvMessages);
        CustomListChatsMessages adapter ;

        tv_Day.setText(Data.get(position).day + " ");
        adapter = new CustomListChatsMessages(Activity , Data.get(position).day_messages);
        lvMessages.setExpanded(true);
        lvMessages.setAdapter(adapter);
        return rowView;
    }


}
