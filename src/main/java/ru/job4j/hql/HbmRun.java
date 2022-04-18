package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hql.model.Candidate;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Max", 1.5, 100_000);
            Candidate two = Candidate.of("Joe", 6.0, 500_000);
            Candidate three = Candidate.of("John", 0.0, 50_000);

            session.save(one);
            session.save(two);
            session.save(three);

            /**
             * Выборка всех кандидатов
             */
            List<Candidate> allCandidates = session.createQuery("from Candidate").list();

            /**
             * Выборка по id
             */
            Candidate candidate = (Candidate) session.createQuery("from Candidate where id = 1").uniqueResult();

            /**
             * Выборка по name
             */
            List<Candidate> listCandidatesByName = session.createQuery("from Candidate where name = 'Max'").list();

            /**
             * update
             */
            session.createQuery("update Candidate c "
                            + "set c.name = :newName, experience = :newExp, c.salary = :newSalary where c.id = :id")
                    .setParameter("newName", "Maxim")
                    .setParameter("newExp", 2.0)
                    .setParameter("newSalary", 150_000)
                    .setParameter("id", 1)
                    .executeUpdate();

            /**
             * Удаление
             */
            session.createQuery("delete from Candidate where id = :id")
                    .setParameter("id", 3)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
