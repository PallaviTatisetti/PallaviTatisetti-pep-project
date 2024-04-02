package Controller;

import java.util.*;
import Model.Account;
import Model.Message;

//import org.eclipse.jetty.http.MetaData.ConnectRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Service.MessageService;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::accountRegisterHandler);
        app.post("/login", this::accountLoginHandler);
        app.post("/messages",this::createMessageHandler);
        app.get("/messages",this::getAllMessagesHandler);
        app.get("/messages/{message_id}",this::getMessageByIdHandler);
        app.delete("/messages/{message_id}",this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByAccountIdhandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void accountRegisterHandler(Context ctx) throws Exception {
        ObjectMapper map = new ObjectMapper();
        Account account = map.readValue(ctx.body(), Account.class);
        try{
            Account register = accountService.accountRegister(account);
            if(register!=null){
                ctx.json(map.writeValueAsString(register));
                ctx.status(200);
            }else{
                ctx.status(400);
            }
        }catch(Exception e){
            e.printStackTrace();
            ctx.status(400);
        
        }
        
    }

    private void accountLoginHandler(Context ctx) throws Exception {
        ObjectMapper map = new ObjectMapper();
        Account account = map.readValue(ctx.body(), Account.class);
        Account login = accountService.accountLogin(account);
        if(login!=null){
            ctx.json(map.writeValueAsString(login));
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }



    private void createMessageHandler(Context ctx) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message messageCreated = MessageService.messageCreate(message);
        if(messageCreated!=null){
            ctx.json(mapper.writeValueAsString(messageCreated));
            ctx.status(200);
        }else{
            ctx.status(400);
        }

    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages(null);
        ctx.json(messages);
        ctx.status(200);
    }
    
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
        System.out.println("!");
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        if(messageService.getMessageById(message_id) != null){
            try{
                Message message = messageService.getMessageById(message_id);
                ctx.json(message);
                ctx.status(200);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
       
    }


    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        if(messageService.getMessageById(message_id) != null){
            try{
         Message deletedMessage = MessageService.deleteMessageById(message_id);
            ctx.json(deletedMessage);
            ctx.status(200);
            }catch(Exception e){
                e.printStackTrace();
                ctx.status(200);
            }
        }else{
            ctx.status(200);
        }
    }

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        String newMessageText = message.message_text;
        if(newMessageText.length()>0 && newMessageText.length()<=255){
        
        try {
            MessageService.updateMessage(message_id, newMessageText);
            ctx.status(400);
            ctx.json(MessageService.getMessage(message_id));
            ctx.status(400);
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(400);
        }
    }else{
        ctx.status(400);
    }
    }
    private void getMessageByAccountIdhandler(Context ctx) throws Exception {
        int account_id=Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessageByAccountId(account_id);
        ctx.json(messages);
        ctx.status(200); 
    }

}