package org.lpro.entity.apiModels;

import io.swagger.annotations.ApiModel;

@ApiModel(value="NewCommande", description="Modèle pour passer du JSON pour créer une commande")
public class NewCommande {

    private String nom;

    private String prenom;

    private String mail;

    private String dateLivraison;

    private String heureLivraison;

    private String adresseLivraison;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }
}
