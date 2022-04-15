package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.AutoBrand;
import ru.job4j.model.AutoModel;

import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<AutoModel> models = List.of(
                    AutoModel.of("Rav-4"),
                    AutoModel.of("Harrier"),
                    AutoModel.of("Hilux"),
                    AutoModel.of("HighLander"),
                    AutoModel.of("Camry")
            );
            for (AutoModel model : models) {
                session.save(model);
            }
            AutoBrand toyota = AutoBrand.of("Toyota");

            for (int i = 1; i < models.size(); i++) {
                toyota.addModel(session.load(AutoModel.class, i));
            }
            session.save(toyota);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
