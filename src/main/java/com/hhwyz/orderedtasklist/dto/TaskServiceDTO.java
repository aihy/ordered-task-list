package com.hhwyz.orderedtasklist.dto;

import lombok.Data;

import java.util.List;

/**
 * @author erniu.wzh
 * @date 2022/4/28 23:48
 */
@Data
public class TaskServiceDTO {
    private List<TaskDTO> taskOrderedList;
    private List<TaskDTO> taskBufferedList;
    private Integer taskAIndex;
    private Integer taskBIndex;
    private Integer leftIndex;
    private Integer rightIndex;
    private Integer midIndex;
}
