# Use Cases - Workflow Gestion des conditions derogatoires (V2)

Document source utilise: Spécifications fonctionnelles Workflow Gestion des conditions dérogatoires V2.pdf
Date de synthese: 08/06/2026

## 0. Resume detaille du projet

Le projet vise a digitaliser le cycle de vie complet des demandes de conditions derogatoires, depuis la saisie initiale par ACE jusqu a la cloture finale apres traitement SIOP et confirmation metier.

Le fonctionnement attendu est le suivant:
- un demandeur cree une demande via un formulaire dynamique;
- plusieurs niveaux de validation metier se succedent avant le traitement operationnel;
- le traitement SIOP prend le relais pour la saisie et la validation technique;
- le metier confirme ensuite la bonne execution et cloture le dossier;
- toutes les actions doivent etre tracees, notifiees et visibles dans des tableaux de bord;
- les SLA doivent etre mesures sur des horaires ouvrables et hors jours feries;
- le socle doit rester evolutif pour supporter plus tard la GED, la base de connaissance, les exports et les KPI.

Sur le plan architectural, le projet est organise en trois couches:
- `api` pour les contrats exposes, les DTO et les use cases;
- `core` pour la logique metier et les regles de traitement;
- `gateways` pour les adaptateurs techniques, l exposition web, la persistence et les integrations externes.

Cette structure permet de garder le metier independant de la technique tout en facilitant l evolution du workflow.

## 1. Fichiers a creer par module

Les noms ci-dessous sont proposes pour couvrir proprement les use cases du workflow. Ils peuvent etre adaptes si votre convention de nommage interne impose un autre vocabulaire, mais la decoupe fonctionnelle doit rester la meme.

### 1.1 Module API

Role du module: definir les contrats fonctionnels, les DTO et les interfaces des use cases.

Fichiers a creer:
- `src/main/java/com/socgen/unibank/services/api/usecases/CreateDerogatoryConditionRequestUseCase.java`
- `src/main/java/com/socgen/unibank/services/api/usecases/UpdateDerogatoryConditionRequestUseCase.java`
- `src/main/java/com/socgen/unibank/services/api/usecases/ValidateDerogatoryConditionRequestUseCase.java`
- `src/main/java/com/socgen/unibank/services/api/usecases/ReturnDerogatoryConditionRequestUseCase.java`
- `src/main/java/com/socgen/unibank/services/api/usecases/AssignDerogatoryConditionRequestUseCase.java`
- `src/main/java/com/socgen/unibank/services/api/usecases/CloseDerogatoryConditionRequestUseCase.java`
- `src/main/java/com/socgen/unibank/services/api/usecases/SearchDerogatoryConditionRequestsUseCase.java`
- `src/main/java/com/socgen/unibank/services/api/usecases/ExportDerogatoryConditionRequestsUseCase.java`
- `src/main/java/com/socgen/unibank/services/api/usecases/GetDerogatoryConditionDashboardUseCase.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionRequestCreateRequest.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionRequestUpdateRequest.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionRequestValidationRequest.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionRequestReturnRequest.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionRequestAssignmentRequest.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionRequestCloseRequest.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionRequestResponse.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionRequestSearchCriteria.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionRequestSearchResponse.java`
- `src/main/java/com/socgen/unibank/services/api/model/DerogatoryConditionDashboardResponse.java`

Fonction de ces fichiers:
- les `usecases` declarent les operations metier disponibles;
- les `model` contiennent les entrees et sorties du service;
- la couche API ne doit pas contenir la logique de traitement.

### 1.2 Module Core

Role du module: porter la logique metier du workflow, les transitions d etat, les regles de SLA et les ports vers les services externes.

