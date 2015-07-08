package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 6/23/2015.
 */
public class TableKhachhang {
    int id_kh;
    String id_loaikh;
    String ma_kh;
    String ten_kh;
    String diachi;
    String sdt;
    String ten_tp;
    String ten_quanhuyen;
    String ten_px;
    String created_at;
    String modified_at;
    String deleted_at;

    public TableKhachhang(){

    }
    public TableKhachhang(int id_kh){
        this.id_kh=id_kh;
    }
    public TableKhachhang(int id_kh,String id_loaikh,String ma_kh,String ten_kh,String diachi,String sdt,String ten_tp,String ten_quanhuyen,String ten_px,String created_at,String modified_at){
        this.id_kh=id_kh;
        this.id_loaikh=id_loaikh;
        this.ma_kh=ma_kh;
        this.ten_kh=ten_kh;
        this.diachi=diachi;
        this.sdt=sdt;
        this.ten_tp=ten_tp;
        this.ten_quanhuyen=ten_quanhuyen;
        this.ten_px=ten_px;
        this.created_at=created_at;
        this.modified_at=modified_at;
    }
    //set hang hoa
    public void setId_kh(int id_kh){
        this.id_kh=id_kh;
    }
    public void setId_loaikh(String id_loaikh){
        this.id_loaikh=id_loaikh;
    }
    public void setMa_kh(String ma_kh){
        this.ma_kh=ma_kh;
    }
    public void setTen_kh(String ten_kh){
        this.ten_kh=ten_kh;
    }
    public void setDiachi(String diachi){
        this.diachi=diachi;
    }
    public void setSdt(String sdt){
        this.sdt=sdt;
    }
    public void setTen_tp(String ten_tp){
        this.ten_tp=ten_tp;
    }
    public void setTen_quanhuyen(String ten_quanhuyen){
        this.ten_quanhuyen=ten_quanhuyen;
    }
    public void setTen_px(String ten_px){
        this.ten_px=ten_px;
    }
    public void setCreated_at(String created_at){
        this.created_at=created_at;
    }
    public void setModified_at(String modified_at){
        this.modified_at=modified_at;
    }
    public void setDeleted_at(String deleted_at){
        this.deleted_at=deleted_at;
    }

    //get doi tuong
    public int getId_kh(){
        return this.id_kh;
    }
    public String getId_loaikh(){return this.id_loaikh;}
    public String getMa_kh(){return this.ma_kh;}
    public String getTen_kh(){return this.ten_kh;}
    public String getDiachi(){return this.diachi;}
    public String getSdt(){return this.sdt;}
    public String getTen_tp(){return this.ten_tp;}
    public String getTen_quanhuyen(){return this.ten_quanhuyen;}
    public String getTen_px(){return this.ten_px;}
    public String getCreated_at(){return this.created_at;}
    public String getModified_at(){return this.modified_at;}
    public String getDeleted_at(){return this.deleted_at;}
}
