package ru.job4j.manytomany;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.manytomany.model.Author;
import ru.job4j.manytomany.model.Book;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book classicalMusicBook = Book.of("Classical music");
            Book jazzMusicBook = Book.of("Jazz music");
            Book bluesMinorBook = Book.of("Blues minor");
            Book syncopationBook = Book.of("Syncopation");

            Author max = Author.of("Max");
            Author joe = Author.of("Joe");
            Author miles = Author.of("Miles");

            max.getBooks().add(classicalMusicBook);
            joe.getBooks().add(classicalMusicBook);
            joe.getBooks().add(jazzMusicBook);
            joe.getBooks().add(bluesMinorBook);
            miles.getBooks().add(syncopationBook);
            miles.getBooks().add(jazzMusicBook);

            session.persist(max);
            session.persist(joe);
            session.persist(miles);

            session.remove(joe);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
