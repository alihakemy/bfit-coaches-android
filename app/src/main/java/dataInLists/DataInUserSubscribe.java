package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInUserSubscribe implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInUserSubscribe() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public MainData data;

    public class MainData {
        public UserData user_data;
        public ArrayList<Subscriptions> subscriptions = new ArrayList<>();
    }

    public class UserData {
        public int id;
        public String name;
        public String email;
        public String image;
    }

    public class Subscriptions {
        public int id;
        public String name;
        public String value;
    }

}
