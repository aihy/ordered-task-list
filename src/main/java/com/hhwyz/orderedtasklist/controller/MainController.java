package com.hhwyz.orderedtasklist.controller;

import com.alibaba.fastjson.JSON;
import com.hhwyz.orderedtasklist.dto.TaskDTO;
import com.hhwyz.orderedtasklist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author erniu.wzh
 * @date 2022/4/28 17:41
 */
@RestController
public class MainController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/getOrderedTaskList")
    public List<TaskDTO> getOrderedTaskList() {
        return taskService.getOrderedTaskList();
    }

    @GetMapping("/getBufferedTaskList")
    public List<TaskDTO> getBufferedTaskList() {
        return taskService.getBufferedTaskList();
    }

    @GetMapping("/favicon.ico")
    public void fav() {

    }

    @PostMapping("/killTask")
    public void killTask(String taskUuid) {
        taskService.killTask(taskUuid);
    }

    @PostMapping("/addTaskToBufferedList")
    public void addTaskToBufferedList(@RequestBody String task) {
        taskService.addTaskToBufferedList(JSON.parseObject(task, String.class));
    }

    @GetMapping("/getTwoToCompare")
    public List<TaskDTO> getTwoToCompare() {
        return taskService.getTwoToCompare();
    }

    /**
     * flag==0 先做taskA
     * flag==1 先做taskB
     *
     * @param flag
     */
    @PostMapping("/setCompare")
    public void setCompare(int flag) {
        taskService.setCompare(flag);
    }

    @GetMapping("/clear")
    public void clear() {
        taskService.clear();
    }
}
