package com.tripleS.server.match.dto.response;

import com.tripleS.server.user.domain.User;
import lombok.*;

@Getter
@Setter
public class MatchResultResponse {

    private User Player;
    private User opponent;
    private Long playerScroe;
    private Long opponentScore;


}
