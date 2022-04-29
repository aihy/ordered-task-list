package com.hhwyz.orderedtasklist.dto;

import lombok.Data;

/**
 * @author erniu.wzh
 * @date 2022/4/28 17:42
 */
@Data
public class TaskDTO {
    private String taskContent;

    public TaskDTO(String taskContent) {
        this.taskContent = taskContent;
    }

    public TaskDTO() {
    }
}
