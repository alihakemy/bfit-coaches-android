package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataInPersonalOptions implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInPersonalOptions() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public ArrayList<Options> data;


    public class Options {
        public int id;
        public String is_required;
        public String type;
        public String title;
        public ArrayList<OptionsValues> goals = new ArrayList<>();
    }

    public class OptionsValues {
        public int id;
        public int type_id;
        public String title;
    }

}
