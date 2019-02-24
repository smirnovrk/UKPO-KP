package com.yellow.photoshare.controller;

import com.yellow.photoshare.dto.TaskDTO;
import com.yellow.photoshare.entity.TaskEntity;
import com.yellow.photoshare.entity.UserEntity;
import com.yellow.photoshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class APIController {

    private UserService userService;

    @Autowired
    @Qualifier(value = "userService")
    public void setPersonService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/registration")
    public Map registration(@RequestBody UserEntity userEntity) {
        if (this.userService.addPerson(userEntity)) {
            return Collections.singletonMap("status", "success");
        } else {
            return Collections.singletonMap("status", "error");
        }
    }

    @PostMapping("api/createTask")
    public Map createTask(@RequestBody TaskDTO taskDTO) {
        TaskEntity taskEntity = new TaskEntity(taskDTO.getTitle(), taskDTO.getDescription());

        if (this.userService.addTask(taskEntity, 9L)) {
            return Collections.singletonMap("status", "success");
        } else {
            return Collections.singletonMap("status", "error");
        }
    }

    @PostMapping("api/getTasksList")
    public List getTasksList(@RequestBody Map<String, Long> response) {
        Long userID = response.get("userID");
        List<TaskEntity> tasksList = this.userService.getTasksList(userID);
        for (int i = 0; i < tasksList.size(); i++) {
            tasksList.get(i).setUserEntity(null);
        }
        return tasksList;
    }

    @PostMapping("api/deleteTask")
    public void deleteTask(@RequestBody Map<String, Long> response) {
        Long taskID = response.get("taskID");
        this.userService.deleteTask(taskID);
    }

    @GetMapping("api/getTasksList")
    public List getTasksList() {
            List<TaskEntity> tasksList = this.userService.getTasksList(9L);
            for (int i = 0; i < tasksList.size(); i++) {
                tasksList.get(i).setUserEntity(null);
            }
            return tasksList;

    }


//    @PostMapping("api/getUserData")
//    public Map getUserData (@RequestBody String title, String description, Long userID) {
//        TaskEntity taskEntity = new TaskEntity(title, description);
//
//        if (this.userService.addTask(taskEntity, userID)) {
//            return Collections.singletonMap("status", "success");
//        } else {
//            return Collections.singletonMap("status", "error");
//        }
//    }

}
