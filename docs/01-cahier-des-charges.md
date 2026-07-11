# Cahier des charges

Version : 1.0

---

# 1. Présentation

## Contexte

Le projet consiste à développer une application Android moderne permettant l'apprentissage progressif du japonais.

L'application est destinée dans un premier temps à un usage personnel. Elle devra cependant être conçue avec une qualité suffisante pour permettre une publication sur le Google Play Store si le projet évolue dans cette direction.

Le développement suivra une approche professionnelle basée sur une architecture propre, une documentation complète et une évolution progressive des fonctionnalités.

---

# 2. Objectifs

L'application doit permettre à l'utilisateur :

- d'apprendre les hiragana ;
- d'apprendre les katakana ;
- d'associer chaque caractère à sa prononciation ;
- de mémoriser durablement les caractères grâce à la répétition espacée ;
- de suivre sa progression.

L'objectif principal est de construire une base solide avant d'ajouter des contenus plus avancés.

---

# 3. Public cible

## Version actuelle

Le développeur lui-même.

## Évolution possible

Toute personne souhaitant apprendre le japonais à partir de zéro.

---

# 4. Périmètre fonctionnel de la Version 1

La Version 1 comprend uniquement les fonctionnalités suivantes.

## Apprentissage

- Hiragana
- Katakana

## Flashcards

- affichage des cartes
- affichage du romaji
- possibilité de masquer le romaji (fonctionnalité future de la V1 si le temps le permet)

## Audio

- lecture de la prononciation
- fichiers audio locaux
- fonctionnement hors connexion

## Quiz

- reconnaissance visuelle
- questions simples
- validation de la réponse

## Répétition espacée

- planification automatique des révisions
- calcul de la prochaine date de révision
- historique des révisions

## Progression

- nombre de caractères maîtrisés
- progression globale
- cartes à revoir

---

# 5. Fonctionnalités hors périmètre

Les fonctionnalités suivantes sont volontairement exclues de la Version 1.

- Kanji
- vocabulaire
- phrases
- grammaire
- reconnaissance vocale
- analyse de prononciation
- synchronisation cloud
- compte utilisateur
- notifications
- classement
- mode multijoueur

Ces fonctionnalités feront l'objet de versions futures.

---

# 6. Exigences fonctionnelles

L'application devra permettre :

- de consulter les cartes d'apprentissage ;
- d'écouter la prononciation d'un caractère ;
- de lancer une session de révision ;
- d'enregistrer les réponses de l'utilisateur ;
- de calculer automatiquement les prochaines révisions ;
- de sauvegarder la progression.

---

# 7. Exigences non fonctionnelles

L'application devra :

- fonctionner entièrement hors connexion ;
- être rapide et fluide ;
- être simple d'utilisation ;
- conserver les données localement ;
- être maintenable ;
- être facilement extensible.

---

# 8. Contraintes techniques

Le projet utilisera :

- Kotlin
- Jetpack Compose
- Material Design 3
- MVVM
- Room
- Hilt
- Navigation Compose
- Coroutines
- Flow
- fichiers JSON
- Git
- GitHub

---

# 9. Critères de réussite

La Version 1 sera considérée comme terminée lorsque l'utilisateur pourra :

- apprendre les hiragana ;
- apprendre les katakana ;
- écouter la prononciation de chaque caractère ;
- réaliser des sessions de révision ;
- retrouver automatiquement les caractères à réviser ;
- suivre sa progression.

---

# 10. Évolutions prévues

Après la Version 1, les évolutions envisagées sont :

Version 2

- Kanji

Version 3

- Vocabulaire

Version 4

- Phrases

Version 5

- Exercices avancés
- Prononciation
- Écoute
- Publication Play Store

---

# Validation

Statut :

✅ Validé

Version :

1.0
