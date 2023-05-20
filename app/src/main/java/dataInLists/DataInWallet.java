package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInWallet implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInWallet() {
        super();
    }


    public boolean success;
    public int code;
    public String message_en;
    public String message_ar;
    public String message;
    public Data data = new Data();

    public class Data {
        public int id;
        public Balance my_balance;
        public ArrayList<History> history;

    }

    public class Balance implements Serializable {
        public int id;
        public String my_balance;
    }

    public class History implements Serializable {
        public int id;
        public String created_at;
        public String name;
        public String price;
    }

}
