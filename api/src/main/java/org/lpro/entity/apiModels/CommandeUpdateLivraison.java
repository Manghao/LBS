package org.lpro.entity.apiModels;

import io.swagger.annotations.ApiModel;

@ApiModel(value="CommandeUpdateLivraison", description="Modèle pour passer du JSON pour modifier la date de livraison d'une commande")
public class CommandeUpdateLivraison {

    private String dateLivraison;

    private String heureLivraison;

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
}
