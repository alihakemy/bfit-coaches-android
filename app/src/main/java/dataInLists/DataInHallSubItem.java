package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DataInHallSubItem implements Serializable {


    private static final long serialVersionUID = 2L;

    public DataInHallSubItem() {
        super();
    }


    public int booking_id;
    public String booking_title;
    public String booking_price;
    public String booking_discount_price;
    public HashMap<Integer, DataInOptions> personals_data = new HashMap<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public HashMap<Integer, DataInOptions> getPersonals_data() {
        return personals_data;
    }

    public void setPersonals_data(HashMap<Integer, DataInOptions> personals_data) {
        this.personals_data = personals_data;
    }

    public String getBooking_title() {
        return booking_title;
    }

    public void setBooking_title(String booking_title) {
        this.booking_title = booking_title;
    }

    public String getBooking_price() {
        return booking_price;
    }

    public void setBooking_price(String booking_price) {
        this.booking_price = booking_price;
    }

    public String getBooking_discount_price() {
        return booking_discount_price;
    }

    public void setBooking_discount_price(String booking_discount_price) {
        this.booking_discount_price = booking_discount_price;
    }

    @Override
    public String toString() {
        return "DataInHallSubItem{" +
                "booking_id=" + booking_id +
                ", personals_data=" + personals_data.toString() +
                '}';
    }
}
