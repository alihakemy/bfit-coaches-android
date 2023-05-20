package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInPoints implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInPoints() {
        super();
    }


    public boolean success;
    public int code;
    public String message_en;
    public String message_ar;
    public String message;
    public Data data = new Data();

    public class Data {
        public Points my_points;
        public ArrayList<Packages> packages;

    }

    public class Points implements Serializable {
        public String points;
    }

    public class Packages implements Serializable {
        public int id;
        public String points;
        public String price;
    }

}
