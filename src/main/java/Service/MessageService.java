package Service;

import java.util.*;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    private static MessageDAO messageDAO;

    public MessageService()
    {
        messageDAO =new MessageDAO();
    }

    public static MessageDAO getMessageDAO() {
            return messageDAO;
        }
    
    public static void setMessageDAO(MessageDAO messageDAO) {
        MessageService.messageDAO = messageDAO;
    }


    public static Message messageCreate(Message message) {
            return messageDAO.createMessage(message);
        }

    public List<Message> getAllMessages(Message message) {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public static Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public static void updateMessage(int message_id, String message_text) {
        messageDAO.updateMessage(message_id, message_text);
    }

    public static Object getMessage(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getMessageByAccountId(int account_id) throws Exception {
        return messageDAO.getMessageByAccountId(account_id);
    }

    public Message getallMessage(int message_id) {
        return getMessageById(message_id);
    }
    

    
    
}
