package customLists.Coaches;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.usmart.com.bfit_coaches.R;

import java.util.ArrayList;

import dataInLists.DataInCoachesDetails;

@SuppressLint("ResourceAsColor")
public class CustomListCoachesRates extends ArrayAdapter<DataInCoachesDetails.Rates> {
    private Activity Activity;
    private ArrayList<DataInCoachesDetails.Rates> Data;

    public CustomListCoachesRates(Activity context, ArrayList<DataInCoachesDetails.Rates> data) {
        super(context, R.layout.singel_rate_list, data);
        // TODO Auto-generated constructor stub
        this.Activity = context;
        this.Data = data;

    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = Activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.singel_rate_list, null, true);
        TextView txtTitle = rowView.findViewById(R.id.tv_Title);
        TextView txtDate = rowView.findViewById(R.id.tv_Date);
        TextView txtName = rowView.findViewById(R.id.tv_Name);
        RatingBar rateBar = rowView.findViewById(R.id.rateBar);
        TextView txtComment = rowView.findViewById(R.id.tv_Comment);

        txtName.setText(Data.get(position).user + "");
        txtComment.setText(Data.get(position).text + "");
        //if (!Data.get(position).title.isEmpty()) {
        // txtTitle.setText(Data.get(position).title + "");
        txtTitle.setText("");

        txtDate.setText(Data.get(position).created_at + "");

        rateBar.setRating(Data.get(position).rate);
        rateBar.setEnabled(false);


        return rowView;
    }

}
