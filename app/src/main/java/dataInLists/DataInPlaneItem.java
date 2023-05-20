package dataInLists;

import java.io.Serializable;

public class DataInPlaneItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInPlaneItem() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public String message_en;
    public String message_ar;
    public Plan data = new Plan();

    public class Plan {
        public int id;
        public String name_ar;
        public String name_en;
        public String title_ar;
        public String title_en;
        public String price;
        public String discount;
        public String discount_price;
        public int months_num;
        public byte is_discount;

    }
}
