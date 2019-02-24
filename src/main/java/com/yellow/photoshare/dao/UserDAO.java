package com.yellow.photoshare.dao;

import com.yellow.photoshare.entity.TaskEntity;
import com.yellow.photoshare.entity.UserEntity;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
//@Transactional
public class UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    public boolean addTask(TaskEntity taskEntity, Long userID) {
        UserEntity userEntity = entityManager.find(UserEntity.class, userID);
        userEntity.addTask(taskEntity);
        entityManager.merge(userEntity);
        return true;
    }

    public List getTasksList(Long userID) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TaskEntity> cq = cb.createQuery(TaskEntity.class);
        Root<TaskEntity> rootEntry = cq.from(TaskEntity.class);
        CriteriaQuery<TaskEntity> dbEmail = cq.select(rootEntry).where(cb.equal(rootEntry.get("userEntity"), userID));
        TypedQuery<TaskEntity> userWithEmail = entityManager.createQuery(dbEmail);
        List userList = userWithEmail.getResultList();

        return userList;
    }

    public boolean deleteTask(Long taskID) {
        TaskEntity taskEntity = entityManager.find(TaskEntity.class, taskID);
        if(null != taskEntity){
            entityManager.remove(taskEntity);
        }


        return true;
    }

//    @Override
    public boolean addPerson(UserEntity userEntity) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<UserEntity> rootEntry = cq.from(UserEntity.class);
        CriteriaQuery<String> email = cq.select(rootEntry.get("email"));
        TypedQuery<String> allEmail = entityManager.createQuery(email);
        List emailList = allEmail.getResultList();
        if (emailList.contains(userEntity.getEmail())) {
            return false;
        }
        entityManager.persist(userEntity);
        logger.info("Person saved successfully, Person Details="+ userEntity);
        return true;
    }

//    @Override
    public void updatePerson(UserEntity userEntity) {
        entityManager.merge(userEntity);
        logger.info("Person updated successfully, Person Details="+ userEntity);
    }

    public List<UserEntity> listPersons() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> rootEntry = cq.from(UserEntity.class);
        CriteriaQuery<UserEntity> all = cq.select(rootEntry);
        TypedQuery<UserEntity> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    public UserEntity getPersonByUsername(String username) {
        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(UserEntity.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onField("username")
                .matching(username)
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, UserEntity.class);

        List<UserEntity> results = jpaQuery.getResultList();

        return results.get(0);
    }

//    @Override
    public void removePerson(Long id) {
        UserEntity userEntity = entityManager.find(UserEntity.class,id);
        if(null != userEntity){
            entityManager.remove(userEntity);
        }
        logger.info("Person deleted successfully, person details="+ userEntity);
    }

    public boolean authUser(String email, String password) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
        Root<UserEntity> rootEntry = cq.from(UserEntity.class);
        CriteriaQuery<UserEntity> dbEmail = cq.select(rootEntry).where(cb.equal(rootEntry.get("email"), email));
        TypedQuery<UserEntity> userWithEmail = entityManager.createQuery(dbEmail);
        List userList = userWithEmail.getResultList();

        if (userList.size() != 0) {
            Object object = userList.get(0);
            UserEntity user = (UserEntity) object;
            if (user.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
