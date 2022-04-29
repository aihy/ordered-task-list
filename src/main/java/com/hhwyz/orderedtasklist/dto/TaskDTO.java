package com.hhwyz.orderedtasklist.dto;

import lombok.Data;

import java.util.UUID;

/**
 * @author erniu.wzh
 * @date 2022/4/28 17:42
 */
@Data
public class TaskDTO {
    private String taskContent;
    private String taskUuid;

    public TaskDTO(String taskContent) {
        this.taskContent = taskContent;
        this.taskUuid = UUID.randomUUID().toString();
    }
}
