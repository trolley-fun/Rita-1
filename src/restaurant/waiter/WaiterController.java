package restaurant.waiter;

import restaurant.kitchen.Actor;
import restaurant.kitchen.Message;
import restaurant.kitchen.MessageType;
import restaurant.kitchen.Order;
import restaurant.waiter.view.WaiterView;

import java.io.IOException;

/**
 * Created by Anatoly on 18.03.2016.
 */
public class WaiterController extends Actor {
    private WaiterModel model = new WaiterModel();
    private WaiterView view = new WaiterView(this, model);

    public static void main(String[] args) {
        WaiterController waiterController = new WaiterController();
        waiterController.view.initView();
        waiterController.run();
    }

    @Override
    protected void actorHandshake() throws IOException, ClassNotFoundException {
        shake(MessageType.WAITER_CONNECTION);
    }

    @Override
    protected void actorMainLoop() throws IOException, ClassNotFoundException {
        while (true) {
            Message receivedMessage = connection.receive();
            switch(receivedMessage.getMessageType()) {
                case NEW_CLIENT:
                    informAboutNewClient(receivedMessage.getFirstString());
                    break;
                case ORDER_IS_READY:
                    informAboutReadyOrder(receivedMessage.getOrder());
                    break;
                case TEXT:
                    informAboutNewText(receivedMessage.getFirstString(), receivedMessage.getSecondString());
                    break;
                case END_MEAL:
                    informAboutEndMeal(receivedMessage.getFirstString(), receivedMessage.getBill());
            }
        }
    }

    private void informAboutEndMeal(String clientName, double bill) {
        view.informAboutEndMeal(clientName, bill);
        model.deleteClient(clientName);
    }

    private void informAboutNewText(String clientName, String text) {
        view.informAboutNewText(clientName, text);
    }

    private void informAboutReadyOrder(Order order) {
        view.informAboutReadyOrder(order);
    }

    private void informAboutNewClient(String clientName) {
        WaiterModel.Client newClient = model.addNewClient(clientName);
        view.addNewClientDialog(newClient);
    }

    @Override
    public void sendMessage(Message message) {
        try {
            connection.send(message);
        } catch (IOException e) {
            view.notifyConnectionStatusChanged(false);
        }
    }

    @Override
    protected int askServerPort() {
        return view.askServerPort();
    }

    @Override
    protected String askServerAddress() {
        return view.askServerAddress();
    }

    @Override
    protected String askName() {
        return view.askName();
    }

    @Override
    protected void notifyConnectionStatusChanged(boolean actorConnected) {
        view.notifyConnectionStatusChanged(actorConnected);
    }
}
