package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "terms_db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Term.CREATE_TABLE);

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Term.TABLE_NAME);

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

    public int getTermsCount() {
        String countQuery = "SELECT * FROM " + Term.TABLE_NAME;
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

    public void deleteTerm(Term term){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Term.TABLE_NAME, Term.COLUMN_ID + "=?", new String[]{String.valueOf(term.getId())});
        db.close();
    }
}
