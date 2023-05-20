package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;

public class DataInOrderItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInOrderItem() {
        super();
    }


    public boolean success;
    public int code;
    public String message;
    public MainData data = new MainData();

    public class MainData {
        public int id;
        public int user_id;
        public int booking_id;
        public String price;
        public String expire_date;
        public String created_at;
        public String payment;
        public String status;
        public UserInfo user_info  ;
        public PlanDetails plan_details ;
    }

    public class UserInfo {
        public int id;
        public String name;
        public String email;
        public String image;
    }


    public class PlanDetails {
        public int id;
        public int coach_id;
        public String name;
        public String title;
    }


}
