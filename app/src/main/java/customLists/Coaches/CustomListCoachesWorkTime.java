package customLists.Coaches;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.usmart.com.bfit_coaches.R;

import java.util.ArrayList;

import dataInLists.DataInCoachesDetails;

@SuppressLint("ResourceAsColor")
public class CustomListCoachesWorkTime extends ArrayAdapter<DataInCoachesDetails.WorkTime> {
    private Activity Activity;
    private ArrayList<DataInCoachesDetails.WorkTime> Data;
    private DisplayImageOptions options;

    public CustomListCoachesWorkTime(Activity context, ArrayList<DataInCoachesDetails.WorkTime> data) {
        super(context, R.layout.singel_work_time_list, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singel_work_time_list, null, true);
        TextView txtKey = rowView.findViewById(R.id.tv_Key);
        TextView tv_OptionVal = rowView.findViewById(R.id.tv_OptionVal);

        tv_OptionVal.setText(Activity.getResources().getString(R.string.From) + " " +
                Data.get(position).time_from + " " +
                Activity.getResources().getString(R.string.To) + " " +
                Data.get(position).time_to + "");
        txtKey.setVisibility(View.GONE);

       /* if (Data.get(position).type.equals("male")) {
            txtKey.setText(R.string.Men);
        } else if (Data.get(position).type.equals("female")) {
            txtKey.setText(R.string.WoMen);
        } else if (Data.get(position).type.equals("mix")) {
            txtKey.setText(R.string.Mix);
        }*/



        return rowView;
    }

}
