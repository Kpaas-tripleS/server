package com.tripleS.server.room.service;

import com.tripleS.server.room.domain.Room;
import com.tripleS.server.room.repository.RoomRepository;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    public Long startMatch(Long userId, LocalDateTime createTime) {

        Optional<Room> availableRoom = roomRepository.findRandomAvailableRoom();
        if (availableRoom.isPresent()) {
            User follower = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
            Room room = availableRoom.get();
            room.setIsMatch(true);
            room.setFollower(follower);
            roomRepository.save(room);
            return room.getId();
        } else {
            User leader = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
            Room room = Room.builder()
                    .isMatch(false)
                    .createTime(createTime)
                    .leader(leader)
                    .follower(null)
                    .build();
            roomRepository.save(room);
            return room.getId();
        }

    }


}

