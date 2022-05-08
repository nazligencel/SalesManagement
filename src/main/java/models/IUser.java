package models;

import props.User;

public interface IUser
{
    int userInsert(User user);
    int userUpdate(User user);
    int userUpdateVerification(String date,String code, String email);
    boolean userLogin(String email,String password);
    //String userSingle(String email);
    boolean userGetEmail(String email);
    String userGetVerification();


    int changeUserPassword(String password);





}
