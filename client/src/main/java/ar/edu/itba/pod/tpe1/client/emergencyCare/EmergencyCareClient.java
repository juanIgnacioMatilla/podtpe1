package ar.edu.itba.pod.tpe1.client.emergencyCare;

import ar.edu.itba.pod.tpe1.client.Action;
import ar.edu.itba.pod.tpe1.client.Client;
import ar.edu.itba.pod.tpe1.client.emergencyCare.actions.CareAllPatientAction;
import ar.edu.itba.pod.tpe1.client.emergencyCare.actions.CarePatientAction;
import ar.edu.itba.pod.tpe1.client.emergencyCare.actions.DischargePatientAction;

import java.util.function.Supplier;

public class EmergencyCareClient extends Client {
    public EmergencyCareClient(String serverAddress, Action action) {
        super(serverAddress, action);
    }
    public static void main(String[] args) {
        String host = System.getProperty("serverAddress");
        String actionStr = System.getProperty("action");
        Action action = EmergencyCareClient.EmergencyCareAction.getAction(actionStr).get();
        try (Client client = new EmergencyCareClient(host, action)) {
            client.run();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private enum EmergencyCareAction {
        CARE_PATIENT("carePatient", CarePatientAction::new),
        CARE_ALL_PATIENTS("careAllPatients", CareAllPatientAction::new),
        DISCHARGE_PATIENT("dischargePatient",DischargePatientAction::new);
        private String actionStr;
        private Supplier<Action> actionSupplier;

        EmergencyCareAction(String actionStr, Supplier<Action> actionSupplier) {
            this.actionStr = actionStr;
            this.actionSupplier = actionSupplier;
        }

        public static Supplier<Action> getAction(String actionStr) {
            for (EmergencyCareClient.EmergencyCareAction adminAction : EmergencyCareClient.EmergencyCareAction.values()) {
                if (adminAction.actionStr.equals(actionStr)) {
                    return adminAction.actionSupplier;
                }
            }
            throw new IllegalArgumentException("No action found for: " + actionStr);
        }
    }
}
