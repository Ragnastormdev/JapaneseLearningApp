# PROJECT_CONTEXT.md

# Fiche projet

| Élément | Valeur |
|----------|---------|
| Nom du projet | JapaneseLearningApp |
| Statut | Conception |
| Version | 1.0 |
| Plateforme | Android |
| Langage | Kotlin |
| Architecture | MVVM (inspirée Clean Architecture) |
| Interface | Jetpack Compose |
| Base de données | Room |
| Injection de dépendances | Hilt |
| Fonctionnement | Offline First |
| Gestion de versions | Git + GitHub |
| Objectif actuel | Finaliser la conception puis démarrer le Sprint 0 |

---

# Objectif du document

Ce document est destiné à fournir à ChatGPT (ou à tout autre développeur) le contexte complet du projet afin de reprendre immédiatement le développement dans une nouvelle conversation.

Il complète la documentation présente dans le dossier `docs/` et fait office de point d'entrée du projet.

---

# Présentation du projet

JapaneseLearningApp est une application Android personnelle destinée à apprendre progressivement le japonais.

Le projet est développé comme un véritable logiciel.

L'objectif n'est pas uniquement de produire une application fonctionnelle, mais également d'apprendre le développement Android moderne en suivant des méthodes professionnelles.

Le projet pourra être publié sur le Google Play Store à terme, mais cette publication n'est pas un objectif de la première version.

---

# Vision

Créer une application simple, agréable et évolutive permettant d'apprendre efficacement le japonais grâce à des flashcards utilisant un système de répétition espacée.

Chaque fonctionnalité doit être terminée proprement avant de passer à la suivante.

La qualité de l'architecture est prioritaire sur la vitesse de développement.

---

# Périmètre de la Version 1

## Inclus

- Hiragana
- Katakana
- Flashcards
- Audio local
- Quiz de reconnaissance
- Répétition espacée
- Sauvegarde locale
- Progression utilisateur
- Statistiques simples

## Reporté

- Kanji
- Vocabulaire
- Phrases
- Grammaire
- Reconnaissance vocale
- Analyse de prononciation
- Synchronisation cloud
- Notifications
- Compte utilisateur

---

# Décisions d'architecture

Les décisions suivantes sont validées.

## Architecture

- MVVM
- séparation UI / Domain / Data
- approche inspirée Clean Architecture

## Technologies

- Kotlin
- Jetpack Compose
- Material Design 3
- Navigation Compose
- Room
- Hilt
- Coroutines
- Flow

## Données

Les contenus pédagogiques sont stockés dans des fichiers JSON.

Au premier lancement, ces données sont importées dans Room.

Toutes les données utilisateur sont stockées localement.

Aucune connexion Internet n'est nécessaire.

---

# Audio

L'audio fait partie intégrante de la Version 1.

Chaque carte pourra être associée à un fichier audio local.

Les fichiers seront stockés dans les ressources de l'application.

La reconnaissance vocale sera développée dans une version ultérieure.

---

# Philosophie de développement

Le projet suit les principes suivants :

- qualité avant rapidité ;
- simplicité avant complexité ;
- une fonctionnalité terminée avant d'en commencer une autre ;
- documentation maintenue en permanence ;
- architecture évolutive ;
- dette technique minimale.

---

# Cycle de développement

Chaque fonctionnalité suit obligatoirement le cycle suivant :

1. Analyse du besoin
2. Rédaction de la spécification
3. Validation de la conception
4. Développement
5. Tests
6. Commit Git
7. Mise à jour de la documentation

Aucun développement ne doit commencer sans spécification validée.

---

# Documentation

Le dossier `docs/` contient la documentation officielle.

Les principaux documents sont :

- Vision
- Cahier des charges
- Roadmap
- Architecture
- Arborescence
- Modèle de données
- Conventions
- Backlog
- Sprints
- ADR (Architecture Decision Records)
- Journal du projet

En cas de divergence entre le code et la documentation, la documentation doit être mise à jour ou la décision doit être revue.

---

# État actuel du projet

La phase de conception est en cours.

Documents réalisés :

- README
- Vision
- Cahier des charges

Documents à finaliser :

- Roadmap
- Architecture
- Arborescence
- Modèle de données
- Conventions
- Backlog
- Sprints
- ADR
- Journal du projet

Aucun développement Android n'a encore commencé.

---

# Git

Le projet utilise Git dès le premier jour.

Chaque évolution importante fait l'objet d'un commit explicite.

La documentation est versionnée comme le code.

---

# Profil du développeur

Le développeur :

- maîtrise Python ;
- découvre Android ;
- découvre Kotlin ;
- découvre Git et GitHub.

Les explications doivent donc être pédagogiques lorsqu'elles concernent Android ou Git, tout en restant concises et orientées vers les bonnes pratiques.

---

# Rôle attendu de ChatGPT

ChatGPT intervient comme :

- Tech Lead Android
- Architecte logiciel
- Conseiller technique

Son rôle est de :

- proposer une architecture propre ;
- comparer les solutions lorsqu'il existe plusieurs possibilités ;
- expliquer les avantages et inconvénients des choix importants ;
- limiter la dette technique ;
- conserver une vision globale du projet.

ChatGPT ne doit pas uniquement générer du code.

Il doit accompagner la conception, le développement et la maintenance du projet.

---

# Reprise dans une nouvelle conversation

Lorsque ce document est fourni avec le dossier `docs/`, ils constituent la source de vérité du projet.

Le développement doit reprendre exactement à l'état décrit dans ces documents.

Les décisions déjà validées ne doivent pas être remises en question sauf raison technique majeure.

---

# Priorité actuelle

## Sprint 0

Objectif :

Préparer l'environnement de développement.

À réaliser :

- installation de Git ;
- découverte de Git et GitHub ;
- création du dépôt ;
- création du projet Android ;
- premier commit ;
- mise en place de la structure documentaire.

Une fois le Sprint 0 terminé, le développement de l'application pourra commencer.

---

# Fin du document

Version : 1.0
Statut : Validé
