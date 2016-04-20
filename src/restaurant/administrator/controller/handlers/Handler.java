package restaurant.administrator.controller.handlers;

import restaurant.kitchen.Message;
import restaurant.kitchen.MessageType;
import restaurant.kitchen.Connection;
import restaurant.administrator.controller.Server;

import java.io.IOException;
import java.util.List;

/**
 * Created by Аркадий on 13.03.2016.
 */
public abstract class Handler implements Runnable {
    private static List<String> actorsNames = Server.getActorsNames();
    protected final Connection connection;
    protected String actorName;

    public Handler(Connection connection) {
        this.connection = connection;
    }

    protected void requestActorName() throws IOException, ClassNotFoundException {
        while (true) {
            connection.send(new Message(MessageType.NAME_REQUEST));
            Message nameMessage = connection.receive();
            if(nameMessage.getMessageType() == MessageType.ACTOR_NAME) {
                String name = nameMessage.getFirstString();
                if(name != null && !name.isEmpty() && !actorsNames.contains(name)) {
                    actorName = name;
                    actorsNames.add(actorName);
                    connection.send(new Message(MessageType.NAME_ACCEPTED));
                    break;
                }
            }
        }
    }

    protected abstract void handlerMainLoop()
            throws IOException, ClassNotFoundException, InterruptedException;

    void informServerAndCloseConnection(String actorType) {
        Server.updateConnectionsInfo(actorType + " " + actorName + " was disconnected!");
        Server.getActorsNames().remove(actorName);
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
