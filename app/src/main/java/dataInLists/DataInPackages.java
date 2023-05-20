package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInPackages implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInPackages() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public String message_en;
    public String message_ar;
    public ArrayList<PackagesDetails> data = new ArrayList<>();


    public class PackagesDetails {
        public int id;
        public String name;
        public String price;
        public String discount_price;
        public String title;
        public float discount;
        public int months_num;
        public byte common;

    }
}
