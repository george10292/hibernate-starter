package com.dmdev;

import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.*;
import com.dmdev.util.HibernateUtil;
import com.dmdev.util.TestDataImporter;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.QueryHints;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.graph.GraphSemantic;
import org.hibernate.graph.RootGraph;
import org.hibernate.graph.SubGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.openSession())
        {
            session.beginTransaction();
//            session.enableFetchProfile("withCompanyAndPayment");
            var userGraph = session.createEntityGraph(User.class);
            userGraph.addAttributeNodes("company", "userChats");
            var userChatsSubgraph = userGraph.addSubgraph("userChats", UsersChat.class);
            userChatsSubgraph.addAttributeNodes("chat");

           // RootGraph<?> graph = session.getEntityGraph("WithCompanyAndChat");

            Map<String, Object> properties = Map.of(
                //   GraphSemantic.LOAD.getJpaHintName(), session.getEntityGraph("WithCompanyAndChat")
                    GraphSemantic.LOAD.getJpaHintName(), userGraph
           );

            var user = session.find(User.class, 1L, properties);
            System.out.println(user.getCompany().getName());
            System.out.println(user.getUserChats().size());
            var users = session.createQuery("select u from User u " +
                            "join fetch u.payments " +
                            "join fetch u.company " +
                            "where 1=1", User.class)
                    .setHint(GraphSemantic.LOAD.getJpaHintName(), userGraph)
                            .list();
            users.forEach(it -> System.out.println(user.getPayments().size()));
            users.forEach(it -> System.out.println(user.getCompany().getName()));
//            System.out.println(user.getCompany().getName());

            session.getTransaction().commit();
        }
    }
}
