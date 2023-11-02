package ru.liga.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLogMessage {
    private String logId;
    private String username;
    private String className;
    private String methodName;
    private String args;
}
