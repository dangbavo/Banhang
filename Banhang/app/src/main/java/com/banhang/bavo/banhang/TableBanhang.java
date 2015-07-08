package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 6/25/2015.
 */
public class TableBanhang {
    int id_bh;
    int id_kh;
    int tukho_id;
    String bh_msp;
    String bh_ngayban;
    double bh_stpt;
    double bh_stdt;
    String bh_nguoiban;
    String bh_usname;
    String bh_create;
    String bh_modify;

    public TableBanhang(){

    }
    public TableBanhang(int id_bh,int id_kh,int tukho_id,String bh_msp,String bh_ngayban,double bh_stpt,double bh_stdt,String bh_nguoiban,String bh_usname,String bh_create,String bh_modify){
        this.id_bh=id_bh;
        this.id_kh=id_kh;
        this.tukho_id=tukho_id;
        this.bh_msp=bh_msp;
        this.bh_ngayban=bh_ngayban;
        this.bh_stpt=bh_stpt;
        this.bh_stdt=bh_stdt;
        this.bh_nguoiban=bh_nguoiban;
        this.bh_usname=bh_usname;
        this.bh_create=bh_create;
        this.bh_modify=bh_modify;
    }
    public TableBanhang(int id_kh,int tukho_id,String bh_msp,String bh_ngayban,double bh_stpt,double bh_stdt,String bh_nguoiban,String bh_usname,String bh_create,String bh_modify){
        this.id_kh=id_kh;
        this.tukho_id=tukho_id;
        this.bh_msp=bh_msp;
        this.bh_ngayban=bh_ngayban;
        this.bh_stpt=bh_stpt;
        this.bh_stdt=bh_stdt;
        this.bh_nguoiban=bh_nguoiban;
        this.bh_usname=bh_usname;
        this.bh_create=bh_create;
        this.bh_modify=bh_modify;
    }
    public void setId_bh(int id_bh){this.id_bh=id_bh;}
    public void setId_kh(int id_kh){this.id_kh=id_kh;}
    public void setTukho_id(int tukho_id){this.tukho_id=tukho_id;}
    public void setBh_msp(String bh_msp){this.bh_msp=bh_msp;}
    public void setBh_ngayban(String bh_ngayban){this.bh_ngayban=bh_ngayban;}
    public void setBh_stpt(double bh_stpt){this.bh_stpt=bh_stpt;}
    public void setBh_stdt(double bh_stdt){this.bh_stdt=bh_stdt;}
    public void setBh_nguoiban(String bh_nguoiban){this.bh_nguoiban=bh_nguoiban;}
    public void setBh_usname(String bh_usname){this.bh_usname=bh_usname;}
    public void setBh_create(String bh_create){this.bh_create=bh_create;}
    public void setBh_modify(String bh_modify){this.bh_modify=bh_modify;}
    //get
    public int getId_bh(){return this.id_bh;}
    public int getId_kh(){return this.id_kh;}
    public int getTukho_id(){return this.tukho_id;}
    public String getBh_msp(){return this.bh_msp;}
    public String getBh_ngayban(){return this.bh_ngayban;}
    public double getBh_stpt(){return this.bh_stpt;}
    public double getBh_stdt(){return this.bh_stdt;}
    public String getBh_nguoiban(){return this.bh_nguoiban;}
    public String getBh_usname(){return this.bh_usname;}
    public String getBh_create(){return this.bh_create;}
    public String getBh_modify(){return this.bh_modify;}


}
