# Plan de creation des fichiers (API, Core, Gateways, Liquibase)

## 1. Contexte observe dans la configuration

Base de reference: application principale du module gateways.

Points importants vus dans la configuration:
- Le profil local active `database-migration`.
- Des modules de plateforme sont inclus (`unibank-core`, `unibank-persistence-simple`).
- Le service est structure en couches `api`, `core`, `gateways`.

Conclusion pratique:
- Les fichiers metier se creent dans les 3 modules.
- Les fichiers Liquibase doivent etre places dans les ressources du module `gateways`.

## 2. Convention generale pour une nouvelle fonctionnalite

Exemple de nom fonctionnel utilise ci-dessous: `CreateCustomer`.
Tu peux remplacer `Customer` par ton domaine reel (`Contract`, `Payment`, etc.).

## 3. Fichiers a creer dans API

Racine: `unibank-service-sgsn-demo-api/src/main/java/com/socgen/unibank/services/api`

### 3.1 Contrat use case
- `usecases/CreateCustomer.java`
- Role: interface du cas d usage expose au reste du systeme.
- Contenu attendu: methode `handle(...)` avec input, output, et contexte (`RequestContext`).

### 3.2 DTO d entree
- `model/CreateCustomerRequest.java`
- Role: payload d entree du endpoint.
- Contenu attendu: champs de la requete, validations, getters/setters.

### 3.3 DTO de sortie
- `model/CreateCustomerResponse.java`
- Role: payload de reponse.
- Contenu attendu: identifiant cree, statut, message metier.

### 3.4 Endpoint declare dans l API principale
- Fichier potentiellement deja present: `UnibankServiceSgsnDemoAPI.java`
- Action: ajouter la methode REST/GraphQL du use case `CreateCustomer`.
- Note: ce point est une modification, pas une creation de fichier.

## 4. Fichiers a creer dans Core

Racine: `unibank-service-sgsn-demo-core/src/main/java/com/socgen/unibank/services/core`

### 4.1 Implementation du use case
- `usecases/CreateCustomerImpl.java`
- Role: logique metier principale.
- Contenu attendu:
  - implementation de l interface API `CreateCustomer`
  - orchestration des regles metier
  - appel des ports sortants

### 4.2 Port sortant (si acces externe necessaire)
- `gateways/outbound/CustomerRepositoryGateway.java`
- Role: contrat abstrait vers la persistence ou un service externe.
- Contenu attendu: methodes du type `save`, `findById`, `existsByBusinessKey`.

### 4.3 Modele metier interne (optionnel mais recommande)
- `model/Customer.java`
- Role: objet metier interne au core, decouple des DTO API.

## 5. Fichiers a creer dans Gateways

Racine Java: `unibank-service-sgsn-demo-gateways/src/main/java/com/socgen/unibank/services/gateway`

### 5.1 Adaptateur inbound (souvent deja en place)
- Fichier present: `inbound/UnibankServiceSgsnDemoEndpoint.java`
- Action habituelle: pas de nouveau fichier, le proxy endpoint existant suffit.
- Cas particulier: creer un nouvel endpoint inbound seulement si un canal specifique est requis.

### 5.2 Adaptateur outbound pour le port du core
- `outbound/CustomerRepositoryGatewayImpl.java`
- Role: implementation concrete de `CustomerRepositoryGateway`.
- Contenu attendu:
  - mapping metier <-> persistence
  - acces DB (JPA/JdbcTemplate/autre stack du socle)

### 5.3 Entite de persistence (si JPA)
- `outbound/entities/CustomerEntity.java`
- Role: representation table SQL.
- Contenu attendu: `@Entity`, table, colonnes, cle primaire.

### 5.4 Repository technique (si JPA)
- `outbound/repositories/CustomerJpaRepository.java`
- Role: interface Spring Data pour la table.

## 6. Fichiers Liquibase a creer

Racine ressources: `unibank-service-sgsn-demo-gateways/src/main/resources`

### 6.1 Changelog maitre
- `db/changelog/db.changelog-master.yaml`
- Role: point d entree Liquibase, inclut tous les changesets.

### 6.2 Dossier des changesets
- `db/changelog/changes/001-create-customer-table.yaml`
- `db/changelog/changes/002-add-customer-indexes.yaml`
- `db/changelog/changes/003-seed-customer-reference-data.yaml` (optionnel)

### 6.3 SQL externalise (optionnel)
- `db/changelog/sql/001-create-customer-table.sql`
- Role: separer SQL brut du YAML si besoin de lisibilite.

### 6.4 Profil de migration dedie (recommande)
- `application-database-migration.yml`
- Role: porter les proprietes Liquibase si elles ne sont pas heritees du socle.
- Proprietes typiques:
  - `spring.liquibase.enabled=true`
  - `spring.liquibase.change-log=classpath:db/changelog/db.changelog-master.yaml`

## 7. Arborescence cible (resume)

```text
unibank-service-sgsn-demo-api/
  src/main/java/com/socgen/unibank/services/api/
    model/
      CreateCustomerRequest.java
      CreateCustomerResponse.java
    usecases/
      CreateCustomer.java

unibank-service-sgsn-demo-core/
  src/main/java/com/socgen/unibank/services/core/
    usecases/
      CreateCustomerImpl.java
    gateways/outbound/
      CustomerRepositoryGateway.java
    model/
      Customer.java

unibank-service-sgsn-demo-gateways/
  src/main/java/com/socgen/unibank/services/gateway/
    outbound/
      CustomerRepositoryGatewayImpl.java
      entities/CustomerEntity.java
      repositories/CustomerJpaRepository.java
  src/main/resources/
    application-database-migration.yml
    db/changelog/
      db.changelog-master.yaml
      changes/
        001-create-customer-table.yaml
        002-add-customer-indexes.yaml
        003-seed-customer-reference-data.yaml
      sql/
        001-create-customer-table.sql
```

## 8. Ordre de creation recommande

1. API: use case + request/response.
2. Core: implementation + port sortant.
3. Gateways: implementation port + persistence.
4. Liquibase: master + changesets.
5. Activation profil `database-migration` en local/test.

## 9. Regles de qualite pour eviter les erreurs

- Un changeset Liquibase est immuable apres execution.
- Toujours ajouter un nouveau fichier changeset au lieu de modifier un ancien.
- Garder la compatibilite des DTO API (pas de breaking change non maitrise).
- Separer DTO API et modele metier core.
- Garder les noms de classes coherents entre `CreateX` et `CreateXImpl`.
- Verifier la coherence package/module avant commit.
