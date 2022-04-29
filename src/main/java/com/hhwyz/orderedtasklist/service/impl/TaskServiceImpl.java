package com.hhwyz.orderedtasklist.service.impl;

import com.alibaba.fastjson.JSON;
import com.hhwyz.orderedtasklist.dto.TaskDTO;
import com.hhwyz.orderedtasklist.dto.TaskServiceDTO;
import com.hhwyz.orderedtasklist.service.TaskService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author erniu.wzh
 * @date 2022/4/28 17:45
 */
@Service
public class TaskServiceImpl implements TaskService {
    private static TaskServiceDTO taskServiceDTO;

    @PostConstruct
    public void init() throws IOException {
        taskServiceDTO = new TaskServiceDTO();
        if (new File("TaskServiceDTO.json").exists()) {
            String taskServiceImplString = FileUtils.readFileToString(new File("TaskServiceDTO.json"), StandardCharsets.UTF_8);
            TaskServiceDTO temp = JSON.parseObject(taskServiceImplString, TaskServiceDTO.class);
            taskServiceDTO.setTaskOrderedList(temp.getTaskOrderedList());
            taskServiceDTO.setTaskBufferedList(temp.getTaskBufferedList());
            taskServiceDTO.setTaskAIndex(temp.getTaskAIndex());
            taskServiceDTO.setTaskBIndex(temp.getTaskBIndex());
            taskServiceDTO.setLeftIndex(temp.getLeftIndex());
            taskServiceDTO.setRightIndex(temp.getRightIndex());
            taskServiceDTO.setMidIndex(temp.getMidIndex());
        } else {
            taskServiceDTO.setTaskOrderedList(new ArrayList<>());
            taskServiceDTO.setTaskBufferedList(new ArrayList<>());
        }
    }

    @PreDestroy
    public void destroy() throws IOException {
        FileUtils.writeStringToFile(new File("TaskServiceDTO.json"), JSON.toJSONString(taskServiceDTO), StandardCharsets.UTF_8);
    }

    @Override
    public synchronized List<TaskDTO> getOrderedTaskList() {
        return taskServiceDTO.getTaskOrderedList();
    }

    @Override
    public synchronized List<TaskDTO> getBufferedTaskList() {
        return taskServiceDTO.getTaskBufferedList();
    }

    @Override
    public synchronized void addTaskToBufferedList(String task) {
        taskServiceDTO.getTaskBufferedList().add(new TaskDTO(task));
    }

    private int getIndexFromLeft() {
        int mid = (taskServiceDTO.getLeftIndex() + taskServiceDTO.getRightIndex()) / 2;
        taskServiceDTO.setMidIndex(mid);
        return mid;
    }

    private int getIndexFromRight() {
        if (listIsEmpty(taskServiceDTO.getTaskBufferedList())) {
            return -1;
        } else {
            return 0;
        }
    }

    private boolean listIsEmpty(List list) {
        return list == null || list.isEmpty();
    }

    @Override
    public synchronized List<TaskDTO> getTwoToCompare() {
        // 如果左边OrderedList是空，就先从右边拿一个过来
        if (listIsEmpty(taskServiceDTO.getTaskOrderedList())) {
            if (listIsEmpty(taskServiceDTO.getTaskBufferedList())) {
                return null;
            } else {
                List<TaskDTO> taskOrderedList = new ArrayList<>();
                taskOrderedList.add(taskServiceDTO.getTaskBufferedList().get(0));
                taskServiceDTO.getTaskBufferedList().remove(0);
                taskServiceDTO.setTaskOrderedList(taskOrderedList);
                taskServiceDTO.setLeftIndex(0);
                taskServiceDTO.setRightIndex(taskServiceDTO.getTaskOrderedList().size() - 1);
            }
        }
        System.out.println(JSON.toJSONString(taskServiceDTO));
        // 先确定左边OrderedList的index
        int taskAIndex = getIndexFromLeft();
        taskServiceDTO.setTaskAIndex(taskAIndex);
        // 再确定右边BufferedList的index
        int taskBIndex = getIndexFromRight();
        taskServiceDTO.setTaskBIndex(taskBIndex);
        // 然后返回
        if (taskAIndex == -1 || taskBIndex == -1) {
            return null;
        } else {
            return Stream.of(taskServiceDTO.getTaskOrderedList().get(taskAIndex), taskServiceDTO.getTaskBufferedList().get(taskBIndex)).collect(Collectors.toList());
        }
    }

    @Override
    public void setCompare(int flag) {
        int insertIndex;
        if (flag == 0) {
            // 先做taskA
            taskServiceDTO.setLeftIndex(taskServiceDTO.getMidIndex() + 1);
            insertIndex = taskServiceDTO.getTaskAIndex() + 1;
        } else {
            // 先做taskB
            taskServiceDTO.setRightIndex(taskServiceDTO.getMidIndex() - 1);
            insertIndex = taskServiceDTO.getTaskAIndex();
        }
        if (taskServiceDTO.getLeftIndex() > taskServiceDTO.getRightIndex()) {
            // 二分法结束
            taskServiceDTO.getTaskOrderedList().add(insertIndex, taskServiceDTO.getTaskBufferedList().get(taskServiceDTO.getTaskBIndex()));
            taskServiceDTO.getTaskBufferedList().remove(taskServiceDTO.getTaskBIndex().intValue());
            taskServiceDTO.setTaskAIndex(-1);
            taskServiceDTO.setTaskBIndex(-1);
            taskServiceDTO.setLeftIndex(0);
            taskServiceDTO.setRightIndex(taskServiceDTO.getTaskOrderedList().size() - 1);
        }
    }

    @Override
    public void clear() {
        taskServiceDTO = new TaskServiceDTO();
        taskServiceDTO.setTaskOrderedList(new ArrayList<>());
        taskServiceDTO.setTaskBufferedList(new ArrayList<>());
    }
}
