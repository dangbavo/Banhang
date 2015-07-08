package com.banhang.bavo.banhang;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewAnimationUtils;

import java.awt.font.TextAttribute;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.validation.Validator;

/**
 * Created by Ba Vo on 5/30/2015.
 */
public class TaoDatabase extends SQLiteOpenHelper{
    private static final String LOG="DatabaseHelper";
    //colunm user
    private static final String KEY_ID="id";
    private static final String KEY_USERNAME = "U_Name";
    private static final String KEY_PASS = "U_Pass";
    private static final String KEY_STATUS = "Status";
    private static final String KEY_CREATED_AT = "Create_at";
    private static final String KEY_MODIFIED_AT="Modified_at";
    private static final String KEY_DELETED_AT="Deleted_at";
    //colunm hang hoa
    private static final String ID_HANGHOA="HH_ID";
    private static final String LHH_ID="HH_LOAIHANGHOAID";
    private static final String DVT_ID="HH_DONVITINHID";
    private static final String TEN_HANG="HH_TENHANG";
    private static final String MAHANGHOA="HH_MAHANGHOA";
    private static final String QUYCACH="HH_QUYCACH";
    private static final String DONGIABAN="HH_DONGIABAN";
    private static final String MAU="HH_MAU";
    private static final String HH_DVT_CD_ID="HH_DONVITINH_CD_ID";
    private static final String SL_CD="HH_SL_CD";

