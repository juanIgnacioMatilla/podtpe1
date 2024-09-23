package ar.edu.itba.pod.tpe1.server.services;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.HashSet;

import ar.edu.itba.pod.tpe1.server.models.CareHistory;
import ar.edu.itba.pod.tpe1.server.models.Patient;
import ar.edu.itba.pod.tpe1.server.models.Room;
import ar.edu.itba.pod.tpe1.server.repositories.CareHistoryRepository;
import ar.edu.itba.pod.tpe1.server.repositories.PatientRepository;
import ar.edu.itba.pod.tpe1.server.repositories.RoomRepository;
import ar.edu.itba.pod.tpe1.server.services.interfaces.QueryService;

public class QueryServiceImpl implements QueryService {
    RoomRepository roomRepo;
    PatientRepository patientRepo;
    CareHistoryRepository careHistoryRepo;

    public QueryServiceImpl(RoomRepository roomRepo, PatientRepository patientRepo,
            CareHistoryRepository careHistoryRepo) {
        this.roomRepo = roomRepo;
        this.patientRepo = patientRepo;
        this.careHistoryRepo = careHistoryRepo;
    }

    @Override
    public Set<Room> queryRooms() {
        Set<Room> rooms = roomRepo.getRooms();
        if (rooms.isEmpty())
            throw new RuntimeException("No rooms have been added");
        return rooms;
    }

    @Override
    public Map<Integer, List<Patient>> queryWaitingRoom() {
        Map<Integer, List<Patient>> waitingPatients = patientRepo.getPatients();

        if (waitingPatients.isEmpty())
            throw new RuntimeException("No patients are waiting");
        return waitingPatients;
    }

    @Override
    public Queue<CareHistory> queryCares(Integer room) {
        Queue<CareHistory> out = careHistoryRepo.getHistory();
        if (room != null) {
            out.removeIf(careHistory -> careHistory.getRoom().getId() != room);
        }
        if (out.isEmpty())
            throw new RuntimeException("No patients have been care yet");
        return out;
    }

}
