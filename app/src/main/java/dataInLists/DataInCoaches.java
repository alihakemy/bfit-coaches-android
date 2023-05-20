package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInCoaches implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInCoaches() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public ArrayList<Coaches> data = new ArrayList<>();

    public class Coaches {
        public int id;
        public boolean favorite;
        public String about;
        public String coachname;
        public String image;
        public String name;
        public String rate;
    }

}
