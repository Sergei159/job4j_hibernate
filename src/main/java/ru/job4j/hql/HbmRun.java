package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hql.model.Candidate;
import ru.job4j.hql.model.Vacancy;
import ru.job4j.hql.model.VacancyDB;


public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Max", 1.5, 100_000);
            session.save(one);

            VacancyDB vacancyDB = VacancyDB.of("A-group");
            vacancyDB.addVacancy(Vacancy.of("Cpp developer"));
            vacancyDB.addVacancy(Vacancy.of("Java developer"));
            vacancyDB.addVacancy(Vacancy.of("C# developer"));

            one.setVacancyDB(vacancyDB);
            session.save(vacancyDB);


            Candidate result = session.createQuery(
                    "select distinct c from Candidate c "
                    + "join fetch c.vacancyDB b "
                    + "join fetch b.vacancies v "
                    + "where c.id = :cId", Candidate.class
                    ).setParameter("cId", 1).uniqueResult();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
