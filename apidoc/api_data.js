define({ "api": [
  {
    "type": "post",
    "url": "/categories/:id/sandwichs",
    "title": "Ajouter un sandwich à une catégorie",
    "name": "addSandwichToCategorie",
    "group": "Categorie",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'une catégorie.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Sandwich",
            "optional": false,
            "field": "sandwich",
            "description": "<p>Le sandwich ajouté à la catégorie.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "get",
    "url": "/categories/:id/sandwichs",
    "title": "Récupérer les sandwichs d'une catégorie",
    "name": "getCategorieSandwichs",
    "group": "Categorie",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'une catégorie.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "List",
            "optional": false,
            "field": "sandwichs",
            "description": "<p>Les sandwichs d'une catégorie.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "get",
    "url": "/categories",
    "title": "Récupérer toutes les catégories",
    "name": "getCategories",
    "group": "Categorie",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "List",
            "optional": false,
            "field": "categories",
            "description": "<p>Liste des catégories.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "get",
    "url": "/categories/:id",
    "title": "Récupérer une catégorie",
    "name": "getOneCategorie",
    "group": "Categorie",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'une catégorie.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Categorie",
            "optional": false,
            "field": "categorie",
            "description": "<p>Une catégorie.</p>"
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "CategorieNotFound",
            "description": "<p>L'<code>id</code> de la catégorie n'existe pas.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "post",
    "url": "/categories",
    "title": "Créer une nouvelle catégorie",
    "name": "newCategorie",
    "group": "Categorie",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Categorie",
            "optional": false,
            "field": "categorie",
            "description": "<p>La catégorie nouvellement créée.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "delete",
    "url": "/categories/:id",
    "title": "Supprimer une catégorie",
    "name": "removeCategorie",
    "group": "Categorie",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'une catégorie.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Status",
            "optional": false,
            "field": "status",
            "description": "<p>Retourne le code 204 (No Content).</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "put",
    "url": "/categories/:id",
    "title": "Modifier une catégorie",
    "name": "updateCategorie",
    "group": "Categorie",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'une catégorie.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Categorie",
            "optional": false,
            "field": "categorie",
            "description": "<p>La catégorie modifiée.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "post",
    "url": "/commandes",
    "title": "Créer une nouvelle commande",
    "name": "addCommande",
    "group": "Commande",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Commande",
            "optional": false,
            "field": "commande",
            "description": "<p>Une commande.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/commande/CommandeRessource.java",
    "groupTitle": "Commande"
  },
  {
    "type": "post",
    "url": "/commandes/:id/sandwichs",
    "title": "Ajouter un sandwich à une commande",
    "name": "addSandwichToCommande",
    "group": "Commande",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'une commande.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>token unique d'une commande passé en paramètre de l'url.</p>"
          },
          {
            "group": "Parameter",
            "type": "Sandwich",
            "optional": false,
            "field": "s",
            "description": "<p>sandwich à ajouter à la commande.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "r",
            "description": "<p>ésultat String indiquant que le sandwich a bien été ajouté à la commande.</p>"
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "CommandeNotFound",
            "description": "<p>L'<code>id</code> de la commande n'existe pas.</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "CommandeForbidden",
            "description": "<p>Le <code>token</code> de la commande n'existe pas ou n'est pas le bon.</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "SandwichNotFound",
            "description": "<p>Le <code>sandwich</code> à ajouter n'existe pas.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/commande/CommandeRessource.java",
    "groupTitle": "Commande"
  },
  {
    "type": "get",
    "url": "/commandes/:id",
    "title": "Récupérer une commande",
    "name": "getOneCommande",
    "group": "Commande",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'une commande.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>token unique d'une commande passé en paramètre de l'url.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Commande",
            "optional": false,
            "field": "commande",
            "description": "<p>Une commande.</p>"
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "CommandeNotFound",
            "description": "<p>L'<code>id</code> de la commande n'existe pas.</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "CommandeForbidden",
            "description": "<p>Le <code>token</code> de la commande n'existe pas ou n'est pas le bon.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/commande/CommandeRessource.java",
    "groupTitle": "Commande"
  },
  {
    "type": "put",
    "url": "/commandes/:id",
    "title": "Modifier la date et l'heure de livraison d'une commande",
    "name": "updateCommande",
    "group": "Commande",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'une commande.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "token",
            "description": "<p>token unique d'une commande passé en paramètre de l'url.</p>"
          },
          {
            "group": "Parameter",
            "type": "Commande",
            "optional": false,
            "field": "c",
            "description": "<p>commande contenant la nouvelle date et heure de livraison.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Commande",
            "optional": false,
            "field": "commande",
            "description": "<p>Une commande.</p>"
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "CommandeNotFound",
            "description": "<p>L'<code>id</code> de la commande n'existe pas.</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "CommandeForbidden",
            "description": "<p>Le <code>token</code> de la commande n'existe pas ou n'est pas le bon.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/commande/CommandeRessource.java",
    "groupTitle": "Commande"
  },
  {
    "type": "get",
    "url": "/sandwichs/:id",
    "title": "Récupérer un sandwich",
    "name": "getOneSandwich",
    "group": "Sandwich",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'un sandwich.</p>"
          },
          {
            "group": "Parameter",
            "type": "Int",
            "optional": true,
            "field": "optional",
            "description": "<p>details permet d'afficher la description détaillée ou non d'un sandwich.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Sandwich",
            "optional": false,
            "field": "sandwich",
            "description": "<p>Un sandwich.</p>"
          }
        ]
      }
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "SandwichNotFound",
            "description": "<p>L'<code>id</code> du sandwich n'existe pas.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "get",
    "url": "/sandwichs/:id/categories",
    "title": "Récupérer les catégories d'un sandwich",
    "name": "getSandwichCategories",
    "group": "Sandwich",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'un sandwich.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "List",
            "optional": false,
            "field": "categories",
            "description": "<p>Les catégories d'un sandwich.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "get",
    "url": "/sandwichs",
    "title": "Récupérer tous les sandwichs",
    "name": "getSandwichs",
    "group": "Sandwich",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Int",
            "optional": true,
            "field": "optional",
            "description": "<p>page numéro de la page courante.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "List",
            "optional": false,
            "field": "sandwichs",
            "description": "<p>Liste des sandwichs.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "post",
    "url": "/sandwichs",
    "title": "Créer un nouveau sandwich",
    "name": "newSandwich",
    "group": "Sandwich",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Sandwich",
            "optional": false,
            "field": "sandwich",
            "description": "<p>Le sandwich nouvellement créé.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "delete",
    "url": "/sandwichs/:id",
    "title": "Supprimer un sandwich",
    "name": "removeSandwich",
    "group": "Sandwich",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'un sandwich.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Status",
            "optional": false,
            "field": "status",
            "description": "<p>Retourne le code 204 (No Content).</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "put",
    "url": "/sandwichs/:id",
    "title": "Modifier un sandwich",
    "name": "updateSandwich",
    "group": "Sandwich",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>ID unique d'un sandwich.</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Sandwich",
            "optional": false,
            "field": "sandwich",
            "description": "<p>Le sandwich modifié.</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  }
] });
