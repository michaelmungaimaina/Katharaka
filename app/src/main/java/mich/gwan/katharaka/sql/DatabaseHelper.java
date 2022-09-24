package mich.gwan.katharaka.sql;

import static java.time.ZonedDateTime.now;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import mich.gwan.katharaka.model.Employee;
import mich.gwan.katharaka.model.pdf.Note;
import mich.gwan.katharaka.model.User;
import mich.gwan.katharaka.model.allsales.AllSales;
import mich.gwan.katharaka.model.bolt.BoltSale;
import mich.gwan.katharaka.model.bolt.Bolts;
import mich.gwan.katharaka.model.gas.Gas;
import mich.gwan.katharaka.model.gas.GasSale;
import mich.gwan.katharaka.model.oil.Oil;
import mich.gwan.katharaka.model.oil.OilSale;
import mich.gwan.katharaka.model.powersaw.Powersaw;
import mich.gwan.katharaka.model.powersaw.PowersawSale;
import mich.gwan.katharaka.model.spanner.Spanner;
import mich.gwan.katharaka.model.spanner.SpannerSale;
import mich.gwan.katharaka.model.spare.Spare;
import mich.gwan.katharaka.model.spare.SpareSale;
import mich.gwan.katharaka.model.welding.Welding;
import mich.gwan.katharaka.model.welding.WeldingSale;

