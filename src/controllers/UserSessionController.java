package controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import db.DaoHelper;
import entities.User;
import entities.dictionary.LoginResult;
import entities.dictionary.UserRole;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserSessionController {

    private static UserSessionController ourInstance = new UserSessionController();

    public static UserSessionController getInstance() {
        return ourInstance;
    }

    private UserSessionController() {
        user = null;
    }


    private User user;
    private Dao<User, String> dao = DaoHelper.getInstance().USER_DAO;

    public void createUser(String name, String password, UserRole role) throws SQLException, NoSuchAlgorithmException {
        byte[] bytePassword = password.getBytes();
        byte[] byteMd5Password = MessageDigest.getInstance("MD5").digest(bytePassword);
        BigInteger bigInt = new BigInteger(1, byteMd5Password);
        String hashtext = bigInt.toString(16);
        dao.create(new User(name, hashtext, role));
    }

    public LoginResult login(String userName, String password) throws SQLException, NoSuchAlgorithmException {
        QueryBuilder<User, String> queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq("name", userName);
        User user = dao.iterator(queryBuilder.prepare()).next();
        if (user == null){
            return LoginResult.USER_NOT_FOUND;
        }
        byte[] bytePassword = password.getBytes();
        byte[] byteMd5Password = MessageDigest.getInstance("MD5").digest(bytePassword);
        BigInteger bigInt = new BigInteger(1, byteMd5Password);
        String hashtext = bigInt.toString(16);
        if (user.getPassword().equals(hashtext)){
            this.user = user;
            return LoginResult.SUCCESS;
        }else {
            return LoginResult.WRONG_PASSWORD;
        }

    }



}
