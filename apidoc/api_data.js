define({ "api": [
  {
    "type": "post",
    "url": "/categories/:id/sandwichs",
    "title": "Ajoute un sandwich à une catégorie",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "get",
    "url": "/categories/:id/sandwichs",
    "title": "Récupère les sandwichs d'une catégorie",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "get",
    "url": "/categories",
    "title": "Récupère toutes les catégories",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "get",
    "url": "/categories/:id",
    "title": "Récupère une catégorie",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "post",
    "url": "/categories",
    "title": "Crée une nouvelle catégorie",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "delete",
    "url": "/categories/:id",
    "title": "Supprime une catégorie",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "put",
    "url": "/categories/:id",
    "title": "Modifie une catégorie",
    "name": "update",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/categorie/CategorieRessource.java",
    "groupTitle": "Categorie"
  },
  {
    "type": "post",
    "url": "/commandes",
    "title": "Crée une nouvelle commande",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/commande/CommandeRessource.java",
    "groupTitle": "Commande"
  },
  {
    "type": "get",
    "url": "/commandes/:id",
    "title": "Récupère une commande",
    "name": "getOneCommande",
    "group": "Commande",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "commandeId",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/commande/CommandeRessource.java",
    "groupTitle": "Commande"
  },
  {
    "type": "get",
    "url": "/sandwichs/:id",
    "title": "Récupère un sandwich",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "get",
    "url": "/sandwichs/:id/categories",
    "title": "Récupère les catégories d'un sandwich",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "get",
    "url": "/sandwichs",
    "title": "Récupère tous les sandwichs",
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
            "field": "andwichs",
            "description": "<p>Liste des sandwichs.</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "post",
    "url": "/sandwichs",
    "title": "Crée un nouveau sandwich",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "delete",
    "url": "/sandwichs/:id",
    "title": "Supprime un sandwich",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  },
  {
    "type": "put",
    "url": "/sandwichs/:id",
    "title": "Modifie un sandwich",
    "name": "update",
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
    "version": "0.0.0",
    "filename": "api/src/main/java/org/lpro/boundary/sandwich/SandwichRessource.java",
    "groupTitle": "Sandwich"
  }
] });
