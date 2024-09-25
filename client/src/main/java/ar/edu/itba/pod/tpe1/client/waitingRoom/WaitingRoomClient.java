package ar.edu.itba.pod.tpe1.client.waitingRoom;

import ar.edu.itba.pod.tpe1.client.Action;
import ar.edu.itba.pod.tpe1.client.Client;
import ar.edu.itba.pod.tpe1.client.admin.AdminClient;
import ar.edu.itba.pod.tpe1.client.waitingRoom.actions.AddPatientAction;
import ar.edu.itba.pod.tpe1.client.waitingRoom.actions.CheckPatientAction;
import ar.edu.itba.pod.tpe1.client.waitingRoom.actions.UpdateLevelAction;

import java.util.function.Supplier;

public class WaitingRoomClient extends Client {
    public WaitingRoomClient(String serverAddress, Action action) {
        super(serverAddress, action);
    }

    public static void main(String[] args) {
        String host = System.getProperty("serverAddress");
        String actionStr = System.getProperty("action");
        Action action = WaitingRoomClient.WaitingRoomAction.getAction(actionStr).get();
        try (Client client = new AdminClient(host, action)) {
            client.run();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private enum WaitingRoomAction {
        ADD_PATIENT("addPatient", AddPatientAction::new),

        UPDATE_LEVEL("updateLevel", UpdateLevelAction::new),
        CHECK_PATIENT("checkPatient", CheckPatientAction::new);

        private String actionStr;
        private Supplier<Action> actionSupplier;

        WaitingRoomAction(String actionStr, Supplier<Action> actionSupplier) {
            this.actionStr = actionStr;
            this.actionSupplier = actionSupplier;
        }

        public static Supplier<Action> getAction(String actionStr) {
            for (WaitingRoomClient.WaitingRoomAction adminAction : WaitingRoomClient.WaitingRoomAction.values()) {
                if (adminAction.actionStr.equals(actionStr)) {
                    return adminAction.actionSupplier;
                }
            }
            throw new IllegalArgumentException("No action found for: " + actionStr);
        }
    }
}
