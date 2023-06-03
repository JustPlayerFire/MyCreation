package com.example.mycreation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBPet {
    private static final String DATABASE_NAME = "pet_chronic.db";
    private static final int DATABASE_VERSION = 203 ;
    private static final String TABLE_NAME = "petChronic";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_HEALTH = "Health";
    private static final String COLUMN_HAPPINESS = "Happiness";
    private static final String COLUMN_FOOD = "Food";
    private static final String COLUMN_MONEY = "Money";
    private static final String COLUMN_ALIVE = "Alive";
    private static final String COLUMN_POWER = "Power";
    private static final String COLUMN_SLEEP = "Sleep";

    private static final String COLUMN_CHICKEN = "Chicken";
    private static final String COLUMN_CAKE = "Cake";
    private static final String COLUMN_SANDWICH = "Sandwich";
    private static final String COLUMN_PIZZA = "Pizza";
    private static final String COLUMN_TIME_STARTED = "Time_started";
    private static final String COLUMN_TIME_FINISHED = "Time_finished";


    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME= 1;
    private static final int NUM_COLUMN_HEALTH = 2;
    private static final int NUM_COLUMN_HAPPINESS = 3;
    private static final int NUM_COLUMN_FOOD = 4;
    private static final int NUM_COLUMN_MONEY = 5;
    private static final int NUM_COLUMN_ALIVE = 6;
    private static final int NUM_COLUMN_POWER = 7;
    private static final int NUM_COLUMN_SLEEP = 8;
    private static final int NUM_COLUMN_CHICKEN = 9;
    private static final int NUM_COLUMN_CAKE = 10;
    private static final int NUM_COLUMN_SANDWICH = 11;
    private static final int NUM_COLUMN_PIZZA = 12;
    private static final int NUM_COLUMN_TIME_STARTED = 13;
    private static final int NUM_COLUMN_TIME_FINISHED = 14;

    private SQLiteDatabase petDataBase;

    public DBPet(Context context){
        OpenHelper mOpenHelper = new OpenHelper(context);
        petDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String name, int health, int happiness, int food, int money, int alive, int power, int sleep, int chicken,
                       int cake, int sandwich, int pizza, long time_started, long time_finished) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_HEALTH, health);
        cv.put(COLUMN_HAPPINESS, happiness);
        cv.put(COLUMN_FOOD, food);
        cv.put(COLUMN_MONEY, money);
        cv.put(COLUMN_ALIVE, alive);
        cv.put(COLUMN_POWER, power);
        cv.put(COLUMN_SLEEP, sleep);
        cv.put(COLUMN_CHICKEN, chicken);
        cv.put(COLUMN_CAKE, cake);
        cv.put(COLUMN_SANDWICH, sandwich);
        cv.put(COLUMN_PIZZA, pizza);
        cv.put(COLUMN_TIME_STARTED, time_started);
        cv.put(COLUMN_TIME_FINISHED, time_finished);
        return petDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Pet p) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, p.getName());
        cv.put(COLUMN_HEALTH, p.getHealth());
        cv.put(COLUMN_HAPPINESS, p.getHappiness());
        cv.put(COLUMN_FOOD, p.getFood());
        cv.put(COLUMN_MONEY, p.getMoney());
        cv.put(COLUMN_ALIVE, p.getAlive());
        cv.put(COLUMN_POWER, p.getPower());
        cv.put(COLUMN_SLEEP, p.getSleep());
        cv.put(COLUMN_CHICKEN, p.getCount_chicken());
        cv.put(COLUMN_CAKE, p.getCount_cake());
        cv.put(COLUMN_SANDWICH, p.getCount_sandwich());
        cv.put(COLUMN_PIZZA, p.getCount_pizza());
        cv.put(COLUMN_TIME_STARTED, p.getTime_started());
        cv.put(COLUMN_TIME_FINISHED, p.getTime_finished());
        return petDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(p.getId())});
    }

    public void deleteAll() {
        petDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        petDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Pet select(long id) {
        Cursor mCursor = petDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String name = mCursor.getString(NUM_COLUMN_NAME);
        int health = mCursor.getInt(NUM_COLUMN_HEALTH);
        int happiness = mCursor.getInt(NUM_COLUMN_HAPPINESS);
        int food = mCursor.getInt(NUM_COLUMN_FOOD);
        int money = mCursor.getInt(NUM_COLUMN_MONEY);
        int alive = mCursor.getInt(NUM_COLUMN_ALIVE);
        int power = mCursor.getInt(NUM_COLUMN_POWER);
        int sleep = mCursor.getInt(NUM_COLUMN_SLEEP);
        int chicken = mCursor.getInt(NUM_COLUMN_CHICKEN);
        int cake = mCursor.getInt(NUM_COLUMN_CAKE);
        int sandwich = mCursor.getInt(NUM_COLUMN_SANDWICH);
        int pizza = mCursor.getInt(NUM_COLUMN_PIZZA);
        long time_started = mCursor.getLong(NUM_COLUMN_TIME_STARTED);
        long time_finished = mCursor.getLong(NUM_COLUMN_TIME_FINISHED);
        return new Pet(id, name, health, happiness, food, money, alive, power, sleep, chicken, cake, sandwich, pizza, time_started, time_finished);
    }

    public boolean anyAlive(){
        Cursor mCursor = petDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Pet> arr = new ArrayList<Pet>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String name = mCursor.getString(NUM_COLUMN_NAME);
                int health = mCursor.getInt(NUM_COLUMN_HEALTH);
                int happiness = mCursor.getInt(NUM_COLUMN_HAPPINESS);
                int food = mCursor.getInt(NUM_COLUMN_FOOD);
                int money = mCursor.getInt(NUM_COLUMN_MONEY);
                int alive = mCursor.getInt(NUM_COLUMN_ALIVE);
                int power = mCursor.getInt(NUM_COLUMN_POWER);
                int sleep = mCursor.getInt(NUM_COLUMN_SLEEP);
                int chicken = mCursor.getInt(NUM_COLUMN_CHICKEN);
                int cake = mCursor.getInt(NUM_COLUMN_CAKE);
                int sandwich = mCursor.getInt(NUM_COLUMN_SANDWICH);
                int pizza = mCursor.getInt(NUM_COLUMN_PIZZA);
                long time_started = mCursor.getLong(NUM_COLUMN_TIME_STARTED);
                long time_finished = mCursor.getLong(NUM_COLUMN_TIME_FINISHED);
                arr.add(new Pet(id, name, health, happiness, food, money, alive, power, sleep,
                        chicken, cake, sandwich, pizza, time_started, time_finished));
            } while (mCursor.moveToNext());
        }
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i).alive == 1){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Pet> selectAll() {
        Cursor mCursor = petDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Pet> arr = new ArrayList<Pet>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String name = mCursor.getString(NUM_COLUMN_NAME);
                int health = mCursor.getInt(NUM_COLUMN_HEALTH);
                int happiness = mCursor.getInt(NUM_COLUMN_HAPPINESS);
                int food = mCursor.getInt(NUM_COLUMN_FOOD);
                int money = mCursor.getInt(NUM_COLUMN_MONEY);
                int alive = mCursor.getInt(NUM_COLUMN_ALIVE);
                int power = mCursor.getInt(NUM_COLUMN_POWER);
                int sleep = mCursor.getInt(NUM_COLUMN_SLEEP);
                int chicken = mCursor.getInt(NUM_COLUMN_CHICKEN);
                int cake = mCursor.getInt(NUM_COLUMN_CAKE);
                int sandwich = mCursor.getInt(NUM_COLUMN_SANDWICH);
                int pizza = mCursor.getInt(NUM_COLUMN_PIZZA);
                long time_started = mCursor.getLong(NUM_COLUMN_TIME_STARTED);
                long time_finished = mCursor.getLong(NUM_COLUMN_TIME_FINISHED);
                arr.add(new Pet(id, name, health, happiness, food, money, alive, power, sleep,
                        chicken, cake, sandwich, pizza, time_started, time_finished));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_HEALTH + " INTEGER, " +
                    COLUMN_HAPPINESS + " INTEGER, "+
                    COLUMN_FOOD +" INTEGER, " +
                    COLUMN_MONEY + " INTEGER, " +
                    COLUMN_ALIVE + " INTEGER, " +
                    COLUMN_POWER + " INTEGER, " +
                    COLUMN_SLEEP + " INTEGER, " +
                    COLUMN_CHICKEN + " INTEGER, " +
                    COLUMN_CAKE + " INTEGER, " +
                    COLUMN_SANDWICH + " INTEGER, " +
                    COLUMN_PIZZA + " INTEGER, " +
                    COLUMN_TIME_STARTED + " LONG, " +
                    COLUMN_TIME_FINISHED + " LONG);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
