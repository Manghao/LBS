package org.lpro.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name="Sandwich.findAll",query="SELECT s FROM Sandwich s")
public class Sandwich implements Serializable {

    @Id
    private String id;

    private String nom;

    private String type_pain;

    private String description;

    private String img;

    @ManyToMany(mappedBy = "sandwich")
    private Set<Categorie> categorie = new HashSet<Categorie>();

    @ManyToMany
    private Set<Taille> taille = new HashSet<Taille>();

    public Sandwich() { }

    public Sandwich(String id, String nom, String pain, String description, String img) {
        this.id = id;
        this.nom = nom;
        this.type_pain = pain;
        this.description = description;
        this.img = img;
    }

    public Sandwich(Sandwich s) {
        this.id = s.getId();
        this.nom = s.getNom();
        this.type_pain = s.getType_pain();
        this.description = s.getDescription();
        this.img = s.getImg();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType_pain() {
        return type_pain;
    }

    public void setType_pain(String pain) {
        this.type_pain = pain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Set<Categorie> getCategorie() {
        return categorie;
    }

    public void setCategorie(Set<Categorie> categorie) {
        this.categorie = categorie;
    }

    public Set<Taille> getTaille() {
        return taille;
    }

    public void setTaille(Set<Taille> taille) {
        this.taille = taille;
    }
}
