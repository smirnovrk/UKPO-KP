package com.yellow.photoshare.controller;

import com.yellow.photoshare.entity.TaskEntity;
import com.yellow.photoshare.entity.UserEntity;
import com.yellow.photoshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/auth")
    public String authentification() {
        return "authentification";
    }

    @GetMapping("/task/add")
    public String newTask() {
        return "newtask";
    }

    @PostMapping("/task/add/done")
    public @ResponseBody String addTask(TaskEntity taskEntity) {
        this.userService.addTask(taskEntity, 9L);
        return "Task successfully added";
    }
//
//    @PostMapping("api/getTasksList")
////    public List getTasksList (@RequestBody Long userID) {
//////        Long userID = response.get("userID");
////        List<TaskEntity> tasksList = this.userService.getTasksList(userID);
//////        List<TaskEntity> tasksListImprove = new
////        for (int i = 0; i < tasksList.size(); i++) {
////            tasksList.get(i).setUserEntity(null);
////        }
//////        JSONArray tasksListJSON = new JSONArray(tasksList);
////        return tasksList;
////    }

    @PostMapping("/registration/done")
    public @ResponseBody String registrationRequest(@Valid UserEntity userEntity) {
        String name = userEntity.getName();
        if (this.userService.addPerson(userEntity)) {
            System.out.println("Hello, " + name);
            return "registration";
        } else {
            return "Try again";
        }
    }

    @PostMapping("/auth/done")
    public @ResponseBody String authRequest(String email, String password) {
        System.out.println(email);
        System.out.println(password);
        if (this.userService.authUser(email, password)) {
            return "Auth done";
        } else {
            return "Try again";
        }
    }
}
