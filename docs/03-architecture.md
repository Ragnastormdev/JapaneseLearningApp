# Architecture du projet

Version : 1.0

Statut : Validé

---

# 1. Objectif

Ce document décrit l'architecture logicielle de JapaneseLearningApp.

Il constitue la référence technique du projet.

Toute évolution devra respecter les principes décrits ici.

---

# 2. Philosophie

Le projet privilégie :

- la simplicité ;
- la lisibilité ;
- la maintenabilité ;
- l'évolutivité.

L'objectif n'est pas de construire une architecture complexe mais une architecture facile à comprendre et à faire évoluer.

Chaque choix technique doit pouvoir être expliqué simplement.

---

# 3. Architecture générale

L'application suit une architecture MVVM inspirée de la Clean Architecture.

Les responsabilités sont clairement séparées afin de limiter le couplage entre les différentes parties de l'application.

L'architecture est organisée autour de trois couches principales :

```
UI
↓

Domain
↓

Data
```

Les dépendances ne circulent que du haut vers le bas.

La couche Data ne connaît jamais l'interface utilisateur.

La couche UI ne manipule jamais directement la base de données.

---

# 4. Description des couches

## UI

Responsabilités :

- affichage ;
- navigation ;
- interaction utilisateur ;
- gestion de l'état de l'écran.

Elle contient notamment :

- écrans Compose ;
- ViewModels ;
- composants graphiques.

La couche UI ne contient aucune logique métier.

---

## Domain

Responsabilités :

- règles métier ;
- modèles métier ;
- cas d'utilisation.

Le Domain ne dépend d'aucune bibliothèque Android.

Cette couche représente le cœur de l'application.

---

## Data

Responsabilités :

- accès aux données ;
- lecture des fichiers JSON ;
- base Room ;
- repositories ;
- mapping des données.

Cette couche est la seule autorisée à communiquer avec les sources de données.

---

# 5. Flux des données

Le flux standard est le suivant :

```
Utilisateur

↓

Compose Screen

↓

ViewModel

↓

Use Case

↓

Repository

↓

Room / JSON

↓

Repository

↓

ViewModel

↓

Compose Screen
```

Toutes les fonctionnalités suivront ce principe.

---

# 6. Fonctionnement hors ligne

L'application est conçue selon le principe Offline First.

Toutes les données nécessaires au fonctionnement sont stockées localement.

Les contenus pédagogiques sont importés depuis des fichiers JSON lors du premier lancement.

La progression utilisateur est enregistrée dans Room.

Aucune connexion Internet n'est nécessaire.

---

# 7. Injection de dépendances

Le projet utilise Hilt.

Les dépendances seront injectées automatiquement afin de :

- limiter le couplage ;
- faciliter les tests ;
- simplifier la maintenance.

---

# 8. Navigation

La navigation repose sur Navigation Compose.

Chaque écran possède une responsabilité unique.

La navigation ne contient aucune logique métier.

---

# 9. Persistance

Les données pédagogiques sont stockées sous forme de fichiers JSON.

Les données utilisateur sont stockées dans Room.

Les repositories constituent l'unique point d'accès aux données.

---

# 10. Principes de développement

Chaque fonctionnalité doit respecter les règles suivantes :

- une seule responsabilité par classe ;
- une seule responsabilité par fonction ;
- éviter la duplication de code ;
- privilégier la lisibilité à l'optimisation prématurée ;
- documenter les décisions importantes.

---

# 11. Évolutivité

L'architecture doit permettre d'ajouter ultérieurement :

- les kanji ;
- le vocabulaire ;
- les phrases ;
- les exercices ;
- la synchronisation cloud.

Ces évolutions ne devront pas nécessiter une refonte de l'architecture.

---

# 12. Décisions validées

Les décisions suivantes sont considérées comme acquises :

- Kotlin
- Jetpack Compose
- Material Design 3
- MVVM
- Clean Architecture (inspirée)
- Room
- Hilt
- Navigation Compose
- Coroutines
- Flow
- JSON
- Offline First

Toute remise en question devra être justifiée par un avantage technique significatif.

---

# 13. Philosophie du projet

La priorité du projet est la suivante :

1. créer une application réellement utile pour apprendre le japonais ;
2. apprendre Android moderne ;
3. construire une architecture propre.

Chaque sprint doit rendre l'application plus utile à son utilisateur.

---

Fin du document.