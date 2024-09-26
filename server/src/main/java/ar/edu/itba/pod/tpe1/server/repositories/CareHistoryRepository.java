package ar.edu.itba.pod.tpe1.server.repositories;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;

public class CareHistoryRepository {
    private final Queue<CareHistory> history;

    public CareHistoryRepository() {
        history = new ConcurrentLinkedQueue<>();
    }
    public Queue<CareHistory> getHistory() {
        return history;
    }

    public CareHistory addToHistory(CareHistory careHistory) {
        history.add(careHistory);
        return careHistory;
    }

}
