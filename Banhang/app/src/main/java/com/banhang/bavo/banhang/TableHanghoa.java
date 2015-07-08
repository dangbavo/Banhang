package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 6/13/2015.
 */
public class TableHanghoa {
    int id_hh;
    int id_loaihang;
    int dvt_id;
    String tenhang;
    String mahanghoa;
    String quycach;
    String dongiaban;
    String mau;
    String hh_dvt_cd_id;
    String sl_cd;
    String created_at;
    String modified_at;
    String deleted_at;

    public TableHanghoa(){

    }
    public TableHanghoa(int id_hh){
        this.id_hh=id_hh;
    }
    public TableHanghoa(int id_hh,int id_loaihang,int dvt_id,String tenhang){
        this.id_hh=id_hh;
        this.id_loaihang=id_loaihang;
        this.dvt_id=dvt_id;
        this.tenhang=tenhang;
    }
    public TableHanghoa(int id_hh,int id_loaihang,int dvt_id,String tenhang,String mahanghoa,String quycach,String dongiaban,String mau,String hh_dvt_cd_id,String sl_cd,String created_at,String modified_at){
        this.id_hh=id_hh;
        this.id_loaihang=id_loaihang;
        this.dvt_id=dvt_id;
        this.tenhang=tenhang;
        this.mahanghoa=mahanghoa;
        this.quycach=quycach;
        this.dongiaban=dongiaban;
        this.mau=mau;
        this.hh_dvt_cd_id=hh_dvt_cd_id;
        this.sl_cd=sl_cd;
        this.created_at=created_at;
        this.modified_at=modified_at;
    }
    //set hang hoa
    public void setId_hh(int id_hh){
        this.id_hh=id_hh;
    }
    public void setId_loaihang(int id_loaihang){
        this.id_loaihang=id_loaihang;
    }
    public void setDvt_id(int dvt_id){
        this.dvt_id=dvt_id;
    }
    public void setTenhang(String tenhang){
        this.tenhang=tenhang;
    }
    public void setMahanghoa(String mahanghoa){
        this.mahanghoa=mahanghoa;
    }
    public void setQuycach(String quycach){
        this.quycach=quycach;
    }
    public void setDongiaban(String dongiaban){
        this.dongiaban=dongiaban;
    }
    public void setMau(String mau){
        this.mau=mau;
    }
    public void setHh_dvt_cd_id(String hh_dvt_cd_id){
        this.hh_dvt_cd_id=hh_dvt_cd_id;
    }
    public void setSl_cd(String sl_cd){
        this.sl_cd=sl_cd;
    }
    public void setCreate_at(String created_at){
        this.created_at=created_at;
    }
    public void setModified_at(String modified_at){this.modified_at=modified_at;}
    public void setDeleted_at(String deleted_at){this.deleted_at=deleted_at;}
    //get doi tuong
    public int getId_hh(){
        return this.id_hh;
    }
    public int getId_loaihang(){
        return this.id_loaihang;
    }
    public int getDvt_id(){
        return this.dvt_id;
    }
    public String getTenhang(){
        return this.tenhang;
    }
    public String getMahanghoa(){
        return this.mahanghoa;
    }
    public String getQuycach(){
        return this.quycach;
    }
    public String getDongiaban(){
        return this.dongiaban;
    }
    public String getMau(){
        return this.mau;
    }
    public String getHh_dvt_cd_id(){
        return this.hh_dvt_cd_id;
    }
    public String getSl_cd(){
        return this.sl_cd;
    }
    public String getCreate_at(){
        return this.created_at;
    }
    public String getModified_at(){
        return this.modified_at;
    }
    public String getDeleted_at(){
        return this.deleted_at;
    }
}
