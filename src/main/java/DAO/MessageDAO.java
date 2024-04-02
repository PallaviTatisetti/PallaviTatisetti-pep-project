package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Util.ConnectionUtil;
import Model.Message;

public class MessageDAO {
    public Message createMessage(Message message){
        int posted_by=0;
        System.out.println(message);
        if(message.message_text.length()>0 && message.message_text.length()<255 && message.posted_by>posted_by)
        {
        Connection con = ConnectionUtil.getConnection();
        try {
            String query = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int generated_message_id = (int) rs.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

        }catch(SQLException ex) {
            System.out.println(ex);
        }
    }
        return null;
    }


// GET ALL MESSAGES
public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        Connection con = ConnectionUtil.getConnection();

        try{
            String query = "SELECT * FROM message";
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery(); 

            while (rs.next()) {
                int messageId = rs.getInt("message_id");
                int postedBy = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long timePostedEpoch = rs.getLong("time_posted_epoch");

                // Creating new Message object and adding it to the list
                Message message = new Message(messageId, postedBy, messageText, timePostedEpoch);
                messages.add(message);
            }
        }catch (SQLException ex) {
            System.out.println(ex);
        }
        return messages;
    }


    //GET MESSAGE BY ID
    public Message getMessageById(int message_id){
        Connection con = ConnectionUtil.getConnection();
        try {
            String query = "SELECT * from message WHERE message_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }   

   
    // DELETE MESSAGE BY ID
    public Message deleteMessageById(int message_id){
        Connection con = ConnectionUtil.getConnection();
        //  SQL logic
        try{
        String query = "SELECT * FROM message WHERE message_id=?";
        PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, message_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Message message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                String delete_query ="DELETE FROM message WHERE message_id=?";
                    try (PreparedStatement delete = con.prepareStatement(delete_query)) {
                        delete.setInt(1, message_id);
                        delete.executeUpdate();
                        return message;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }


    //UPDATE MESSAGE 
    public Message updateMessage(int message_id, String message_text) {
        Connection con = ConnectionUtil.getConnection();
        
        if (message_text==null || message_text.length()==0 || message_text.length()>255) 
        {
            return null;
        }   
        try
        {  
            String query = "UPDATE message SET message_text=? WHERE message_id=?";
            PreparedStatement ps = con.prepareStatement(query); {
            ps.setString(1, message_text);
            ps.setInt(2, message_id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
               throw new SQLException("Message not found");
            }
         }
        }catch(SQLException ex){
            System.out.println(ex);

        }
        return null;
        
    }
    private static List<Message> messages = new ArrayList<>();

    public Message getMessage(int message_id) {
        System.out.println("@");
        for (Message message : messages) {
            System.out.println("@");
            if (message.getMessage_id() == message_id) {
                return message;
            }
        }
        return null;
    }

    public List<Message> getMessageByAccountId(int account_id) throws Exception
    {
        Connection con = ConnectionUtil.getConnection();
        List<Message> user_message=new ArrayList<>();
        try{
        String query = "SELECT * FROM message WHERE posted_by =?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1,account_id);
        try(ResultSet rs=ps.executeQuery()){
        while(rs.next())
        {
            int id = rs.getInt("message_id");
            int posted=rs.getInt("posted_by");
            String text=rs.getString("message_text");
            Long time_posted=rs.getLong("time_posted_epoch");
            Message message = new Message(id,posted,text,time_posted);
            user_message.add(message);
        }
        }
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
        return user_message;
    }

}

    
