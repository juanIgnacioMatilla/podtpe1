package ar.edu.itba.pod.tpe1.server.repositories;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;

public class CareHistoryRepository {
    private Queue<CareHistory> history;
    private Queue<CareHistory> currentAppointments;

    public CareHistoryRepository() {
        history = new ConcurrentLinkedQueue<>();
        currentAppointments = new ConcurrentLinkedQueue<>();
    }

    public Queue<CareHistory> getCurrentAppointments() {
        return currentAppointments;
    }

    public Queue<CareHistory> getHistory() {
        return history;
    }

    public Boolean addToHistory(CareHistory careHistory) {
        return history.add(careHistory);
    }

    public Boolean addToCurrent(CareHistory careHistory) {
        return currentAppointments.add(careHistory);
    }

    public Boolean removeFromCurrent(CareHistory careHistory) {
        for (CareHistory ch : currentAppointments) {
            if (ch.equals(careHistory)) {
                history.add(ch);
                return currentAppointments.remove(ch);
            }
        }
        return false;
    }
}
