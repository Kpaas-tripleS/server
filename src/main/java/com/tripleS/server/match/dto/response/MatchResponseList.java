package com.tripleS.server.match.dto.response;

import java.util.List;

public record MatchResponseList(
        List<MatchResponse> matchResponseList
) {
    public static MatchResponseList from(List<MatchResponse> matchResponseList) {
        return new MatchResponseList(matchResponseList);
    }
}
