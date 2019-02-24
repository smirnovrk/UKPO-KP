package com.yellow.photoshare.service;

import com.yellow.photoshare.dao.UserDAO;
import com.yellow.photoshare.entity.TaskEntity;
import com.yellow.photoshare.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService {

    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public boolean addPerson(UserEntity userEntity) {
        return this.userDAO.addPerson(userEntity);
    }

    @Override
    @Transactional
    public void updatePerson(UserEntity userEntity) {
        this.userDAO.updatePerson(userEntity);
    }

    @Override
    @Transactional
    public List<UserEntity> listPersons() {
        return this.userDAO.listPersons();
    }

    @Override
    @Transactional
    public UserEntity getPersonByUsername(String username) {
        return this.userDAO.getPersonByUsername(username);
    }

    @Override
    @Transactional
    public void removePerson(Long id) {
        this.userDAO.removePerson(id);
    }

    @Override
    @Transactional
    public boolean authUser(String email, String password) {
       return this.userDAO.authUser(email, password);
    }

    @Override
    @Transactional
    public boolean addTask(TaskEntity taskEntity, Long userID) {
        return this.userDAO.addTask(taskEntity, userID);
    }

    @Override
    @Transactional
    public List getTasksList(Long userID) {
        return this.userDAO.getTasksList(userID);
    }

    @Override
    @Transactional
    public boolean deleteTask(Long userID) {
        return this.userDAO.deleteTask(userID);
    }

}
