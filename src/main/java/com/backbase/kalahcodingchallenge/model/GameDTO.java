package com.backbase.kalahcodingchallenge.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record GameDTO(Integer id, String uri, Map<Integer, Integer>status) implements Serializable {
}