/******************************************************************************************************************
 ******************************************************************************************************************
 **************     _______    __        __     ____      ___   ________    ________   _________  *****************
 **************    /       \  |  |  /\  |  |   /    \    |  |  /        \  |   ____|  /   _____|  *****************
 **************   |  |  |  |  |  | /  \ |  |  |  |\  \   |  |  |  |  |  |  |  |       |  |        *****************
 **************   |  |  |  |  |  |/ /\ \|  |  |  | \  \  |  |  |   _    |  |  |-----  |  |_____   *****************
 **************   |  |  |  |  |    /  \    |  |  |  \  \ |  |  |  | \  \   |  |----|  |______  \  *****************
 **************   |  |  |  |  |   /    \   |  |  |   \  \   |  |  |  \  \  |  |_____   _____|  |  *****************
 **************   \_______/   \__/      \__/  \__|    \____/   |__|   \__\ |_______|  |________|  *****************
 ******************************************************************************************************************
 ******************************************************************************************************************
 ******************************Created by ownrestech on 21/12/2021*************************************************
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    //Tablet
    //private static final int DATABASE_VERSION = 2;
    //Testing
    private static final int DATABASE_VERSION = 17;

    // Database Name
    private static final String DATABASE_NAME = "Katharaka.db";

    // Table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_EMPLOYEE = "employee";
    private static final String TABLE_OIL = "oil";
    private static final String TABLE_OIL_SALE = "oilsales";
    private static final String TABLE_SPARE = "spare";
    private static final String TABLE_SPARE_SALES = "sparesales";
    private static final String TABLE_POWERSAW = "powersaw";
    private static final String TABLE_POWERSAW_SALES = "powersawsales";
    private static final String TABLE_SPANNER ="spanners";
    private static final String TABLE_SPANNER_SALES = "spannersales";
    private static final String TABLE_WELDING = "welding";
    private static final String TABLE_WELDING_SALES = "weldingsales";
    public static final String TABLE_BOLT = "bolts";
    private static final String TABLE_BOLT_SALES = "boltsales";
    private static final String TABLE_GAS = "gas";
    private static final String TABLE_GAS_SALES = "gassales";
    private static final String TABLE_ALL_SALE = "allsales";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
   // private  static final String COLUMN_USER_STATION = "user_station";

    // Columns for managing petty transactions
    public static final String TABLE_PETTY = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PAID = "paid";
    public static final String COLUMN_UNPAID = "unpaid";
    public static final String COLUMN_TOTAL = "total";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    //Oilsales Table Columns names
    private static final String COLUMN_OILSALE_DAY = "saleday";
    private static final String COLUMN_OILSALE_TIME = "time";
    private static final String COLUMN_OILSALE_CATEGORY = "category";
    private static final String COLUMN_OILSALE_QUANTITY = "quantity";
    private static final String COLUMN_OILSALE_UNITPRICE = "unitprice";
    private static final String COLUMN_OILSALE_TOTAL = "total";
    private static final String COLUMN_OILSALE_PROFIT = "profit";
    private static final String COLUMN_OILSALE_COUNT = "count";

    //Sparesales Table Columns names
    private static final String COLUMN_SPARESALE_DAY = "saleday";
    private static final String COLUMN_SPARESALE_TIME = "time";
    private static final String COLUMN_SPARESALE_CATEGORY = "category";
    private static final String COLUMN_SPARESALE_QUANTITY = "quantity";
    private static final String COLUMN_SPARESALE_UNITPRICE = "unitprice";
    private static final String COLUMN_SPARESALE_TOTAL = "total";
    private static final String COLUMN_SPARESALE_PROFIT = "profit";
    private static final String COLUMN_SPARESALE_COUNT = "count";

    //Allsales Table Columns names
    private static final String COLUMN_SALE_DAY = "saleday";
    private static final String COLUMN_SALE_TIME = "time";
    private static final String COLUMN_SALE_CATEGORY = "category";
    private static final String COLUMN_SALE_QUANTITY = "quantity";
    private static final String COLUMN_SALE_UNITPRICE = "unitprice";
    private static final String COLUMN_SALE_TOTAL = "total";
    private static final String COLUMN_SALE_PROFIT = "profit";
    private static final String COLUMN_SALE_COUNT = "count";

    //Powersawsales Table Columns names
    private static final String COLUMN_POWERSAWSALE_DAY = "saleday";
    private static final String COLUMN_POWERSAWSALE_TIME = "time";
    private static final String COLUMN_POWERSAWSALE_CATEGORY = "category";
    private static final String COLUMN_POWERSAWSALE_QUANTITY = "quantity";
    private static final String COLUMN_POWERSAWSALE_UNITPRICE = "unitprice";
    private static final String COLUMN_POWERSAWSALE_TOTAL = "total";
    private static final String COLUMN_POWERSAWSALE_PROFIT = "profit";
    private static final String COLUMN_POWERSAWSALE_COUNT = "count";

    //Spannersales Table Columns names
    private static final String COLUMN_SPANNERSALE_DAY = "saleday";
    private static final String COLUMN_SPANNERSALE_TIME = "time";
    private static final String COLUMN_SPANNERSALE_CATEGORY = "category";
    private static final String COLUMN_SPANNERSALE_QUANTITY = "quantity";
    private static final String COLUMN_SPANNERSALE_UNITPRICE = "unitprice";
    private static final String COLUMN_SPANNERSALE_TOTAL = "total";
    private static final String COLUMN_SPANNERSALE_PROFIT = "profit";
    private static final String COLUMN_SPANNERSALE_COUNT = "count";

    //Weldingsales Table Columns names
    private static final String COLUMN_WELDINGSALE_DAY = "saleday";
    private static final String COLUMN_WELDINGSALE_TIME = "time";
    private static final String COLUMN_WELDINGSALE_CATEGORY = "category";
    private static final String COLUMN_WELDINGSALE_QUANTITY = "quantity";
    private static final String COLUMN_WELDINGSALE_UNITPRICE = "unitprice";
    private static final String COLUMN_WELDINGSALE_TOTAL = "total";
    private static final String COLUMN_WELDINGSALE_PROFIT = "profit";
    private static final String COLUMN_WELDINGSALE_COUNT = "count";

    //Boltsales Table Columns names
    private static final String COLUMN_BOLTSALE_DAY = "saleday";
    private static final String COLUMN_BOLTSALE_TIME = "time";
    private static final String COLUMN_BOLTSALE_CATEGORY = "category";
    private static final String COLUMN_BOLTSALE_QUANTITY = "quantity";
    private static final String COLUMN_BOLTSALE_UNITPRICE = "unitprice";
    private static final String COLUMN_BOLTSALE_TOTAL = "total";
    private static final String COLUMN_BOLTSALE_PROFIT = "profit";
    private static final String COLUMN_BOLTSALE_COUNT = "count";

    //Gassales Table Columns names
    private static final String COLUMN_GASSALE_DAY = "saleday";
    private static final String COLUMN_GASSALE_TIME = "time";
    private static final String COLUMN_GASSALE_CATEGORY = "category";
    private static final String COLUMN_GASSALE_QUANTITY = "quantity";
    private static final String COLUMN_GASSALE_UNITPRICE = "unitprice";
    private static final String COLUMN_GASSALE_TOTAL = "total";
    private static final String COLUMN_GASSALE_PROFIT = "profit";
    private static final String COLUMN_GASSALE_COUNT = "count";

    //Employee table column names
    private static final String COLUMN_EMPLOYEE_NAME = "employee_name";
    private static final String COLUMN_EMPLOYEE_ID = "employee_id";
    private static final String COLUMN_EMPLOYEE_EMAIL = "employee_email";
    private static final String COLUMN_EMPLOYEE_PHONE = "employee_phone";
    private static final String COLUMN_EMPLOYEE_PASSWORD = "employee_password";
    //private static final String COLUMN_EMPLOYEE_USER_TYPE = "employee_user_type";
   // private static final String COLUMN_EMPLOYEE_STATION = "employee_station";

    // Oil Table Column names
    private static final String COLUMN_OIL_ID ="oil_id";
    private static final String COLUMN_OIL_CATEGORY = "oil_category";
    private static final String COLUMN_OIL_QUANTIY = "oil_quantity";
    private static final String COLUMN_OIL_BP = "oil_bp";
    private static final String COLUMN_OIL_SP = "oil_sp";

    // Spares Table Column names
    private static final String COLUMN_SPARE_ID ="spare_id";
    private static final String COLUMN_SPARE_CATEGORY = "spare_category";
    private static final String COLUMN_SPARE_QUANTIY = "spare_quantity";
    private static final String COLUMN_SPARE_BP = "spare_bp";
    private static final String COLUMN_SPARE_SP = "spare_sp";

    // Powersaw Table Column names
    private static final String COLUMN_POWERSAW_ID ="powersaw_id";
    private static final String COLUMN_POWERSAW_CATEGORY = "powersaw_category";
    private static final String COLUMN_POWERSAW_QUANTIY = "powersaw_quantity";
    private static final String COLUMN_POWERSAW_BP = "powersaw_bp";
    private static final String COLUMN_POWERSAW_SP = "powersaw_sp";

    // Spanner Table Column names
    private static final String COLUMN_SPANNER_ID ="spanner_id";
    private static final String COLUMN_SPANNER_CATEGORY = "spanner_category";
    private static final String COLUMN_SPANNER_QUANTIY = "spanner_quantity";
    private static final String COLUMN_SPANNER_BP = "spanner_bp";
    private static final String COLUMN_SPANNER_SP = "spanner_sp";

    // Welding Table Column names
    private static final String COLUMN_WELDING_ID ="welding_id";
    private static final String COLUMN_WELDING_CATEGORY = "welding_category";
    private static final String COLUMN_WELDING_QUANTIY = "welding_quantity";
    private static final String COLUMN_WELDING_BP = "welding_bp";
    private static final String COLUMN_WELDING_SP = "welding_sp";

    // Bolts Table Column names
    private static final String COLUMN_BOLT_ID ="bolt_id";
    public static final String COLUMN_BOLT_CATEGORY = "bolt_category";
    public static final String COLUMN_BOLT_QUANTIY = "bolt_quantity";
    private static final String COLUMN_BOLT_BP = "bolt_bp";
    private static final String COLUMN_BOLT_SP = "bolt_sp";

    // Gas Table Column names
    private static final String COLUMN_GAS_ID ="gas_id";
    private static final String COLUMN_GAS_CATEGORY = "gas_category";
    private static final String COLUMN_GAS_QUANTIY = "gas_quantity";
    private static final String COLUMN_GAS_BP = "gas_bp";
    private static final String COLUMN_GAS_SP = "gas_sp";

    /**
     * SQL Queries
     * These is a list of all queries in this program for CREATE TABLE
     */

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT"  + ")";

    //create table oil sql query
    private String CREATE_TABLE_OIL = "CREATE TABLE " + TABLE_OIL + "(" + COLUMN_OIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + COLUMN_OIL_CATEGORY + " VARCHAR," + COLUMN_OIL_QUANTIY +
            " TEXT," + COLUMN_OIL_BP + " DOUBLE," + COLUMN_OIL_SP + " DOUBLE" + ")";

    //create table spare sql query
    private String CREATE_TABLE_SPARE = "CREATE TABLE " + TABLE_SPARE + "(" + COLUMN_SPARE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + COLUMN_SPARE_CATEGORY + " VARCHAR," + COLUMN_SPARE_QUANTIY +
            " TEXT," + COLUMN_SPARE_BP + " DOUBLE," + COLUMN_SPARE_SP + " DOUBLE" + ")";

    //create table powersaw sql query
    private String CREATE_TABLE_POWERSAW = "CREATE TABLE " + TABLE_POWERSAW + "(" + COLUMN_POWERSAW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + COLUMN_POWERSAW_CATEGORY + " VARCHAR," + COLUMN_POWERSAW_QUANTIY +
            " TEXT," + COLUMN_POWERSAW_BP + " DOUBLE," + COLUMN_POWERSAW_SP + " DOUBLE" + ")";

    //create table spanner sql query
    private String CREATE_TABLE_SPANNER = "CREATE TABLE " + TABLE_SPANNER + "(" + COLUMN_SPANNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + COLUMN_SPANNER_CATEGORY + " VARCHAR," + COLUMN_SPANNER_QUANTIY +
            " TEXT," + COLUMN_SPANNER_BP + " DOUBLE," + COLUMN_SPANNER_SP + " DOUBLE" + ")";

    //create table welding sql query
    private String CREATE_TABLE_WELDING = "CREATE TABLE " + TABLE_WELDING + "(" + COLUMN_WELDING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + COLUMN_WELDING_CATEGORY + " VARCHAR," + COLUMN_WELDING_QUANTIY +
            " TEXT," + COLUMN_WELDING_BP + " DOUBLE," + COLUMN_WELDING_SP + " DOUBLE" + ")";

    //create table bolt sql query
    private String CREATE_TABLE_BOLT = "CREATE TABLE " + TABLE_BOLT + "(" + COLUMN_BOLT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + COLUMN_BOLT_CATEGORY + " VARCHAR," + COLUMN_BOLT_QUANTIY +
            " TEXT," + COLUMN_BOLT_BP + " DOUBLE," + COLUMN_BOLT_SP + " DOUBLE" + ")";

    //create table gas sql query
    private String CREATE_TABLE_GAS = "CREATE TABLE " + TABLE_GAS + "(" + COLUMN_GAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," + COLUMN_GAS_CATEGORY + " VARCHAR," + COLUMN_GAS_QUANTIY +
            " TEXT," + COLUMN_GAS_BP + " DOUBLE," + COLUMN_GAS_SP + " DOUBLE" + ")";

    //create table employee sql query
    private String CREATE_EMPLOYEE_TABLE = "CREATE TABLE " + TABLE_EMPLOYEE + "(" + COLUMN_EMPLOYEE_ID
             + "INTEGER PRIMARY KEY," + COLUMN_EMPLOYEE_NAME + "TEXT," + COLUMN_EMPLOYEE_PHONE + "INTEGER,"
             + COLUMN_EMPLOYEE_EMAIL + "TEXT," /**+ COLUMN_EMPLOYEE_USER_TYPE + "TEXT," + COLUMN_EMPLOYEE_STATION
            + "TEXT,"**/ + COLUMN_EMPLOYEE_PASSWORD + "TEXT" + ")";

            //create table oilsale sql query
    private String CREATE_TABLE_OILSALE = "CREATE TABLE " + TABLE_OIL_SALE + "(" + COLUMN_OILSALE_COUNT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_OILSALE_DAY + " TEXT," + COLUMN_OILSALE_TIME + " TIME," + COLUMN_OILSALE_CATEGORY + " TEXT," + COLUMN_OILSALE_QUANTITY +
                    " INTEGER," + COLUMN_OILSALE_UNITPRICE + " INTEGER," + COLUMN_OILSALE_TOTAL + " INTEGER," + COLUMN_OILSALE_PROFIT
                    + " INTEGER" + ")";

    //create table sparesale sql query
    private String CREATE_TABLE_SPARESALE = "CREATE TABLE " + TABLE_SPARE_SALES + "(" + COLUMN_SPARESALE_COUNT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SPARESALE_DAY + " TEXT," + COLUMN_SPARESALE_TIME + " TIME," + COLUMN_SPARESALE_CATEGORY + " TEXT," + COLUMN_SPARESALE_QUANTITY +
                    " INTEGER," + COLUMN_SPARESALE_UNITPRICE + " INTEGER," + COLUMN_SPARESALE_TOTAL + " INTEGER," + COLUMN_SPARESALE_PROFIT
                    + " INTEGER" + ")";

    //create table POWERSAW sql query
    private String CREATE_TABLE_POWERSAWSALE = "CREATE TABLE " + TABLE_POWERSAW_SALES + "(" + COLUMN_POWERSAWSALE_COUNT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_POWERSAWSALE_DAY + " TEXT," + COLUMN_POWERSAWSALE_TIME + " TIME," + COLUMN_POWERSAWSALE_CATEGORY + " TEXT," + COLUMN_POWERSAWSALE_QUANTITY +
                    " INTEGER," + COLUMN_POWERSAWSALE_UNITPRICE + " INTEGER," + COLUMN_POWERSAWSALE_TOTAL + " INTEGER," + COLUMN_POWERSAWSALE_PROFIT
                    + " INTEGER" + ")";

    //create table spanner sql query
    private String CREATE_TABLE_SPANNERSALE = "CREATE TABLE " + TABLE_SPANNER_SALES + "(" + COLUMN_SPANNERSALE_COUNT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SPANNERSALE_DAY + " TEXT," + COLUMN_SPANNERSALE_TIME + " TIME," + COLUMN_SPANNERSALE_CATEGORY + " TEXT," + COLUMN_SPANNERSALE_QUANTITY +
                    " INTEGER," + COLUMN_SPANNERSALE_UNITPRICE + " INTEGER," + COLUMN_SPANNERSALE_TOTAL + " INTEGER," + COLUMN_SPANNERSALE_PROFIT
                    + " INTEGER" + ")";
    //create table spanner sql query
    private String CREATE_TABLE_ALLSALES = "CREATE TABLE " + TABLE_ALL_SALE + "(" + COLUMN_SALE_COUNT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SALE_DAY + " TEXT," + COLUMN_SALE_TIME + " TIME," + COLUMN_SALE_CATEGORY + " TEXT," + COLUMN_SALE_QUANTITY +
                    " INTEGER," + COLUMN_SALE_UNITPRICE + " INTEGER," + COLUMN_SALE_TOTAL + " INTEGER," + COLUMN_SALE_PROFIT
                    + " INTEGER" + ")";

    //create table welding sql query
    private String CREATE_TABLE_WELDINGSALE = "CREATE TABLE " + TABLE_WELDING_SALES + "(" + COLUMN_WELDINGSALE_COUNT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_WELDINGSALE_DAY + " TEXT," + COLUMN_WELDINGSALE_TIME + " TIME," + COLUMN_WELDINGSALE_CATEGORY + " TEXT," + COLUMN_WELDINGSALE_QUANTITY +
                    " INTEGER," + COLUMN_WELDINGSALE_UNITPRICE + " INTEGER," + COLUMN_WELDINGSALE_TOTAL + " INTEGER," + COLUMN_WELDINGSALE_PROFIT
                    + " INTEGER" + ")";

    //create table bolts sql query
    private String CREATE_TABLE_BOLTSALE = "CREATE TABLE " + TABLE_BOLT_SALES + "(" + COLUMN_BOLTSALE_COUNT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BOLTSALE_DAY + " TEXT," + COLUMN_BOLTSALE_TIME + " TIME," + COLUMN_BOLTSALE_CATEGORY + " TEXT," + COLUMN_BOLTSALE_QUANTITY +
                    " INTEGER," + COLUMN_BOLTSALE_UNITPRICE + " INTEGER," + COLUMN_BOLTSALE_TOTAL + " INTEGER," + COLUMN_BOLTSALE_PROFIT
                    + " INTEGER" + ")";

    //create table gas sql query
    private String CREATE_TABLE_GASSALE = "CREATE TABLE " + TABLE_GAS_SALES + "(" + COLUMN_GASSALE_COUNT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_GASSALE_DAY + " TEXT," + COLUMN_GASSALE_TIME + " TIME," + COLUMN_GASSALE_CATEGORY + " TEXT," + COLUMN_GASSALE_QUANTITY +
                    " INTEGER," + COLUMN_GASSALE_UNITPRICE + " INTEGER," + COLUMN_GASSALE_TOTAL + " INTEGER," + COLUMN_GASSALE_PROFIT
                    + " INTEGER" + ")";
    // Create table notes SQL query
    public static final String CREATE_PETTY_TABLE =
            "CREATE TABLE " + TABLE_PETTY + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME NOT NULL,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PAID + " TEXT,"
                    + COLUMN_UNPAID + " TEXT,"
                    + COLUMN_TOTAL + " TEXT"
                    + ")";
    /**
     * A LIST of all "DROP TABLE IF EXIST" Query
      */
    // drop table user sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    //drop table oil
    private String DROP_OIL_TABLE = "DROP TABLE IF EXISTS " + TABLE_OIL;
    private String DROP_SPARE_TABLE = "DROP TABLE IF EXISTS " + TABLE_SPARE;
    private String DROP_POWERSAW_TABLE = "DROP TABLE IF EXISTS " + TABLE_POWERSAW;
    private String DROP_SPANNER_TABLE = "DROP TABLE IF EXISTS " + TABLE_SPANNER;
    private String DROP_WELDING_TABLE = "DROP TABLE IF EXISTS " + TABLE_WELDING;
    private String DROP_BOLT_TABLE = "DROP TABLE IF EXISTS " + TABLE_BOLT;
    private String DROP_GAS_TABLE = "DROP TABLE IF EXISTS " + TABLE_GAS;
    //drop table employee sql query
    private String DROP_EMPLOYEE_TABLE = "DROP TABLE IF EXISTS " + TABLE_EMPLOYEE;
    //drop table oilsale sql query
    private String DROP_OILSALE_TABLE = "DROP TABLE IF EXISTS " + TABLE_OIL_SALE;
    //drop table sparesale sql query
    private String DROP_SPARESALE_TABLE = "DROP TABLE IF EXISTS " + TABLE_SPARE_SALES;
    //drop table powersawsale sql query
    private String DROP_POWERSAWSALE_TABLE = "DROP TABLE IF EXISTS " + TABLE_POWERSAW_SALES;
    //drop table SPANNERsale sql query
    private String DROP_SPANNERSALE_TABLE = "DROP TABLE IF EXISTS " + TABLE_SPANNER_SALES;
    //drop table weldingsale sql query
    private String DROP_WELDINGSALE_TABLE = "DROP TABLE IF EXISTS " + TABLE_WELDING_SALES;
    //drop table boltsale sql query
    private String DROP_BOLTSALE_TABLE = "DROP TABLE IF EXISTS " + TABLE_BOLT_SALES;
    //drop table GASsale sql query
    private String DROP_GASSALE_TABLE = "DROP TABLE IF EXISTS " + TABLE_GAS_SALES;
    //drop table note sql query
    private String DROP_PETTY_TABLE = "DROP TABLE IF EXISTS " + TABLE_PETTY;
    //drop table allsale sql query
    private String DROP_ALLSALE_TABLE = "DROP TABLE IF EXISTS " + TABLE_ALL_SALE;

    private String APPEND_DATA = "INSERT INTO " + TABLE_ALL_SALE + "(" + COLUMN_SALE_DAY +","+
            COLUMN_SALE_TIME+","+COLUMN_SALE_CATEGORY+","+ COLUMN_SALE_QUANTITY+","+COLUMN_SALE_UNITPRICE+","
            +COLUMN_SALE_TOTAL+","+COLUMN_SALE_PROFIT+") SELECT "+COLUMN_OILSALE_DAY+","+COLUMN_OILSALE_TIME+","
            +COLUMN_OILSALE_CATEGORY+","+COLUMN_OILSALE_QUANTITY+","+COLUMN_OILSALE_UNITPRICE+","+COLUMN_OILSALE_TOTAL+","
            +COLUMN_OILSALE_PROFIT+" FROM "+TABLE_OIL_SALE;

    private String APPEND_GAS = "INSERT INTO " + TABLE_ALL_SALE + "(" + COLUMN_SALE_DAY +","+
            COLUMN_SALE_TIME+","+COLUMN_SALE_CATEGORY+","+ COLUMN_SALE_QUANTITY+","+COLUMN_SALE_UNITPRICE+","
            +COLUMN_SALE_TOTAL+","+COLUMN_SALE_PROFIT+") SELECT "+COLUMN_GASSALE_DAY+","+COLUMN_GASSALE_TIME+","
            +COLUMN_GASSALE_CATEGORY+","+COLUMN_GASSALE_QUANTITY+","+COLUMN_GASSALE_UNITPRICE+","+COLUMN_GASSALE_TOTAL+","
            +COLUMN_GASSALE_PROFIT+" FROM "+TABLE_GAS_SALES;

    private String APPEND_POWERSAW = "INSERT INTO " + TABLE_ALL_SALE + "(" + COLUMN_SALE_DAY +","+
            COLUMN_SALE_TIME+","+COLUMN_SALE_CATEGORY+","+ COLUMN_SALE_QUANTITY+","+COLUMN_SALE_UNITPRICE+","
            +COLUMN_SALE_TOTAL+","+COLUMN_SALE_PROFIT+") SELECT "+COLUMN_POWERSAWSALE_DAY+","+COLUMN_POWERSAWSALE_TIME+","
            +COLUMN_POWERSAWSALE_CATEGORY+","+COLUMN_POWERSAWSALE_QUANTITY+","+COLUMN_POWERSAWSALE_UNITPRICE+","+COLUMN_POWERSAWSALE_TOTAL+","
            +COLUMN_POWERSAWSALE_PROFIT+" FROM "+TABLE_POWERSAW_SALES;

    /**8
     * Constructor for databasehelper
     * It's used to switch database based on the context parameter
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 7);

    }

    /**
     * Method for creating the database oce the apk has been launched
     * @param db SQLite Database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.execSQL(CREATE_TABLE_OIL);
        db.execSQL(CREATE_TABLE_SPARE);
        db.execSQL(CREATE_TABLE_POWERSAW);
        db.execSQL(CREATE_TABLE_SPANNER);
        db.execSQL(CREATE_TABLE_WELDING);
        db.execSQL(CREATE_TABLE_BOLT);
        db.execSQL(CREATE_TABLE_GAS);
        db.execSQL(CREATE_TABLE_OILSALE);
        db.execSQL(CREATE_TABLE_SPARESALE);
        db.execSQL(CREATE_TABLE_POWERSAWSALE);
        db.execSQL(CREATE_TABLE_SPANNERSALE);
        db.execSQL(CREATE_TABLE_WELDINGSALE);
        db.execSQL(CREATE_TABLE_BOLTSALE);
        db.execSQL(CREATE_TABLE_GASSALE);
        db.execSQL(CREATE_PETTY_TABLE);
        db.execSQL(CREATE_TABLE_ALLSALES);
    }

    /**
     * Method to drop tables if Database version is changed
     * It drops the existing tables and recreates them once again
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /**Drop <-----> Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_EMPLOYEE_TABLE);
        db.execSQL(DROP_OIL_TABLE);
        db.execSQL(DROP_SPARE_TABLE);
        db.execSQL(DROP_POWERSAW_TABLE);
        db.execSQL(DROP_SPANNER_TABLE);
        db.execSQL(DROP_WELDING_TABLE);
        db.execSQL(DROP_BOLT_TABLE);
        db.execSQL(DROP_GAS_TABLE);**/
        db.execSQL(DROP_OILSALE_TABLE);
        db.execSQL(DROP_SPARESALE_TABLE);
        db.execSQL(DROP_SPANNERSALE_TABLE);
        db.execSQL(DROP_WELDINGSALE_TABLE);
        db.execSQL(DROP_BOLTSALE_TABLE);
        db.execSQL(DROP_POWERSAWSALE_TABLE);
        db.execSQL(DROP_GASSALE_TABLE);
        db.execSQL(DROP_ALLSALE_TABLE);

        db.execSQL(CREATE_TABLE_OILSALE);
        db.execSQL(CREATE_TABLE_SPARESALE);
        db.execSQL(CREATE_TABLE_POWERSAWSALE);
        db.execSQL(CREATE_TABLE_SPANNERSALE);
        db.execSQL(CREATE_TABLE_WELDINGSALE);
        db.execSQL(CREATE_TABLE_BOLTSALE);
        db.execSQL(CREATE_TABLE_GASSALE);
        db.execSQL(CREATE_TABLE_ALLSALES);
        // Create tables again
        //onCreate(db);
        //db.execSQL(CREATE_TABLE_ALLSALES);
        //db.execSQL(APPEND_DATA);
        //db.execSQL(APPEND_GAS);
        //db.execSQL(APPEND_POWERSAW);

    }

    @Override
     public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    /**
     * Method to create POWERSAWSALE record
     *
     * @param powersawSales
     */
    public void addPowersawSale(PowersawSale powersawSales){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POWERSAWSALE_DAY, powersawSales.getPowersawSaleDate());
        values.put(COLUMN_POWERSAWSALE_TIME, powersawSales.getPowersawSaleTime());
        values.put(COLUMN_POWERSAWSALE_CATEGORY, powersawSales.getPowersawSalecategory());
        values.put(COLUMN_POWERSAWSALE_QUANTITY, powersawSales.getPowersawSaleQnty());
        values.put(COLUMN_POWERSAWSALE_UNITPRICE, powersawSales.getPowersawSaleUprice());
        values.put(COLUMN_POWERSAWSALE_TOTAL, powersawSales.getPowersawSaleTotal());
        values.put(COLUMN_POWERSAWSALE_PROFIT, powersawSales.getPowersawSaleProfit());

        //Insert Row
        db.insert(TABLE_POWERSAW_SALES, null, values);
        db.close();
    }

    /**
     * Method to create spannersale record
     *
     * @param sales
     */
    public void addSpannerSale(SpannerSale sales){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SPANNERSALE_DAY, sales.getSpannerSaleDate());
        values.put(COLUMN_SPANNERSALE_TIME, sales.getSpannerSaleTime());
        values.put(COLUMN_SPANNERSALE_CATEGORY, sales.getSpannerSalecategory());
        values.put(COLUMN_SPANNERSALE_QUANTITY, sales.getSpannerSaleQnty());
        values.put(COLUMN_SPANNERSALE_UNITPRICE, sales.getSpannerSaleUprice());
        values.put(COLUMN_SPANNERSALE_TOTAL, sales.getSpannerSaleTotal());
        values.put(COLUMN_SPANNERSALE_PROFIT, sales.getSpannerSaleProfit());

        //Insert Row
        db.insert(TABLE_SPANNER_SALES, null, values);
        db.close();
    }

    /**
     * Method to create welding record
     *
     * @param sales
     */
    public void addWeldingSale(WeldingSale sales){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WELDINGSALE_DAY, sales.getWeldingSaleDate());
        values.put(COLUMN_WELDINGSALE_TIME, sales.getWeldingSaleTime());
        values.put(COLUMN_WELDINGSALE_CATEGORY, sales.getWeldingSalecategory());
        values.put(COLUMN_WELDINGSALE_QUANTITY, sales.getWeldingSaleQnty());
        values.put(COLUMN_WELDINGSALE_UNITPRICE, sales.getWeldingSaleUprice());
        values.put(COLUMN_WELDINGSALE_TOTAL, sales.getWeldingSaleTotal());
        values.put(COLUMN_WELDINGSALE_PROFIT, sales.getWeldingSaleProfit());

        //Insert Row
        db.insert(TABLE_WELDING_SALES, null, values);
        db.close();
    }/**
     * Method to create gas record
     *
     * @param sales
     */
    public void addGasSale(GasSale sales){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GASSALE_DAY, sales.getGasSaleDate());
        values.put(COLUMN_GASSALE_TIME, sales.getGasSaleTime());
        values.put(COLUMN_GASSALE_CATEGORY, sales.getGasSalecategory());
        values.put(COLUMN_GASSALE_QUANTITY, sales.getGasSaleQnty());
        values.put(COLUMN_GASSALE_UNITPRICE, sales.getGasSaleUprice());
        values.put(COLUMN_GASSALE_TOTAL, sales.getGasSaleTotal());
        values.put(COLUMN_GASSALE_PROFIT, sales.getGasSaleProfit());

        //Insert Row
        db.insert(TABLE_GAS_SALES, null, values);
        db.close();
    }
    /**
     * Method to create allsales record
     *
     * @param sales
     */
    public void addAllSale(AllSales sales){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SALE_DAY, sales.getSaleDate());
        values.put(COLUMN_SALE_TIME, sales.getSaleTime());
        values.put(COLUMN_SALE_CATEGORY, sales.getSalecategory());
        values.put(COLUMN_SALE_QUANTITY, sales.getSaleQnty());
        values.put(COLUMN_SALE_UNITPRICE, sales.getSaleUprice());
        values.put(COLUMN_SALE_TOTAL, sales.getSaleTotal());
        values.put(COLUMN_SALE_PROFIT, sales.getSaleProfit());

        //Insert Row
        db.insert(TABLE_ALL_SALE, null, values);
        db.close();
    }

    /**
     * Method to create bolt record
     *
     * @param sales
     */
    public void addBoltSale(BoltSale sales){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOLTSALE_DAY, sales.getBoltSaleDate());
        values.put(COLUMN_BOLTSALE_TIME, sales.getBoltSaleTime());
        values.put(COLUMN_BOLTSALE_CATEGORY, sales.getBoltSalecategory());
        values.put(COLUMN_BOLTSALE_QUANTITY, sales.getBoltSaleQnty());
        values.put(COLUMN_BOLTSALE_UNITPRICE, sales.getBoltSaleUprice());
        values.put(COLUMN_BOLTSALE_TOTAL, sales.getBoltSaleTotal());
        values.put(COLUMN_BOLTSALE_PROFIT, sales.getBoltSaleProfit());

        //Insert Row
        db.insert(TABLE_BOLT_SALES, null, values);
        db.close();
    }

    /**
     * Method to create oil record
     *
     * @param oilSales
     */
    public void addOilSale(OilSale oilSales){
        SQLiteDatabase db = this.getWritableDatabase();
        //final Cursor cursorOil = db.rawQuery("SELECT oil_sp FROM oil WHERE oil_category = '");
        ContentValues oilSaleValues = new ContentValues();
        oilSaleValues.put(COLUMN_OILSALE_DAY, oilSales.getOilSaleDate());
        oilSaleValues.put(COLUMN_OILSALE_TIME, oilSales.getOilSaleTime());
        oilSaleValues.put(COLUMN_OILSALE_CATEGORY, oilSales.getOilSalecategory());
        oilSaleValues.put(COLUMN_OILSALE_QUANTITY, oilSales.getOilSaleQnty());
        oilSaleValues.put(COLUMN_OILSALE_UNITPRICE, oilSales.getOilSaleUprice());
        oilSaleValues.put(COLUMN_OILSALE_TOTAL, oilSales.getOilSaleTotal());
        oilSaleValues.put(COLUMN_OILSALE_PROFIT, oilSales.getOilSaleProfit());

        //Insert Row
        db.insert(TABLE_OIL_SALE, null, oilSaleValues);
        db.close();
    }

    /**
     * Method to create spares record
     *
     * @param spareSales
     */
    public void addSpareSale(SpareSale spareSales){
        SQLiteDatabase db = this.getWritableDatabase();
        //final Cursor cursorOil = db.rawQuery("SELECT oil_sp FROM oil WHERE oil_category = '");
        ContentValues spareSaleValues = new ContentValues();
        spareSaleValues.put(COLUMN_SPARESALE_DAY, spareSales.getSpareSaleDate());
        spareSaleValues.put(COLUMN_SPARESALE_TIME, spareSales.getSpareSaleTime());
        spareSaleValues.put(COLUMN_SPARESALE_CATEGORY, spareSales.getSpareSalecategory());
        spareSaleValues.put(COLUMN_SPARESALE_QUANTITY, spareSales.getSpareSaleQnty());
        spareSaleValues.put(COLUMN_SPARESALE_UNITPRICE, spareSales.getSpareSaleUprice());
        spareSaleValues.put(COLUMN_SPARESALE_TOTAL, spareSales.getSpareSaleTotal());
        spareSaleValues.put(COLUMN_SPARESALE_PROFIT, spareSales.getSpareSaleProfit());

        //Insert Row
        db.insert(TABLE_SPARE_SALES, null, spareSaleValues);
        db.close();
    }

    /**
     * Method to insert New Note value
     * @param note
     * @return
     */
    public long insertNote(String note, String name, String paid, String unpaid, String total) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_NOTE, note);
        values.put(COLUMN_TIMESTAMP, String.valueOf(now()));
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PAID, paid);
        values.put(COLUMN_UNPAID, unpaid);
        values.put(COLUMN_TOTAL, total);

        // insert row
        long id = db.insert(TABLE_PETTY, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    /**
     * Method to Fetch notes
     * @param id
     * @return
     */

    public Note getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PETTY,
                new String[]{COLUMN_ID, COLUMN_NOTE, COLUMN_TIMESTAMP, COLUMN_NAME, COLUMN_PAID, COLUMN_UNPAID, COLUMN_TOTAL},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        @SuppressLint("Range") Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getInt(Integer.parseInt(String.valueOf(cursor.getColumnIndex(COLUMN_PAID)))),
                cursor.getInt(Integer.parseInt(String.valueOf(cursor.getColumnIndex(COLUMN_UNPAID)))),
                cursor.getInt(Integer.parseInt(String.valueOf(cursor.getColumnIndex(COLUMN_TOTAL)))));

        // close the db connection
        cursor.close();

        return note;
    }

    /**
     * Method to fetch all notes
     * @return
     */
    @SuppressLint("Range")
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PETTY + " ORDER BY " +
                COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                note.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                note.setPaid(cursor.getInt(cursor.getColumnIndex(COLUMN_PAID)));
                note.setUnpaid(cursor.getInt(cursor.getColumnIndex(COLUMN_UNPAID)));
                note.setTotal(cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    /**
     * Method to get Notes Count
     * @return
     */
    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PETTY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    /**
     * Method to Update petty transactions
     * @param note
     * @return
     */
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, note.getNote());
        values.put(COLUMN_NAME, note.getName());
        values.put(COLUMN_PAID, note.getPaid());
        values.put(COLUMN_UNPAID, note.getUnpaid());
        values.put(COLUMN_TOTAL, note.getTotal());
        values.put(COLUMN_TIMESTAMP,note.getTimestamp());
        // updating row
        return db.update(TABLE_PETTY, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    /**
     * Method to delete a petty transaction
     * @param note
     */
    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PETTY, COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
    public void deleteBolt(Bolts note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOLT, COLUMN_BOLT_ID + " = ?",
                new String[]{String.valueOf(note.getBolt_id())});
        db.close();
    }
    public void deleteAllSale(AllSales note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALL_SALE, COLUMN_SALE_DAY + " = ? AND " + COLUMN_SALE_TIME +" = ?",
                new String[]{String.valueOf(note.getSaleDate()), String.valueOf(note.getSaleTime())});
        db.close();
    }


    /**
     * Method to create spare record
     *
     * @param spare
     */
    public void addSpare(Spare spare){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues spareValues = new ContentValues();
        //spareValues.put(COLUMN_SPARE_ID, spare.getSpare_id());
        spareValues.put(COLUMN_SPARE_CATEGORY, spare.getSpare_category());
        //spareValues.put(COLUMN_SPARE_QUANTIY, spare.getSpare_qnty());
        spareValues.put(COLUMN_SPARE_BP, spare.getSpare_bp());
        spareValues.put(COLUMN_SPARE_SP, spare.getSpare_sp());
        //Insert Row
        db.insert(TABLE_SPARE,null,spareValues);
        db.close();
    }

    /**
     * Method to create oil record
     *
     * @param oil
     */
    public void addOil(Oil oil){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues oilValues = new ContentValues();
        //oilValues.put(COLUMN_OIL_ID, oil.getOil_id());
        oilValues.put(COLUMN_OIL_CATEGORY, oil.getOil_category());
        //oilValues.put(COLUMN_OIL_QUANTIY, oil.getOil_qnty());
        oilValues.put(COLUMN_OIL_BP, oil.getOil_bp());
        oilValues.put(COLUMN_OIL_SP, oil.getOil_sp());
        //Insert Row
        db.insert(TABLE_OIL,null,oilValues);
        db.close();
    }

    /**
     * Method to create gas record
     *
     * @param par
     */
    public void addGas(Gas par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_GAS_ID, par.getGas_id());
        values.put(COLUMN_GAS_CATEGORY, par.getGas_category());
        //values.put(COLUMN_GAS_QUANTIY, par.getGas_qnty());
        values.put(COLUMN_GAS_BP, par.getGas_bp());
        values.put(COLUMN_GAS_SP, par.getGas_sp());
        //Insert Row
        db.insert(TABLE_GAS,null,values);
        db.close();
    }

    /**
     * Method to create powersaw record
     *
     * @param par
     */
    public void addPowersaw(Powersaw par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_POWERSAW_ID, par.getPowersaw_id());
        values.put(COLUMN_POWERSAW_CATEGORY, par.getPowersaw_category());
        //values.put(COLUMN_POWERSAW_QUANTIY, par.getPowersaw_qnty());
        values.put(COLUMN_POWERSAW_BP, par.getPowersaw_bp());
        values.put(COLUMN_POWERSAW_SP, par.getPowersaw_sp());
        //Insert Row
        db.insert(TABLE_POWERSAW,null,values);
        db.close();
    }

    /**
     * Method to create spanner record
     *
     * @param par
     */
    public void addSpanner(Spanner par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_SPANNER_ID, par.getSpanner_id());
        values.put(COLUMN_SPANNER_CATEGORY, par.getSpanner_category());
        //values.put(COLUMN_SPANNER_QUANTIY, par.getSpanner_qnty());
        values.put(COLUMN_SPANNER_BP, par.getSpanner_bp());
        values.put(COLUMN_SPANNER_SP, par.getSpanner_sp());
        //Insert Row
        db.insert(TABLE_SPANNER,null,values);
        db.close();
    }

    /**
     * Method to create welding record
     *
     * @param par
     */
    public void addWelding(Welding par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_WELDING_ID, par.getWelding_id());
        values.put(COLUMN_WELDING_CATEGORY, par.getWelding_category());
        //values.put(COLUMN_WELDING_QUANTIY, par.getWelding_qnty());
        values.put(COLUMN_WELDING_BP, par.getWelding_bp());
        values.put(COLUMN_WELDING_SP, par.getWelding_sp());
        //Insert Row
        db.insert(TABLE_WELDING,null,values);
        db.close();
    }

    /**
     * Method to create bolts record
     *
     * @param par
     */
    public void addBolt(Bolts par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_BOLT_ID, par.getBolt_id());
        values.put(COLUMN_BOLT_CATEGORY, par.getBolt_category());
        //values.put(COLUMN_BOLT_QUANTIY, par.getBolt_qnty());
        values.put(COLUMN_BOLT_BP, par.getBolt_bp());
        values.put(COLUMN_BOLT_SP, par.getBolt_sp());
        //Insert Row
        db.insert(TABLE_BOLT,null,values);
        db.close();
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
   public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        //values.put(COLUMN_USER_STATION, user.getStation());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    /**
     * Method to create employee record
     */
    public void addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_ID, employee.getEmp_id());
        values.put(COLUMN_EMPLOYEE_NAME, employee.getEmp_name());
        values.put(COLUMN_EMPLOYEE_EMAIL, employee.getEmp_email());
        values.put(COLUMN_EMPLOYEE_PHONE, employee.getEmp_phone());
      //  values.put(COLUMN_EMPLOYEE_USER_TYPE, employee.getEmp_usertype());
       // values.put(COLUMN_EMPLOYEE_STATION, employee.getEmp_station());
        values.put(COLUMN_EMPLOYEE_PASSWORD, employee.getEmp_password());

        //Insert Row
        db.insert(TABLE_EMPLOYEE,null, values);
        db.close();
    }

    /**
     * Method to fetch all oils and return the list of oils records
     */
    @SuppressLint("Range")
    public List<Oil> getAllOil(){
        //Columns to fetch
        String[] oilColumns = {
                COLUMN_OIL_ID,
                COLUMN_OIL_CATEGORY,
                COLUMN_OIL_BP,
                COLUMN_OIL_SP
        };
        //Sorting order
        String oilSortOder = COLUMN_OIL_SP + " ASC";
        List<Oil> oilList = new ArrayList<Oil>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Query the Oil Table
        Cursor oilCursor = db.query(TABLE_OIL, oilColumns,null,null,null,null,oilSortOder);
        //Traversing through all rows and adding to list
        if (oilCursor.moveToFirst()){
            do{
                Oil oil = new Oil();
                oil.setOil_id(oilCursor.getInt(oilCursor.getColumnIndex(COLUMN_OIL_ID)));
                oil.setOil_category(oilCursor.getString(oilCursor.getColumnIndex(COLUMN_OIL_CATEGORY)));
                //oil.setOil_qnty(oilCursor.getString(oilCursor.getColumnIndex(COLUMN_OIL_QUANTIY)));
                oil.setOil_bp(Integer.parseInt(oilCursor.getString(oilCursor.getColumnIndex(COLUMN_OIL_BP))));
                oil.setOil_sp(Integer.parseInt(oilCursor.getString(oilCursor.getColumnIndex(COLUMN_OIL_SP))));
                oilList.add(oil);
            }
            while (oilCursor.moveToNext());
        }
        oilCursor.close();
        return oilList;
    }

    /**
     * Method to fetch all gas and return the list of gas records
     */
    @SuppressLint("Range")
    public List<Gas> getAllGas(){
        //Columns to fetch
        String[] columns = {
                COLUMN_GAS_ID,
                COLUMN_GAS_CATEGORY,
                //COLUMN_GAS_QUANTIY,
                COLUMN_GAS_BP,
                COLUMN_GAS_SP
        };
        //Sorting order
        String sortOder = COLUMN_GAS_SP + " ASC";
        List<Gas> list = new ArrayList<Gas>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Query the Table
        Cursor cursor = db.query(TABLE_GAS, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Gas obj = new Gas();
                obj.setGas_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GAS_ID))));
                obj.setGas_category(cursor.getString(cursor.getColumnIndex(COLUMN_GAS_CATEGORY)));
                //obj.setGas_qnty(cursor.getString(cursor.getColumnIndex(COLUMN_GAS_QUANTIY)));
                obj.setGas_bp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GAS_BP))));
                obj.setGas_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GAS_SP))));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    /**
     * Method to fetch all gas and return the list of Powersaw records
     */
    @SuppressLint("Range")
    public List<Powersaw> getAllPowersaw(){
        //Columns to fetch
        String[] columns = {
                COLUMN_POWERSAW_ID,
                COLUMN_POWERSAW_CATEGORY,
                //COLUMN_POWERSAW_QUANTIY,
                COLUMN_POWERSAW_BP,
                COLUMN_POWERSAW_SP
        };
        //Sorting order
        String sortOder = COLUMN_POWERSAW_SP + " ASC";
        List<Powersaw> list = new ArrayList<Powersaw>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Query the Table
        Cursor cursor = db.query(TABLE_POWERSAW, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Powersaw obj = new Powersaw();
                obj.setPowersaw_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAW_ID))));
                obj.setPowersaw_category(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAW_CATEGORY)));
               // obj.setPowersaw_qnty(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAW_QUANTIY)));
                obj.setPowersaw_bp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAW_BP))));
                obj.setPowersaw_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAW_SP))));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to fetch all gas and return the list of Spanner records
     */
    @SuppressLint("Range")
    public List<Spanner> getAllSpanner(){
        //Columns to fetch
        String[] columns = {
                COLUMN_SPANNER_ID,
                COLUMN_SPANNER_CATEGORY,
                COLUMN_SPANNER_QUANTIY,
                COLUMN_SPANNER_BP,
                COLUMN_SPANNER_SP
        };
        //Sorting order
        String sortOder = COLUMN_SPANNER_SP + " ASC";
        List<Spanner> list = new ArrayList<Spanner>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Query the Table
        Cursor cursor = db.query(TABLE_SPANNER, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Spanner obj = new Spanner();
                obj.setSpanner_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNER_ID))));
                obj.setSpanner_category(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNER_CATEGORY)));
                //obj.setSpanner_qnty(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNER_QUANTIY)));
                obj.setSpanner_bp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNER_BP))));
                obj.setSpanner_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNER_SP))));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to fetch all gas and return the list of weldig records
     */
    @SuppressLint("Range")
    public List<Welding> getAllWelding(){
        //Columns to fetch
        String[] columns = {
                COLUMN_WELDING_ID,
                COLUMN_WELDING_CATEGORY,
                //COLUMN_WELDING_QUANTIY,
                COLUMN_WELDING_BP,
                COLUMN_WELDING_SP
        };
        //Sorting order
        String sortOder = COLUMN_WELDING_SP + " ASC";
        List<Welding> list = new ArrayList<Welding>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Query the Table
        Cursor cursor = db.query(TABLE_WELDING, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Welding obj = new Welding();
                obj.setWelding_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDING_ID))));
                obj.setWelding_category(cursor.getString(cursor.getColumnIndex(COLUMN_WELDING_CATEGORY)));
                //obj.setWelding_qnty(cursor.getString(cursor.getColumnIndex(COLUMN_WELDING_QUANTIY)));
                obj.setWelding_bp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDING_BP))));
                obj.setWelding_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDING_SP))));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }/**
     * Method to fetch all gas and return the list of bolt records
     */
    @SuppressLint("Range")
    public List<Bolts> getAllBolts(){
        //Columns to fetch
        String[] columns = {
                COLUMN_BOLT_ID,
                COLUMN_BOLT_CATEGORY,
                //COLUMN_BOLT_QUANTIY,
                COLUMN_BOLT_BP,
                COLUMN_BOLT_SP
        };
        //Sorting order
        String sortOder = COLUMN_BOLT_SP + " ASC";
        List<Bolts> list = new ArrayList<Bolts>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Query the Table
        Cursor cursor = db.query(TABLE_BOLT, columns,null,null,null,null,sortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Bolts obj = new Bolts();
                obj.setBolt_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLT_ID))));
                obj.setBolt_category(cursor.getString(cursor.getColumnIndex(COLUMN_BOLT_CATEGORY)));
                //obj.setBolt_qnty(cursor.getString(cursor.getColumnIndex(COLUMN_BOLT_QUANTIY)));
                obj.setBolt_bp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLT_BP))));
                obj.setBolt_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLT_SP))));
                list.add(obj);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * Method to fetch all oils and return the list of spares records
     */
    @SuppressLint("Range")
    public List<Spare> getAllSpare(){
        //Columns to fetch
        String[] spareColumns = {
                COLUMN_SPARE_ID,
                COLUMN_SPARE_CATEGORY,
                COLUMN_SPARE_BP,
                COLUMN_SPARE_SP
        };
        //Sorting order
        String spareSortOder = COLUMN_SPARE_SP + " ASC";
        List<Spare> spareList = new ArrayList<Spare>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Query the Oil Table
        Cursor cursor = db.query(TABLE_SPARE, spareColumns,null,null,null,null,spareSortOder);
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                Spare spare = new Spare();
                spare.setSpare_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARE_ID))));
                spare.setSpare_category(cursor.getString(cursor.getColumnIndex(COLUMN_SPARE_CATEGORY)));
                //spare.setSpare_qnty(cursor.getString(cursor.getColumnIndex(COLUMN_SPARE_QUANTIY)));
                spare.setSpare_bp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARE_BP))));
                spare.setSpare_sp(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARE_SP))));
                spareList.add(spare);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return spareList;
    }

    /**
     * This method is to fetch all oilsale and return the list of oilsale records
     * This is the deafault method as it returns oilsale as at today's date
     * @return list
     */

    @SuppressLint("Range")
    public List<OilSale> getAllOilSale(){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_OILSALE_DAY,
                COLUMN_OILSALE_TIME,
                COLUMN_OILSALE_CATEGORY,
                COLUMN_OILSALE_QUANTITY,
                COLUMN_OILSALE_UNITPRICE,
                COLUMN_OILSALE_TOTAL,
                COLUMN_OILSALE_PROFIT
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_OILSALE_COUNT + " ASC";
        List<OilSale> oilSaleList = new ArrayList<OilSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_OILSALE_DAY;
        //selection argument
        String[] selectionArgs = {String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))};
        //Query the OilSale Table
        //SELECT saleday,time,category,quantity,unitprice,total,profit FROM oilsales ORDER BY count;
        Cursor oilSaleCursor = db.query(
                TABLE_OIL_SALE, //table to query
                oilSaleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                OilSale oilSale = new OilSale();
                oilSale.setOilSaleDate(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_DAY)));
                oilSale.setOilSaleTime(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_TIME)));
                oilSale.setOilSalecategory(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_CATEGORY)));
                oilSale.setOilSaleQnty(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_QUANTITY))));
                oilSale.setOilSaleUprice(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_UNITPRICE))));
                oilSale.setOilSaleTotal(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_TOTAL))));
                oilSale.setOilSaleProfit(oilSaleCursor.getInt(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_PROFIT)));
                oilSaleList.add(oilSale);
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return oilSaleList;
    }
    /**
     * This method is to fetch all allsale and return the list of the sale records
     * This is the deafault method as it returns allsale as at today's date
     * @return list
     */

    @SuppressLint("Range")
    public List<AllSales> getAllSale(){
        //Columns to fetch
        String[] saleColumns = {
                COLUMN_SALE_DAY,
                COLUMN_SALE_TIME,
                COLUMN_SALE_CATEGORY,
                COLUMN_SALE_QUANTITY,
                COLUMN_SALE_UNITPRICE,
                COLUMN_SALE_TOTAL,
                COLUMN_SALE_PROFIT
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_SALE_COUNT + " ASC";
        List<AllSales> list = new ArrayList<AllSales>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.query(
                TABLE_ALL_SALE, //table to query
                saleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                AllSales sale = new AllSales();
                sale.setSaleDate(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_SALE_DAY)));
                sale.setSaleTime(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_SALE_TIME)));
                sale.setSalecategory(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_SALE_CATEGORY)));
                sale.setSaleQnty(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_SALE_QUANTITY))));
                sale.setSaleUprice(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_SALE_UNITPRICE))));
                sale.setSaleTotal(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_SALE_TOTAL))));
                sale.setSaleProfit(oilSaleCursor.getInt(oilSaleCursor.getColumnIndex(COLUMN_SALE_PROFIT)));
                list.add(sale);
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return list;
    }
    /**
     * This method is to fetch all oilsale and return the list of oilsale records
     * @return list
     */

    @SuppressLint("Range")
    public List <String> getAllSaleOil(){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_OIL_CATEGORY
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_OIL_CATEGORY + " ASC";
        List<String> oilSaleList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.query(
                TABLE_OIL, //table to query
                oilSaleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                oilSaleList.add(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OIL_CATEGORY)));

            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return oilSaleList;
    }
    @SuppressLint("Range")
    public List <String> getAllSaleSpare(){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_SPARE_CATEGORY
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_SPARE_CATEGORY + " ASC";
        List<String> oilSaleList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.query(
                TABLE_SPARE, //table to query
                oilSaleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                oilSaleList.add(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_SPARE_CATEGORY)));

            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        return oilSaleList;
    }
    @SuppressLint("Range")
    public List <String> getAllSalePowerSaw(){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_POWERSAW_CATEGORY
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_POWERSAW_CATEGORY + " ASC";
        List<String> oilSaleList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.query(
                TABLE_POWERSAW, //table to query
                oilSaleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                oilSaleList.add(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_POWERSAW_CATEGORY)));

            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return oilSaleList;
    }
    @SuppressLint("Range")
    public List <String> getAllSaleSpanner(){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_SPANNER_CATEGORY
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_SPANNER_CATEGORY + " ASC";
        List<String> oilSaleList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.query(
                TABLE_SPANNER, //table to query
                oilSaleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                oilSaleList.add(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_SPANNER_CATEGORY)));

            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        return oilSaleList;
    }
    @SuppressLint("Range")
    public List <String> getAllSaleWelding(){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_WELDING_CATEGORY
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_WELDING_CATEGORY + " ASC";
        List<String> oilSaleList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.query(
                TABLE_WELDING, //table to query
                oilSaleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                oilSaleList.add(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_WELDING_CATEGORY)));

            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        return oilSaleList;
    }
    @SuppressLint("Range")
    public List <String> getAllSaleBolt(){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_BOLT_CATEGORY
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_BOLT_CATEGORY + " ASC";
        List<String> oilSaleList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.query(
                TABLE_BOLT, //table to query
                oilSaleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                oilSaleList.add(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_BOLT_CATEGORY)));

            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        return oilSaleList;
    }
    @SuppressLint("Range")
    public List <String> getAllSaleGas(){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_GAS_CATEGORY
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_GAS_CATEGORY + " ASC";
        List<String> oilSaleList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.query(
                TABLE_GAS, //table to query
                oilSaleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                oilSaleList.add(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_GAS_CATEGORY)));

            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        return oilSaleList;
    }

    /**
     * This method is to fetch all gassale and return the list of gassale records
     * This is the deafault method as it returns gassale as at today's date
     * @return list
     */

    @SuppressLint("Range")
    public List<GasSale> getAllGasSale(){
        //Columns to fetch
        String[] columns = {
                COLUMN_GASSALE_DAY,
                COLUMN_GASSALE_TIME,
                COLUMN_GASSALE_CATEGORY,
                COLUMN_GASSALE_QUANTITY,
                COLUMN_GASSALE_UNITPRICE,
                COLUMN_GASSALE_TOTAL,
                COLUMN_GASSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_GASSALE_COUNT + " ASC";
        List<GasSale> list = new ArrayList<GasSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_GASSALE_DAY;
        //selection argument
        String[] selectionArgs = {String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))};
        Cursor cursor = db.query(
                TABLE_GAS_SALES, //table to query
                columns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                GasSale sale = new GasSale();
                sale.setGasSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_DAY)));
                sale.setGasSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_TIME)));
                sale.setGasSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_CATEGORY)));
                sale.setGasSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_QUANTITY))));
                sale.setGasSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_UNITPRICE))));
                sale.setGasSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_TOTAL))));
                sale.setGasSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_GASSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * This method is to fetch all powersawsale and return the list of powersawsale records
     * This is the deafault method as it returns powersawsale as at today's date
     * @return list
     */

    @SuppressLint("Range")
    public List<PowersawSale> getAllPowersawSale(){
        //Columns to fetch
        String[] columns = {
                COLUMN_POWERSAWSALE_DAY,
                COLUMN_POWERSAWSALE_TIME,
                COLUMN_POWERSAWSALE_CATEGORY,
                COLUMN_POWERSAWSALE_QUANTITY,
                COLUMN_POWERSAWSALE_UNITPRICE,
                COLUMN_POWERSAWSALE_TOTAL,
                COLUMN_POWERSAWSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_POWERSAWSALE_COUNT + " ASC";
        List<PowersawSale> list = new ArrayList<PowersawSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_POWERSAWSALE_DAY;
        Cursor cursor = db.query(
                TABLE_POWERSAW_SALES, //table to query
                columns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                PowersawSale sale = new PowersawSale();
                sale.setPowersawSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_DAY)));
                sale.setPowersawSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_TIME)));
                sale.setPowersawSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_CATEGORY)));
                sale.setPowersawSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_QUANTITY))));
                sale.setPowersawSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_UNITPRICE))));
                sale.setPowersawSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_TOTAL))));
                sale.setPowersawSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_POWERSAWSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * This method is to fetch all spannersale and return the list of spannersale records
     * This is the deafault method as it returns spannersale as at today's date
     * @return list
     */

    @SuppressLint("Range")
    public List<SpannerSale> getAllSpannerSale(){
        //Columns to fetch
        String[] columns = {
                COLUMN_SPANNERSALE_DAY,
                COLUMN_SPANNERSALE_TIME,
                COLUMN_SPANNERSALE_CATEGORY,
                COLUMN_SPANNERSALE_QUANTITY,
                COLUMN_SPANNERSALE_UNITPRICE,
                COLUMN_SPANNERSALE_TOTAL,
                COLUMN_SPANNERSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SPANNERSALE_COUNT + " ASC";
        List<SpannerSale> list = new ArrayList<SpannerSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SPANNERSALE_DAY;
        Cursor cursor = db.query(
                TABLE_SPANNER_SALES, //table to query
                columns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                SpannerSale sale = new SpannerSale();
                sale.setSpannerSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_DAY)));
                sale.setSpannerSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_TIME)));
                sale.setSpannerSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_CATEGORY)));
                sale.setSpannerSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_QUANTITY))));
                sale.setSpannerSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_UNITPRICE))));
                sale.setSpannerSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_TOTAL))));
                sale.setSpannerSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SPANNERSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * This method is to fetch all weldingsale and return the list of weldingsale records
     * This is the deafault method as it returns weldingsale as at today's date
     * @return list
     */

    @SuppressLint("Range")
    public List<WeldingSale> getAllWeldingSale(){
        //Columns to fetch
        String[] columns = {
                COLUMN_WELDINGSALE_DAY,
                COLUMN_WELDINGSALE_TIME,
                COLUMN_WELDINGSALE_CATEGORY,
                COLUMN_WELDINGSALE_QUANTITY,
                COLUMN_WELDINGSALE_UNITPRICE,
                COLUMN_WELDINGSALE_TOTAL,
                COLUMN_WELDINGSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_WELDINGSALE_COUNT + " ASC";
        List<WeldingSale> list = new ArrayList<WeldingSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_WELDINGSALE_DAY;
        Cursor cursor = db.query(
                TABLE_WELDING_SALES, //table to query
                columns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                WeldingSale sale = new WeldingSale();
                sale.setWeldingSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_DAY)));
                sale.setWeldingSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_TIME)));
                sale.setWeldingSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_CATEGORY)));
                sale.setWeldingSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_QUANTITY))));
                sale.setWeldingSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_UNITPRICE))));
                sale.setWeldingSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_TOTAL))));
                sale.setWeldingSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_WELDINGSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * This method is to fetch all boltssale and return the list of boltssale records
     * This is the deafault method as it returns boltssale as at today's date
     * @return list
     */

    @SuppressLint("Range")
    public List<BoltSale> getAllBoltSale(){
        //Columns to fetch
        String[] columns = {
                COLUMN_BOLTSALE_DAY,
                COLUMN_BOLTSALE_TIME,
                COLUMN_BOLTSALE_CATEGORY,
                COLUMN_BOLTSALE_QUANTITY,
                COLUMN_BOLTSALE_UNITPRICE,
                COLUMN_BOLTSALE_TOTAL,
                COLUMN_BOLTSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_BOLTSALE_COUNT + " ASC";
        List<BoltSale> list = new ArrayList<BoltSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_BOLTSALE_DAY;
        Cursor cursor = db.query(
                TABLE_BOLT_SALES, //table to query
                columns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                BoltSale sale = new BoltSale();
                sale.setBoltSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_DAY)));
                sale.setBoltSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_TIME)));
                sale.setBoltSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_CATEGORY)));
                sale.setBoltSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_QUANTITY))));
                sale.setBoltSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_UNITPRICE))));
                sale.setBoltSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_TOTAL))));
                sale.setBoltSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_BOLTSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * This method is to fetch all sparesale and return the list of sparesale records
     * This is the deafault method as it returns sparesale as at today's date
     * @return list
     */

    @SuppressLint("Range")
    public List<SpareSale> getAllSpareSale(){
        //Columns to fetch
        String[] saleColumns = {
                COLUMN_SPARESALE_DAY,
                COLUMN_SPARESALE_TIME,
                COLUMN_SPARESALE_CATEGORY,
                COLUMN_SPARESALE_QUANTITY,
                COLUMN_SPARESALE_UNITPRICE,
                COLUMN_SPARESALE_TOTAL,
                COLUMN_SPARESALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SPARESALE_COUNT + " ASC";
        List<SpareSale> saleList = new ArrayList<SpareSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SPARESALE_DAY;
        //selection argument
        String[] selectionArgs = {String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))};
        //Query the OilSale Table
        //SELECT saleday,time,category,quantity,unitprice,total,profit FROM oilsales ORDER BY count;
        Cursor cursor = db.query(
                TABLE_SPARE_SALES, //table to query
                saleColumns,  //columns to return
                null,  //columns for the where clause
                null,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                SpareSale sale = new SpareSale();
                sale.setSpareSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_DAY)));
                sale.setSpareSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_TIME)));
                sale.setSpareSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_CATEGORY)));
                sale.setSpareSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_QUANTITY))));
                sale.setSpareSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_UNITPRICE))));
                sale.setSpareSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_TOTAL))));
                sale.setSpareSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SPARESALE_PROFIT)));
                saleList.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return saleList;
    }

    /**
     * a method to fetch all oilsale as per a perticular date
     * The method picks the set date
     * @param date
     * @return
     */

    @SuppressLint("Range")
    public List<OilSale> getAllOilSale(String date){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_OILSALE_DAY,
                COLUMN_OILSALE_TIME,
                COLUMN_OILSALE_CATEGORY,
                COLUMN_OILSALE_QUANTITY,
                COLUMN_OILSALE_UNITPRICE,
                COLUMN_OILSALE_TOTAL,
                COLUMN_OILSALE_PROFIT
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_OILSALE_COUNT + " ASC";
        List<OilSale> oilSaleList = new ArrayList<OilSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_OILSALE_DAY + " = ?";
        //selection argument
        String[] selectionArgs = {date};
        //Query the OilSale Table
        //SELECT saleday,time,category,quantity,unitprice,total,profit FROM oilsales ORDER BY count;
        Cursor oilSaleCursor = db.query(
                TABLE_OIL_SALE, //table to query
                oilSaleColumns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                OilSale oilSale = new OilSale();
                oilSale.setOilSaleDate(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_DAY)));
                oilSale.setOilSaleTime(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_TIME)));
                oilSale.setOilSalecategory(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_CATEGORY)));
                oilSale.setOilSaleQnty(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_QUANTITY))));
                oilSale.setOilSaleUprice(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_UNITPRICE))));
                oilSale.setOilSaleTotal(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_TOTAL))));
                oilSale.setOilSaleProfit(oilSaleCursor.getInt(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_PROFIT)));
                oilSaleList.add(oilSale);
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return oilSaleList;
    }

    /**
     * a method to fetch all gassale as per a perticular date
     * The method picks the set date
     * @param date
     * @return
     */

    @SuppressLint("Range")
    public List<GasSale> getAllGasSale(String date){
        //Columns to fetch
        String[] columns = {
                COLUMN_GASSALE_DAY,
                COLUMN_GASSALE_TIME,
                COLUMN_GASSALE_CATEGORY,
                COLUMN_GASSALE_QUANTITY,
                COLUMN_GASSALE_UNITPRICE,
                COLUMN_GASSALE_TOTAL,
                COLUMN_GASSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_GASSALE_COUNT + " ASC";
        List<GasSale> list = new ArrayList<GasSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_GASSALE_DAY + " = ?";
        //selection argument
        String[] selectionArgs = {date};
        Cursor cursor = db.query(
                TABLE_GAS_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                GasSale sale = new GasSale();
                sale.setGasSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_DAY)));
                sale.setGasSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_TIME)));
                sale.setGasSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_CATEGORY)));
                sale.setGasSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_QUANTITY))));
                sale.setGasSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_UNITPRICE))));
                sale.setGasSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_TOTAL))));
                sale.setGasSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_GASSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * a method to fetch all sale as per a perticular date
     * The method picks the set date
     * @param date
     * @return
     */

    @SuppressLint("Range")
    public List<AllSales> getAllSale(String date){
        //Columns to fetch
        String[] columns = {
                COLUMN_SALE_DAY,
                COLUMN_SALE_TIME,
                COLUMN_SALE_CATEGORY,
                COLUMN_SALE_QUANTITY,
                COLUMN_SALE_UNITPRICE,
                COLUMN_SALE_TOTAL,
                COLUMN_SALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SALE_COUNT + " ASC";
        List<AllSales> list = new ArrayList<AllSales>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SALE_DAY + " = ?";
        //selection argument
        String[] selectionArgs = {date};
        Cursor cursor = db.query(
                TABLE_ALL_SALE, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                AllSales sale = new AllSales();
                sale.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_DAY)));
                sale.setSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_TIME)));
                sale.setSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_CATEGORY)));
                sale.setSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_QUANTITY))));
                sale.setSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_UNITPRICE))));
                sale.setSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_TOTAL))));
                sale.setSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * a method to fetch all powersawsale as per a perticular date
     * The method picks the set date
     * @param date
     * @return
     */

    @SuppressLint("Range")
    public List<PowersawSale> getAllPowersawSale(String date){
        //Columns to fetch
        String[] columns = {
                COLUMN_POWERSAWSALE_DAY,
                COLUMN_POWERSAWSALE_TIME,
                COLUMN_POWERSAWSALE_CATEGORY,
                COLUMN_POWERSAWSALE_QUANTITY,
                COLUMN_POWERSAWSALE_UNITPRICE,
                COLUMN_POWERSAWSALE_TOTAL,
                COLUMN_POWERSAWSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_POWERSAWSALE_COUNT + " ASC";
        List<PowersawSale> list = new ArrayList<PowersawSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_POWERSAWSALE_DAY + " = ?";
        //selection argument
        String[] selectionArgs = {date};
        Cursor cursor = db.query(
                TABLE_POWERSAW_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                PowersawSale sale = new PowersawSale();
                sale.setPowersawSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_DAY)));
                sale.setPowersawSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_TIME)));
                sale.setPowersawSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_CATEGORY)));
                sale.setPowersawSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_QUANTITY))));
                sale.setPowersawSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_UNITPRICE))));
                sale.setPowersawSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_TOTAL))));
                sale.setPowersawSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_POWERSAWSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * a method to fetch all spannersale as per a perticular date
     * The method picks the set date
     * @param date
     * @return
     */

    @SuppressLint("Range")
    public List<SpannerSale> getAllSpannerSale(String date){
        //Columns to fetch
        String[] columns = {
                COLUMN_SPANNERSALE_DAY,
                COLUMN_SPANNERSALE_TIME,
                COLUMN_SPANNERSALE_CATEGORY,
                COLUMN_SPANNERSALE_QUANTITY,
                COLUMN_SPANNERSALE_UNITPRICE,
                COLUMN_SPANNERSALE_TOTAL,
                COLUMN_SPANNERSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SPANNERSALE_COUNT + " ASC";
        List<SpannerSale> list = new ArrayList<SpannerSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SPANNERSALE_DAY + " = ?";
        //selection argument
        String[] selectionArgs = {date};
        Cursor cursor = db.query(
                TABLE_SPANNER_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                SpannerSale sale = new SpannerSale();
                sale.setSpannerSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_DAY)));
                sale.setSpannerSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_TIME)));
                sale.setSpannerSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_CATEGORY)));
                sale.setSpannerSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_QUANTITY))));
                sale.setSpannerSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_UNITPRICE))));
                sale.setSpannerSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_TOTAL))));
                sale.setSpannerSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SPANNERSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * a method to fetch all weldingsale as per a perticular date
     * The method picks the set date
     * @param date
     * @return
     */

    @SuppressLint("Range")
    public List<WeldingSale> getAllWeldingSale(String date){
        //Columns to fetch
        String[] columns = {
                COLUMN_WELDINGSALE_DAY,
                COLUMN_WELDINGSALE_TIME,
                COLUMN_WELDINGSALE_CATEGORY,
                COLUMN_WELDINGSALE_QUANTITY,
                COLUMN_WELDINGSALE_UNITPRICE,
                COLUMN_WELDINGSALE_TOTAL,
                COLUMN_WELDINGSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_WELDINGSALE_COUNT + " ASC";
        List<WeldingSale> list = new ArrayList<WeldingSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_WELDINGSALE_DAY + " = ?";
        //selection argument
        String[] selectionArgs = {date};
        Cursor cursor = db.query(
                TABLE_WELDING_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                WeldingSale sale = new WeldingSale();
                sale.setWeldingSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_DAY)));
                sale.setWeldingSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_TIME)));
                sale.setWeldingSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_CATEGORY)));
                sale.setWeldingSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_QUANTITY))));
                sale.setWeldingSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_UNITPRICE))));
                sale.setWeldingSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_TOTAL))));
                sale.setWeldingSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_WELDINGSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * a method to fetch all boltssale as per a perticular date
     * The method picks the set date
     * @param date
     * @return
     */

    @SuppressLint("Range")
    public List<BoltSale> getAllBoltSale(String date){
        //Columns to fetch
        String[] columns = {
                COLUMN_BOLTSALE_DAY,
                COLUMN_BOLTSALE_TIME,
                COLUMN_BOLTSALE_CATEGORY,
                COLUMN_BOLTSALE_QUANTITY,
                COLUMN_BOLTSALE_UNITPRICE,
                COLUMN_BOLTSALE_TOTAL,
                COLUMN_BOLTSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_BOLTSALE_COUNT + " ASC";
        List<BoltSale> list = new ArrayList<BoltSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_BOLTSALE_DAY + " = ?";
        //selection argument
        String[] selectionArgs = {date};
        Cursor cursor = db.query(
                TABLE_BOLT_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                BoltSale sale = new BoltSale();
                sale.setBoltSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_DAY)));
                sale.setBoltSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_TIME)));
                sale.setBoltSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_CATEGORY)));
                sale.setBoltSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_QUANTITY))));
                sale.setBoltSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_UNITPRICE))));
                sale.setBoltSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_TOTAL))));
                sale.setBoltSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_BOLTSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * a method to fetch all sparesale as per a perticular date
     * The method picks the set date
     * @param date
     * @return
     */

    @SuppressLint("Range")
    public List<SpareSale> getAllSpareSale(String date){
        //Columns to fetch
        String[] saleColumns = {
                COLUMN_SPARESALE_DAY,
                COLUMN_SPARESALE_TIME,
                COLUMN_SPARESALE_CATEGORY,
                COLUMN_SPARESALE_QUANTITY,
                COLUMN_SPARESALE_UNITPRICE,
                COLUMN_SPARESALE_TOTAL,
                COLUMN_SPARESALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SPARESALE_COUNT + " ASC";
        List<SpareSale> saleList = new ArrayList<SpareSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SPARESALE_DAY + " = ?";
        //selection argument
        String[] selectionArgs = {date};
        //Query the OilSale Table
        //SELECT saleday,time,category,quantity,unitprice,total,profit FROM oilsales ORDER BY count;
        Cursor cursor = db.query(
                TABLE_SPARE_SALES, //table to query
                saleColumns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                SpareSale sale = new SpareSale();
                sale.setSpareSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_DAY)));
                sale.setSpareSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_TIME)));
                sale.setSpareSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_CATEGORY)));
                sale.setSpareSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_QUANTITY))));
                sale.setSpareSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_UNITPRICE))));
                sale.setSpareSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_TOTAL))));
                sale.setSpareSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SPARESALE_PROFIT)));
                saleList.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return saleList;
    }

    /**
     * Method to fetch oilsales between two specific dates
     * It returns a list of oilsales
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressLint("Range")
    public List<OilSale> getAllOilSaleBetween(String startDate, String endDate){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_OILSALE_DAY,
                COLUMN_OILSALE_TIME,
                COLUMN_OILSALE_CATEGORY,
                COLUMN_OILSALE_QUANTITY,
                COLUMN_OILSALE_UNITPRICE,
                COLUMN_OILSALE_TOTAL,
                COLUMN_OILSALE_PROFIT
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_OILSALE_COUNT + " ASC";
        List<OilSale> oilSaleList = new ArrayList<OilSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_OILSALE_DAY + " >= ? AND "+COLUMN_OILSALE_DAY+" <= ?";
        //selection argument
        String[] selectionArgs = {startDate, endDate};
        //Query the OilSale Table
        //SELECT saleday,time,category,quantity,unitprice,total,profit FROM oilsales ORDER BY count;
        //Cursor oil = db.rawQuery()
        Cursor oilSaleCursor = db.query(
                TABLE_OIL_SALE, //table to query
                oilSaleColumns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                OilSale oilSale = new OilSale();
                oilSale.setOilSaleDate(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_DAY)));
                oilSale.setOilSaleTime(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_TIME)));
                oilSale.setOilSalecategory(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_CATEGORY)));
                oilSale.setOilSaleQnty(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_QUANTITY))));
                oilSale.setOilSaleUprice(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_UNITPRICE))));
                oilSale.setOilSaleTotal(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_TOTAL))));
                oilSale.setOilSaleProfit(oilSaleCursor.getInt(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_PROFIT)));
                oilSaleList.add(oilSale);
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return oilSaleList;
    }

    /**
     * Method to fetch gaslsales between two specific dates
     * It returns a list of gassales
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressLint("Range")
    public List<GasSale> getAllGasSaleBetween(String startDate, String endDate){
        //Columns to fetch
        String[] columns = {
                COLUMN_GASSALE_DAY,
                COLUMN_GASSALE_TIME,
                COLUMN_GASSALE_CATEGORY,
                COLUMN_GASSALE_QUANTITY,
                COLUMN_GASSALE_UNITPRICE,
                COLUMN_GASSALE_TOTAL,
                COLUMN_GASSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_GASSALE_COUNT + " ASC";
        List<GasSale> list = new ArrayList<GasSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_GASSALE_DAY + " >= ? AND "+COLUMN_GASSALE_DAY+" <= ?";
        //selection argument
        String[] selectionArgs = {startDate, endDate};
        //Query the OilSale Table
        Cursor cursor = db.query(
                TABLE_GAS_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                GasSale sale = new GasSale();
                sale.setGasSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_DAY)));
                sale.setGasSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_TIME)));
                sale.setGasSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_CATEGORY)));
                sale.setGasSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_QUANTITY))));
                sale.setGasSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_UNITPRICE))));
                sale.setGasSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_TOTAL))));
                sale.setGasSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_GASSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * Method to fetch allsales between two specific dates
     * It returns a list of sales
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressLint("Range")
    public List<AllSales> getAllSaleBetween(String startDate, String endDate){
        //Columns to fetch
        String[] columns = {
                COLUMN_SALE_DAY,
                COLUMN_SALE_TIME,
                COLUMN_SALE_CATEGORY,
                COLUMN_SALE_QUANTITY,
                COLUMN_SALE_UNITPRICE,
                COLUMN_SALE_TOTAL,
                COLUMN_SALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SALE_COUNT + " ASC";
        List<AllSales> list = new ArrayList<AllSales>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SALE_DAY + " >= ? AND "+COLUMN_SALE_DAY+" <= ?";
        //selection argument
        String[] selectionArgs = {startDate, endDate};
        //Query the OilSale Table
        Cursor cursor = db.query(
                TABLE_ALL_SALE, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                AllSales sale = new AllSales();
                sale.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_DAY)));
                sale.setSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_TIME)));
                sale.setSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_CATEGORY)));
                sale.setSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_QUANTITY))));
                sale.setSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_UNITPRICE))));
                sale.setSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_TOTAL))));
                sale.setSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to fetch spannerlsales between two specific dates
     * It returns a list of spannersales
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressLint("Range")
    public List<SpannerSale> getAllSpannerSaleBetween(String startDate, String endDate){
        //Columns to fetch
        String[] columns = {
                COLUMN_SPANNERSALE_DAY,
                COLUMN_SPANNERSALE_TIME,
                COLUMN_SPANNERSALE_CATEGORY,
                COLUMN_SPANNERSALE_QUANTITY,
                COLUMN_SPANNERSALE_UNITPRICE,
                COLUMN_SPANNERSALE_TOTAL,
                COLUMN_SPANNERSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SPANNERSALE_COUNT + " ASC";
        List<SpannerSale> list = new ArrayList<SpannerSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SPANNERSALE_DAY + " >= ? AND "+COLUMN_SPANNERSALE_DAY+" <= ?";
        //selection argument
        String[] selectionArgs = {startDate, endDate};
        //Query the OilSale Table
        Cursor cursor = db.query(
                TABLE_SPANNER_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                SpannerSale sale = new SpannerSale();
                sale.setSpannerSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_DAY)));
                sale.setSpannerSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_TIME)));
                sale.setSpannerSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_CATEGORY)));
                sale.setSpannerSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_QUANTITY))));
                sale.setSpannerSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_UNITPRICE))));
                sale.setSpannerSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_TOTAL))));
                sale.setSpannerSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SPANNERSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to fetch boltlsales between two specific dates
     * It returns a list of boltsales
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressLint("Range")
    public List<BoltSale> getAllBoltSaleBetween(String startDate, String endDate){
        //Columns to fetch
        String[] columns = {
                COLUMN_BOLTSALE_DAY,
                COLUMN_BOLTSALE_TIME,
                COLUMN_BOLTSALE_CATEGORY,
                COLUMN_BOLTSALE_QUANTITY,
                COLUMN_BOLTSALE_UNITPRICE,
                COLUMN_BOLTSALE_TOTAL,
                COLUMN_BOLTSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_BOLTSALE_COUNT + " ASC";
        List<BoltSale> list = new ArrayList<BoltSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_BOLTSALE_DAY + " >= ? AND "+COLUMN_BOLTSALE_DAY+" <= ?";
        //selection argument
        String[] selectionArgs = {startDate, endDate};
        //Query the OilSale Table
        Cursor cursor = db.query(
                TABLE_BOLT_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                BoltSale sale = new BoltSale();
                sale.setBoltSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_DAY)));
                sale.setBoltSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_TIME)));
                sale.setBoltSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_CATEGORY)));
                sale.setBoltSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_QUANTITY))));
                sale.setBoltSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_UNITPRICE))));
                sale.setBoltSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_TOTAL))));
                sale.setBoltSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_BOLTSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to fetch weldinglsales between two specific dates
     * It returns a list of weldingsales
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressLint("Range")
    public List<WeldingSale> getAllWeldingSaleBetween(String startDate, String endDate){
        //Columns to fetch
        String[] columns = {
                COLUMN_WELDINGSALE_DAY,
                COLUMN_WELDINGSALE_TIME,
                COLUMN_WELDINGSALE_CATEGORY,
                COLUMN_WELDINGSALE_QUANTITY,
                COLUMN_WELDINGSALE_UNITPRICE,
                COLUMN_WELDINGSALE_TOTAL,
                COLUMN_WELDINGSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_WELDINGSALE_COUNT + " ASC";
        List<WeldingSale> list = new ArrayList<WeldingSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_WELDINGSALE_DAY + " >= ? AND "+COLUMN_WELDINGSALE_DAY+" <= ?";
        //selection argument
        String[] selectionArgs = {startDate, endDate};
        //Query the OilSale Table
        Cursor cursor = db.query(
                TABLE_WELDING_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                WeldingSale sale = new WeldingSale();
                sale.setWeldingSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_DAY)));
                sale.setWeldingSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_TIME)));
                sale.setWeldingSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_CATEGORY)));
                sale.setWeldingSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_QUANTITY))));
                sale.setWeldingSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_UNITPRICE))));
                sale.setWeldingSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_TOTAL))));
                sale.setWeldingSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_WELDINGSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to fetch powersawlsales between two specific dates
     * It returns a list of powersawsales
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressLint("Range")
    public List<PowersawSale> getAllPowersawSaleBetween(String startDate, String endDate){
        //Columns to fetch
        String[] columns = {
                COLUMN_POWERSAWSALE_DAY,
                COLUMN_POWERSAWSALE_TIME,
                COLUMN_POWERSAWSALE_CATEGORY,
                COLUMN_POWERSAWSALE_QUANTITY,
                COLUMN_POWERSAWSALE_UNITPRICE,
                COLUMN_POWERSAWSALE_TOTAL,
                COLUMN_POWERSAWSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_POWERSAWSALE_COUNT + " ASC";
        List<PowersawSale> list = new ArrayList<PowersawSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_POWERSAWSALE_DAY + " >= ? AND "+COLUMN_POWERSAWSALE_DAY+" <= ?";
        //selection argument
        String[] selectionArgs = {startDate, endDate};
        //Query the OilSale Table
        Cursor cursor = db.query(
                TABLE_POWERSAW_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                PowersawSale sale = new PowersawSale();
                sale.setPowersawSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_DAY)));
                sale.setPowersawSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_TIME)));
                sale.setPowersawSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_CATEGORY)));
                sale.setPowersawSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_QUANTITY))));
                sale.setPowersawSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_UNITPRICE))));
                sale.setPowersawSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_TOTAL))));
                sale.setPowersawSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_POWERSAWSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to fetch sparesales between two specific dates
     * It returns a list of sparesales
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressLint("Range")
    public List<SpareSale> getAllSpareSaleBetween(String startDate, String endDate){
        //Columns to fetch
        String[] columns = {
                COLUMN_SPARESALE_DAY,
                COLUMN_SPARESALE_TIME,
                COLUMN_SPARESALE_CATEGORY,
                COLUMN_SPARESALE_QUANTITY,
                COLUMN_SPARESALE_UNITPRICE,
                COLUMN_SPARESALE_TOTAL,
                COLUMN_SPARESALE_PROFIT
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_SPARESALE_COUNT + " ASC";
        List<SpareSale> saleList = new ArrayList<SpareSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SPARESALE_DAY + " >= ? AND "+COLUMN_SPARESALE_DAY+" <= ?";
        //selection argument
        String[] selectionArgs = {startDate, endDate};
        //SELECT saleday,time,category,quantity,unitprice,total,profit FROM oilsales ORDER BY count;
        Cursor cursor = db.query(
                TABLE_SPARE_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                SpareSale sale = new SpareSale();
                sale.setSpareSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_DAY)));
                sale.setSpareSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_TIME)));
                sale.setSpareSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_CATEGORY)));
                sale.setSpareSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_QUANTITY))));
                sale.setSpareSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_UNITPRICE))));
                sale.setSpareSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_TOTAL))));
                sale.setSpareSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SPARESALE_PROFIT)));
                saleList.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return saleList;
    }

    /**
     * Method to get a list of all oil sales of a certain oil category between
     * two different dates.
     * @param startDate
     * @param endDate
     * @param catName
     * @return
     */
    @SuppressLint("Range")
    public List<OilSale> getAllOilSaleBetween(String startDate, String endDate, String catName){
        //Columns to fetch
        String[] oilSaleColumns = {
                COLUMN_OILSALE_DAY,
                COLUMN_OILSALE_TIME,
                COLUMN_OILSALE_CATEGORY,
                COLUMN_OILSALE_QUANTITY,
                COLUMN_OILSALE_UNITPRICE,
                COLUMN_OILSALE_TOTAL,
                COLUMN_OILSALE_PROFIT
        };
        //Sorting order
        String oilSaleSortOder = COLUMN_OILSALE_COUNT + " ASC";
        List<OilSale> oilSaleList = new ArrayList<OilSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_OILSALE_DAY + " >= ? AND "+COLUMN_OILSALE_DAY+" <= ? AND " + COLUMN_OILSALE_CATEGORY + "=?";
        //selection argument
        String[] selectionArgs = {startDate, endDate, catName};
        //Query the OilSale Table
        //SELECT saleday,time,category,quantity,unitprice,total,profit FROM oilsales ORDER BY count;
        //Cursor oil = db.rawQuery()
        Cursor oilSaleCursor = db.query(
                TABLE_OIL_SALE, //table to query
                oilSaleColumns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                oilSaleSortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (oilSaleCursor.moveToFirst()){
            do{
                OilSale oilSale = new OilSale();
                oilSale.setOilSaleDate(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_DAY)));
                oilSale.setOilSaleTime(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_TIME)));
                oilSale.setOilSalecategory(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_CATEGORY)));
                oilSale.setOilSaleQnty(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_QUANTITY))));
                oilSale.setOilSaleUprice(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_UNITPRICE))));
                oilSale.setOilSaleTotal(Integer.parseInt(oilSaleCursor.getString(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_TOTAL))));
                oilSale.setOilSaleProfit(oilSaleCursor.getInt(oilSaleCursor.getColumnIndex(COLUMN_OILSALE_PROFIT)));
                oilSaleList.add(oilSale);
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return oilSaleList;
    }

    /**
     * Method to get a list of all gas sales of a certain gas category between
     * two different dates.
     * @param startDate
     * @param endDate
     * @param catName
     * @return
     */
    @SuppressLint("Range")
    public List<GasSale> getAllGasSaleBetween(String startDate, String endDate, String catName){
        //Columns to fetch
        String[] columns = {
                COLUMN_GASSALE_DAY,
                COLUMN_GASSALE_TIME,
                COLUMN_GASSALE_CATEGORY,
                COLUMN_GASSALE_QUANTITY,
                COLUMN_GASSALE_UNITPRICE,
                COLUMN_GASSALE_TOTAL,
                COLUMN_GASSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_GASSALE_COUNT + " ASC";
        List<GasSale> list = new ArrayList<GasSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_GASSALE_DAY + " >= ? AND "+COLUMN_GASSALE_DAY+" <= ? AND " + COLUMN_GASSALE_CATEGORY + "=?";
        //selection argument
        String[] selectionArgs = {startDate, endDate, catName};
        Cursor cursor = db.query(
                TABLE_GAS_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                GasSale sale = new GasSale();
                sale.setGasSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_DAY)));
                sale.setGasSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_TIME)));
                sale.setGasSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_CATEGORY)));
                sale.setGasSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_QUANTITY))));
                sale.setGasSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_UNITPRICE))));
                sale.setGasSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GASSALE_TOTAL))));
                sale.setGasSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_GASSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    /**
     * Method to get a list of all sales of a certain category between
     * two different dates.
     * @param startDate
     * @param endDate
     * @param catName
     * @return
     */
    @SuppressLint("Range")
    public List<AllSales> getAllSaleBetween(String startDate, String endDate, String catName){
        //Columns to fetch
        String[] columns = {
                COLUMN_SALE_DAY,
                COLUMN_SALE_TIME,
                COLUMN_SALE_CATEGORY,
                COLUMN_SALE_QUANTITY,
                COLUMN_SALE_UNITPRICE,
                COLUMN_SALE_TOTAL,
                COLUMN_SALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SALE_COUNT + " ASC";
        List<AllSales> list = new ArrayList<AllSales>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SALE_DAY + " >= ? AND "+COLUMN_SALE_DAY+" <= ? AND " + COLUMN_SALE_CATEGORY + "=?";
        //selection argument
        String[] selectionArgs = {startDate, endDate, catName};
        Cursor cursor = db.query(
                TABLE_ALL_SALE, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                AllSales sale = new AllSales();
                sale.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_DAY)));
                sale.setSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_TIME)));
                sale.setSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_CATEGORY)));
                sale.setSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_QUANTITY))));
                sale.setSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_UNITPRICE))));
                sale.setSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SALE_TOTAL))));
                sale.setSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to get a list of all powersaw sales of a certain powersaw category between
     * two different dates.
     * @param startDate
     * @param endDate
     * @param catName
     * @return
     */
    @SuppressLint("Range")
    public List<PowersawSale> getAllPowerSawSaleBetween(String startDate, String endDate, String catName){
        //Columns to fetch
        String[] columns = {
                COLUMN_POWERSAWSALE_DAY,
                COLUMN_POWERSAWSALE_TIME,
                COLUMN_POWERSAWSALE_CATEGORY,
                COLUMN_POWERSAWSALE_QUANTITY,
                COLUMN_POWERSAWSALE_UNITPRICE,
                COLUMN_POWERSAWSALE_TOTAL,
                COLUMN_POWERSAWSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_POWERSAWSALE_COUNT + " ASC";
        List<PowersawSale> list = new ArrayList<PowersawSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_POWERSAWSALE_DAY + " >= ? AND "+COLUMN_POWERSAWSALE_DAY+" <= ? AND " + COLUMN_POWERSAWSALE_CATEGORY + "=?";
        //selection argument
        String[] selectionArgs = {startDate, endDate, catName};
        Cursor cursor = db.query(
                TABLE_POWERSAW_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                PowersawSale sale = new PowersawSale();
                sale.setPowersawSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_DAY)));
                sale.setPowersawSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_TIME)));
                sale.setPowersawSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_CATEGORY)));
                sale.setPowersawSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_QUANTITY))));
                sale.setPowersawSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_UNITPRICE))));
                sale.setPowersawSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_POWERSAWSALE_TOTAL))));
                sale.setPowersawSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_POWERSAWSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to get a list of all spanner sales of a certain spanner category between
     * two different dates.
     * @param startDate
     * @param endDate
     * @param catName
     * @return
     */
    @SuppressLint("Range")
    public List<SpannerSale> getAllSpannerSaleBetween(String startDate, String endDate, String catName){
        //Columns to fetch
        String[] columns = {
                COLUMN_SPANNERSALE_DAY,
                COLUMN_SPANNERSALE_TIME,
                COLUMN_SPANNERSALE_CATEGORY,
                COLUMN_SPANNERSALE_QUANTITY,
                COLUMN_SPANNERSALE_UNITPRICE,
                COLUMN_SPANNERSALE_TOTAL,
                COLUMN_SPANNERSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SPANNERSALE_COUNT + " ASC";
        List<SpannerSale> list = new ArrayList<SpannerSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SPANNERSALE_DAY + " >= ? AND "+COLUMN_SPANNERSALE_DAY+" <= ? AND " + COLUMN_SPANNERSALE_CATEGORY + "=?";
        //selection argument
        String[] selectionArgs = {startDate, endDate, catName};
        Cursor cursor = db.query(
                TABLE_SPANNER_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                SpannerSale sale = new SpannerSale();
                sale.setSpannerSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_DAY)));
                sale.setSpannerSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_TIME)));
                sale.setSpannerSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_CATEGORY)));
                sale.setSpannerSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_QUANTITY))));
                sale.setSpannerSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_UNITPRICE))));
                sale.setSpannerSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPANNERSALE_TOTAL))));
                sale.setSpannerSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SPANNERSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to get a list of all welding sales of a certain welding category between
     * two different dates.
     * @param startDate
     * @param endDate
     * @param catName
     * @return
     */
    @SuppressLint("Range")
    public List<WeldingSale> getAllWeldingSaleBetween(String startDate, String endDate, String catName){
        //Columns to fetch
        String[] columns = {
                COLUMN_WELDINGSALE_DAY,
                COLUMN_WELDINGSALE_TIME,
                COLUMN_WELDINGSALE_CATEGORY,
                COLUMN_WELDINGSALE_QUANTITY,
                COLUMN_WELDINGSALE_UNITPRICE,
                COLUMN_WELDINGSALE_TOTAL,
                COLUMN_WELDINGSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_WELDINGSALE_COUNT + " ASC";
        List<WeldingSale> list = new ArrayList<WeldingSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_WELDINGSALE_DAY + " >= ? AND "+COLUMN_WELDINGSALE_DAY+" <= ? AND " + COLUMN_WELDINGSALE_CATEGORY + "=?";
        //selection argument
        String[] selectionArgs = {startDate, endDate, catName};
        Cursor cursor = db.query(
                TABLE_WELDING_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                WeldingSale sale = new WeldingSale();
                sale.setWeldingSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_DAY)));
                sale.setWeldingSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_TIME)));
                sale.setWeldingSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_CATEGORY)));
                sale.setWeldingSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_QUANTITY))));
                sale.setWeldingSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_UNITPRICE))));
                sale.setWeldingSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WELDINGSALE_TOTAL))));
                sale.setWeldingSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_WELDINGSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to get a list of all bolts sales of a certain bolt category between
     * two different dates.
     * @param startDate
     * @param endDate
     * @param catName
     * @return
     */
    @SuppressLint("Range")
    public List<BoltSale> getAllBoltSaleBetween(String startDate, String endDate, String catName){
        //Columns to fetch
        String[] columns = {
                COLUMN_BOLTSALE_DAY,
                COLUMN_BOLTSALE_TIME,
                COLUMN_BOLTSALE_CATEGORY,
                COLUMN_BOLTSALE_QUANTITY,
                COLUMN_BOLTSALE_UNITPRICE,
                COLUMN_BOLTSALE_TOTAL,
                COLUMN_BOLTSALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_BOLTSALE_COUNT + " ASC";
        List<BoltSale> list = new ArrayList<BoltSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_BOLTSALE_DAY + " >= ? AND "+COLUMN_BOLTSALE_DAY+" <= ? AND " + COLUMN_BOLTSALE_CATEGORY + "=?";
        //selection argument
        String[] selectionArgs = {startDate, endDate, catName};
        Cursor cursor = db.query(
                TABLE_BOLT_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                BoltSale sale = new BoltSale();
                sale.setBoltSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_DAY)));
                sale.setBoltSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_TIME)));
                sale.setBoltSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_CATEGORY)));
                sale.setBoltSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_QUANTITY))));
                sale.setBoltSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_UNITPRICE))));
                sale.setBoltSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_BOLTSALE_TOTAL))));
                sale.setBoltSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_BOLTSALE_PROFIT)));
                list.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * Method to get a list of all spare sales of a certain spare category between
     * two different dates.
     * @param startDate
     * @param endDate
     * @param catName
     * @return
     */
    @SuppressLint("Range")
    public List<SpareSale> getAllSpareSaleBetween(String startDate, String endDate, String catName){
        //Columns to fetch
        String[] columns = {
                COLUMN_SPARESALE_DAY,
                COLUMN_SPARESALE_TIME,
                COLUMN_SPARESALE_CATEGORY,
                COLUMN_SPARESALE_QUANTITY,
                COLUMN_SPARESALE_UNITPRICE,
                COLUMN_SPARESALE_TOTAL,
                COLUMN_SPARESALE_PROFIT
        };
        //Sorting order
        String sortOder = COLUMN_SPARESALE_COUNT + " ASC";
        List<SpareSale> saleList = new ArrayList<SpareSale>();
        SQLiteDatabase db = this.getWritableDatabase();
        //selection criteria
        String selection = COLUMN_SPARESALE_DAY + " >= ? AND "+COLUMN_SPARESALE_DAY+" <= ? AND " + COLUMN_SPARESALE_CATEGORY + "=?";
        //selection argument
        String[] selectionArgs = {startDate, endDate, catName};
        //SELECT saleday,time,category,quantity,unitprice,total,profit FROM oilsales ORDER BY count;
        Cursor cursor = db.query(
                TABLE_SPARE_SALES, //table to query
                columns,  //columns to return
                selection,  //columns for the where clause
                selectionArgs,  //the values for the where clause
                null,   //group the rows
                null,   //filter by row groups
                sortOder);  //the sortorder
        //Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do{
                SpareSale sale = new SpareSale();
                sale.setSpareSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_DAY)));
                sale.setSpareSaleTime(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_TIME)));
                sale.setSpareSalecategory(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_CATEGORY)));
                sale.setSpareSaleQnty(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_QUANTITY))));
                sale.setSpareSaleUprice(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_UNITPRICE))));
                sale.setSpareSaleTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SPARESALE_TOTAL))));
                sale.setSpareSaleProfit(cursor.getInt(cursor.getColumnIndex(COLUMN_SPARESALE_PROFIT)));
                saleList.add(sale);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return saleList;
    }

    /**
     * Method to get sum of getAllOilSaleBetween(String startDate, String endDate, String catName) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllOilSaleProfit(String startDate, String endDate, String catName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.rawQuery("SELECT SUM(" + COLUMN_OILSALE_PROFIT + ") as Profit FROM "
                + TABLE_OIL_SALE + " WHERE " + COLUMN_OILSALE_DAY + " BETWEEN ? AND ? AND " + COLUMN_OILSALE_CATEGORY + "=?",
                new String[]{startDate, endDate, catName});

        //Traversing through all rows and adding to list
        int total = 0;
        if (oilSaleCursor.moveToFirst()){
            do{
                total = oilSaleCursor.getInt(oilSaleCursor.getColumnIndex("Profit"));
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllGasSaleBetween(String startDate, String endDate, String catName) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllGasSaleProfit(String startDate, String endDate, String catName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_GASSALE_PROFIT + ") as Profit FROM "
                + TABLE_GAS_SALES + " WHERE " + COLUMN_GASSALE_DAY + " BETWEEN ? AND ? AND " + COLUMN_GASSALE_CATEGORY + "=?",
                new String[]{startDate, endDate, catName});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }
    /**
     * Method to get sum of getAllSaleBetween(String startDate, String endDate, String catName) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSaleProfit(String startDate, String endDate, String catName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SALE_PROFIT + ") as Profit FROM "
                + TABLE_ALL_SALE + " WHERE " + COLUMN_SALE_DAY + " BETWEEN ? AND ? AND " + COLUMN_SALE_CATEGORY + "=?",
                new String[]{startDate, endDate, catName});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllPowersawSaleBetween(String startDate, String endDate, String catName) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllPowersawSaleProfit(String startDate, String endDate, String catName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_POWERSAWSALE_PROFIT + ") as Profit FROM "
                + TABLE_POWERSAW_SALES + " WHERE " + COLUMN_POWERSAWSALE_DAY + " BETWEEN ? AND ? AND " + COLUMN_POWERSAWSALE_CATEGORY + "=?",
                new String[]{startDate, endDate, catName});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllsPANNERSaleBetween(String startDate, String endDate, String catName) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpannerSaleProfit(String startDate, String endDate, String catName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPANNERSALE_PROFIT + ") as Profit FROM "
                + TABLE_SPANNER_SALES + " WHERE " + COLUMN_SPANNERSALE_DAY + " BETWEEN ? AND ? AND " + COLUMN_SPANNERSALE_CATEGORY + "=?",
                new String[]{startDate, endDate, catName});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllWeldingSaleBetween(String startDate, String endDate, String catName) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllWeldingSaleProfit(String startDate, String endDate, String catName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_WELDINGSALE_PROFIT + ") as Profit FROM "
                + TABLE_WELDING_SALES + " WHERE " + COLUMN_WELDINGSALE_DAY + " BETWEEN ? AND ? AND " + COLUMN_WELDINGSALE_CATEGORY + "=?",
                new String[]{startDate, endDate, catName});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllBoltsSaleBetween(String startDate, String endDate, String catName) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllBoltSaleProfit(String startDate, String endDate, String catName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_BOLTSALE_PROFIT + ") as Profit FROM "
                + TABLE_BOLT_SALES + " WHERE " + COLUMN_BOLTSALE_DAY + " BETWEEN ? AND ? AND " + COLUMN_BOLTSALE_CATEGORY + "=?",
                new String[]{startDate, endDate, catName});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllSpareSaleBetween(String startDate, String endDate, String catName) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpareSaleProfit(String startDate, String endDate, String catName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPARESALE_PROFIT + ") as Profit FROM "
                + TABLE_SPARE_SALES + " WHERE " + COLUMN_SPARESALE_DAY + " BETWEEN ? AND ? AND " + COLUMN_SPARESALE_CATEGORY + "=?",
                new String[]{startDate, endDate, catName});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllOilSaleBetween(String startDate, String endDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllOilSaleProfit(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.rawQuery("SELECT SUM(" + COLUMN_OILSALE_PROFIT + ") as Profit FROM "
                + TABLE_OIL_SALE + " WHERE " + COLUMN_OILSALE_DAY + " BETWEEN ? AND ?",
                new String[]{startDate, endDate});

        //Traversing through all rows
        int total = 0;
        if (oilSaleCursor.moveToFirst())
                total = oilSaleCursor.getInt(oilSaleCursor.getColumnIndex("Profit"));
        oilSaleCursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllGasSaleBetween(String startDate, String endDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllGasSaleProfit(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_GASSALE_PROFIT + ") as Profit FROM "
                + TABLE_GAS_SALES + " WHERE " + COLUMN_GASSALE_DAY + " BETWEEN ? AND ?",
                new String[]{startDate, endDate});

        //Traversing through all rows
        int total = 0;
        if (cursor.moveToFirst())
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
        cursor.close();
        db.close();
        return total;
    }
    /**
     * Method to get sum of getAllSaleBetween(String startDate, String endDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSaleProfit(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SALE_PROFIT + ") as Profit FROM "
                + TABLE_ALL_SALE + " WHERE " + COLUMN_SALE_DAY + " BETWEEN ? AND ?",
                new String[]{startDate, endDate});

        //Traversing through all rows
        int total = 0;
        if (cursor.moveToFirst())
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllPowersawSaleBetween(String startDate, String endDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllPowersawSaleProfit(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_POWERSAWSALE_PROFIT + ") as Profit FROM "
                + TABLE_POWERSAW_SALES + " WHERE " + COLUMN_POWERSAWSALE_DAY + " BETWEEN ? AND ?",
                new String[]{startDate, endDate});

        //Traversing through all rows
        int total = 0;
        if (cursor.moveToFirst())
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllSpannerSaleBetween(String startDate, String endDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpannerSaleProfit(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPANNERSALE_PROFIT + ") as Profit FROM "
                + TABLE_SPANNER_SALES + " WHERE " + COLUMN_SPANNERSALE_DAY + " BETWEEN ? AND ?",
                new String[]{startDate, endDate});

        //Traversing through all rows
        int total = 0;
        if (cursor.moveToFirst())
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllWeldingSaleBetween(String startDate, String endDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllWeldingSaleProfit(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_WELDINGSALE_PROFIT + ") as Profit FROM "
                + TABLE_WELDING_SALES + " WHERE " + COLUMN_WELDINGSALE_DAY + " BETWEEN ? AND ?",
                new String[]{startDate, endDate});

        //Traversing through all rows
        int total = 0;
        if (cursor.moveToFirst())
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllBoltSaleBetween(String startDate, String endDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllBoltSaleProfit(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_BOLTSALE_PROFIT + ") as Profit FROM "
                + TABLE_BOLT_SALES + " WHERE " + COLUMN_BOLTSALE_DAY + " BETWEEN ? AND ?",
                new String[]{startDate, endDate});

        //Traversing through all rows
        int total = 0;
        if (cursor.moveToFirst())
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllSpareSaleBetween(String startDate, String endDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpareSaleProfit(String startDate, String endDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPARESALE_PROFIT + ") as Profit FROM "
                + TABLE_SPARE_SALES + " WHERE " + COLUMN_SPARESALE_DAY + " BETWEEN ? AND ?",
                new String[]{startDate, endDate});

        //Traversing through all rows
        int total = 0;
        if (cursor.moveToFirst())
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllOilSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllOilSaleProfit(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.rawQuery("SELECT SUM(" + COLUMN_OILSALE_PROFIT + ") as Profit FROM "
                + TABLE_OIL_SALE + " WHERE " + COLUMN_OILSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (oilSaleCursor.moveToFirst()){
            do{
                total = oilSaleCursor.getInt(oilSaleCursor.getColumnIndex("Profit"));
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllGasSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllGasSaleProfit(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_GASSALE_PROFIT + ") as Profit FROM "
                + TABLE_GAS_SALES + " WHERE " + COLUMN_GASSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }
    /**
     * Method to get sum of getAllGasSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSaleProfit(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SALE_PROFIT + ") as Profit FROM "
                + TABLE_ALL_SALE + " WHERE " + COLUMN_SALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllPowersawSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllPowersawSaleProfit(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_POWERSAWSALE_PROFIT + ") as Profit FROM "
                + TABLE_POWERSAW_SALES + " WHERE " + COLUMN_POWERSAWSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllSpannerSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpannerSaleProfit(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPANNERSALE_PROFIT + ") as Profit FROM "
                + TABLE_SPANNER_SALES + " WHERE " + COLUMN_SPANNERSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllWeldingSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllWeldingSaleProfit(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_WELDINGSALE_PROFIT + ") as Profit FROM "
                + TABLE_WELDING_SALES + " WHERE " + COLUMN_WELDINGSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllBoltSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllBoltSaleProfit(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_BOLTSALE_PROFIT + ") as Profit FROM "
                + TABLE_BOLT_SALES + " WHERE " + COLUMN_BOLTSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllSpareSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpareSaleProfit(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPARESALE_PROFIT + ") as Profit FROM "
                + TABLE_SPARE_SALES + " WHERE " + COLUMN_SPARESALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllOilSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllOilSaleTotal(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.rawQuery("SELECT SUM(" + COLUMN_OILSALE_TOTAL + ") as Profit FROM "
                + TABLE_OIL_SALE + " WHERE " + COLUMN_OILSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (oilSaleCursor.moveToFirst()){
            do{
                total = oilSaleCursor.getInt(oilSaleCursor.getColumnIndex("Profit"));
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllGasSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllGasSaleTotal(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_GASSALE_TOTAL + ") as Profit FROM "
                + TABLE_GAS_SALES + " WHERE " + COLUMN_GASSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }
    /**
     * Method to get sum of getAllGasSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSaleTotal(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SALE_TOTAL + ") as Profit FROM "
                + TABLE_ALL_SALE + " WHERE " + COLUMN_SALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllPowersawSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllPowersawSaleTotal(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_POWERSAWSALE_TOTAL + ") as Profit FROM "
                + TABLE_POWERSAW_SALES + " WHERE " + COLUMN_POWERSAWSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllSpannerSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpannerSaleTotal(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPANNERSALE_TOTAL + ") as Profit FROM "
                + TABLE_SPANNER_SALES + " WHERE " + COLUMN_SPANNERSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllWeldingSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllWeldingSaleTotal(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_WELDINGSALE_TOTAL + ") as Profit FROM "
                + TABLE_WELDING_SALES + " WHERE " + COLUMN_WELDINGSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllbOLTSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllBoltSaleTotal(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_BOLTSALE_TOTAL + ") as Profit FROM "
                + TABLE_BOLT_SALES + " WHERE " + COLUMN_BOLTSALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get sum of getAllSpareSale(String startDate) Method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpareSaleTotal(String startDate){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPARESALE_TOTAL + ") as Profit FROM "
                + TABLE_SPARE_SALES + " WHERE " + COLUMN_SPARESALE_DAY + " = ?",
                new String[]{startDate});

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to return sum of profit for getAllOilSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllOilSaleProfit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.rawQuery("SELECT SUM(" + COLUMN_OILSALE_PROFIT + ") as Profit FROM " + TABLE_OIL_SALE, null);

        //Traversing through all rows and adding to list
        int total = 0;
        if (oilSaleCursor.moveToFirst()){
            do{
                total = oilSaleCursor.getInt(oilSaleCursor.getColumnIndex("Profit"));
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return total;
    }

    /**
     * Method to return sum of profit for getAllGasSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllGasSaleProfit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_GASSALE_PROFIT + ") as Profit FROM " +
                TABLE_GAS_SALES, null);

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }
    /**
     * Method to return sum of profit for getAllGasSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSaleProfit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SALE_PROFIT + ") as Profit FROM " +
                TABLE_ALL_SALE, null);

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to return sum of profit for getAllPowersawSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllPowersawSaleProfit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_POWERSAWSALE_PROFIT + ") as Profit FROM " +
                TABLE_POWERSAW_SALES, null);

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }
    /**
     * Method to return sum of profit for getAllSpannerSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpannerSaleProfit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPANNERSALE_PROFIT + ") as Profit FROM " +
                TABLE_SPANNER_SALES, null);

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to return sum of profit for getAllWeldingSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllWeldingSaleProfit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_WELDINGSALE_PROFIT + ") as Profit FROM " +
                TABLE_WELDING_SALES, null);

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to return sum of profit for getAllBoltSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllBoltSaleProfit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_BOLTSALE_PROFIT + ") as Profit FROM " +
                TABLE_BOLT_SALES, null);

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to return sum of profit for getAllSpareSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpareSaleProfit(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPARESALE_PROFIT + ") as Profit FROM " + TABLE_SPARE_SALES, null);

        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Profit"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get total sum for getAllOilSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllOilSaleTotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor oilSaleCursor = db.rawQuery("SELECT SUM(" + COLUMN_OILSALE_TOTAL + ") as Total FROM " + TABLE_OIL_SALE, null);
        //Traversing through all rows and adding to list
        int total = 0;
        if (oilSaleCursor.moveToFirst()){
            do{
                total = oilSaleCursor.getInt(oilSaleCursor.getColumnIndex("Total"));
            }
            while (oilSaleCursor.moveToNext());
        }
        oilSaleCursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get total sum for getAllGasSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllGasSaleTotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_GASSALE_TOTAL + ") as Total FROM "
                + TABLE_GAS_SALES, null);
        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Total"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }
   /**
     * Method to get total sum for getAllSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSaleTotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SALE_TOTAL + ") as Total FROM "
                + TABLE_ALL_SALE, null);
        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Total"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get total sum for getAllPowersawSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllPowersawSaleTotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_POWERSAWSALE_TOTAL + ") as Total FROM "
                + TABLE_POWERSAW_SALES, null);
        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Total"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get total sum for getAllSpannerSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpannerSaleTotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPANNERSALE_TOTAL + ") as Total FROM "
                + TABLE_SPANNER_SALES, null);
        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Total"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get total sum for getAllWeldingSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllWeldingSaleTotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_WELDINGSALE_TOTAL + ") as Total FROM "
                + TABLE_WELDING_SALES, null);
        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Total"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get total sum for getAllBoltSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllBoltSaleTotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_BOLTSALE_TOTAL + ") as Total FROM "
                + TABLE_BOLT_SALES, null);
        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Total"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * Method to get total sum for getAllOilSale() method
     * @return
     */
    @SuppressLint("Range")
    public int getAllSpareSaleTotal(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_SPARESALE_TOTAL + ") as Total FROM " + TABLE_SPARE_SALES, null);
        //Traversing through all rows and adding to list
        int total = 0;
        if (cursor.moveToFirst()){
            do{
                total = cursor.getInt(cursor.getColumnIndex("Total"));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return total;
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    @SuppressLint("Range")
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
                //COLUMN_USER_STATION
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
               // user.setStation(cursor.getString(cursor.getColumnIndex(COLUMN_USER_STATION)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }
    /**
     * Method to fetch all Employeees and return the list of all employees
     */
    @SuppressLint("Range")
    public List <Employee> getAllEmployee() {
        //Array of columns to fetch
        String [] columns = {
                COLUMN_EMPLOYEE_NAME,
                COLUMN_EMPLOYEE_ID,
                COLUMN_EMPLOYEE_EMAIL,
                COLUMN_EMPLOYEE_PHONE,
              //  COLUMN_EMPLOYEE_STATION,
               // COLUMN_EMPLOYEE_USER_TYPE,
                COLUMN_EMPLOYEE_PASSWORD
        };
        //Sorting order
        String sortOrder =
                COLUMN_EMPLOYEE_NAME + " ASC";
        List<Employee> employeeList = new ArrayList<Employee>();

        SQLiteDatabase db = this.getReadableDatabase();

        //Query table Employee
        Cursor cursor = db.query(TABLE_EMPLOYEE, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        if (cursor.moveToFirst()) {
            do {
                Employee employee = new Employee();
                employee.setEmp_id((cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_ID))));
                employee.setEmp_name(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_NAME)));
                employee.setEmp_email(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_EMAIL)));
                employee.setEmp_password(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_PASSWORD)));
                employee.setEmp_phone((cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_PHONE))));
                //employee.setEmp_station(cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_STATION)));
               // employee.setEmp_usertype((cursor.getString(cursor.getColumnIndex(COLUMN_EMPLOYEE_USER_TYPE))));

                // Adding Employee record to list
                employeeList.add(employee);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return Employee list
        return employeeList;

    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to update oil record
     *
     * @param oilSale
     */

    public void updateOIlSale(OilSale oilSale) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_OILSALE_DAY, oilSale.getOilSaleDate());
        values.put(COLUMN_OILSALE_TIME, oilSale.getOilSaleTime());
        values.put(COLUMN_OILSALE_CATEGORY, oilSale.getOilSalecategory());
        values.put(COLUMN_OILSALE_QUANTITY, oilSale.getOilSaleQnty());
        values.put(COLUMN_OILSALE_UNITPRICE, oilSale.getOilSaleUprice());
        values.put(COLUMN_OILSALE_TOTAL, oilSale.getOilSaleTotal());
        // updating row
        db.update(TABLE_OIL_SALE, values, COLUMN_OILSALE_TIME + " = ?",
                new String[]{String.valueOf(oilSale.getOilSaleTime())});
        db.close();
    }

    /**
     * This method to update gassale record
     *
     * @param sale
     */

    public void updateGasSale(GasSale sale) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_GASSALE_DAY, sale.getGasSaleDate());
        values.put(COLUMN_GASSALE_TIME, sale.getGasSaleTime());
        values.put(COLUMN_GASSALE_CATEGORY, sale.getGasSalecategory());
        values.put(COLUMN_GASSALE_QUANTITY, sale.getGasSaleQnty());
        values.put(COLUMN_GASSALE_UNITPRICE, sale.getGasSaleUprice());
        values.put(COLUMN_GASSALE_TOTAL, sale.getGasSaleTotal());
        // updating row
        db.update(TABLE_GAS_SALES, values, COLUMN_GASSALE_TIME + " = ?",
                new String[]{String.valueOf(sale.getGasSaleTime())});
        db.close();
    }

    /**
     * This method to update spare record
     *
     * @param spareSale
     */

    public void updateSpareSale(SpareSale spareSale) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SPARESALE_DAY, spareSale.getSpareSaleDate());
        values.put(COLUMN_SPARESALE_TIME, spareSale.getSpareSaleTime());
        values.put(COLUMN_SPARESALE_CATEGORY, spareSale.getSpareSalecategory());
        values.put(COLUMN_SPARESALE_QUANTITY, spareSale.getSpareSaleQnty());
        values.put(COLUMN_SPARESALE_UNITPRICE, spareSale.getSpareSaleUprice());
        values.put(COLUMN_SPARESALE_TOTAL, spareSale.getSpareSaleTotal());
        // updating row
        db.update(TABLE_SPARE_SALES, values, COLUMN_SPARESALE_TIME + " = ?",
                new String[]{String.valueOf(spareSale.getSpareSaleTime())});
        db.close();
    }

    /**
     * Method to update oil record by oilId
     */
    public void updateOil(Oil oil){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues oilValues = new ContentValues();
        //oilValues.put(COLUMN_OIL_CATEGORY, oil.getOil_category());
        //oilValues.put(COLUMN_OIL_QUANTIY, oil.getOil_qnty());
        oilValues.put(COLUMN_OIL_BP, oil.getOil_bp());
        oilValues.put(COLUMN_OIL_SP, oil.getOil_sp());

        db.update(TABLE_OIL, oilValues, COLUMN_OIL_ID + " = ?", new String[]{String.valueOf(oil.getOil_id())});
        db.close();
    }
    /**
     * Method to update spare record by oilId
     */
    public void updateSpare(Spare oil){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues oilValues = new ContentValues();
        //oilValues.put(COLUMN_OIL_CATEGORY, oil.getOil_category());
        //oilValues.put(COLUMN_OIL_QUANTIY, oil.getOil_qnty());
        oilValues.put(COLUMN_SPARE_BP, oil.getSpare_bp());
        oilValues.put(COLUMN_SPARE_SP, oil.getSpare_sp());

        db.update(TABLE_SPARE, oilValues, COLUMN_SPARE_ID + " = ?", new String[]{String.valueOf(oil.getSpare_id())});
        db.close();
    }
    /**
     * Method to update gas record by gasId
     */
    public void updateGas(Gas par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_GAS_CATEGORY, par.getGas_category());
        //values.put(COLUMN_GAS_QUANTIY, par.getGas_qnty());
        values.put(COLUMN_GAS_BP, par.getGas_bp());
        values.put(COLUMN_GAS_SP, par.getGas_sp());

        db.update(TABLE_GAS, values, COLUMN_GAS_ID + " = ?", new String[]{String.valueOf(par.getGas_id())});
        db.close();
    }
    /**
     * Method to update powersaw record by powersawId
     */
    public void updatePowersaw(Powersaw par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_POWERSAW_CATEGORY, par.getPowersaw_category());
        //values.put(COLUMN_POWERSAW_QUANTIY, par.getPowersaw_qnty());
        values.put(COLUMN_POWERSAW_BP, par.getPowersaw_bp());
        values.put(COLUMN_POWERSAW_SP, par.getPowersaw_sp());

        db.update(TABLE_POWERSAW, values, COLUMN_POWERSAW_ID + " = ?",
                new String[]{String.valueOf(par.getPowersaw_id())});
        db.close();
    }
    /**
     * Method to update spanner record by spannerId
     */
    public void updateSpanner(Spanner par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_SPANNER_CATEGORY, par.getSpanner_category());
        //values.put(COLUMN_SPANNER_QUANTIY, par.getSpanner_qnty());
        values.put(COLUMN_SPANNER_BP, par.getSpanner_bp());
        values.put(COLUMN_SPANNER_SP, par.getSpanner_sp());

        db.update(TABLE_SPANNER, values, COLUMN_SPANNER_ID + " = ?", new String[]{String.valueOf(par.getSpanner_id())});
        db.close();
    }
    /**
     * Method to update welding record by weldingId
     */
    public void updateWelding(Welding par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_WELDING_CATEGORY, par.getWelding_category());
        //values.put(COLUMN_WELDING_QUANTIY, par.getWelding_qnty());
        values.put(COLUMN_WELDING_BP, par.getWelding_bp());
        values.put(COLUMN_WELDING_SP, par.getWelding_sp());

        db.update(TABLE_WELDING, values, COLUMN_WELDING_ID + " = ?", new String[]{String.valueOf(par.getWelding_id())});
        db.close();
    }
    /**
     * Method to update Bolts record by boltId
     */
    public void updateBolt(Bolts par){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
       // values.put(COLUMN_BOLT_CATEGORY, par.getBolt_category());
        //values.put(COLUMN_BOLT_QUANTIY, par.getBolt_qnty());
        values.put(COLUMN_BOLT_BP, par.getBolt_bp());
        values.put(COLUMN_BOLT_SP, par.getBolt_sp());

        db.update(TABLE_BOLT, values, COLUMN_BOLT_ID + " = ?", new String[]{String.valueOf(par.getBolt_id())});
        db.close();
    }

    /**
     * Method to update Employee record
     * @param employee
     */
    public void updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_NAME, employee.getEmp_name());
        values.put(COLUMN_EMPLOYEE_EMAIL, employee.getEmp_email());
        values.put(COLUMN_EMPLOYEE_PASSWORD, employee.getEmp_password());
        values.put(COLUMN_EMPLOYEE_ID, employee.getEmp_id());
        values.put(COLUMN_EMPLOYEE_PHONE, employee.getEmp_phone());
        //values.put(COLUMN_EMPLOYEE_USER_TYPE, employee.getEmp_usertype());
        //values.put(COLUMN_EMPLOYEE_STATION, employee.getEmp_station());

        // updating row
        db.update(TABLE_EMPLOYEE, values, COLUMN_EMPLOYEE_ID + " = ?",
                new String[]{String.valueOf(employee.getEmp_id())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     *  user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     *  oilsale
     */

    public void deleteOilSale(OilSale oilSale) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete oilSale record by time
        db.delete(TABLE_OIL_SALE, COLUMN_OILSALE_TIME + " = ?",
                new String[]{String.valueOf(oilSale.getOilSaleTime())});
        db.close();
    }

    public void deleteGasSale(GasSale sale) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete sale record by time
        db.delete(TABLE_GAS_SALES, COLUMN_GASSALE_TIME + " = ?",
                new String[]{String.valueOf(sale.getGasSaleTime())});
        db.close();
    }

    public void deleteWeldingSale(WeldingSale sale) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete sale record by time
        db.delete(TABLE_WELDING_SALES, COLUMN_WELDINGSALE_TIME + " = ?",
                new String[]{String.valueOf(sale.getWeldingSaleTime())});
        db.close();
    }

    public void deletePowersawSale(PowersawSale sale) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete sale record by time
        db.delete(TABLE_POWERSAW_SALES, COLUMN_POWERSAWSALE_TIME + " = ?",
                new String[]{String.valueOf(sale.getPowersawSaleTime())});
        db.close();
    }

    public void deleteSpannerSale(SpannerSale sale) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete sale record by time
        db.delete(TABLE_SPANNER_SALES, COLUMN_SPANNERSALE_TIME + " = ?",
                new String[]{String.valueOf(sale.getSpannerSaleTime())});
        db.close();
    }

    public void deleteBoltSale(BoltSale sale) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete sale record by time
        db.delete(TABLE_BOLT_SALES, COLUMN_BOLTSALE_TIME + " = ?",
                new String[]{String.valueOf(sale.getBoltSaleTime())});
        db.close();
    }

    /**
     * Method to delete an oilsale record
     * @param spareSale
     */
    public void deleteSpareSale(SpareSale spareSale) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete oilSale record by time
        db.delete(TABLE_SPARE_SALES, COLUMN_SPARESALE_TIME + " = ?",
                new String[]{String.valueOf(spareSale.getSpareSaleTime())});
        db.close();
    }

    /**
     * Method to delete employee record
     * @param employee
     */
    public void deleteEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete employee record by id
        db.delete(TABLE_EMPLOYEE, COLUMN_EMPLOYEE_ID + " = ?",
                new String[]{String.valueOf(employee.getEmp_id())});
        db.close();
    }
    /**
     * Method to delete oil record
     */
    public void deleteOil(Oil oil){
        SQLiteDatabase db = this.getWritableDatabase();
        //delete record by oilId
        db.delete(TABLE_OIL, COLUMN_OIL_ID + " = ?", new String[]{String.valueOf(oil.getOil_id())});
        db.close();
    }
    /**
     * Method to delete spanner record
     */
    public void deleteSpanner(Spanner oil){
        SQLiteDatabase db = this.getWritableDatabase();
        //delete record by oilId
        db.delete(TABLE_SPANNER, COLUMN_SPANNER_ID + " = ?", new String[]{String.valueOf(oil.getSpanner_id())});
        db.close();
    }
    /**
     * Method to delete gas record
     */
    public void deleteGas(Gas oil){
        SQLiteDatabase db = this.getWritableDatabase();
        //delete record by oilId
        db.delete(TABLE_GAS, COLUMN_GAS_ID + " = ?", new String[]{String.valueOf(oil.getGas_id())});
        db.close();
    }
    /**
     * Method to delete welding record
     */
    public void deleteWelding(Welding oil){
        SQLiteDatabase db = this.getWritableDatabase();
        //delete record by oilId
        db.delete(TABLE_WELDING, COLUMN_WELDING_ID + " = ?", new String[]{String.valueOf(oil.getWelding_id())});
        db.close();
    }
    /**
     * Method to delete poweresaw record
     */
    public void deletePowersaw(Powersaw oil){
        SQLiteDatabase db = this.getWritableDatabase();
        //delete record by oilId
        db.delete(TABLE_POWERSAW, COLUMN_POWERSAW_ID + " = ?", new String[]{String.valueOf(oil.getPowersaw_id())});
        db.close();
    }
    /**
     * Method to delete Spare record
     */
    public void deleteSpare(Spare oil){
        SQLiteDatabase db = this.getWritableDatabase();
        //delete record by oilId
        db.delete(TABLE_SPARE, COLUMN_SPARE_ID + " = ?", new String[]{String.valueOf(oil.getSpare_id())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * Method for checking if an oil category exists
     * @param category
     * @return
     */
    public boolean checkOilCategory(String category) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_OIL_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_OIL_CATEGORY + " = ?";

        // selection argument
        String[] selectionArgs = {category};

        // query user table with condition
        Cursor cursor = db.query(TABLE_OIL, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount >= 1;
    }

    /**
     * method for checking if spare category exists
     * @param category
     * @return
     */
    public boolean checkSpareCategory(String category) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_SPARE_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_SPARE_CATEGORY + " = ?";
        // selection argument
        String[] selectionArgs = {category};
        // query spare table with condition
        Cursor cursor = db.query(TABLE_SPARE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount >= 1;
    }

    /**
     * method for checking if gas category exists
     * @param category
     * @return
     */
    public boolean checkGasCategory(String category) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_GAS_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_GAS_CATEGORY + " = ?";
        // selection argument
        String[] selectionArgs = {category};
        // query spare table with condition
        Cursor cursor = db.query(TABLE_GAS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount >= 1;
    }
    /**
     * method for checking if powersaw category exists
     * @param category
     * @return
     */
    public boolean checkPowersawCategory(String category) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_POWERSAW_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_POWERSAW_CATEGORY + " = ?";
        // selection argument
        String[] selectionArgs = {category};
        // query spare table with condition
        Cursor cursor = db.query(TABLE_POWERSAW, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount >= 1;
    }

    /**
     * method for checking if Spanner category exists
     * @param category
     * @return
     */
    public boolean checkSpannerCategory(String category) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_SPANNER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_SPANNER_CATEGORY + " = ?";
        // selection argument
        String[] selectionArgs = {category};
        // query spare table with condition
        Cursor cursor = db.query(TABLE_SPANNER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount >= 1;
    }

    /**
     * method for checking if welding category exists
     * @param category
     * @return
     */
    public boolean checkWeldingCategory(String category) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_WELDING_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_WELDING_CATEGORY + " = ?";
        // selection argument
        String[] selectionArgs = {category};
        // query spare table with condition
        Cursor cursor = db.query(TABLE_WELDING, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount >= 1;
    }

    /**
     * method for checking if bolts category exists
     * @param category
     * @return
     */
    public boolean checkBoltCategory(String category) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_BOLT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_BOLT_CATEGORY + " = ?";
        // selection argument
        String[] selectionArgs = {category};
        // query spare table with condition
        Cursor cursor = db.query(TABLE_BOLT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount >= 1;
    }

    /**
     * This method checks whether Employee exists
     * @param emp_id
     * @return
     */
    public boolean checkEmployee(String emp_id) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_EMPLOYEE_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_EMPLOYEE_ID + " = ?";

        // selection argument
        String[] selectionArgs = {emp_id};

        // query user table with condition
        /**
         * Here query function is used to fetch records from employee table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT employee_name FROM employee WHERE employee_id = '123456';
         */
        Cursor cursor = db.query(TABLE_EMPLOYEE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * Method to check if cat exists
     * @param id
     * @return
     */
    public boolean ifExists(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = COLUMN_OIL_CATEGORY + " = ?";
        String[] selArgs = {id};
        String[] columns = {COLUMN_OIL_CATEGORY};
        cursor= db.query(TABLE_OIL, columns, checkQuery,selArgs,null,null,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    /**
     * Method to check if cat exists
     * @param id
     * @return
     */
    public boolean ifExistsBolt(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = COLUMN_BOLT_CATEGORY + " = ?";
        String[] selArgs = {id};
        String[] columns = {COLUMN_BOLT_CATEGORY};
        cursor= db.query(TABLE_BOLT, columns, checkQuery,selArgs,null,null,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    /**
     * Method to check if cat exists
     * @param id
     * @return
     */
    public boolean ifExistsGas(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = COLUMN_GAS_CATEGORY + " = ?";
        String[] selArgs = {id};
        String[] columns = {COLUMN_GAS_CATEGORY};
        cursor= db.query(TABLE_GAS, columns, checkQuery,selArgs,null,null,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    /**
     * Method to check if cat exists
     * @param id
     * @return
     */
    public boolean ifExistsPowersaw(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = COLUMN_POWERSAW_CATEGORY + " = ?";
        String[] selArgs = {id};
        String[] columns = {COLUMN_POWERSAW_CATEGORY};
        cursor= db.query(TABLE_POWERSAW, columns, checkQuery,selArgs,null,null,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    /**
     * Method to check if cat exists
     * @param id
     * @return
     */
    public boolean ifExistsSpanner(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = COLUMN_SPANNER_CATEGORY + " = ?";
        String[] selArgs = {id};
        String[] columns = {COLUMN_SPANNER_CATEGORY};
        cursor= db.query(TABLE_SPANNER, columns, checkQuery,selArgs,null,null,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    /**
     * Method to check if cat exists
     * @param id
     * @return
     */
    public boolean ifExistsSpare(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = COLUMN_SPARE_CATEGORY + " = ?";
        String[] selArgs = {id};
        String[] columns = {COLUMN_SPARE_CATEGORY};
        cursor= db.query(TABLE_SPARE, columns, checkQuery,selArgs,null,null,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    /**
     * Method to check if cat exists
     * @param id
     * @return
     */
    public boolean ifExistsWelding(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = COLUMN_WELDING_CATEGORY + " = ?";
        String[] selArgs = {id};
        String[] columns = {COLUMN_WELDING_CATEGORY};
        cursor= db.query(TABLE_WELDING, columns, checkQuery,selArgs,null,null,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    /**
     * Method to check if oil provided in category exists in the oil table
     * @param oil_category
     * @return
     */
    public boolean ifOilCatExists(String oil_category) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = COLUMN_OIL_CATEGORY + " = ?";
        String[] selArgs = {oil_category};
        String[] columns = {COLUMN_OIL_CATEGORY};
        cursor= db.query(TABLE_OIL, columns, checkQuery,selArgs,null,null,null);
        //boolean exists = (cursor.getCount() >= 1);
        //cursor.close();
        //return exists;
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    /**
     * Method to check if oil provided in category exists in the oil table
     * @param oil_category
     * @return
     */

    public boolean ifExist(String oil_category) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        String checkQuery = COLUMN_OIL_CATEGORY + " = ?";
        String[] selArgs = {oil_category};
        String[] columns = {COLUMN_OIL_ID};
        cursor= db.query(TABLE_OIL, columns, checkQuery,selArgs,null,null,null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean checkEmployee(String emp_id, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_EMPLOYEE_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_EMPLOYEE_ID + " = ?" + " AND " + COLUMN_EMPLOYEE_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {emp_id, password};
        Cursor cursor = db.query(TABLE_EMPLOYEE, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
