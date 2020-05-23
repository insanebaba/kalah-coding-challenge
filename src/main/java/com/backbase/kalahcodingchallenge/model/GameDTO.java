package com.backbase.kalahcodingchallenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record GameDTO(Integer id, String uri, Map<Integer, Integer>status) {
}
