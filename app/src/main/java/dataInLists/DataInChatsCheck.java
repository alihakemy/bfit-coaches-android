package dataInLists;

public class DataInChatsCheck {

    public DataInChatsCheck() {
        super();
    }


    public boolean success;
    public String message;
    public int code;
    public Data data;

    public class Data {
        public int conversation_id;
        public boolean exist;

        @Override
        public String toString() {
            return "Data{" +
                    "conversation_id=" + conversation_id +
                    ", exist=" + exist +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DataInChatsCheck{" +
                "success=" + success +
                ", code=" + code +
                ", data=" + data.toString() +
                '}';
    }
}
