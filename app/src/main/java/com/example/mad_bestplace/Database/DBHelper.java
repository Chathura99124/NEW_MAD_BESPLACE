package com.example.mad_bestplace.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.mad_bestplace.Shop_Profile;
import com.example.mad_bestplace.shops;

import java.util.ArrayList;

import static com.example.mad_bestplace.Database.UserMaster.COLUMN_NAME_IMAGE;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BestPlace.db";
    String type;

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, 6);

    }



    public void queryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + UserMaster.SHOPE_TABLE + " (" +
                UserMaster.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                UserMaster.COLUMN_NAME_SHOP + " TEXT," +
                UserMaster.COLUMN_NAME_COMPANY + " TEXT," +
                UserMaster.COLUMN_NAME_ADDRESS + " TEXT," +
                UserMaster.COLUMN_NAME_DISCRIPTION + " TEXT," +
                UserMaster.COLUMN_NAME_IMAGE + "BLOB)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + UserMaster.USER_INFORMAION + " (" +
                UserMaster.COLUMN_NAME1_USERNAME + " TEXT PRIMARY KEY," +
                UserMaster.COLUMN_NAME2_NAME + " TEXT," +
                UserMaster.COLUMN_NAME3_EMAIL + " TEXT," +
                UserMaster.COLUMN_NAME4_PHONE + " TEXT," +
                UserMaster.COLUMN_NAME5_ADDRESS + " TEXT," +
                UserMaster.COLUMN_NAME7_TYPE + " TEXT," +
                UserMaster.COLUMN_NAME6_PASSWORD + " TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserMaster.SHOPE_TABLE);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserMaster.USER_INFORMAION);
        onCreate(sqLiteDatabase);
    }

    //add shop

        public void insertData(String id,String name,String company,String address,String discription,byte[] image){
            SQLiteDatabase database = getWritableDatabase();
            String sql = "INSERT INTO " + UserMaster.SHOPE_TABLE +" VALUES ( ?, ?, ?, ?, ?, ?)";

            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();

            statement.bindString(1, id);
            statement.bindString(2, name);
            statement.bindString(3,company);
            statement.bindString(4,address);
            statement.bindString(5,discription);
            statement.bindBlob(6, image);

            statement.executeInsert();
        }

    //add users
    public boolean Register_user(String UserName,String name,String email,String phone,String address,String type,String Passwrod){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserMaster.COLUMN_NAME1_USERNAME,UserName);
        contentValues.put(UserMaster.COLUMN_NAME2_NAME,name);
        contentValues.put(UserMaster.COLUMN_NAME3_EMAIL,email);
        contentValues.put(UserMaster.COLUMN_NAME4_PHONE,phone);
        contentValues.put(UserMaster.COLUMN_NAME5_ADDRESS,address);
        contentValues.put(UserMaster.COLUMN_NAME7_TYPE,type);
        contentValues.put(UserMaster.COLUMN_NAME6_PASSWORD,Passwrod);

        long insert = db.insert(UserMaster.USER_INFORMAION,null,contentValues);
        if (insert == -1 ){
            return false;
        }else{
            return true;

        }


    }
    public String login_Check(String username){

        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "SELECT " + UserMaster.COLUMN_NAME1_USERNAME + "," + UserMaster.COLUMN_NAME6_PASSWORD +
                "," + UserMaster.COLUMN_NAME7_TYPE + " FROM " + UserMaster.USER_INFORMAION;
        Cursor cursor = db.rawQuery(quary,null);

        String user,pass = "User not found!";

        if(cursor.moveToFirst()){

            do{
                user = cursor.getString(0);

                if (user.equals(username)){
                    pass = cursor.getString(1);
                   type =  cursor.getString(2);

                    break;
                }
            }while (cursor.moveToNext());

        }

        return pass;

    }
    public String passType(){
        return type;
    }
    public String search_Check(String ID){

        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "SELECT " + UserMaster.COLUMN_NAME_ID +  " FROM " + UserMaster.SHOPE_TABLE;
        Cursor cursor = db.rawQuery(quary,null);

        String SID;
        SID = "User not found!";

        if(cursor.moveToFirst()){

            do{
                SID = cursor.getString(0);

                if (SID.equals(ID)){
                    SID = cursor.getString(0);
                    break;
                }
            }while (cursor.moveToNext());

        }

        return SID;

    }
    public Cursor get_shop_values(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ UserMaster.SHOPE_TABLE,null);
        return cursor;
    }

    public ArrayList<shops> getAllData(){

        ArrayList<shops> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ UserMaster.SHOPE_TABLE,null);

        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String company = cursor.getString(2);
            String address = cursor.getString(3);
            String dis = cursor.getString(4);
            byte[] img = cursor.getBlob(5);

            shops sh = new shops(id, name, company, address, dis, img);

            arrayList.add(sh);
        }
    return  arrayList;
    }



    public boolean update_shop(String id,String name,String company,String address,String discription){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserMaster.COLUMN_NAME_ID,id);
        contentValues.put(UserMaster.COLUMN_NAME_SHOP,name);
        contentValues.put(UserMaster.COLUMN_NAME_COMPANY,company);
        contentValues.put(UserMaster.COLUMN_NAME_ADDRESS,address);
        contentValues.put(UserMaster.COLUMN_NAME_DISCRIPTION,discription);
        db.update(UserMaster.SHOPE_TABLE,contentValues,"ID = ?",new String[] {id});
        return true;

    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(UserMaster.SHOPE_TABLE,"ID = ?",new String[] {id});
    }





}
