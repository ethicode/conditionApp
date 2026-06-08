# Spécifications fonctionnelles - Workflow gestion des conditions dérogatoires (V2)

## Informations de version

| Version | Date | Auteur | Modification |
| --- | --- | --- | --- |
| 1.0 | 13/11/2025 | SECK Ramatoulaye | Création |
| 2.0 | 19/02/2026 | SECK Ramatoulaye | Modification |

Dernière mise à jour mentionnée dans le document source : 19/02/2026.

## Table des matières

1. Contexte et objectifs
2. Périmètre fonctionnel
3. Fonctionnalités clés
4. Définition des rôles
5. Workflow détaillé (états et transitions)
6. SLA
7. Tableaux de bord
8. Exigences techniques
9. Synthèse des étapes (tableau)

## 1. Contexte et objectifs

Le workflow vise à industrialiser et automatiser le processus de mise en place des conditions dérogatoires, en garantissant la traçabilité, la conformité et la fluidité des échanges entre les différents acteurs :

- ACE
- Chargé de clientèle
- Responsable de marché
- Directeur Commercial Entreprise (DCE)
- Agent de saisie SIOP
- Valideur SIOP

## 2. Périmètre fonctionnel

Le système doit permettre :

- Création et suivi des demandes de modification des conditions dérogatoires via un formulaire intégré.
- Personnalisation des types de demandes et des écrans.
- Gestion des rôles et permissions selon un RACI.
- Surveillance des flux via des tableaux de bord.
- Notifications par mail.
- Recherche avancée et reporting.
- Extraction au format Excel du formulaire de demande.
- Extraction au format Excel des tickets de demande.
- Configuration des SLA convenus.
- Mise en place de KPI.

## 3. Fonctionnalités clés

### 3.1 Création et gestion des demandes

- Formulaire dynamique pour saisir les informations nécessaires à la demande de modification.
- Génération automatique d'un identifiant unique pour chaque demande.
- Possibilité de joindre des documents :
  - Lot 1 : ajout direct de pièces jointes.
  - Lot 2 (évolution) : intégration d'une GED.

### 3.2 Base de connaissance

- Évolution lot 2 : espace dédié pour consulter procédures et modes opératoires.

### 3.3 Notifications

- Automatiques à chaque changement d'état.
- Configurables par rôle.

### 3.4 Reporting et recherche

- Filtres par statut, acteur, date, type de demande.
- Export en Excel/PDF.

## 4. Définition des rôles

- ACE (demandeur) : initie la demande.
- Chargé de clientèle : valide la demande.
- Responsable de marché : valide la demande.
- DCE : dernière validation métier avant saisie.
- NB : le profil DCE doit pouvoir être habilité pour deux utilisateurs (Directeur Commercial Entreprise et DGA Exploitation).
- Agent SIOP : contrôle et saisie.
- Valideur SIOP RUN : contrôle et validation finale.
- Métier ACE : confirmation et clôture.

## 5. Workflow détaillé (états et transitions)

1. Création
- ACE complète le formulaire.
- Statut : Nouvelle demande.

2. Contrôle Chargé de clientèle
- Réception de la notification mail.
- Vérification de la conformité.
- Si conforme : commentaire de validation et envoi en validation RM.
- Statut cible : En attente validation RM.
- Sinon : retour ACE.
- Statut cible : En attente correction.

3. Validation Responsable de marché
- Réception de la notification mail.
- Vérification de la conformité.
- Si conforme : commentaire de validation et envoi en validation DCE.
- Statut cible : En attente validation DCE.
- Sinon : retour ACE.
- Statut cible : En attente correction.

4. Validation Directeur Commercial Entreprise (DCE)
- Réception de la notification mail.
- Vérification de la conformité.
- Si conforme : commentaire de validation et envoi pour traitement SIOP.
- Notification Agent SIOP.
- Statut cible : Déclaré.
- Sinon : retour ACE.
- Statut cible : En attente correction.

5. Saisie Agent SIOP
- Réception de la notification mail.
- Prise en charge (auto-affectation) et contrôle.
- Si non conforme : retour ACE.
- Statut cible : En attente d'information.
- Si conforme : saisie dans Amplitude.
- Statut cible : En attente de validation SIOP.

6. Validation SIOP RUN
- Réception de la notification mail.
- Prise en charge et vérification conformité.
- Statut intermédiaire : En cours de validation.
- Si conforme : saisie dans Amplitude puis commentaire de validation.
- Statut cible : Validé.
- Sinon : retour Agent SIOP.
- Statut cible : En cours.

7. Finalisation traitement Agent SIOP
- Réception de la notification mail.
- Si validé : intégrer l'extraction des conditions paramétrées.
- Envoi d'une demande de confirmation au métier avec commentaire de fin de traitement.
- Statut cible : Résolu.

8. Clôture ACE / Référent métier
- Réception de la notification mail.
- Si conforme : commentaire de confirmation.
- Statut cible : Clôturé.
- Si non conforme : commentaire de rejet et retour Agent SIOP.
- Statut cible : En cours.

## 6. SLA

Les SLA doivent être calculés :

- Sur les jours/heures ouvrables : 7h30 à 16h30.
- En excluant les jours fériés.

SLA attendus :

- SLA global de traitement : commence à la prise en charge par l'agent SIOP et se termine au statut Résolu.
- SLA de prise en charge saisie : de la fin de validation DCE à la prise en charge par l'agent SIOP.
- SLA de saisie : de la prise en charge par l'agent SIOP au début de la validation SIOP.
- SLA de prise en charge validation : de l'envoi en validation par l'agent SIOP à la prise en charge par le valideur SIOP.
- SLA validation : de la prise en charge par le valideur SIOP à la fin de validation.
- SLA retour métier : de l'envoi au métier pour informations complémentaires jusqu'au retour métier.
- Arrêt du chrono des autres SLA dès l'enclenchement du SLA retour métier.

## 7. Tableaux de bord

- Vue globale : nombre de demandes par statut.
- Vue par rôle : tâches en attente.
- KPI durée de traitement : basé sur les SLA dès passage au statut Validé par le valideur SIOP.
- KPI taux qualité de traitement : basé sur confirmation/rejet métier.
- KPI qualité entrante : basé sur les retours métier.
- KPI durée retour métier : basé sur le SLA retour métier.

## 8. Exigences techniques

- Interface web responsive.
- Sécurité : authentification et gestion des rôles.
- Intégration GED.
- Base de données avec traçabilité complète.
- Workflow de gestion des conditions dérogatoires.

## 9. Synthèse des étapes (tableau)

| Étape | Acteur | Statut cible | SLA indicatif | Description |
| --- | --- | --- | --- | --- |
| Initialiser la demande | ACE | Nouvelle demande | À convenir avec le métier | Création de la demande via formulaire |
| Contrôle de conformité | Chargé de clientèle / Référent métier | En attente validation RM | À convenir avec le métier | Contrôle de conformité de la demande |
| Validation demande | Responsable de marché | En attente validation DCE | À convenir avec le métier | Validation RM |
| Validation demande | DCE | En attente saisie | À convenir avec le métier | Validation DCE |
| Traitement demande | Agent SIOP | En cours | SLA saisie : 2 jh | Traitement de la demande |
| Validation SIOP RUN | Valideur SIOP RUN | Validation | SLA validation : 1 jh | Validation du traitement de la demande |
| Finaliser traitement | Agent SIOP | Résolu | SLA traitement global : 3 jh | Intégration des conditions paramétrées et envoi de finalisation au métier |
| Retour métier ACE | Métier ACE | En attente d'information | SLA retour métier : 2 jh | Clôture après confirmation ACE |
