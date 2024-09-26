package ar.edu.itba.pod.tpe1.client.query;

import ar.edu.itba.pod.tpe1.client.Action;
import ar.edu.itba.pod.tpe1.client.Client;
import ar.edu.itba.pod.tpe1.client.query.actions.QueryCaresAction;
import ar.edu.itba.pod.tpe1.client.query.actions.QueryRoomsAction;
import ar.edu.itba.pod.tpe1.client.query.actions.QueryWaitingRoomAction;

import java.util.function.Supplier;

public class QueryClient extends Client {

    public QueryClient(String serverAddress, Action action) {
        super(serverAddress, action);
    }

    public static void main(String[] args) {
        String host = System.getProperty("serverAddress");
        String actionStr = System.getProperty("action");
        Action action = QueryAction.getAction(actionStr).get();
        try (Client client = new QueryClient(host, action)) {
            client.run();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private enum QueryAction {
        QUERY_ROOMS("queryRooms", QueryRoomsAction::new),
        QUERY_WAITING_ROOM("queryWaitingRoom", QueryWaitingRoomAction::new),
        QUERY_CARES("queryCares", QueryCaresAction::new);

        private String actionStr;
        private Supplier<Action> actionSupplier;

        QueryAction(String actionStr, Supplier<Action> actionSupplier) {
            this.actionStr = actionStr;
            this.actionSupplier = actionSupplier;
        }

        public static Supplier<Action> getAction(String actionStr) {
            for (QueryAction adminAction : QueryAction.values()) {
                if (adminAction.actionStr.equals(actionStr)) {
                    return adminAction.actionSupplier;
                }
            }
            throw new IllegalArgumentException("No action found for: " + actionStr);
        }
    }
}
