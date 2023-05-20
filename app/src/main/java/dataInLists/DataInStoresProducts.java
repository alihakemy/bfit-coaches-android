package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInStoresProducts implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInStoresProducts() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public Data data = new Data();

    public class Data {
        public boolean favorite;
        public Basic basic ;
        public ArrayList<Category> categories = new ArrayList<>();
        public ArrayList<Products> products = new ArrayList<>();
    }

    public class Basic {
        public int id;
        public String cover;
        public String logo;
        public String title;
    }
    public class Category {
        public int id;
        public boolean selected;
        public String image;
        public String title;
    }

    public class Products {
        public int id;
        public int category_id;
        public byte offer;
        public float offer_percentage;
        public boolean favorite;
        public String image;
        public String title;
        public String final_price;
        public String price_before_offer;
    }

}
