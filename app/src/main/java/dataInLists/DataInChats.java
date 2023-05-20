package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInChats implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInChats() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public Main data ;


    public class Main {
        public  ArrayList<Chats> conversations = new ArrayList<>();
    }

    public class Chats {
        public int user_id;
        public int other_user_id;
        public int conversation_id;
        public int coach_id;
        public int un_read_num;
        public String user_type;
        public String other_user_type;
        public String user_name;
        public String image;
        public String last_message;
        public String last_message_time;
        public String conversation_type;
        public String created_at;
        public String updated_at;
        public byte deleted;
        public boolean free_ask;
    }

}
