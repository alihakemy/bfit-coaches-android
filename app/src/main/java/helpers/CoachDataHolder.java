package helpers;

import dataInLists.DataInCoachesSubItem;

public class CoachDataHolder {
    private DataInCoachesSubItem FaceID;

    public DataInCoachesSubItem getData() {
        return FaceID;
    }

    public void setData(DataInCoachesSubItem data) {
        this.FaceID = data;
    }

    public void setData() {
        this.FaceID = new DataInCoachesSubItem();
    }

    private static final CoachDataHolder holder = new CoachDataHolder();

    public static CoachDataHolder getInstance() {
        return holder;
    }



}
