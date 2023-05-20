package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInStores implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInStores() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public Data data = new Data();

    public class Data {
        public ArrayList<Category> categories = new ArrayList<>();
        public ArrayList<Stores> shops = new ArrayList<>();
    }

    public class Category {
        public int id;
        public boolean selected;
        public String image;
        public String title;
    }

    public class Stores {
        public int id;
        public boolean favorite;
        public String cover;
        public String logo;
        public String title;
        public String name;
    }

}
