package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInHallsDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInHallsDetails() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public Data data = new Data();

    public class Data implements Serializable {
        public byte rates_count;
        public boolean favorite;
        public Basic basic;
        public ArrayList<WorkTime> work_times;
        public ArrayList<Branches> branches;
        public ArrayList<Rates> all_rates;
        public ArrayList<Reservations> reservations;
        public ArrayList<Media> media;
        public Stars stars_count;
    }

    public class Basic implements Serializable {
        public int id;
        public boolean favorite;
        public String cover;
        public String logo;
        public String name;
        public String about_hole;
        public String story;
        public String hallname;
        public String about;
        public float rate;
    }

    public class WorkTime implements Serializable {
        public int id;
        public String time_from;
        public String time_to;
        public String type;
    }

    public class Media implements Serializable {
        public int id;
        public String image;
        public String type;
        public String thumbnail;
    }

    public class Branches implements Serializable {
        public int id;
        public String title;
        public float latitude;
        public float longitude;
        public boolean selected;
    }

    public class Stars implements Serializable {
        public byte one;
        public byte tow;
        public byte three;
        public byte four;
        public byte five;
    }

    public class Rates implements Serializable {
        public String title = "";
        public String text;
        public String user_name;
        public String created_at;
        public String user;
        public float rate;
    }

    public class Reservations implements Serializable {
        public int id;
        public String name;
        public String description;
        public String price;
        public String discount;
        public String discount_price;
        public byte is_discount;
        public byte common;
        public ArrayList<ReservationsDetails> details;
    }
    public class ReservationsDetails implements Serializable {
        public int id;
        public int booking_id;
        public String name;
    }

}
