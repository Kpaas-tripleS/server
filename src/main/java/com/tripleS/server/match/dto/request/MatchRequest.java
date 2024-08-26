package com.tripleS.server.match.dto.request;

import java.time.LocalDateTime;

public class MatchRequest {

    private Long userId;
    private LocalDateTime createTime;

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
}
