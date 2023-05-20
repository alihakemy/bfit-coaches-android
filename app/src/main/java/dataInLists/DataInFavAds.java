package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInFavAds implements Serializable {


    private static final long serialVersionUID = 1L;

    public DataInFavAds() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public Main data = new Main();


    public class Main {
        public ArrayList<Halls> halls = new ArrayList<>();
        public ArrayList<Coaches> coaches = new ArrayList<>();
        public ArrayList<Shops> shops = new ArrayList<>();
    }
    public class Halls {
        public int id;
        public String cover;
        public String hallname;
        public String about;
        public String logo;
        public String started_price;
        public int hall_id;
        public float rate;
        public boolean favorite;
    }
    public class Coaches {
        public int id;
    }
    public class Shops {
        public int id;
    }

}
