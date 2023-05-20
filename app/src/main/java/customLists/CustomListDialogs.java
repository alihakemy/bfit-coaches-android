package customLists;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.usmart.com.bfit_coaches.R;

import java.util.ArrayList;

import dataInLists.DataInDialog;


@SuppressLint("ResourceAsColor")
public class CustomListDialogs extends ArrayAdapter<DataInDialog> {
	private Activity Activity;
	private ArrayList<DataInDialog> Data;

	public CustomListDialogs(Activity context, ArrayList<DataInDialog> data) {
		super(context, R.layout.singel_dialog_list, data);
		// TODO Auto-generated constructor stub
		this.Activity = context;
		this.Data = data;

	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = Activity.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.singel_dialog_list, null, true);
		TextView txtTitle = rowView.findViewById(R.id.tv_name);


		txtTitle.setText(Data.get(position).name);

		return rowView;
	}

}
