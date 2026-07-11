# JapaneseLearningApp
appli pour apprendre le japonais
# JapaneseLearningApp

Application Android personnelle d'apprentissage du japonais.

## 1. Présentation du projet

JapaneseLearningApp est une application Android conçue pour accompagner l'apprentissage progressif du japonais.

L'objectif initial est de créer un outil personnel permettant d'apprendre les systèmes d'écriture japonais grâce à une approche basée sur :

* les flashcards ;
* la répétition espacée ;
* l'audio ;
* le suivi de progression.

Le projet est développé avec une approche professionnelle afin de rester maintenable et évolutif.

---

# 2. Objectif de la version 1

La première version de l'application se concentre sur les kana :

* Hiragana ;
* Katakana.

Fonctionnalités prévues :

* apprentissage des caractères ;
* flashcards ;
* audio de prononciation ;
* quiz ;
* répétition espacée ;
* progression utilisateur.

Les fonctionnalités suivantes sont volontairement reportées :

* Kanji ;
* vocabulaire ;
* phrases ;
* reconnaissance vocale.

---

# 3. Philosophie du projet

Le projet suit plusieurs principes :

* construire une application durable plutôt qu'un prototype rapide ;
* privilégier une architecture propre ;
* documenter les décisions importantes ;
* développer par petites fonctionnalités terminées ;
* éviter la dette technique.

---

# 4. Technologies utilisées

## Application Android

* Kotlin
* Jetpack Compose
* Material Design 3
* Navigation Compose

## Architecture

* MVVM
* approche inspirée Clean Architecture

## Données

* Room Database
* fichiers JSON pour les contenus pédagogiques

## Injection de dépendances

* Hilt

## Versionnement

* Git
* GitHub

---

# 5. Architecture générale

Organisation simplifiée :

```
UI (Compose)

↓

ViewModel

↓

Use Cases

↓

Repository

↓

Data Sources

(Room / JSON)
```

Chaque couche possède une responsabilité clairement définie.

---

# 6. Organisation du projet

```
JapaneseLearningApp

├── app
│
├── docs
│
├── assets
│
└── README.md
```

Le dossier `docs` contient la documentation complète du projet.

---

# 7. Documentation

Documents disponibles :

```
docs/

00-vision.md
01-cahier-des-charges.md
02-roadmap.md
03-architecture.md
04-arborescence.md
05-modele-de-donnees.md
06-conventions.md
07-backlog.md
08-sprints.md
09-decisions-architecture.md
10-journal-de-projet.md
11-learning-notes.md
12-questions-en-attente.md
```

---

# 8. État du projet

Statut actuel :

Phase :

```
Conception terminée
```

Prochaine étape :

```
Sprint 0
- Installation des outils
- Mise en place Git/GitHub
- Création du projet Android
```

---

# 9. Objectif long terme

À terme, l'application pourra évoluer vers :

* apprentissage des kanji ;
* vocabulaire ;
* phrases ;
* lecture ;
* compréhension orale ;
* exercices avancés.

La V1 doit cependant rester centrée sur les fondamentaux : maîtriser les kana efficacement.

---

# 10. Auteur

Projet personnel d'apprentissage Android et japonais.

Version du document :

```
1.0
```
