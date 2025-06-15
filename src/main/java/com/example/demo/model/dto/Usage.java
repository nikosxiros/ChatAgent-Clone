package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Usage {
    @JsonProperty("queue_time")
    private double queueTime;
    @JsonProperty("prompt_tokens")
    private double promptTokens;
    @JsonProperty("completion_tokens")
    private int completionTokens;
    @JsonProperty("completion_time")
    private double completionTime;
    @JsonProperty("total_tokens")
    private int totalTokens;
    @JsonProperty("total_time")
    private double totalTime;

}
