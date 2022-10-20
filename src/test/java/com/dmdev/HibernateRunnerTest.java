package com.dmdev;

import com.dmdev.entity.*;
import com.dmdev.util.HibernateTestUtil;
import com.dmdev.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;


import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

class HibernateRunnerTest {

    @Test
    void checkHQL(){
        try(var sessionFactory = HibernateTestUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();
            var result = session.createQuery(
                    "select u from User u " +
                            "join u.company c " +
                            "where u.personalInfo.first_name = :firstname and c.name = :companyName", User.class)
                    .setParameter("firstname", "Ivan")
                    .setParameter("companyName", "Google")
            .list();
            session.getTransaction().commit();
        }
    }

    @Test
    void checkH2() {
        try(var sessionFactory = HibernateTestUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();


            var google = Company.builder()
                    .name("Google")
                    .build();
            session.save(google);

//            Programmer programmer = Programmer.builder()
//                    .username("ivan@gmail.com")
//                    .language(Language.JAVA)
//                    .company(google)
//                    .build();
//            session.save(programmer);
//
//            Manager manager = Manager.builder()
//                    .username("sveta@gmail.com")
//                    .projectName("Starter")
//                    .company(google)
//                    .build();
//            session.save(manager);
//            session.flush();
//
//            session.clear();
//            session.get(Programmer.class, 1L);
//
//            var programmer1 = session.get(Programmer.class, 1L);
//            var manager1 = session.get(User.class, 2L);
//
//            System.out.println();
//            session.getTransaction().commit();
        }
    }

    @Test
    void localeInfo() {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();


            var company = session.get(Company.class, 1);
//            company.getLocales().add(LocaleInfo.of("ru", "Описание на русском"));
//            company.getLocales().add(LocaleInfo.of("en", "English description"));
            company.getUsers().forEach(System.out::println);


            session.getTransaction().commit();
        }

    }

    @Test
    void checkManyToMany() {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();


            var user = session.get(User.class, 11L);
            var chat = session.get(Chat.class, 1L);

//            var userChat = UsersChat.builder()
//                    .createdAt(Instant.now())
//                    .createdBy(user.getUsername())
//                    .build();
//
//            userChat.setUser(user);
//            userChat.setChat(chat);

//            session.save(userChat);

//            user.getChats().clear();

//            var chat = Chat.builder()
//                    .name("dmdev")
//                    .build();
//
//            user.addChat(chat);
//
//            session.save(chat);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkOneToOne() {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var user = session.get(User.class, 11L);
            System.out.println();
////            var user = session.get(User.class, 10L);
////            System.out.println();
//           var user = User.builder()
//                           .username("test4@gmail.com")
//                           .build();
//           var profile = Profile.builder()
//                   .language("ru")
//                   .street("Pushkina")
//                   .build();
////           session.save(user);
//           profile.setUser(user);
//            session.save(user);
          // session.save(profile);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkOrphanRemoval() {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.getReference(Company.class, 3);
            company.getUsers().removeIf(user -> user.getId().equals(9L));
            session.getTransaction().commit();
        }
    }

    @Test
    void checkLazyInitialization() {
        Company company = null;
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
            var session = sessionFactory.openSession()) {
            session.beginTransaction();

            company = session.getReference(Company.class, 1);

            session.getTransaction().commit();
        }
        var users = company.getUsers();
        System.out.println(users.size());
    }

    @Test
    void deleteCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var user = session.get(User.class, 1L);

        session.delete(user);

        session.getTransaction().commit();
    }

    @Test
    void addUserToNewCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = Company.builder()
                .name("Facebook")
                .build();
//        var user = User.builder()
//                .username("sveta@gmail.com")
//                .build();
//        user.setCompany(company);
//        company.getUsers().add(user);
//        company.addUser(user);
//        session.save(company);
        session.getTransaction().commit();
    }

    @Test
    void oneToMany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var company = session.get(Company.class, 1);
        Hibernate.initialize(company.getUsers());
        System.out.println("");
        session.getTransaction().commit();
    }

    @Test
    void checkGetReflectionApi() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.getString("username");
        resultSet.getString("first_name");
        resultSet.getString("last_name");

        Class<User> clazz = User.class;
        Constructor<User> constructor = clazz.getConstructor();
        User user = constructor.newInstance();
        Field usernameField = clazz.getDeclaredField("username");
        usernameField.setAccessible(true);
        usernameField.set(user, resultSet.getString("username"));
    }

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
//        User user = User.builder()
//                .build();

//        String sql = """
//                insert
//                into
//                %s
//                (%s)
//                values
//                (%s)
//                """;
//        String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
//                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
//                .orElse(user.getClass().getName());
//        Field[] declaredFields = user.getClass().getDeclaredFields();
//        String columnNames = Arrays.stream(declaredFields)
//                .map(field -> ofNullable(field.getAnnotation(Column.class))
//                        .map(Column::name)
//                        .orElse(field.getName()))
//                .collect(joining(", "));
//        String columnValues = Arrays.stream(declaredFields)
//                .map(field -> "?")
//                .collect(joining(", "));
//
//        System.out.println(sql.formatted(tableName, columnNames, columnValues));
//
//        Connection connection = null;
//        PreparedStatement preparedStatement = connection.prepareStatement(sql.formatted(tableName, columnNames, columnValues));
//        for (Field declaredField : declaredFields) {
//            declaredField.setAccessible(true);
//            preparedStatement.setObject(1, declaredField.get(user));
//        }
    }

}