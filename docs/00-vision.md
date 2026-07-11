# Vision du projet

## 1. Introduction

JapaneseLearningApp est une application Android personnelle destinée à accompagner l'apprentissage progressif du japonais.

Le projet est développé avec une approche de développement logiciel professionnelle afin de créer une application :

- utile au quotidien ;
- maintenable ;
- évolutive ;
- techniquement propre.

L'application est initialement conçue pour un usage personnel, mais son architecture doit permettre une publication future sur le Play Store si le projet atteint un niveau de maturité suffisant.

---

# 2. Vision produit

L'objectif est de créer un compagnon d'apprentissage permettant à l'utilisateur de progresser progressivement dans la maîtrise du japonais.

L'application doit favoriser :

- la mémorisation durable ;
- la régularité ;
- l'apprentissage actif ;
- l'association entre écriture et prononciation.

---

# 3. Philosophie pédagogique

L'application repose principalement sur :

- les flashcards ;
- la répétition espacée ;
- la reconnaissance visuelle ;
- l'écoute.

L'objectif n'est pas seulement de reconnaître un caractère, mais d'associer :
caractère japonais
+
prononciation
+
mémorisation


---

# 4. Périmètre de la version 1

La première version se concentre volontairement sur les bases :

## Inclus

- Hiragana
- Katakana
- Audio de prononciation
- Flashcards
- Répétition espacée
- Suivi de progression
- Quiz simples

## Exclus temporairement

- Kanji
- Vocabulaire
- Phrases
- Reconnaissance vocale
- Analyse de prononciation

Ces éléments pourront être ajoutés dans des versions futures.

---

# 5. Principes de développement

Le projet suit les principes suivants :

## Qualité avant rapidité

Une fonctionnalité doit être :

- terminée ;
- testée ;
- documentée ;
- versionnée.

---

## Simplicité contrôlée

L'objectif n'est pas de créer immédiatement une application complexe.

Chaque fonctionnalité doit répondre à un besoin réel.

---

## Architecture évolutive

Les choix techniques doivent permettre l'ajout futur de :

- kanji ;
- vocabulaire ;
- phrases ;
- exercices avancés.

---

# 6. Contraintes du projet

## Technique

Application :

- Android uniquement ;
- Kotlin ;
- Jetpack Compose ;
- fonctionnement hors ligne.

---

## Données

Les contenus pédagogiques seront stockés localement :

- fichiers JSON ;
- base Room.

Aucun serveur n'est nécessaire pour la première version.

---

# 7. Critère de réussite V1

La version 1 sera considérée comme réussie lorsque l'application permettra :

- d'apprendre les hiragana ;
- d'apprendre les katakana ;
- d'écouter la prononciation ;
- de réaliser des sessions de révision ;
- de suivre sa progression.

L'objectif est d'obtenir une application réellement utilisée au quotidien.

---

# 8. Évolution future

Les évolutions possibles :

Version 2 :
- Kanji

Version 3 :
- Vocabulaire

Version 4 :
- Phrases et lecture

Version 5 :
- Exercices avancés d'écoute et de prononciation

---

# Version du document

Version :
1.0

Date :
2026
