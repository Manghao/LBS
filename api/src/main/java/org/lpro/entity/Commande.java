package org.lpro.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Commande implements Serializable {

    @Id
    private String id;

    @NotNull
    private String dateLivraison;

    @NotNull
    private String heureLivraison;

    private String token;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToMany(mappedBy = "commande")
    private Set<Sandwich> sandwich = new HashSet<Sandwich>();

    public Commande() {  }

    public Commande(Utilisateur utilisateur, String dateLivraison, String heureLivraison) {
        this.utilisateur = utilisateur;
        this.dateLivraison = dateLivraison;
        this.heureLivraison = heureLivraison;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getHeureLivraison() {
        return heureLivraison;
    }

    public void setHeureLivraison(String heureLivraison) {
        this.heureLivraison = heureLivraison;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Set<Sandwich> getSandwich() {
        return sandwich;
    }

    public void setSandwich(Set<Sandwich> sandwich) {
        this.sandwich = sandwich;
    }

}