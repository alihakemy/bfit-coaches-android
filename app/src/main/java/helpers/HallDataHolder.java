package helpers;

import dataInLists.DataInHallSubItem;

public class HallDataHolder {
    private DataInHallSubItem FaceID;

    public DataInHallSubItem getData() {
        return FaceID;
    }

    public void setData(DataInHallSubItem data) {
        this.FaceID = data;
    }

    public void setData() {
        this.FaceID = new DataInHallSubItem();
    }

    private static final HallDataHolder holder = new HallDataHolder();

    public static HallDataHolder getInstance() {
        return holder;
    }



}
