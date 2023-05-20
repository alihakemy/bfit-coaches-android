package Slider;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.usmart.com.bfit_coaches.R;


public class CustomSlider extends BaseSliderView {

    public CustomSlider(Context context) {
        super(context);
    }

    public View getView() {

        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.render_type_text, null);
        ImageView target = v.findViewById(R.id.daimajia_slider_image);
        LinearLayout frame = v.findViewById(R.id.description_layout);
        frame.setBackgroundColor(Color.TRANSPARENT);

       /* int pagerPadding = 16;
        target.setPadding(10, 0, pagerPadding, 0);*/

//      if you need description
//      description.setText(this.getDescription());

        this.bindEventAndShow(v, target);

        return v;
    }
}