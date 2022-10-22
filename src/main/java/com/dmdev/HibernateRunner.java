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
import org.hibernate.jdbc.AbstractWork;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

@Slf4j
public class HibernateRunner {

    @Transactional
    public static void main(String[] args) throws SQLException {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        )
        {
            TestDataImporter.importData(sessionFactory);
           session.beginTransaction();
            session1.beginTransaction();

           var payment = session.find(Payment.class, 1L);
          payment.setAmount(payment.getAmount() + 10);

            var payment1 = session1.find(Payment.class, 1L);
            payment1.setAmount(payment1.getAmount() + 20);
           session.getTransaction().commit();
            session1.getTransaction().commit();
        }
    }
}