Fichiers a creer:
- `src/main/java/com/socgen/unibank/services/core/usecases/CreateDerogatoryConditionRequestImpl.java`
- `src/main/java/com/socgen/unibank/services/core/usecases/UpdateDerogatoryConditionRequestImpl.java`
- `src/main/java/com/socgen/unibank/services/core/usecases/ValidateDerogatoryConditionRequestImpl.java`
- `src/main/java/com/socgen/unibank/services/core/usecases/ReturnDerogatoryConditionRequestImpl.java`
- `src/main/java/com/socgen/unibank/services/core/usecases/AssignDerogatoryConditionRequestImpl.java`
- `src/main/java/com/socgen/unibank/services/core/usecases/CloseDerogatoryConditionRequestImpl.java`
- `src/main/java/com/socgen/unibank/services/core/usecases/SearchDerogatoryConditionRequestsImpl.java`
- `src/main/java/com/socgen/unibank/services/core/usecases/ExportDerogatoryConditionRequestsImpl.java`
- `src/main/java/com/socgen/unibank/services/core/usecases/GetDerogatoryConditionDashboardImpl.java`
- `src/main/java/com/socgen/unibank/services/core/model/DerogatoryConditionRequest.java`
- `src/main/java/com/socgen/unibank/services/core/model/DerogatoryConditionRequestStatus.java`
- `src/main/java/com/socgen/unibank/services/core/model/DerogatoryConditionRequestTransition.java`
- `src/main/java/com/socgen/unibank/services/core/model/DerogatoryConditionSla.java`
- `src/main/java/com/socgen/unibank/services/core/gateways/outbound/DerogatoryConditionRequestRepositoryGateway.java`
- `src/main/java/com/socgen/unibank/services/core/gateways/outbound/DerogatoryConditionNotificationGateway.java`
- `src/main/java/com/socgen/unibank/services/core/gateways/outbound/DerogatoryConditionExportGateway.java`
- `src/main/java/com/socgen/unibank/services/core/gateways/outbound/DerogatoryConditionKnowledgeBaseGateway.java` (lot 2 si active)

Fonction de ces fichiers:
- les `Impl` portent les scenarios du workflow;
- `model` regroupe les objets metier et les statuts;
- `gateways/outbound` definit les ports abstraits que les adaptateurs techniques implementeront dans `gateways`.

### 1.3 Module Gateways

Role du module: exposer les APIs, brancher les composants Spring, implementer la persistence, la notification, l export et les integrations techniques.

Fichiers a creer:
- `src/main/java/com/socgen/unibank/services/gateway/inbound/DerogatoryConditionRequestController.java`
- `src/main/java/com/socgen/unibank/services/gateway/inbound/DerogatoryConditionDashboardController.java`
- `src/main/java/com/socgen/unibank/services/gateway/inbound/DerogatoryConditionReferenceDataController.java` (si besoin)
- `src/main/java/com/socgen/unibank/services/gateway/DerogatoryConditionGatewayBeansFactory.java`
- `src/main/java/com/socgen/unibank/services/gateway/outbound/DerogatoryConditionRequestRepositoryGatewayImpl.java`
- `src/main/java/com/socgen/unibank/services/gateway/outbound/entities/DerogatoryConditionRequestEntity.java`
- `src/main/java/com/socgen/unibank/services/gateway/outbound/entities/DerogatoryConditionRequestAttachmentEntity.java`
- `src/main/java/com/socgen/unibank/services/gateway/outbound/entities/DerogatoryConditionRequestHistoryEntity.java`
- `src/main/java/com/socgen/unibank/services/gateway/outbound/repositories/DerogatoryConditionRequestJpaRepository.java`
- `src/main/java/com/socgen/unibank/services/gateway/outbound/repositories/DerogatoryConditionRequestAttachmentJpaRepository.java`
- `src/main/java/com/socgen/unibank/services/gateway/outbound/DerogatoryConditionNotificationGatewayImpl.java`
- `src/main/java/com/socgen/unibank/services/gateway/outbound/DerogatoryConditionExportGatewayImpl.java`
- `src/main/java/com/socgen/unibank/services/gateway/outbound/DerogatoryConditionKnowledgeBaseGatewayImpl.java` (lot 2 si active)

