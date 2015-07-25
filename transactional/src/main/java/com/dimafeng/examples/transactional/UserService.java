package com.dimafeng.examples.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

@Service
public class UserService {

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    UserRepository repository;

    @Autowired
    @Qualifier("count")
    Integer count;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    DataSource dataSource;

    @Transactional
    public void transactional() {
        IntStream.range(0, count).forEach(i -> repository.save(new User("name")));
    }

    public void transactionManager() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = transactionManager.getTransaction(def);
            IntStream.range(0, count).forEach(i -> repository.save(new User("name")));
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            if (transactionStatus != null) {
                transactionManager.rollback(transactionStatus);
            }
            throw new RuntimeException(e);
        }
    }

    public void jdbc() {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (name) VALUES (?)")) {

                IntStream.range(0, count).forEach(i -> {
                    try {
                        preparedStatement.setString(1, "name");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                connection.commit();
            } catch (Exception e1) {
                connection.rollback();
                throw new RuntimeException(e1);
            }
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }
    }

    public void entityManager() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            IntStream.range(0, count).forEach(i -> entityManager.persist(new User("name")));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Transactional
    public void cleanTable() {
        repository.deleteAll();
    }

}
