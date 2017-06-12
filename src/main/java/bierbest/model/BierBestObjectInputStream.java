package bierbest.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class BierBestObjectInputStream extends ObjectInputStream {
    public BierBestObjectInputStream(InputStream in) throws IOException {
        super(in);
    }
    @Override
    protected java.io.ObjectStreamClass readClassDescriptor()
            throws IOException, ClassNotFoundException {
        ObjectStreamClass desc = super.readClassDescriptor();
        switch (desc.getName()) {
            case "bierbest.communication.Response":
                return ObjectStreamClass.lookup(bierbest.model.Response.class);
            case "bierbest.communication.payloads.MessageAction":
                return ObjectStreamClass.lookup(bierbest.model.payloads.MessageAction.class);
            case "bierbest.communication.payloads.Payload":
                return ObjectStreamClass.lookup(bierbest.model.payloads.Payload.class);
            case "bierbest.communication.payloads.ClientData":
                return ObjectStreamClass.lookup(bierbest.model.payloads.ClientData.class);
            case "bierbest.communication.payloads.OrderData":
                return ObjectStreamClass.lookup(bierbest.model.payloads.OrderData.class);
            case "bierbest.communication.payloads.Orders":
                return ObjectStreamClass.lookup(bierbest.model.payloads.Orders.class);
            case "bierbest.client.ClientModel":
                return ObjectStreamClass.lookup(bierbest.model.ClientModel.class);
            case "bierbest.order.OrderModel":
                return ObjectStreamClass.lookup(bierbest.model.OrderModel.class);
        }
        return desc;
    }
}