Ressources techniques a prevoir dans ce module:
- `src/main/resources/application-database-migration.yml`
- `src/main/resources/db/changelog/db.changelog-master.yaml`
- `src/main/resources/db/changelog/changes/001-create-derogatory-condition-request.yaml`
- `src/main/resources/db/changelog/changes/002-create-derogatory-condition-history.yaml`
- `src/main/resources/db/changelog/changes/003-create-derogatory-condition-attachment.yaml`
- `src/main/resources/db/changelog/changes/004-add-workflow-status-indexes.yaml`
- `src/main/resources/db/changelog/changes/005-seed-reference-workflow-statuses.yaml` (si necessaire)

Fonction de ces fichiers:
- les controllers inbound exposent les operations du workflow;
- les gateways outbound realisent l acces aux donnees et aux services tiers;
- les entites et repositories portent la persistence;
- les fichiers Liquibase decrivent la structure de la base et ses evolutions.

## 1. Contexte fonctionnel

Le workflow couvre le cycle de vie complet d une demande de condition derogatoire, depuis la creation par ACE jusqu a la cloture par le metier.

Acteurs identifies dans la specification:
- ACE (Demandeur)
- Charge de clientele
- Responsable de marche (RM)
- Directeur Commercial Entreprise (DCE) / DGA Exploitation habilite
- Agent SIOP
- Valideur SIOP RUN
- Metier ACE / Referent metier

## 2. Liste exhaustive des use cases

### UC-01 - Creer une demande de condition derogatoire
- Acteur principal: ACE
- Objectif: creer une nouvelle demande via formulaire dynamique.
- Preconditions:
  - ACE authentifie.
  - Formulaire de creation accessible.
- Declencheur: besoin de demander une modification de condition derogatoire.
- Scenario nominal:
  1. ACE ouvre le formulaire.
  2. ACE renseigne les informations obligatoires.
  3. ACE joint les pieces justificatives (Lot 1).
  4. Le systeme genere un identifiant unique.
  5. Le systeme enregistre la demande au statut Nouvelle demande.
  6. Le systeme notifie le Charge de clientele.
- Postconditions:
  - Demande creee et tracable.
  - Ticket visible dans les tableaux de bord.
- Variantes:
  - En Lot 2, les pieces peuvent etre rattachees via GED.

### UC-02 - Corriger une demande retournee
- Acteur principal: ACE
- Objectif: corriger une demande renvoyee non conforme.
- Preconditions:
  - Demande au statut En attente correction.
  - Motif de retour renseigne par un valideur metier.
- Declencheur: notification de retour pour correction.
- Scenario nominal:
  1. ACE consulte les commentaires de retour.
  2. ACE met a jour les informations ou justificatifs.
  3. ACE resoumet la demande.
  4. Le systeme relance le flux de validation.
- Postconditions:
  - Demande de nouveau candidate a la validation metier.

### UC-03 - Valider la conformite par le Charge de clientele
- Acteur principal: Charge de clientele
- Objectif: effectuer le premier controle metier.
- Preconditions:
  - Demande en Nouvelle demande.
- Declencheur: notification de nouvelle demande.
- Scenario nominal:
  1. Le Charge de clientele prend connaissance de la demande.
  2. Il verifie la conformite.
  3. Il saisit un commentaire de validation.
  4. Il envoie la demande en validation RM.
  5. Le systeme positionne le statut En attente validation RM.
- Postconditions:
  - Dossier transmis au Responsable de marche.
- Variantes:
  - Si non conforme, retour ACE avec statut En attente correction.

### UC-04 - Valider la conformite par le Responsable de marche
- Acteur principal: Responsable de marche (RM)
- Objectif: confirmer la validite metier avant DCE.
- Preconditions:
  - Demande en En attente validation RM.
- Declencheur: notification RM.
- Scenario nominal:
  1. RM controle la demande.
  2. RM saisit un commentaire de validation.
  3. RM envoie en validation DCE.
  4. Le systeme passe le statut En attente validation DCE.
- Postconditions:
  - Dossier transmis au DCE.
- Variantes:
  - Si non conforme, retour ACE en En attente correction.

