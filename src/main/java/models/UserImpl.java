package models;

import props.User;
import utils.DB;
import utils.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserImpl implements IUser{
    DB db=new DB();
    public static  String name="";
    public static String emailaddress="";
    public static String loginEmail="";
    public static String loginPassword="";
    public static int loginUid;
    public static String verficitaionCode="";
    public  String date="";




    @Override
    public int userInsert(User user) {
        int status=0;
        DB db=new DB();
        try{
            String sql="insert into users values(null,?,?,?,?)";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,user.getName());
            pre.setString(2,user.getSurname());
            pre.setString(3,user.getEmail());
            pre.setString(4, Util.MD5(user.getPassword()));
            status= pre.executeUpdate();

        }catch (Exception ex){
            System.err.println("User Insert Error"+ex);
            // ex.printStackTrace();
        }finally {
            db.close();//a��k olan db yi kapat
        }

        return status;
    }

    @Override
    public int userUpdate(User user) {
        int status=0;

        try {

            String sql="update users set password=? where email=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);

            pre.setString(1,Util.MD5(user.getPassword()));
            pre.setString(2,user.getEmail());
            status= pre.executeUpdate();
        } catch (Exception ex) {
            System.err.println("userUpdate error"+ex);
        }finally {
            db.close();
        }


        return status;
    }

    @Override
    public int userUpdateVerification(String date,String code, String email) {

            int status=0;

            try {

                String sql="update users set verificationCode=?,date=? where email=?";
                PreparedStatement pre=db.connect().prepareStatement(sql);

                pre.setString(1,code);
                pre.setString(2,date);
                pre.setString(3,email);
                status= pre.executeUpdate();
            } catch (Exception ex) {
                System.err.println("userUpdate error"+ex);
            }finally {
                db.close();
            }


            return status;
        }




    @Override
    public boolean userLogin(String email, String password) {
        boolean status=false;
        try {
            String sql="Select *from users where email=? and password=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,email);
            pre.setString(2,Util.MD5(password));
            ResultSet rs=pre.executeQuery();
            status= rs.next();
            if(status){
                name=rs.getString("name")+" "+rs.getString("surname");
                loginUid=rs.getInt("uid");
                loginEmail=rs.getString("email");
                loginPassword=rs.getString("password");

            }


        }catch (Exception ex){
            System.err.println("userLogin Error"+ex);
        }finally {
            db.close();
        }


        return status;
    }



    @Override
    public boolean userGetEmail(String email) {
        boolean status=false;
        try {
            String sql="Select *from users where email=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,email);

            ResultSet rs=pre.executeQuery();
            status= rs.next();
            if(status) {
                emailaddress = rs.getString("email");
                date = rs.getString("date");
            }

        }catch (Exception ex){
            System.err.println("userGetEmail Error"+ex);
        }finally {
            db.close();
        }


        return status;
    }

    @Override
    public String userGetVerification() {
        String code="";
        try {
            String sql="Select verificationCode from users ";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            ResultSet rs=pre.executeQuery();
            code =rs.getString("verificationCode");


        }catch (Exception ex){
            System.err.println("userGetVerification Error"+ex);
        }finally {
            db.close();
        }


        return code;
    }

    @Override
    public int changeUserPassword(String password) {
        int status=0;

        try{
            User user= new User(loginEmail,password);
            status=userUpdate(user);
            System.out.println(status);
        }catch (Exception ex){
            System.err.println("changeUserPassword Error"+ex);
        }finally {
            db.close();
        }


        return status;
    }


}
