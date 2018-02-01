package org.lpro.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Carte {

    @Id
    private String id;

    private String numcarte;

    private String nom;

    private String prenom;

    private String adresse;

    @NotNull
    private String password;

    private double montant;

    public Carte() { }

    public Carte(String numcarte, String pwd) {
        this.numcarte = numcarte;
        this.password = pwd;
    }

    public Carte(String nom, String prenom, String adresse, String pwd) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.password = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getNumCarte() {
        return numcarte;
    }

    public void setNumCarte(String numcarte) {
        this.numcarte = numcarte;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
