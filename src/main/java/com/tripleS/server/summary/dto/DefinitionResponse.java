package com.tripleS.server.summary.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DefinitionResponse {
    private String supNo;
    private String definition;

    public DefinitionResponse(String supNo, String definition) {
        this.supNo = supNo;
        this.definition = definition;
    }

}

