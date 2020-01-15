package Database;

public class Assessment {
    public static final String TABLE_NAME = "assessment";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COURSE_ID = "courseid";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NAME = "assessmentname";
    public static final String COLUMN_DATE = "date";

    private int id;
    private int courseId;
    private String type;
    private String name;
    private String date;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_COURSE_ID + " INTEGER,"
                    + " FOREIGN KEY (" + COLUMN_COURSE_ID + ") REFERENCES " + Course.TABLE_NAME + "(" + Course.COLUMN_ID + "));";

    public Assessment() {}

    public Assessment(int id, int courseId, String name, String type, String date){
        this.id = id;
        this.courseId = courseId;
        this.type = type;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
