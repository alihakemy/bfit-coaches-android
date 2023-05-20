package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInChatsDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInChatsDetails() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public Main data;


    public class Main {
        public UserData ad_user_data;
        public ArrayList<Messages> messages = new ArrayList<>();
    }

    public class UserData {
        public String name;
        public String email;
        public String image;
        public String phone;
        public int coach_id;
    }

    public class Messages {
        public Messages(String day, ArrayList<Chats> day_messages) {
            this.day = day;
            this.day_messages = day_messages;
        }

        public String day;
        public ArrayList<Chats> day_messages = new ArrayList<>();
    }

    public class Chats {
        public Chats(int user_id, int conversation_id, int coach_id,
                     String message, String type) {
            this.user_id = user_id;
            this.conversation_id = conversation_id;
            this.coach_id = coach_id;
            this.message = message;
            this.type = type;
            this.time = "";
            this.created_at = "";
            this.position = "right";
        }

        public int id;
        public int user_id;
        public int conversation_id;
        public int coach_id;
        public String message;
        public String type;
        public String user_type;
        public String image;
        public String time;
        public String created_at;
        public String position;
        public String conversation_type;
        public byte deleted;
        public String updated_at;
    }

}
