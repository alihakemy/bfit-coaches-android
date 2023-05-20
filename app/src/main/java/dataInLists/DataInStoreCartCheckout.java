package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInStoreCartCheckout implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInStoreCartCheckout() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public MainData data = new MainData();

    public class MainData {
        public float subtotal_price;
        public float delivery_cost;
        public float total_price;
        public int count  ;
        public Address address  ;
        public ArrayList<Shipments> shipments = new ArrayList<>();
    }

    public class Shipments {
        public int shipment_number;
        public Store store;
    }

    public class Store {
        public int id;
        public String name;
        public String logo;
        public String estimated_arrival_time;
        public float delivery_cost;
        public float total_price;
        public ArrayList<Products> products = new ArrayList<>();
    }

    public class Products {
        public int id;
        public String title;
        public String main_image;
        public String store_name;
        public float offer_percentage;
        public int offer;
        public float price_before_offer;
        public float final_price;
    }
    public class Address {
        public int id;
        public int user_id;
        public double latitude ;
        public double longitude ;
        public String title;
        public byte address_type;
        public int area_id;
        public String area_name;
        public String gaddah;
        public String building;
        public String floor;
        public String apartment_number;
        public String street;
        public String extra_details;
        public String phone;
        public String piece;
        public boolean is_default;
        public String created_at;
        public String updated_at;
    }
}
