package restaurant.kitchen;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Аркадий on 14.03.2016.
 */
public abstract class Actor {
    protected String actorName;
    protected Connection connection;

    protected void run() {
        try {
            String serverAddress = askServerAddress();
            int serverPort = askServerPort();
            Socket socket = new Socket(serverAddress, serverPort);
            connection = new Connection(socket);
            actorHandshake();
            actorMainLoop();
        } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            notifyConnectionStatusChanged(false);
        } finally {
            closeResources();
        }
    }

    protected abstract void actorHandshake() throws  IOException, ClassNotFoundException;

    protected void shake(MessageType connectionType) throws IOException, ClassNotFoundException {
        Message connectionTypeMessage = new Message(connectionType);
        connection.send(connectionTypeMessage);
        String name = null;
        while(true) {
            Message message = connection.receive();
            switch(message.getMessageType()) {
                case NAME_REQUEST:
                    name = askName();
                    connection.send(new Message(MessageType.ACTOR_NAME, name));
                    break;
                case NAME_ACCEPTED:
                    actorName = name;
                    notifyConnectionStatusChanged(true);
                    return;
                default:
                    throw new IOException("Invalid message type: " + message.getMessageType());
            }
        }
    }

    private void closeResources() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void sendMessage(Message message);

    protected abstract void actorMainLoop() throws IOException, ClassNotFoundException;

    protected abstract int askServerPort();

    protected abstract String askServerAddress();

    protected abstract String askName();

    protected abstract void notifyConnectionStatusChanged(boolean actorConnected);

}
