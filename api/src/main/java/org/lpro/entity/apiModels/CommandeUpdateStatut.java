package org.lpro.entity.apiModels;

import io.swagger.annotations.ApiModel;

@ApiModel(value="CommandeUpdateStatut", description="Modèle pour passer du JSON pour modifier le statut d'une commande")
public class CommandeUpdateStatut {

    private String statut;

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
