# League of Legends API Documentation App

## Description
Cette application Android permet aux utilisateurs de consulter les détails des champions et des objets du jeu League of Legends en utilisant l'API officielle de Riot Games. L'application est conçue avec Jetpack Compose pour offrir une interface utilisateur moderne et réactive. Elle permet également aux utilisateurs de sélectionner différentes versions du jeu pour consulter les informations correspondantes.

## Fonctionnalités
- **Liste des Champions** : Affiche une liste de tous les champions disponibles dans la version sélectionnée du jeu.
- **Détail des Champions** : Affiche des informations détaillées sur chaque champion, y compris leur image, leur nom, leur titre, leur lore, et leurs sorts.
- **Liste des Objets** : Affiche une liste de tous les objets disponibles dans la version sélectionnée du jeu.
- **Changement de Version** : Permet aux utilisateurs de sélectionner la version du jeu pour afficher les informations correspondantes.
- **Chargement Paresseux (Lazy Loading)** : Utilisé pour charger les informations et les images des champions et des objets au fur et à mesure que l'utilisateur les fait défiler.

## Techniques et Outils Utilisés
### Jetpack Compose
Jetpack Compose est utilisé pour construire l'interface utilisateur de l'application. Compose permet de créer des interfaces utilisateur réactives et modernes avec moins de code.

### Lazy Loading
La liste des champions et des objets utilise des composants comme `LazyColumn` pour implémenter le chargement paresseux, ce qui signifie que les éléments ne sont chargés que lorsqu'ils deviennent visibles à l'écran. Cela améliore les performances et réduit l'utilisation de la mémoire.

### Gestion des Versions
L'application permet aux utilisateurs de sélectionner la version du jeu à partir d'un menu déroulant. Une fois la version sélectionnée, les informations affichées (champions, objets) sont mises à jour pour refléter cette version. Ceci est réalisé en récupérant les données de l'API correspondant à la version choisie.

### Images et Animation
Les images des champions et des sorts sont chargées à partir de l'API de Riot Games en utilisant la bibliothèque Coil. L'application utilise également Lottie pour afficher des animations de chargement attractives pendant le chargement des données.

### Architecture MVVM
L'application suit le modèle d'architecture MVVM (Model-View-ViewModel) pour séparer la logique de l'interface utilisateur et la gestion des données. Cela facilite la maintenance et le test de l'application.

## Bibliothèques Utilisées
- **Jetpack Compose** : Pour la création de l'interface utilisateur.
- **Coil** : Pour le chargement des images.
- **Lottie** : Pour les animations de chargement.
- **Retrofit** : Pour les appels réseau à l'API de Riot Games.
- **Kotlin Coroutines** : Pour la gestion des tâches asynchrones.
