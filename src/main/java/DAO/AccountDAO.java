package DAO;
import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    // USER REGISTRATION

    public Account getRegister(Account account) {
        if(account.password.length()>=4 && account.username.length()>0){
        Connection con = ConnectionUtil.getConnection();
        try{
            //SQL logic
            String query = "INSERT INTO account(username, password) VALUES (?,?);";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            //preparedStatement's setString and setInt methods
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            ResultSet pKeyResultSet = ps.getGeneratedKeys();
            if(pKeyResultSet.next()){
                int generated_account_id = (int) pKeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

        }catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        return null;
    
}

    // USER LOGIN

    public Account getLogin(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            // SQL logic
            String query = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);

           
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();

            // Valid user
        if (!rs.next()) {
            return null;
        }
        int accountId = rs.getInt("account_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        System.out.println(accountId);
        System.out.println(username);

        return new Account(accountId, username,  password);

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
