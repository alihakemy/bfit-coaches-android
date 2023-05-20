package customLists;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.usmart.com.bfit_coaches.CoachDetails;
import com.usmart.com.bfit_coaches.R;

import java.util.ArrayList;

import constants.Values;
import dataInLists.DataInSearch;

public class CustomListNameAutoComp extends ArrayAdapter<DataInSearch.Details> {
    private ArrayList<DataInSearch.Details> items;
    private ArrayList<DataInSearch.Details> itemsAll;
    private ArrayList<DataInSearch.Details> suggestions;
    private String SearchType;
    private Activity Activity;

    public CustomListNameAutoComp(Activity context, ArrayList<DataInSearch.Details> items) {
        super(context, R.layout.singel_product_list_search, items);
        this.items = items;
        this.itemsAll = (ArrayList<DataInSearch.Details>) items.clone();
        this.suggestions = new ArrayList<>();
       // this.SearchType = SearchType;
        this.Activity = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.singel_product_list_search, null);
        }
        TextView txtTitle = rowView.findViewById(R.id.tv_Title);
        TextView txtPrice = rowView.findViewById(R.id.tv_MainPrice);
        TextView tv_BeforeDiscount = rowView.findViewById(R.id.tv_BeforeDiscount);
        TextView txtDiscountPersent = rowView.findViewById(R.id.tv_discount);
        ImageView imageView = rowView.findViewById(R.id.iv_Feeds);
        FrameLayout List = rowView.findViewById(R.id.List);


        int customer = items.get(position).id;
        if (customer != 0) {

            DisplayImageOptions options;
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.def)
                    .showImageForEmptyUri(R.mipmap.def)
                    .showImageOnFail(R.mipmap.def)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

            if (SearchType.equals("hall")) {
                txtDiscountPersent.setText(R.string.Halls);
                txtDiscountPersent.setBackgroundColor(Color.parseColor("#4FBDDA"));
                txtTitle.setText(Html.fromHtml(items.get(position).name));
                ImageLoader.getInstance().displayImage(Values.Link_Image + items.get(position).logo, imageView, options);
                tv_BeforeDiscount.setText("");
                txtPrice.setText("");
                List.setOnClickListener(v -> {
                   /* Intent i = new Intent(Activity, HallDetails.class);
                    i.putExtra("ID", items.get(position).id);
                    Activity.startActivity(i);*/
                });

            } else if (SearchType.equals("coach")) {
                txtDiscountPersent.setText(R.string.Coaches);
                txtDiscountPersent.setBackgroundColor(Color.parseColor("#FD3018"));
                txtTitle.setText(Html.fromHtml(items.get(position).name));
                ImageLoader.getInstance().displayImage(Values.Link_Image + items.get(position).image, imageView, options);
                tv_BeforeDiscount.setText("");
                txtPrice.setText("");
                List.setOnClickListener(v -> {
                    Intent i = new Intent(Activity, CoachDetails.class);
                    i.putExtra("ID", items.get(position).id);
                    Activity.startActivity(i);
                });

            } else {
                txtDiscountPersent.setText(R.string.Stores);
                txtDiscountPersent.setBackgroundColor(Color.parseColor("#000000"));

                int x = items.get(position).title.length();
              /*  if (x >= 100) {
                    txtTitle.setText(Html.fromHtml(items.get(position).title.substring(0, 100)) + " ...");
                } else {*/
                    txtTitle.setText(Html.fromHtml(items.get(position).title));
                tv_BeforeDiscount.setText("");
                txtPrice.setText("");
                List.setOnClickListener(v -> {
                   /* Intent i = new Intent(Activity, StoresProducts.class);
                    i.putExtra("StoreID", items.get(position).id);
                    Activity.startActivity(i);*/
                });

               // }
            }


        } else {
            txtTitle.setText(Html.fromHtml(items.get(position).title));
            imageView.setVisibility(View.GONE);
            txtPrice.setVisibility(View.GONE);

        }
        return rowView;
    }


    public void addAll(String SearchType , ArrayList<DataInSearch.Details> items) {
        this.SearchType = SearchType;
        super.addAll(items);
    }


    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            DataInSearch.Details str = ((DataInSearch.Details) (resultValue));
            return str.title;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (DataInSearch.Details item : itemsAll) {
                    if (item.title.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(item);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<DataInSearch.Details> filteredList = (ArrayList<DataInSearch.Details>) results.values;
            try {
                if (results != null && results.count > 0) {
                    clear();
                    for (DataInSearch.Details c : filteredList) {
                        add(c);
                    }
                    notifyDataSetChanged();
                }
            } catch (Exception e) {

            }
        }
    };

}