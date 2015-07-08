package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 6/30/2015.
 */
public class Table_chitietbanhang {
    int ctbh_id;
    int ctbh_bh_id;
    int ctbh_hh_id;
    int ctbh_slban;
    double ctbh_dongianhap;
    double ctbh_dongiaban;
    int ctbh_dvt;
    String ctbh_create_at;
    String ctbh_modify_at;
    public Table_chitietbanhang(){

    }
    public Table_chitietbanhang(int ctbh_id,int ctbh_bh_id,int ctbh_hh_id,int ctbh_slban,double ctbh_dongianhap,double ctbh_dongiaban,int ctbh_dvt,String ctbh_create_at,String ctbh_modify_at){
        this.ctbh_id=ctbh_id;
        this.ctbh_bh_id=ctbh_bh_id;
        this.ctbh_hh_id=ctbh_hh_id;
        this.ctbh_slban=ctbh_slban;
        this.ctbh_dongianhap=ctbh_dongianhap;
        this.ctbh_dongiaban=ctbh_dongiaban;
        this.ctbh_dvt=ctbh_dvt;
        this.ctbh_create_at=ctbh_create_at;
        this.ctbh_modify_at=ctbh_modify_at;
    }
    public Table_chitietbanhang(int ctbh_hh_id,int ctbh_slban,double ctbh_dongianhap,double ctbh_dongiaban,int ctbh_dvt,String ctbh_create_at,String ctbh_modify_at){
        this.ctbh_hh_id=ctbh_hh_id;
        this.ctbh_slban=ctbh_slban;
        this.ctbh_dongianhap=ctbh_dongianhap;
        this.ctbh_dongiaban=ctbh_dongiaban;
        this.ctbh_dvt=ctbh_dvt;
        this.ctbh_create_at=ctbh_create_at;
        this.ctbh_modify_at=ctbh_modify_at;
    }
    public int getCtbh_id(){return this.ctbh_id;}
    public int getCtbh_bh_id(){return this.ctbh_bh_id;}
    public int getCtbh_hh_id(){return this.ctbh_hh_id;}
    public int getCtbh_slban(){return this.ctbh_slban;}
    public double getCtbh_dongianhap(){return this.ctbh_dongianhap;}
    public double getCtbh_dongiaban(){return this.ctbh_dongiaban;}
    public int getCtbh_dvt(){return this.ctbh_dvt;}
    public String getCtbh_create_at(){return this.ctbh_create_at;}
    public String getCtbh_modify_at(){return this.ctbh_modify_at;}

}
