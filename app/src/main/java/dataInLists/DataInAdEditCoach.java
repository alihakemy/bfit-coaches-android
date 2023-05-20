package dataInLists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DataInAdEditCoach implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DataInAdEditCoach() {
        super();
    }


    private int id;
    private int work_day_from;
    private int work_day_to;
    private String work_day_from_txt;
    private String work_day_to_txt;
    private String email;
    private String name_ar;
    private String name_en;
    private String about_coach_ar;
    private String about_coach_en;
    private String image;
    private String time_from;
    private String time_to;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWork_day_from() {
        return work_day_from;
    }

    public void setWork_day_from(int work_day_from) {
        this.work_day_from = work_day_from;
    }

    public int getWork_day_to() {
        return work_day_to;
    }

    public void setWork_day_to(int work_day_to) {
        this.work_day_to = work_day_to;
    }

    public String getWork_day_from_txt() {
        return work_day_from_txt;
    }

    public void setWork_day_from_txt(String work_day_from_txt) {
        this.work_day_from_txt = work_day_from_txt;
    }

    public String getWork_day_to_txt() {
        return work_day_to_txt;
    }

    public void setWork_day_to_txt(String work_day_to_txt) {
        this.work_day_to_txt = work_day_to_txt;
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getAbout_coach_ar() {
        return about_coach_ar;
    }

    public void setAbout_coach_ar(String about_coach_ar) {
        this.about_coach_ar = about_coach_ar;
    }

    public String getAbout_coach_en() {
        return about_coach_en;
    }

    public void setAbout_coach_en(String about_coach_en) {
        this.about_coach_en = about_coach_en;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime_from() {
        return time_from;
    }

    public void setTime_from(String time_from) {
        this.time_from = time_from;
    }

    public String getTime_to() {
        return time_to;
    }

    public void setTime_to(String time_to) {
        this.time_to = time_to;
    }

    @Override
    public String toString() {
        return "DataInAdEditCoach{" +
                "id=" + id +
                ", work_day_from=" + work_day_from +
                ", work_day_to=" + work_day_to +
                ", email='" + email + '\'' +
                ", name_ar='" + name_ar + '\'' +
                ", name_en='" + name_en + '\'' +
                ", about_coach_ar='" + about_coach_ar + '\'' +
                ", about_coach_en='" + about_coach_en + '\'' +
                ", image='" + image + '\'' +
                ", time_from='" + time_from + '\'' +
                ", time_to='" + time_to + '\'' +
                '}';
    }
}
