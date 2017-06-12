package bierbest.model;

import bierbest.model.payloads.MessageAction;
import bierbest.model.payloads.Payload;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 100L;
    private MessageAction messageAction;
    private Payload payload;

    private String username = "";
    private String password = "";

    public Request(String username, String password, MessageAction messageAction, Payload payload) {
        this.username = username;
        this.password = password;
        this.messageAction = messageAction;
        this.payload = payload;
    }

    public Request(MessageAction messageAction, Payload payload) {
        this.messageAction = messageAction;
        this.payload = payload;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

