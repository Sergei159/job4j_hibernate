package ru.job4j.tomany.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brands")
public class AutoBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AutoModel> models = new ArrayList<>();

    public AutoBrand() {
    }

    public static AutoBrand of(String name) {
        AutoBrand brand = new AutoBrand();
        brand.name = name;
        return brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AutoModel> getModels() {
        return models;
    }

    public void setModels(List<AutoModel> models) {
        this.models = models;
    }

    public void addModel(AutoModel model) {
        this.models.add(model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AutoBrand brand = (AutoBrand) o;
        return id == brand.id && Objects.equals(name, brand.name) && Objects.equals(models, brand.models);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, models);
    }
}
