package com.hhwyz.orderedtasklist.service;

import com.hhwyz.orderedtasklist.dto.TaskDTO;

import java.util.List;

/**
 * @author erniu.wzh
 * @date 2022/4/28 17:44
 */
public interface TaskService {
    /**
     * 返回有序任务列表
     *
     * @return
     */
    List<TaskDTO> getOrderedTaskList();

    void addTaskToBufferedList(String task);

    List<TaskDTO> getBufferedTaskList();

    List<TaskDTO> getTwoToCompare();

    /**
     * flag==0 先做taskA
     * flag==1 先做taskB
     *
     * @param flag
     */
    void setCompare(int flag);

    void clear();

    void killTask(String taskUuid);
}