### UC-05 - Valider la conformite par le DCE
- Acteur principal: DCE (ou DGA Exploitation habilite)
- Objectif: realiser la derniere validation metier avant traitement SIOP.
- Preconditions:
  - Demande en En attente validation DCE.
- Declencheur: notification DCE.
- Scenario nominal:
  1. DCE verifie la conformite.
  2. DCE saisit un commentaire de validation.
  3. DCE envoie pour traitement SIOP.
  4. Le systeme notifie l Agent SIOP.
  5. La demande passe au statut Declare.
- Postconditions:
  - Demande disponible pour prise en charge SIOP.
- Variantes:
  - Si non conforme, retour ACE en En attente correction.

### UC-06 - Prendre en charge la demande cote Agent SIOP
- Acteur principal: Agent SIOP
- Objectif: affecter la demande et demarrer le traitement.
- Preconditions:
  - Demande au statut Declare.
- Declencheur: notification Agent SIOP.
- Scenario nominal:
  1. L Agent SIOP prend en charge la demande.
  2. Le systeme affecte la demande a cet agent.
  3. L etat de travail passe en Approuve/En cours selon les regles d execution.
- Postconditions:
  - Demande explicitement affectee.
  - SLA de prise en charge active.

### UC-07 - Saisir la demande dans Amplitude
- Acteur principal: Agent SIOP
- Objectif: executer la saisie operationnelle apres controle de conformite.
- Preconditions:
  - Demande prise en charge par Agent SIOP.
- Declencheur: dossier juge conforme par Agent SIOP.
- Scenario nominal:
  1. Agent SIOP controle la conformite.
  2. Agent SIOP saisit la demande dans Amplitude.
  3. Agent SIOP envoie la demande en validation SIOP RUN.
  4. Le systeme passe au statut En attente de validation SIOP.
- Postconditions:
  - Dossier transmis au valideur RUN.
- Variantes:
  - Si non conforme, retour ACE au statut En attente d information.

### UC-08 - Valider le traitement par le Valideur SIOP RUN
- Acteur principal: Valideur SIOP RUN
- Objectif: verifier et valider definitivement le traitement SIOP.
- Preconditions:
  - Demande en En attente de validation SIOP.
- Declencheur: notification Valideur SIOP RUN.
- Scenario nominal:
  1. Le valideur prend en charge le dossier.
  2. Le systeme marque En cours de validation.
  3. Le valideur verifie la conformite.
  4. Le valideur saisit dans Amplitude si necessaire puis commente.
  5. Le valideur valide la demande.
  6. Le systeme passe la demande au statut Valide.
- Postconditions:
  - Traitement SIOP valide.
- Variantes:
  - Si non conforme, retour Agent SIOP au statut En cours.

### UC-09 - Finaliser le traitement technique
- Acteur principal: Agent SIOP
- Objectif: terminer techniquement le dossier apres validation RUN.
- Preconditions:
  - Demande au statut Valide.
- Declencheur: notification de validation RUN.
- Scenario nominal:
  1. Agent SIOP integre l extraction des conditions parametrees.
  2. Agent SIOP envoie la demande de confirmation au metier.
  3. Agent SIOP renseigne le commentaire de fin de traitement.
  4. Le systeme passe au statut Resolu.
- Postconditions:
  - Dossier pret pour confirmation metier.

### UC-10 - Cloturer la demande par le metier
- Acteur principal: ACE / Referent metier
- Objectif: confirmer le resultat et cloturer le ticket.
- Preconditions:
  - Demande au statut Resolu.
- Declencheur: notification metier de fin de traitement.
- Scenario nominal:
  1. Le metier verifie le resultat.
  2. Le metier saisit un commentaire de confirmation.
  3. Le metier valide la conformite.
  4. Le systeme passe au statut Cloture.
- Postconditions:
  - Dossier termine et archive.
- Variantes:
  - Si non conforme, le metier rejette avec commentaire et renvoie Agent SIOP au statut En cours.

### UC-11 - Notifier automatiquement les acteurs
- Acteur principal: Systeme
- Objectif: informer chaque acteur a chaque changement d etat.
- Preconditions:
  - Evenement de transition de statut.
