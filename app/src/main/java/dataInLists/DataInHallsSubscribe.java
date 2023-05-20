package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInHallsSubscribe implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInHallsSubscribe() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public ArrayList<HallsSubscribes> data = new ArrayList<>();

    public class HallsSubscribes {
        public int id;
        public int booking_id;
        public int hall_id;
        public boolean isRated;
        public String cover;
        public String logo;
        public String hall_name;
        public String reserve_name;
        public String price;
        public String expire_date;
        public String status;
        public String rate;
    }

}
