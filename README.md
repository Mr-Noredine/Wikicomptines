# Wikicomptines

Application client-serveur TCP en C implémentant le protocole **WCP (Wikicomptine Protocol)** pour partager des comptines françaises sur un réseau.

**Auteur :** Noureddine MOHAMMEDI

---

## Compilation

```bash
make        # Compile les trois exécutables
make clean  # Supprime les fichiers compilés
```

## Utilisation

### Démarrer le serveur

```bash
./wcp_srv <dossier_comptines>
```

Exemple :
```bash
./wcp_srv comptines
```

Le serveur écoute sur le port **4321**.

### Démarrer le client

```bash
./wcp_clt <adresse_ipv4>
```

Exemple :
```bash
./wcp_clt 127.0.0.1
```

Le client se connecte au serveur, affiche la liste des comptines disponibles, puis demande à l'utilisateur d'en choisir une.

### Lancer les tests

```bash
./test_comptine_utils
```

Programme interactif qui teste les fonctions utilitaires (lecture de fichiers `.cpt`, création de catalogue, etc.).

---

## Structure du projet

```
Wikicomptines/
├── comptine_utils.h        # Structures de données et déclarations
├── comptine_utils.c        # Bibliothèque utilitaire (fichiers .cpt, catalogue)
├── wcp_srv.c               # Serveur TCP
├── wcp_clt.c               # Client TCP
├── test_comptine_utils.c   # Tests unitaires interactifs
├── Makefile
└── comptines/              # Fichiers de comptines (.cpt)
    ├── cerf.cpt
    ├── escargot.cpt
    ├── gaston.cpt
    ├── petit_lapin.cpt
    ├── poissons.cpt
    └── tortues.cpt
```

---

## Format des fichiers `.cpt`

Chaque fichier `.cpt` est un fichier texte dont :
- la **première ligne** est le titre de la comptine
- les **lignes suivantes** sont les paroles

Exemple (`poissons.cpt`) :
```
Les petits poissons dans l'eau
Nagent, nagent, nagent, nagent...
```

---

## Protocole WCP

| Étape | Direction | Format |
|-------|-----------|--------|
| Liste des comptines | Serveur → Client | `<nb>\n\t<0> <titre>\n\t<1> <titre>\n...` |
| Sélection | Client → Serveur | 2 octets en big-endian (network byte order) |
| Contenu de la comptine | Serveur → Client | Lignes de texte, terminées par `\n\n` |
