package com.tripleS.server.match.dto.response;

import com.tripleS.server.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchInfoResponse {

    private Long matchId;
    private User player;
    private User opponent;

}
