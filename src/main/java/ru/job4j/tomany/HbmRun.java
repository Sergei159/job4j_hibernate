package ru.job4j.tomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.tomany.model.AutoBrand;
import ru.job4j.tomany.model.AutoModel;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {

    public static void main(String[] args) {
        List<AutoBrand> result = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            AutoBrand toyota = AutoBrand.of("Toyota");
            session.save(toyota);

            List<AutoModel> models = List.of(
                    AutoModel.of("Rav-4", toyota),
                    AutoModel.of("Harrier", toyota),
                    AutoModel.of("Hilux", toyota),
                    AutoModel.of("HighLander", toyota),
                    AutoModel.of("Camry", toyota)
            );
            for (AutoModel model : models) {
                session.save(model);
            }

            result = session.createQuery(
                    "select distinct b from AutoBrand b join fetch b.models "
            ).list();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (AutoModel model: result.get(0).getModels()) {
            System.out.println(model);
        }
    }
}
