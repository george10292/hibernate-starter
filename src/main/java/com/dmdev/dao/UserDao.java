//package com.dmdev.dao;
//
//import com.dmdev.dto.CompanyDto;
//import com.dmdev.dto.PaymentFilter;
//import com.dmdev.entity.*;
//import com.querydsl.core.Tuple;
//import com.querydsl.core.types.Predicate;
//import com.querydsl.jpa.impl.JPAQuery;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//import org.hibernate.Session;
//
//import javax.persistence.criteria.JoinType;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//public class UserDao {
//
//    private static final UserDao INSTANCE = new UserDao();
//
//    /**
//     * ¬озвращает всех сотрудников
//     */
//    public List<User> findAll(Session session) {
////        return session.createQuery("select u from User u", User.class).list();
//
//        return new JPAQuery<User>(session)
//                .select(user)
//                .from(user)
//                .fetch();
//    }
//
//    /**
//     * ¬озвращает всех сотрудников с указанным именем
//     */
//    public List<User> findAllByFirstName(Session session, String first_name) {
////        return session.createQuery("select u from User u " +
////                "where u.personalInfo.first_name = :firstName", User.class)
////                .setParameter("firstName", first_name)
////                .list();
//
//        return new JPAQuery<User>(session)
//                .select(user)
//                .from(user)
//                .where(user.personalInfo.first_name.eq(first_name))
//                .fetch();
//    }
//
//    /**
//     * ¬озвращает первые {limit} сотрудников, упор€доченных по дате рождени€ (в пор€дке возрастани€)
//     */
//    public List<User> findLimitedUsersOrderedByBirthday(Session session, int limit) {
////        return session.createQuery("select u from User u order by u.personalInfo.birth_day", User.class)
////                .setMaxResults(limit)
////                .list();
//
//        return new JPAQuery<User>(session)
//                .select(user)
//                .from(user)
//                .orderBy(user.personalInfo.birth_day.asc())
//                .limit(limit)
//                .fetch();
//    }
//
//    /**
//     * ¬озвращает всех сотрудников компании с указанным названием
//     */
//    public List<User> findAllByCompanyName(Session session, String companyName) {
////        return session.createQuery("select u from Company c " +
////                        "join c.users u where c.name = :companyName", User.class)
////                .setParameter("companyName", companyName)
////                .list();
//
//        return new JPAQuery<User>(session)
//                .select(user)
//                .from(company)
//                .join(company.users)
//                .where(company.name.eq(companyName))
//                .fetch();
//    }
//
//    /**
//     * ¬озвращает все выплаты, полученные сотрудниками компании с указанными именем,
//     * упор€доченные по имени сотрудника, а затем по размеру выплаты
//     */
//    public List<Payment> findAllPaymentsByCompanyName(Session session, String companyName) {
////        return session.createQuery("select p from Payment p " +
////                "join p.receiver u " +
////                        "join u.company c where c.name = :companyName " +
////                        "order by u.personalInfo.first_name, p.amount", Payment.class)
////                .setParameter("companyName", companyName)
////                .list();
//
//        return new JPAQuery<Payment>(session)
//                .select(payment)
//                .from(payment)
//                .join(payment.receiver, user).fetchJoin()
//                .join(user.company, company)
//                .where(company.name.eq(companyName))
//                .orderBy(user.personalInfo.first_name.asc(),payment.amount.asc())
//                .fetch();
//    }
//
//    /**
//     * ¬озвращает среднюю зарплату сотрудника с указанными именем и фамилией
//     */
//    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, PaymentFilter filter) {
////        return session.createQuery("select avg(p.amount) from Payment p " +
////                        "where p.receiver.personalInfo.first_name = :firstName and p.receiver.personalInfo.last_name = :lastName", Double.class)
////                .setParameter("firstName", firstName)
////                .setParameter("lastName", lastName)
////                .uniqueResult();
////        List<Predicate> predicates = new ArrayList<>();
////        if (filter.getFirstName() != null) {
////            predicates.add(user.personalInfo.first_name.eq(filter.getFirstName()));
////        }
////
////        if (filter.getLastName() != null) {
////            predicates.add(user.personalInfo.last_name.eq(filter.getLastName()));
////        }
//
//        var predicate = QPredicate.builder().add(filter.getFirstName(), user.personalInfo.first_name::eq)
//                .add(filter.getLastName(), user.personalInfo.last_name::eq)
//        .buildAnd();
//
//        return new JPAQuery<Double>(session)
//                .select(payment.amount.avg())
//                .from(payment)
//                .join(payment.receiver, user)
//                .where(predicate)
//                .fetchOne();
//    }
//
//    /**
//     * ¬озвращает дл€ каждой компании: название, среднюю зарплату всех еЄ сотрудников.  омпании упор€дочены по названию.
//     */
//    public List<com.querydsl.core.Tuple> findCompanyNamesWithAvgUserPaymentsOrderedByCompanyName(Session session) {
////        return session.createQuery("select c.name, avg(p.amount) from Company c " +
////                        "join c.users u " +
////                        "join u.payments p group by c.name " +
////                        "order by c.name", Object[].class)
////                .list();
//
//        return new JPAQuery<com.querydsl.core.Tuple>(session)
//                .select(company.name, payment.amount.avg())
//                .from(company)
//                .join(company.users, user)
//                .join(user.payments, payment)
//                .groupBy(company.name)
//                .orderBy(company.name.asc())
//                .fetch();
//    }
//
//    /**
//     * ¬озвращает список: сотрудник (объект User), средний размер выплат, но только дл€ тех сотрудников, чей средний размер выплат
//     * больше среднего размера выплат всех сотрудников
//     * ”пор€дочить по имени сотрудника
//     */
//    public List<com.querydsl.core.Tuple> isItPossible(Session session) {
//
////        return session.createQuery("select u, avg(p.amount) from User u " +
////                        "join u.payments p " +
////                        "group by u " +
////                        "having avg(p.amount) > (select avg(p.amount) from Payment p)" +
////                        "order by u.personalInfo.first_name", Object[].class)
////                .list();
//
//
//        return new JPAQuery<Tuple>(session)
//                .select(user, payment.amount.avg())
//                .from(user)
//                .join(user.payments, payment)
//                .groupBy(user.id)
//                .having(payment.amount.avg().gt(new JPAQuery<Double>(session)
//                                .select(payment.amount.avg())
//                        .from(payment)))
//                .orderBy(user.personalInfo.first_name.asc())
//                .fetch();
//    }
//
//    public static UserDao getInstance() {
//        return INSTANCE;
//    }
//}