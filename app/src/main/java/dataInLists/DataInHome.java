package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInHome implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInHome() {
        super();
    }


    public boolean success;
    public boolean next_page;
    public int code;
    public String message;
    public HomeData data;

    public class HomeData {
        public ArrayList<Ads> slider_ads;
        public ArrayList<Halls> famous_halls;
        public ArrayList<Coaches> famous_coaches;
        public ArrayList<Stores> famous_store;
    }

    public class Ads implements Serializable {
        public int id;
        public String type;
        public String image;
        public String content;
        public String title;
        public String target;
        public String desc;
    }

    public class Halls implements Serializable {
        public int id;
        public boolean favorite;
        public float rate;
        public String cover;
        public String content;
        public String logo;
        public String name;
        public String started_price;
    }

    public class Coaches implements Serializable {
        public int id;
        public boolean favorite;
        public byte available;
        public String image;
        public String name;
        public String coachname;
        public String about;
    }

    public class Stores implements Serializable {
        public int id;
        public String image;
        public String title;
        public String name;
    }
}
