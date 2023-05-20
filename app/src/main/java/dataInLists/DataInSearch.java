package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInSearch implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInSearch() {
        super();
    }


    public boolean success;
    public int code;
    public Data data ;


    public class Data {
        public ArrayList<Details> shops = new ArrayList<>();
        public ArrayList<Details> halls = new ArrayList<>();
        public ArrayList<Details> coaches = new ArrayList<>();
    }

    public class Details {
        public int id;
        public float rate;
        public String title;
        public String coachname;
        public String hallname;
        public String logo;
        public String cover;
        public String image;
        public String name;
        public String about;
        public boolean favorite;
    }

}

