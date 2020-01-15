package Database;

public class Term {
        public static final String TABLE_NAME = "terms";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_START = "startdate";
        public static final String COLUMN_END = "enddate";
        public static final String COLUMN_NAME = "termname";

    private int id;
    private String startdate;
    private String enddate;
    private String termname;

    public static final String CREATE_TABLE =
            "CREATE TABLE " +  TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_START + " TEXT,"
                    + COLUMN_END + " TEXT,"
                    + COLUMN_NAME + "  TEXT"
                    + ")";

    public Term() {}

    public Term(int id, String startdate, String enddate, String termname){
        this.id = id;
        this.startdate = startdate;
        this.enddate = enddate;
        this.termname = termname;
    }

    public int getId() {
        return id;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getTermname() {
        return termname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setTermname(String termname) {
        this.termname = termname;
    }

}
