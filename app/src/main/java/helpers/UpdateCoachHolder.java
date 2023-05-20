package helpers;

import dataInLists.DataInAdEditCoach;

public class UpdateCoachHolder {
    private DataInAdEditCoach FaceID;

    public DataInAdEditCoach getData() {
        return FaceID;
    }

    public void setData(DataInAdEditCoach data) {
        this.FaceID = data;
    }

    public void setData() {
        this.FaceID = new DataInAdEditCoach();
    }

    private static final UpdateCoachHolder holder = new UpdateCoachHolder();

    public static UpdateCoachHolder getInstance() {
        return holder;
    }


}
