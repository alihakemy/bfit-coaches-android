package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInCartCheckout implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInCartCheckout() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public MainData data = new MainData();

    public class MainData {
        public String subtotal_price;
        public String delivery_cost;
        public String total_price;
        public String total_cost;
        public int count  ;
        public Address address  ;
        public ArrayList<Store> cart = new ArrayList<>();
    }


    public class Store {
        public int store_id;
        public int shipment_number;
        public String store_name;
        public String max_estimated_time_month;
        public String max_estimated_time_day;
        public String min_estimated_time_day;
        public String min_estimated_time_month;
        public String min_estimated_time;
        public String max_estimated_time;
        public String total_cost;
        public String delivery_cost;
        public String sub_total_cost;
        public ArrayList<Products> products = new ArrayList<>();
    }

    public class Products {
        public int id;
        public int store_id;
        public String title;
        public String free;
        public String image;
        public String store_name;
        public int type;
        public int order_period;
        public int count;
        public float offer_percentage;
        public String price_before_offer;
        public String final_price;
    }
    public class Address {
        public int id;
        public int user_id;
        public double latitude ;
        public double longitude ;
        public String title;
        public byte address_type;
        public byte deleted;
        public int area_id;
        public String area_name;
        public String gaddah;
        public String building;
        public String floor;
        public String apartment_number;
        public String street;
        public String main_address_id;
        public String area;
        public String extra_details;
        public String phone;
        public String piece;
        public boolean is_default;
        public String created_at;
        public String updated_at;
    }
}
