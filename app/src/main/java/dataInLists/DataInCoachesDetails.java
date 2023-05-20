package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInCoachesDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInCoachesDetails() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public Data data = new Data();

    public class Data {
        public byte plans_num;
        public byte rates_count;
        public boolean favorite;
        public boolean free_ask;
        public boolean payed_ask;
        public Basic basic;
        public ArrayList<WorkDays> week_days;
        public WorkTime work_times;
        public ArrayList<Rates> all_rates;
        public ArrayList<Reservations> reservations;
        public ArrayList<Media> media_image;
        public ArrayList<Media> media_video;
        public Stars stars_count;
    }

    public class Basic {
        public int id;
        public boolean favorite;
        public String thumbnail;
        public String image;
        public String name;
        public String name_en;
        public String about_coach;
        public String about_coach_en;
        public String story;
        public String coachname;
        public String about;
        public String work_day_from;
        public String work_day_to;
        public WorkDays day_from;
        public WorkDays day_to;
        public float rate;
    }

    public class WorkDays {
        public int id;
        public String name;
    }

    public class WorkTime {
        public int id;
        public String time_from;
        public String time_to;
    }

    public class Media {
        public Media(int id, String image, String type) {
            this.id = id;
            this.image = image;
            this.type = type;
        }
        public Media( String image, String type) {

            this.image = image;
            this.type = type;
        }

        public int id;
        public String image;
        public String type;
        public String thumbnail;
    }

    public class Stars {
        public byte one;
        public byte tow;
        public byte three;
        public byte four;
        public byte five;
    }

    public class Rates {
        public String title = "";
        public String text;
        public String user_name;
        public String created_at;
        public String user;
        public float rate;
    }

    public class Reservations {
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
    public class ReservationsDetails {
        public int id;
        public int booking_id;
        public String name;
    }

}