- Declencheur: changement d etat du ticket.
- Scenario nominal:
  1. Le systeme identifie le role cible.
  2. Le systeme envoie le mail de notification.
  3. L acteur receveur peut acceder a sa file de travail.
- Postconditions:
  - Flux de traitement synchronise entre acteurs.
- Variantes:
  - La notification est configurable par role.

### UC-12 - Rechercher et filtrer les demandes
- Acteur principal: tous les roles habilites
- Objectif: retrouver rapidement une demande et suivre son etat.
- Preconditions:
  - Utilisateur authentifie et habilite.
- Declencheur: besoin de suivi/controle.
- Scenario nominal:
  1. L utilisateur ouvre l ecran de recherche.
  2. Il applique des filtres (statut, acteur, date, type de demande).
  3. Le systeme retourne la liste correspondante.
- Postconditions:
  - Visibilite transverse sur les demandes.

### UC-13 - Exporter les donnees en Excel/PDF
- Acteur principal: tous les roles habilites
- Objectif: extraire le formulaire et les tickets pour analyse/exploitation.
- Preconditions:
  - Resultats de recherche ou formulaire disponibles.
- Declencheur: action utilisateur d export.
- Scenario nominal:
  1. L utilisateur choisit le type d export (formulaire ou tickets).
  2. L utilisateur choisit le format (Excel, PDF selon capacite).
  3. Le systeme genere le fichier d export.
- Postconditions:
  - Fichier exporte disponible pour diffusion/controle.

### UC-14 - Consulter les tableaux de bord et KPI
- Acteur principal: management et roles habilites
- Objectif: piloter la performance et la qualite du workflow.
- Preconditions:
  - Donnees de workflow disponibles.
- Declencheur: consultation pilotage.
- Scenario nominal:
  1. L utilisateur ouvre les tableaux de bord.
  2. Le systeme affiche:
     - nombre de demandes par statut
     - taches en attente par role
     - KPI duree de traitement
     - KPI taux qualite de traitement
     - KPI qualite entrante
     - KPI duree retour metier
- Postconditions:
  - Vision operationnelle et managériale a jour.

### UC-15 - Consulter la base de connaissance (Lot 2)
- Acteur principal: tous les roles habilites
- Objectif: consulter procedures et modes operatoires.
- Preconditions:
  - Module base de connaissance active (evolution Lot 2).
- Declencheur: besoin d aide operationnelle.
- Scenario nominal:
  1. L utilisateur ouvre l espace documentaire.
  2. Il consulte la procedure pertinente.
- Postconditions:
  - Reduction des erreurs de traitement.

## 3. Statuts metier identifies dans la specification

Statuts cites explicitement:
- Nouvelle demande
- En attente correction
- En attente validation RM
- En attente validation DCE
- Declare
- En attente d information
- En attente de validation SIOP
- En cours de validation
- Valide
- Resolu
- Cloture
- En cours
- Approuve

Note: certains statuts se recouvrent entre etat de workflow et etat de travail interne. Une harmonisation de nomenclature est recommandee dans la conception detaillee.

## 4. SLA a couvrir fonctionnellement

- SLA global de traitement: prise en charge Agent SIOP -> statut Resolu.
- SLA prise en charge saisie: fin validation DCE -> prise en charge Agent SIOP.
- SLA de saisie: prise en charge Agent SIOP -> debut validation SIOP.
- SLA prise en charge validation: envoi en validation SIOP -> prise en charge Valideur SIOP.
- SLA validation: prise en charge Valideur SIOP -> fin validation.
- SLA retour metier: envoi au metier pour complement -> retour metier.
- Regle d arret: les autres SLA sont stoppes lorsque le SLA retour metier est enclenche.
- Contrainte de calcul: jours/heures ouvrables 07:30-16:30, hors jours feries.

## 5. Exigences transverses associees aux use cases

- Traçabilite complete des actions en base.
- Gestion des roles et permissions selon RACI.
- Interface web responsive.
- Securite (authentification + habilitations).
- Integration GED (Lot 2).
