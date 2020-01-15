package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

// next time if you do this see if for some of the functions if you could use generics... i.e. delete is a prime candidate

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "terms_db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Term.CREATE_TABLE);
        db.execSQL(Course.CREATE_TABLE);
        db.execSQL(Assessment.CREATE_TABLE);

        ContentValues initVals = new ContentValues();
        initVals.put(Term.COLUMN_START, "start1");
        initVals.put(Term.COLUMN_END, "end1");
        initVals.put(Term.COLUMN_NAME, "term1");
        db.insert(Term.TABLE_NAME, null, initVals);

        ContentValues initVals2 = new ContentValues();
        initVals2.put(Term.COLUMN_START, "start2");
        initVals2.put(Term.COLUMN_END, "end2");
        initVals2.put(Term.COLUMN_NAME, "term2");
        db.insert(Term.TABLE_NAME, null, initVals2);

        ContentValues initVals3 = new ContentValues();
        initVals3.put(Term.COLUMN_START, "start3");
        initVals3.put(Term.COLUMN_END, "end3");
        initVals3.put(Term.COLUMN_NAME, "term3");
        db.insert(Term.TABLE_NAME, null, initVals3);

        ContentValues initVals4 = new ContentValues();
        initVals4.put(Course.COLUMN_START, "start1");
        initVals4.put(Course.COLUMN_END, "end1");
        initVals4.put(Course.COLUMN_NAME, "Course1");
        initVals4.put(Course.COLUMN_TERM_ID, 1);
        initVals4.put(Course.COLUMN_STATUS, "finished");
        initVals4.put(Course.COLUMN_NOTE, "this is a note");
        db.insert(Course.TABLE_NAME, null, initVals4);

        ContentValues initVals5 = new ContentValues();
        initVals5.put(Course.COLUMN_START, "start2");
        initVals5.put(Course.COLUMN_END, "end2");
        initVals5.put(Course.COLUMN_NAME, "course2");
        initVals5.put(Course.COLUMN_TERM_ID, 2);
        initVals5.put(Course.COLUMN_STATUS, "finished");
        initVals5.put(Course.COLUMN_NOTE, "this is a note");
        db.insert(Course.TABLE_NAME, null, initVals5);

        ContentValues initVals6 = new ContentValues();
        initVals6.put(Course.COLUMN_START, "start6");
        initVals6.put(Course.COLUMN_END, "end6");
        initVals6.put(Course.COLUMN_NAME, "course6");
        initVals6.put(Course.COLUMN_TERM_ID, 2);
        initVals6.put(Course.COLUMN_STATUS, "finished");
        initVals6.put(Course.COLUMN_NOTE, "this is a note");
        db.insert(Course.TABLE_NAME, null, initVals6);

        ContentValues initVals7 = new ContentValues();
        initVals7.put(Assessment.COLUMN_NAME, "assessment");
        initVals7.put(Assessment.COLUMN_TYPE, "objective");
        initVals7.put(Assessment.COLUMN_DATE, "somedate");
        initVals7.put(Assessment.COLUMN_COURSE_ID, 1);
        db.insert(Assessment.TABLE_NAME, null, initVals7);

        ContentValues initVals8 = new ContentValues();
        initVals8.put(Assessment.COLUMN_NAME, "assessment2");
        initVals8.put(Assessment.COLUMN_TYPE, "objective2");
        initVals8.put(Assessment.COLUMN_DATE, "somedate2");
        initVals8.put(Assessment.COLUMN_COURSE_ID, 2);
        db.insert(Assessment.TABLE_NAME, null, initVals8);

        ContentValues initVals9 = new ContentValues();
        initVals9.put(Assessment.COLUMN_NAME, "assessment3");
        initVals9.put(Assessment.COLUMN_TYPE, "objective3");
        initVals9.put(Assessment.COLUMN_DATE, "somedate3");
        initVals9.put(Assessment.COLUMN_COURSE_ID, 3);
        db.insert(Assessment.TABLE_NAME, null, initVals9);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Term.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Course.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Assessment.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertTerm(String start, String end, String name ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Term.COLUMN_START, start);
        values.put(Term.COLUMN_END, end);
        values.put(Term.COLUMN_NAME, name);

        long id = db.insert(Term.TABLE_NAME, null, values);

        return id;
    }

    public long insertCourse(String start, String end, String name, String note, String status, int termid ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Course.COLUMN_START, start);
        values.put(Course.COLUMN_END, end);
        values.put(Course.COLUMN_NAME, name);
        values.put(Course.COLUMN_NOTE, note);
        values.put(Course.COLUMN_STATUS, status);
        values.put(Course.COLUMN_TERM_ID, termid);

        long id = db.insert(Course.TABLE_NAME, null, values);

        return id;
    }

    public long insertAssessment(String type, String name, String date, int courseId ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Assessment.COLUMN_TYPE, type);
        values.put(Assessment.COLUMN_NAME, name);
        values.put(Assessment.COLUMN_DATE, date);
        values.put(Assessment.COLUMN_COURSE_ID, courseId);

        long id = db.insert(Assessment.TABLE_NAME, null, values);

        return id;
    }


    public Term getTerm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Term.TABLE_NAME,
                new String[]{Term.COLUMN_ID, Term.COLUMN_START, Term.COLUMN_END, Term.COLUMN_NAME},
                Term.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        Term term = new Term(
                cursor.getInt(cursor.getColumnIndex(Term.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Term.COLUMN_START)),
                cursor.getString(cursor.getColumnIndex(Term.COLUMN_END)),
                cursor.getString(cursor.getColumnIndex(Term.COLUMN_NAME)));
        cursor.close();
        return term;
    }

    public Course getCourse(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Course.TABLE_NAME,
                new String[]{Course.COLUMN_ID, Course.COLUMN_START, Course.COLUMN_END, Course.COLUMN_NAME, Course.COLUMN_NOTE, Course.COLUMN_STATUS, Course.COLUMN_TERM_ID},
                Course.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            Boolean statement = cursor.moveToFirst();
        }

        Course course = new Course(
                cursor.getInt(cursor.getColumnIndex(Course.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Course.COLUMN_TERM_ID)),
                cursor.getString(cursor.getColumnIndex(Course.COLUMN_START)),
                cursor.getString(cursor.getColumnIndex(Course.COLUMN_END)),
                cursor.getString(cursor.getColumnIndex(Course.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Course.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Course.COLUMN_STATUS))
        );

        return course;
    }

    public Assessment getAssessment(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Assessment.TABLE_NAME,
                new String[]{Assessment.COLUMN_ID, Assessment.COLUMN_TYPE, Assessment.COLUMN_NAME, Assessment.COLUMN_DATE, Assessment.COLUMN_COURSE_ID},
                Assessment.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            Boolean statement = cursor.moveToFirst();
        }

        Assessment assessment = new Assessment(
                cursor.getInt(cursor.getColumnIndex(Assessment.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Assessment.COLUMN_COURSE_ID)),
                cursor.getString(cursor.getColumnIndex(Assessment.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Assessment.COLUMN_TYPE)),
                cursor.getString(cursor.getColumnIndex(Assessment.COLUMN_DATE))

        );

        return assessment;
    }

    public List<Term> getAllTerms() {
        List<Term> terms = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Term.TABLE_NAME + " ORDER BY " +
                Term.COLUMN_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                Term term = new Term();
                term.setId(cursor.getInt(cursor.getColumnIndex(Term.COLUMN_ID)));
                term.setStartdate(cursor.getString(cursor.getColumnIndex(Term.COLUMN_START)));
                term.setEnddate(cursor.getString(cursor.getColumnIndex(Term.COLUMN_END)));
                term.setTermname(cursor.getString(cursor.getColumnIndex(Term.COLUMN_NAME)));

                terms.add(term);

            } while(cursor.moveToNext());
        }

        db.close();

        return terms;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Course.TABLE_NAME + " ORDER BY " + Course.COLUMN_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setStartdate(cursor.getString(cursor.getColumnIndex(Course.COLUMN_START)));
                course.setEnddate(cursor.getString(cursor.getColumnIndex(Course.COLUMN_END)));
                course.setCoursename(cursor.getString(cursor.getColumnIndex(Course.COLUMN_NAME)));
                course.setNote(cursor.getString(cursor.getColumnIndex(Course.COLUMN_NOTE)));
                course.setStatus(cursor.getString(cursor.getColumnIndex(Course.COLUMN_STATUS)));
                course.setTermid(cursor.getInt(cursor.getColumnIndex(Course.COLUMN_TERM_ID)));
                course.setId(cursor.getInt(cursor.getColumnIndex(Course.COLUMN_ID)));

                courses.add(course);

            } while(cursor.moveToNext());
        }

        db.close();

        return courses;
    }

    public List<Course> getAllAssociatedCourses(int termID) {
        List<Course> courses = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Course.TABLE_NAME + "WHERE termid = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM courses WHERE termid = ?", new String[] {Integer.toString(termID)});

        if(cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setStartdate(cursor.getString(cursor.getColumnIndex(Course.COLUMN_START)));
                course.setEnddate(cursor.getString(cursor.getColumnIndex(Course.COLUMN_END)));
                course.setCoursename(cursor.getString(cursor.getColumnIndex(Course.COLUMN_NAME)));
                course.setNote(cursor.getString(cursor.getColumnIndex(Course.COLUMN_NOTE)));
                course.setStatus(cursor.getString(cursor.getColumnIndex(Course.COLUMN_STATUS)));
                course.setTermid(cursor.getInt(cursor.getColumnIndex(Course.COLUMN_TERM_ID)));
                course.setId(cursor.getInt(cursor.getColumnIndex(Course.COLUMN_ID)));

                courses.add(course);

            } while(cursor.moveToNext());
        }

        db.close();

        return courses;
    }

    public List<Assessment> getAllAssessments() {
        List<Assessment> assessments = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Assessment.TABLE_NAME + " ORDER BY " + Assessment.COLUMN_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Assessment assessment = new Assessment();
                assessment.setType(cursor.getString(cursor.getColumnIndex(assessment.COLUMN_TYPE)));
                assessment.setName(cursor.getString(cursor.getColumnIndex(Assessment.COLUMN_NAME)));
                assessment.setDate(cursor.getString(cursor.getColumnIndex(Assessment.COLUMN_DATE)));
                assessment.setCourseId(cursor.getInt(cursor.getColumnIndex(Assessment.COLUMN_COURSE_ID)));
                assessment.setId(cursor.getInt(cursor.getColumnIndex(Assessment.COLUMN_ID)));

                assessments.add(assessment);

            } while(cursor.moveToNext());
        }

        db.close();

        return assessments;
    }

    public int getTermsCount() {
        String countQuery = "SELECT * FROM " + Term.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        return count;
    }

    public int getCoursesCount() {
        String countQuery = "SELECT * FROM " + Course.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        return count;
    }

    public int getAssessmentsCount() {
        String countQuery = "SELECT * FROM " + Assessment.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        return count;
    }

    public int updateTerm(Term term){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Term.COLUMN_START, term.getStartdate());
        values.put(Term.COLUMN_END, term.getEnddate());
        values.put(Term.COLUMN_NAME, term.getTermname());

        return db.update(Term.TABLE_NAME, values, Term.COLUMN_ID + "=?",
                new String[]{String.valueOf(term.getId())});
    }

    public int updateCourse(Course course){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Course.COLUMN_START, course.getStartdate());
        values.put(Course.COLUMN_END, course.getEnddate());
        values.put(Course.COLUMN_NAME, course.getCoursename());
        values.put(Course.COLUMN_NOTE, course.getNote());
        values.put(Course.COLUMN_STATUS, course.getStatus());
        values.put(Course.COLUMN_TERM_ID, course.getTermid());

        return db.update(Course.TABLE_NAME, values, Course.COLUMN_ID + "=?",
                new String[]{String.valueOf(course.getId())});
    }

    public int updateAssessment(Assessment assessment){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Assessment.COLUMN_TYPE, assessment.getType());
        values.put(Assessment.COLUMN_NAME, assessment.getName());
        values.put(Assessment.COLUMN_DATE, assessment.getDate());
        values.put(Assessment.COLUMN_COURSE_ID, assessment.getCourseId());

        return db.update(Assessment.TABLE_NAME, values, Assessment.COLUMN_ID + "=?",
                new String[]{String.valueOf(assessment.getId())});
    }

    public void deleteTerm(Term term){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Term.TABLE_NAME, Term.COLUMN_ID + "=?", new String[]{String.valueOf(term.getId())});
        db.close();
    }

    public void deleteCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Course.TABLE_NAME, Course.COLUMN_ID + "=?", new String[]{String.valueOf(course.getId())});
        db.close();
    }

    public void deleteAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Assessment.TABLE_NAME, Assessment.COLUMN_ID + "=?", new String[]{String.valueOf(assessment.getId())});
        db.close();
    }
}