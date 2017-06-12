package bierbest.model;

import bierbest.model.payloads.MessageAction;
import bierbest.model.payloads.Payload;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 105L;
    public static final int SUCCESS = 1;
    public static final int INVALID = 0;
    public static final int FAILED = -1;
    public static final int DENIED = 403;
    public static final int NOT_FOUND = 404;
    public static final int NOT_SET = 999;

    public MessageAction messageAction;
    public Payload payload;
    private int responseCode;

    @Override
    public String toString() {
        return "Response{" +
                "messageAction=" + messageAction +
                ", payload=" + payload +
                ", responseCode=" + responseCode +
                '}';
    }

    public Response(MessageAction messageAction, int responseCode) {
        this.messageAction = messageAction;
        this.responseCode = responseCode;
    }

    public Response(MessageAction messageAction, Payload payload, int responseCode) {
        this.messageAction = messageAction;
        this.payload = payload;
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public Payload getPayload() {
        return payload;
    }
}
