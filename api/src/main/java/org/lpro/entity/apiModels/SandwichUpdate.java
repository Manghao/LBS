package org.lpro.entity.apiModels;

import io.swagger.annotations.ApiModel;

@ApiModel(value="SandwichUpdate", description="Modèle pour passer du JSON pour modifier un sandwich d'une commande")
public class SandwichUpdate {

    private String taille;

    private int qte;

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
}