    //column khachhang
    private static final String KHNCC_ID="KHNCC_ID";
    private static final String KHNCC_LOAIKHACHHANG="KHNCC_LOAIKH";
    private static final String KHNCC_MAKHACHHANG="KHNCC_MAKHACHHANG";
    private static final String KHNCC_TEN="KHNCC_TEN";
    private static final String KHNCC_DIACHI="DIACHI";
    private static final String KHNCC_SDT="KHNCC_SDT";
    private static final String TP_TEN="TP_TEN";
    private static final String KHNCC_QUANHUYEN="KHNCC_QUANHUYEN";
    private static final String PX_TEN="PX_TEN";
    private static final String KHNCC_CREATE_AT="KHNCC_CREATE_AT";
    private static final String KHNCC_MODIFY_AT="KHNCC_MODIFY_AT";
    private static final String KHNCC_DELETE_AT="KHNCC_DELETE_AT";
    // column BANHANG
    private static final String BH_ID="BH_ID";
    private static final String BH_KHACHHANG="BH_KHACHHANG";
    private static final String BH_TUKHOID="BH_TUKHOID";
    private static final String BH_MASOPHIEU="BH_MASOPHIEU";
    private static final String BH_NGAYBAN="BH_NGAYBAN";
    private static final String BH_SOTIENPHAITHU="BH_SOTIENPHAITHU";
    private static final String BH_SOTIENDATHU="BH_SOTIENDATHU";
    private static final String BH_NGUOIBAN="BH_NGUOIBAN";
    private static final String BH_Uname="BH_Uname";
    private static final String BH_CREATE_AT="BH_CREATE_AT";
    private static final String BH_MODIFY_AT="BH_MODIFY_AT";
    private static final String BH_DELETE_AT="BH_MODYFY_AT";
    //column chi tiet ban hang
    private static final String CTBH_ID="CTBH_ID";
    private static final String CTBH_BANHANGID="CTBH_BANHANGID";
    private static final String CTBH_HANGHOAID="CTBH_HANGHOAID";
    private static final String CTBH_SLBAN="CTBH_SLBAN";
    private static final String CTBH_DONGIANHAP="CTBH_DONGIANHAP";
    private static final String CTBH_DONGIABAN="CTBH_DONGIABAN";
    private static final String CTBH_DVT_BAN="CTBH_DVT_BAN";
    private static final String CTBH_CREATE_AT="CTBH_CREATE_AT";
    private static final String CTBH_MODIFY_AT="CTBH_MODIFY_AT";
    private static final String CTBH_DELETE_AT="CTBH_MODYFY_AT";
    //database
    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE = "Users";
    private static final String DATABASE_TABLE_HH="HANG_HOA";
    private static final String DATABASE_TABLE_KH="KHACH_HANG";
    private static final String DATABASE_TABLE_BH="BAN_HANG";
    private static final String DATABASE_TABLE_CTBH="CHI_TIET_BAN_HANG";
    private static final int DATABASE_VERSION = 1;
    //table KHACH HANG
    private static final String CREATE_TABLE_KH="CREATE TABLE "+DATABASE_TABLE_KH
            +"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KHNCC_ID+" INTEGER NOT NULL,"+KHNCC_LOAIKHACHHANG+" INTEGER NOT NULL,"+KHNCC_MAKHACHHANG+" TEXT,"+
            KHNCC_TEN+" TEXT,"
            +KHNCC_DIACHI+" TEXT,"+KHNCC_SDT+" TEXT,"+TP_TEN+" TEXT,"+KHNCC_QUANHUYEN+" TEXT,"+
            PX_TEN+" TEXT,"+KHNCC_CREATE_AT+" TEXT,"+KHNCC_MODIFY_AT+" TEXT"+")";
    //table HANG HOA
    private static final String CREATE_TABLE_HH="CREATE TABLE "+DATABASE_TABLE_HH
            +"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ID_HANGHOA+" INTEGER NOT NULL,"+LHH_ID+" INTEGER NOT NULL,"+DVT_ID+" INTEGER NOT NULL,"+
            TEN_HANG+" TEXT NULL,"
            +MAHANGHOA+" TEXT,"+QUYCACH+" TEXT NULL,"+DONGIABAN+" DOUBLE NULL,"+MAU+" TEXT NULL,"+
            HH_DVT_CD_ID+" TEXT NULL,"+SL_CD+" DOUBLE NULL,"+KEY_CREATED_AT+" TIMESTAMP NULL,"+KEY_MODIFIED_AT+" TIMESTAMP NULL"+")";
    //table user
    private static final String CREATE_TABLE_USER="CREATE TABLE "+DATABASE_TABLE
            +"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_USERNAME+" TEXT,"+KEY_PASS+" TEXT,"+
            KEY_STATUS+" INTEGER,"
            +KEY_CREATED_AT+" TIMESTAMP,"+KEY_MODIFIED_AT+" TIMESTAMP,"+KEY_DELETED_AT+" TEXT"+")";
    //table banhang
    private static final String CREATE_TABLE_BH="CREATE TABLE "+DATABASE_TABLE_BH
            +"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+BH_ID+" INTEGER,"+BH_KHACHHANG+" INTEGER,"+BH_TUKHOID+" INTEGER,"+
            BH_MASOPHIEU+" TEXT,"
            +BH_NGAYBAN+" DATETIME,"+BH_SOTIENPHAITHU+" DOUBLE,"+BH_SOTIENDATHU+" DOUBLE,"+BH_NGUOIBAN+" TEXT,"+
            BH_Uname+" TEXT,"+BH_CREATE_AT+" TEXT,"+BH_MODIFY_AT+" TEXT,"+BH_DELETE_AT+" TEXT"+")";
    //table chi tiet ban hang
    private static final String CREATE_TABLE_CTBH="CREATE TABLE "+DATABASE_TABLE_CTBH
            +"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+CTBH_ID+" INTEGER,"+CTBH_BANHANGID+" INTEGER,"+CTBH_HANGHOAID+" INTEGER,"+
            CTBH_SLBAN+" INTEGER,"
            +CTBH_DONGIANHAP+" DOUBLE,"+CTBH_DONGIABAN+" DOUBLE,"+CTBH_DVT_BAN+" INTEGER,"+
            CTBH_CREATE_AT+" TEXT,"+CTBH_MODIFY_AT+" TEXT,"+CTBH_DELETE_AT+" TEXT"+")";
    //tao database
    public TaoDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    //tao bảng
    public void onCreate(SQLiteDatabase db){
        //String CREATETABLE="CREATE TABLE IF NOT EXISTS "+DATABASE_TABLE+"("+KEY_USERNAME+"TEXT PRIMARY KEY"+KEY_PASS+"TEXT"+KEY_TEN+"TEXT"+KEY_BOPHAN+"TEXT"+KEY_TYPE+"TEXT";
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_HH);
        db.execSQL(CREATE_TABLE_KH);
        db.execSQL(CREATE_TABLE_BH);
        db.execSQL(CREATE_TABLE_CTBH);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_HH);
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_KH);
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_BH);
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE_CTBH);
        onCreate(db);
    }
    public void addUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASS, user.getPassword());
        values.put(KEY_STATUS, user.getStatus());
        values.put(KEY_CREATED_AT, user.getCreate_at());
        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }
    public int onUpdate(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        //values.put(KEY_ID,user.getId());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASS, user.getPassword());
        values.put(KEY_STATUS, user.getStatus());
        values.put(KEY_CREATED_AT, user.getCreate_at());
        return db.update(DATABASE_TABLE,values,KEY_ID+ " =? AND "+KEY_CREATED_AT + "<= ?",new
        String[]{String.valueOf(user.getId()),String.valueOf(user.getCreate_at()) });
    }
    public List<User> getAllContacts() {
        List<User> contactList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setStatus(cursor.getInt(3));
                user.setCreate_at(cursor.getString(4));
                // Adding contact to list
                contactList.add(user);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public List<TableHanghoa> getAllContacts1() {
        List<TableHanghoa> contactList = new ArrayList<TableHanghoa>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE_HH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TableHanghoa tableHanghoa=new TableHanghoa();
                tableHanghoa.setId_hh(Integer.parseInt(cursor.getString(0)));
                tableHanghoa.setId_loaihang(Integer.parseInt(cursor.getString(1)));
                tableHanghoa.setDvt_id(Integer.parseInt(cursor.getString(2)));
                tableHanghoa.setTenhang(cursor.getString(3));
                tableHanghoa.setMahanghoa(cursor.getString(4));
                tableHanghoa.setModified_at(cursor.getString(12));
                // Adding contact to list
                contactList.add(tableHanghoa);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public List<TableKhachhang> getAllContacts2() {
        List<TableKhachhang> contactList = new ArrayList<TableKhachhang>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE_KH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TableKhachhang tableKhachhang=new TableKhachhang();
                tableKhachhang.setId_kh(Integer.parseInt(cursor.getString(1)));
                tableKhachhang.setId_loaikh(cursor.getString(2));
                tableKhachhang.setMa_kh(cursor.getString(3));
                tableKhachhang.setTen_kh(cursor.getString(4));
                tableKhachhang.setModified_at(cursor.getString(11));
                // Adding contact to list
                contactList.add(tableKhachhang);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }
    public boolean getLogin(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] columns={KEY_ID,KEY_USERNAME,KEY_PASS,KEY_STATUS,KEY_CREATED_AT};
        String selection=KEY_USERNAME+"=? AND "+KEY_PASS+"=?";
        String[] select={user.getUsername(),user.getPassword()};
        //String select="SELECT * FROM "+DATABASE_TABLE+" WHERE "+KEY_USERNAME+"="+user.getUsername()
          //      +" AND "+KEY_PASS+"="+user.getPassword();
        Cursor c=null;
        try {
            c =db.query(DATABASE_TABLE,columns,selection,select,null,null,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        int row=c.getCount();
        if(row<=0){
            //c.moveToFirst();
            return false;
        }
        return true;
    }
    ///////////////////////////////////BANG HANG HOA///////////////////////////////////
    public void addHH(TableHanghoa tableHanghoa){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(ID_HANGHOA,tableHanghoa.getId_hh());
        values.put(LHH_ID,tableHanghoa.getId_loaihang());
        values.put(DVT_ID,tableHanghoa.getDvt_id());
        values.put(TEN_HANG, tableHanghoa.getTenhang());
        values.put(MAHANGHOA,tableHanghoa.getMahanghoa());
        values.put(QUYCACH,tableHanghoa.getQuycach());
        values.put(DONGIABAN,tableHanghoa.getDongiaban());
        values.put(MAU,tableHanghoa.getMau());
        values.put(HH_DVT_CD_ID,tableHanghoa.getHh_dvt_cd_id());
        values.put(SL_CD,tableHanghoa.getSl_cd());
        values.put(KEY_CREATED_AT,tableHanghoa.getCreate_at());
        values.put(KEY_MODIFIED_AT,tableHanghoa.getModified_at());
        db.insert(DATABASE_TABLE_HH, null, values);
        db.close();
    }
    public int onUpdateHH(TableHanghoa tableHanghoa){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        //values.put(ID_HANGHOA,tableHanghoa.getId_hh());
        values.put(LHH_ID,tableHanghoa.getId_loaihang());
        values.put(DVT_ID,tableHanghoa.getDvt_id());
        values.put(TEN_HANG,tableHanghoa.getTenhang());
        values.put(MAHANGHOA,tableHanghoa.getMahanghoa());
        values.put(QUYCACH,tableHanghoa.getQuycach());
        values.put(DONGIABAN,tableHanghoa.getDongiaban());
        values.put(MAU,tableHanghoa.getMau());
        values.put(HH_DVT_CD_ID,tableHanghoa.getHh_dvt_cd_id());
        values.put(SL_CD,tableHanghoa.getSl_cd());
        values.put(KEY_CREATED_AT, tableHanghoa.getCreate_at());
        values.put(KEY_MODIFIED_AT, tableHanghoa.getModified_at());
        return db.update(DATABASE_TABLE_HH,values,ID_HANGHOA+ "= ? AND "+KEY_MODIFIED_AT+ "< ?",new
                String[]{String.valueOf(tableHanghoa.getId_hh()),String.valueOf(tableHanghoa.getModified_at())});
    }
    public boolean SynchInsertHH(TableHanghoa tableHanghoa){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] columns={ID_HANGHOA};
        String selection=ID_HANGHOA+"= ?";
        String[] select={String.valueOf(tableHanghoa.getId_hh())};
        //String select="SELECT * FROM "+DATABASE_TABLE+" WHERE "+KEY_USERNAME+"="+user.getUsername()
        //      +" AND "+KEY_PASS+"="+user.getPassword();
        Cursor c=null;
        try {
            c =db.query(DATABASE_TABLE_HH,columns,selection,select,null,null,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        int row=c.getCount();
        if(row<=0){
            //c.moveToFirst();
            return false;
        }
        return true;
    }
    public void addKH(TableKhachhang tableKhachhang){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KHNCC_ID,tableKhachhang.getId_kh());
        values.put(KHNCC_LOAIKHACHHANG,tableKhachhang.getId_loaikh());
        values.put(KHNCC_MAKHACHHANG,tableKhachhang.getMa_kh());
        values.put(KHNCC_TEN,tableKhachhang.getTen_kh());
        values.put(KHNCC_DIACHI,tableKhachhang.getDiachi());
        values.put(KHNCC_SDT,tableKhachhang.getSdt());
        values.put(TP_TEN,tableKhachhang.getTen_tp());
        values.put(KHNCC_QUANHUYEN,tableKhachhang.getTen_quanhuyen());
        values.put(PX_TEN,tableKhachhang.getTen_px());
        values.put(KHNCC_CREATE_AT,tableKhachhang.getCreated_at());
        values.put(KHNCC_MODIFY_AT,tableKhachhang.getModified_at());
        db.insert(DATABASE_TABLE_KH, null, values);
        db.close();
    }
    public int UpdateKH(TableKhachhang tableKhachhang){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        //values.put(KHNCC_ID,tableKhachhang.getId_kh());
        values.put(KHNCC_LOAIKHACHHANG,tableKhachhang.getId_loaikh());
        values.put(KHNCC_MAKHACHHANG,tableKhachhang.getMa_kh());
        values.put(KHNCC_TEN,tableKhachhang.getTen_kh());
        values.put(KHNCC_DIACHI,tableKhachhang.getDiachi());
        values.put(KHNCC_SDT,tableKhachhang.getSdt());
        values.put(TP_TEN,tableKhachhang.getTen_tp());
        values.put(KHNCC_QUANHUYEN,tableKhachhang.getTen_quanhuyen());
        values.put(PX_TEN,tableKhachhang.getTen_px());
        values.put(KHNCC_CREATE_AT, tableKhachhang.getCreated_at());
        values.put(KHNCC_MODIFY_AT, tableKhachhang.getModified_at());
        return db.update(DATABASE_TABLE_KH,values,KHNCC_ID+ "= ? AND "+KHNCC_MODIFY_AT+ "< ?",new
                String[]{String.valueOf(tableKhachhang.getId_kh()),String.valueOf(tableKhachhang.getModified_at())});
    }
    public boolean SynchInsertKH(TableKhachhang tableKhachhang){
        SQLiteDatabase db=this.getWritableDatabase();
        String[] columns={KHNCC_ID};
        String selection=KHNCC_ID+"= ?";
        String[] select={String.valueOf(tableKhachhang.getId_kh())};
        //String select="SELECT * FROM "+DATABASE_TABLE+" WHERE "+KEY_USERNAME+"="+user.getUsername()
        //      +" AND "+KEY_PASS+"="+user.getPassword();
        Cursor c=null;
        try {
            c =db.query(DATABASE_TABLE_KH,columns,selection,select,null,null,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        int row=c.getCount();
        if(row<=0){
            //c.moveToFirst();
            return false;
        }
        return true;
    }
    public ArrayList<HashMap<String,String>> getKh(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+DATABASE_TABLE_KH;
        ArrayList<HashMap<String,String>> Khlist=new ArrayList<HashMap<String, String>>();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> temp=new HashMap<String,String>();
                temp.put("ID_KHACHHANG",cursor.getString(cursor.getColumnIndex(KHNCC_ID)));
                temp.put("MA_KHACHHANG","MÃ KHÁCH HÀNG: "+cursor.getString(cursor.getColumnIndex(KHNCC_MAKHACHHANG)));
                temp.put("TEN_KHACHHANG","TÊN KHÁCH HÀNG: "+cursor.getString(cursor.getColumnIndex(KHNCC_TEN)));
                Khlist.add(temp);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Khlist;
    }
    public void addBH(TableBanhang tableBanhang){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(BH_ID,tableBanhang.getId_bh());
        values.put(BH_KHACHHANG,tableBanhang.getId_kh());
        values.put(BH_TUKHOID,tableBanhang.getTukho_id());
        values.put(BH_MASOPHIEU,tableBanhang.getBh_msp());
        values.put(BH_NGAYBAN,tableBanhang.getBh_ngayban());
        values.put(BH_SOTIENPHAITHU,tableBanhang.getBh_stpt());
        values.put(BH_SOTIENDATHU,tableBanhang.getBh_stdt());
        values.put(BH_NGUOIBAN,tableBanhang.getBh_nguoiban());
        values.put(BH_Uname,tableBanhang.getBh_usname());
        values.put(BH_CREATE_AT,tableBanhang.getBh_create());
        values.put(BH_MODIFY_AT,tableBanhang.getBh_modify());
        db.insert(DATABASE_TABLE_BH, null, values);
        db.close();
    }
    public List<TableBanhang> getAllContacts3() {
        List<TableBanhang> contactList = new ArrayList<TableBanhang>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE_BH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TableBanhang tableBanhang=new TableBanhang();
                tableBanhang.setId_bh(Integer.parseInt(cursor.getString(1)));
                tableBanhang.setId_kh(Integer.parseInt(cursor.getString(2)));
                tableBanhang.setTukho_id(Integer.parseInt(cursor.getString(3)));
                tableBanhang.setBh_msp(cursor.getString(4));
                // Adding contact to list
                contactList.add(tableBanhang);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }
    public ArrayList<HashMap<String,String>> getidhh() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DATABASE_TABLE_HH;
        ArrayList<HashMap<String, String>> Idhhlist = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> temp = new HashMap<String, String>();
                temp.put("ID_HANGHOA", cursor.getString(cursor.getColumnIndex(ID_HANGHOA)));
                temp.put("TEN_HANGHOA",cursor.getString(cursor.getColumnIndex(TEN_HANG)));
                temp.put("LHH_ID",cursor.getString(cursor.getColumnIndex(LHH_ID)));
                temp.put("MAHANGHOA", cursor.getString(cursor.getColumnIndex(MAHANGHOA)));
                temp.put("DVT", cursor.getString(cursor.getColumnIndex(DVT_ID)));
                temp.put("SL", cursor.getString(cursor.getColumnIndex(SL_CD)));
                temp.put("DONGIA", cursor.getString(cursor.getColumnIndex(DONGIABAN)));
                Idhhlist.add(temp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Idhhlist;
    }
    public String getMaxMSP(){
        String msp=null;
        String msp1=null;
        int a;
        String kq=null;
        SQLiteDatabase db=this.getWritableDatabase();
        String query= "SELECT * FROM "+DATABASE_TABLE_BH+" WHERE "+KEY_ID+"=(SELECT MAX("+KEY_ID+") FROM "+DATABASE_TABLE_BH+")";
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {
                TableBanhang tableBanhang = new TableBanhang();
                msp = cursor.getString(cursor.getColumnIndex(BH_MASOPHIEU));

            }while (cursor.moveToNext());
            msp1=msp.substring(3);
            a=Integer.parseInt(msp1)+1;
            if(msp.substring(3,4).equals("0")){
                kq="15-0"+a;
            }
            else {
                kq="15-"+a;
            }
        }
        cursor.close();
        db.close();
        return kq;
    }
    public void addBH_CTBH_Offline(Table_chitietbanhang table_chitietbanhang){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        //values.put(CTBH_ID, table_chitietbanhang.getCtbh_id());
        //values.put(CTBH_BANHANGID, table_chitietbanhang.getCtbh_bh_id());
        values.put(CTBH_HANGHOAID, table_chitietbanhang.getCtbh_hh_id());
        values.put(CTBH_SLBAN,table_chitietbanhang.getCtbh_slban());
        values.put(CTBH_DONGIANHAP,table_chitietbanhang.getCtbh_dongianhap());
        values.put(CTBH_DONGIABAN,table_chitietbanhang.getCtbh_dongiaban());
        values.put(CTBH_DVT_BAN,table_chitietbanhang.getCtbh_dvt());
        values.put(CTBH_CREATE_AT,table_chitietbanhang.getCtbh_create_at());
        values.put(CTBH_MODIFY_AT,table_chitietbanhang.getCtbh_modify_at());
        db.insert(DATABASE_TABLE_CTBH, null, values);
        db.close();
    }
    public void addBH_Offline(TableBanhang tableBanhang){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        //values.put(BH_ID,tableBanhang.getId_bh());
        values.put(BH_KHACHHANG,tableBanhang.getId_kh());
        values.put(BH_TUKHOID,tableBanhang.getTukho_id());
        values.put(BH_MASOPHIEU,tableBanhang.getBh_msp());
        values.put(BH_NGAYBAN,tableBanhang.getBh_ngayban());
        values.put(BH_SOTIENPHAITHU,tableBanhang.getBh_stpt());
        values.put(BH_SOTIENDATHU,tableBanhang.getBh_stdt());
        values.put(BH_NGUOIBAN,tableBanhang.getBh_nguoiban());
        values.put(BH_Uname,tableBanhang.getBh_usname());
        values.put(BH_CREATE_AT,tableBanhang.getBh_create());
        values.put(BH_MODIFY_AT,tableBanhang.getBh_modify());
        db.insert(DATABASE_TABLE_BH, null, values);
        db.close();
    }
}
