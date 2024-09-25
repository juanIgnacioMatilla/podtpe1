package ar.edu.itba.pod.tpe1.client.admin;

import ar.edu.itba.pod.tpe1.client.Action;
import ar.edu.itba.pod.tpe1.client.Client;

import java.util.function.Supplier;

public class AdminClient extends Client {

    public AdminClient(String serverAddress, Action action) {
        super(serverAddress, action);
    }

    public static void main(String[] args) {
        String host = System.getProperty("serverAddress");
        String actionStr = System.getProperty("action");
        Action action = AdminAction.getAction(actionStr).get();
        try (Client client = new AdminClient(host, action)) {
            client.run();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private enum AdminAction {
        ADD_DOCTOR("addDoctor", AddDoctorAction::new);
//        ADD_ROOM("addRoom",AddRoomAction::new),
//        SET_DOCTOR("setDoctor", SetDoctorAction::new),
//        CHECK_DOCTOR("checkDoctor", CheckDoctorAction::new);

        private String actionStr;
        private Supplier<Action> actionSupplier;

        AdminAction(String actionStr, Supplier<Action> actionSupplier) {
            this.actionStr = actionStr;
            this.actionSupplier = actionSupplier;
        }

        public static Supplier<Action> getAction(String actionStr) {
            for (AdminAction adminAction : AdminAction.values()) {
                if (adminAction.actionStr.equals(actionStr)) {
                    return adminAction.actionSupplier;
                }
            }
            throw new IllegalArgumentException("No action found for: " + actionStr);
        }
    }
}
