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

    public CareHistory addToHistory(CareHistory careHistory) {
        history.add(careHistory);
        return careHistory;
    }

    public CareHistory addToCurrent(CareHistory careHistory) {
        currentAppointments.add(careHistory);
        return careHistory;
    }

    public Boolean removeFromCurrent(CareHistory careHistory) {
        return currentAppointments.stream()
                .filter(ch -> ch.equals(careHistory))
                .findFirst()
                .map(ch -> {
                    history.add(ch);
                    return currentAppointments.remove(ch);
                })
                .orElse(false);
    }
}
