package com.tripleS.server.room.controller;

import com.tripleS.server.room.domain.Room;
import com.tripleS.server.room.dto.request.MatchRequest;
import com.tripleS.server.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/match")
    @ResponseBody
    public Long startMatch(@RequestBody MatchRequest matchRequest) {
        return roomService.startMatch(matchRequest.getUserId(), matchRequest.getCreateTime());
    }
}
