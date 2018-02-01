package org.lpro.entity.apiModels;

import io.swagger.annotations.ApiModel;

@ApiModel(value="PayCard", description="Modèle pour passer du JSON pour payer la commande")
public class PayCard {

    private String nom;

    private String numeroCarte;

    private String cvv;

    private String dateExpiration;

    private String numeroCarteFidelite;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getNumeroCarteFidelite() {
        return numeroCarteFidelite;
    }

    public void setNumeroCarteFidelite(String numeroCarteFidelite) {
        this.numeroCarteFidelite = numeroCarteFidelite;
    }
}
