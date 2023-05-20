package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInProduct implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInProduct() {
        super();
    }


    public boolean success;
    public int code;
    public String message_en;
    public String message_ar;
    public String message;

    public Data data = new Data();

    public class Data {
        public Product product;
        public ArrayList<Related> related;

    }

    public class Product {
        public int id;
        public int remaining_quantity;
        public String title;
        public String description;
        public byte offer;
        public String final_price;
        public String price_before_offer;
        public Category category_name;
        public float offer_percentage;
        public boolean favorite;
        public ArrayList<PropertiesOptions> options;
        public ArrayList<ProductImages> images;
    }


    public class ProductImages implements Serializable {
        public int id;
        public int product_id;
        public String image;
        public byte main;
    }

    public class Related {
        public int id;
        public String title;
        public String image;
        public String final_price;
        public String price_before_offer;
        public byte offer;
        public float offer_percentage;
        public int category_id;
        public boolean favorite;
    }

    public class Category implements Serializable {
        public int id;
        public String title;
    }
    public class PropertiesOptions implements Serializable {
        public String value;
        public String option;
    }

}
