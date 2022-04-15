package ru.job4j;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.AutoBrand;
import ru.job4j.model.AutoModel;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            AutoModel model = AutoModel.of("Rav-4");
            session.save(model);
            AutoModel model1 = AutoModel.of("Harrier");
            session.save(model1);
            AutoModel model2 = AutoModel.of("Hilux");
            session.save(model2);
            AutoModel model3 = AutoModel.of("HighLander");
            session.save(model3);
            AutoModel model4 = AutoModel.of("Camry");
            session.save(model4);



            AutoBrand toyota = AutoBrand.of("Toyota");
            toyota.addModel(session.load(AutoModel.class, 1));
//            for (int i = 1; i <= models.size(); i++) {
//                toyota.addModel(session.load(AutoModel.class, i));
//            }
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
