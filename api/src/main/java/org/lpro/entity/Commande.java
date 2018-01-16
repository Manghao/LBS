package org.lpro.entity;

import org.lpro.enums.CommandeStatut;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Commande implements Serializable {

    @Id
    private String id;

    @NotNull
    private String dateLivraison;

    @NotNull
    private String heureLivraison;

    private String token;

    @Enumerated(EnumType.STRING)
    private CommandeStatut statut;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToMany(mappedBy = "commande")
    private Set<SandwichChoix> sandwichChoix = new HashSet<SandwichChoix>();

    public Commande() { }

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

    public CommandeStatut getStatut() {
        return statut;
    }

    public void setStatut(CommandeStatut statut) {
        this.statut = statut;
    }

    public Set<SandwichChoix> getSandwichChoix() {
        return sandwichChoix;
    }

    public void setSandwichChoix(Set<SandwichChoix> sandwichChoix) {
        this.sandwichChoix = sandwichChoix;
    }
}