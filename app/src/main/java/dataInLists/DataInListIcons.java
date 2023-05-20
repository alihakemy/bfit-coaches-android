package dataInLists;

public class DataInListIcons {

    public DataInListIcons() {
        super();
    }

    public DataInListIcons(String n, String ph, int i) {
        name = n;
        photo = ph;
        id = i;
        type = 0;
    }

    public DataInListIcons(String n, String ph, int i, byte t) {
        name = n;
        photo = ph;
        type = t;
    }

    public DataInListIcons(String n, String ph, int i, byte t, String cr) {
        name = n;
        photo = ph;
        type = t;
        created_at = cr;
    }

    public String name;
    public String photo;
    public String created_at;
    public int id;
    public byte type = 0;
}
