package org.lpro.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SandwichChoix implements Serializable {

    @Id
    private String id;

    @NotNull
    private String sandwich, taille;

    @ManyToMany
    private Set<Commande> commande = new HashSet<Commande>();

    public SandwichChoix() { }

    public SandwichChoix(String s, String t) {
        this.sandwich = s;
        this.taille = t;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSandwich() {
        return sandwich;
    }

    public void setSandwich(String sandwich) {
        this.sandwich = sandwich;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public Set<Commande> getCommande() {
        return commande;
    }

    public void setCommande(Set<Commande> commande) {
        this.commande = commande;
    }
}
