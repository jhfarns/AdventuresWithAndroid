package Database;

public class Course {

    public static final String TABLE_NAME = "courses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TERM_ID = "termid";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_END = "end";
    public static final String COLUMN_NAME = "coursename";
    public static final String COLUMN_STATUS = "status"; // Should this be an enum?
    public static final String COLUMN_NOTE = "note";

    private int id;
    private int termid;
    private String startdate;
    private String enddate;
    private String coursename;
    private String status; // make a drop down with expected output
    private String note; // another column that should be a drop down

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_START + " TEXT,"
                    + COLUMN_END + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_STATUS + " TEXT,"
                    + COLUMN_TERM_ID + " INTEGER,"
                    + " FOREIGN KEY (" + COLUMN_TERM_ID + ") REFERENCES " + Term.TABLE_NAME + "(" + Term.COLUMN_ID + "));";

    public Course() {}

    public Course(int id, int termid, String startdate, String enddate, String coursename, String status, String note) {
        this.id = id;
        this.termid = termid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.coursename = coursename;
        this.status = status;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public int getTermid() {
        return termid;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getCoursename() {
        return coursename;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTermid(int termid) {
        this.termid = termid;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNote(String note) {
        this.note = note;
    }

}


