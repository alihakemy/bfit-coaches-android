package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInGlobal2 implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInGlobal2() {
        super();
    }


    public boolean success;
    public int code;
    public String message_en;
    public String message_ar;
    public String message;
    public ArrayList<Data> data = new ArrayList<>();

    public class Data {
        public int id;
        public int count;
        public int visitor_id;
        public int product_id;
        public String url;
        public String my_balance;
        public String phone;
        public String instegram;
        public String watsapp;
        public String delivery_cost;
        public float min_free_delivery;
        public boolean show_ad;
        public boolean status;
        public String created_at;
        public String updated_at;

    }
}
