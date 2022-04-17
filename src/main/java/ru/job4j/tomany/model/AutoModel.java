package ru.job4j.tomany.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name  = "models")
public class AutoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "brands_id")
    private AutoBrand brand;

    public AutoModel() {
    }

    public static AutoModel of(String name, AutoBrand brand) {
        AutoModel model = new AutoModel();
        model.name = name;
        model.brand = brand;
        return model;
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

    public AutoBrand getBrand() {
        return brand;
    }

    public void setBrand(AutoBrand brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AutoModel model = (AutoModel) o;
        return id == model.id && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    @Override
    public String toString() {
        return "AutoModel{"
                + "id=" + id
                + ", name='" + name
                + ", brand='" + brand + '\''
                + '}';
    }


}
