package com.tripleS.server.match.dto.request;

import java.time.LocalDateTime;

public record MatchRequest(
        LocalDateTime creatTime
) {
}
