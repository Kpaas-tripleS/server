package com.tripleS.server.room.repository;

import com.tripleS.server.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

//roomrepository
@Repository
public interface RoomRepository  extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.isMatch = false")
    List<Room> findByIsMatchFalse();

    default Optional<Room> findRandomAvailableRoom() {
        List<Room> availableRooms = findByIsMatchFalse();
        if (availableRooms.isEmpty()) {
            return Optional.empty();
        }
        Random random = new Random();
        return Optional.of(availableRooms.get(random.nextInt(availableRooms.size())));
    }
}
