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
public class CustomListCoachesPlanDesc extends ArrayAdapter<DataInCoachesDetails.ReservationsDetails> {
    private Activity Activity;
    private ArrayList<DataInCoachesDetails.ReservationsDetails> Data;
    private DisplayImageOptions options;

    public CustomListCoachesPlanDesc(Activity context, ArrayList<DataInCoachesDetails.ReservationsDetails> data) {
        super(context, R.layout.singel_plan_desc_list, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singel_plan_desc_list, null, true);
        TextView txtKey = rowView.findViewById(R.id.tv_Desc);

        txtKey.setText(Data.get(position).name + "");



        return rowView;
    }

}
