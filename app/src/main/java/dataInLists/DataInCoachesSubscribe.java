package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInCoachesSubscribe implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInCoachesSubscribe() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public ArrayList<CoachesSubscribes> data = new ArrayList<>();

    public class CoachesSubscribes {
        public int id;
        public int booking_id;
        public int user_id;
        public boolean isRated;
        public String user_logo;
        public String logo;
        public String user_name;
        public String reserve_name;
        public String price;
        public String expire_date;
        public String status;
        public String rate;
        public String payment;
        public String created_at;
    }

}
