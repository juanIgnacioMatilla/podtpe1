package ar.edu.itba.pod.tpe1.client.notification;

import ar.edu.itba.pod.tpe1.client.Action;
import ar.edu.itba.pod.tpe1.client.Client;

import java.util.function.Supplier;

public class NotificationClient extends Client {

    public NotificationClient(String serverAddress, Action action) {
        super(serverAddress, action);
    }

    public static void main(String[] args) {
        String host = System.getProperty("serverAddress");
        String actionStr = System.getProperty("action");
        Action action = NotificationAction.getAction(actionStr).get();
        try (Client client = new NotificationClient(host, action)) {
            client.run();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private enum NotificationAction {
        REGISTER("register", RegisterAction::new),
        UNREGISTER("unregister", UnRegisterAction::new);

        private String actionStr;
        private Supplier<Action> actionSupplier;

        NotificationAction(String actionStr, Supplier<Action> actionSupplier) {
            this.actionStr = actionStr;
            this.actionSupplier = actionSupplier;
        }

        public static Supplier<Action> getAction(String actionStr) {
            for (NotificationAction adminAction : NotificationAction.values()) {
                if (adminAction.actionStr.equals(actionStr)) {
                    return adminAction.actionSupplier;
                }
            }
            throw new IllegalArgumentException("No action found for: " + actionStr);
        }
    }
}
