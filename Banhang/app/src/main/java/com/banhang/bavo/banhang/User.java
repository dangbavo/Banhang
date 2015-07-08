package com.banhang.bavo.banhang;

/**
 * Created by Ba Vo on 5/31/2015.
 */
public class User {
    int id;
    String username;
    String password;
    int status;
    String create_at;
    public User(){

    }
    public User(String username,String password){
        this.username=username;
        this.password=password;
    }
    public User(int id,String username,String password,int status,String create_at){
        this.id=id;
        this.username=username;
        this.password=password;
        this.status=status;
        this.create_at=create_at;
    }
    public User(String username,String password,int status,String create_at){
        this.username=username;
        this.password=password;
        this.status=status;
        this.create_at=create_at;
    }
    //set user
    public void setId(int id){
        this.id=id;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setStatus(int status){
        this.status=status;
    }
    public void setCreate_at(String create_at){
        this.create_at=create_at;
    }
    //get
    public long getId(){
        return this.id;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public int getStatus(){
        return this.status;
    }
    public String getCreate_at(){
        return this.create_at;
    }
}
