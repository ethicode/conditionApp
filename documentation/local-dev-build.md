# Local Dev Build

## Objectif

Ce document decrit:
- le mode de build local sans dependances privees;
- les artefacts prives minimaux necessaires pour le build complet d entreprise.

## Commandes localDev

Build local sans plugins ni librairies privees:

```bash
./gradlew -PlocalDev=true :unibank-service-sgsn-demo-api:compileJava
./gradlew -PlocalDev=true :unibank-service-sgsn-demo-core:compileJava
./gradlew -PlocalDev=true :unibank-service-sgsn-demo-gateways:compileJava
```

Lancement local du module applicatif:

```bash
./gradlew -PlocalDev=true :unibank-service-sgsn-demo-gateways:run
```

## Ce que contourne localDev

Le mode `localDev` ne depend plus de:
- `com.socgen.unibank.tools:unibank-gradle-plugin:1.3.10`
- `com.socgen.abs:foundation-gradle-plugin:2.0.5`
- `com.socgen.unibank.core:unibank-core-domain:3.7.79`
- `com.socgen.unibank.core:unibank-core-platform-model:3.7.79`
- `com.socgen.unibank.platform:unibank-platform-core:3.2.85`
- `com.socgen.unibank.platform:unibank-platform-springboot:3.2.85`
- `com.socgen.unibank.platform:unibank-platform-springboot-test:3.2.85` (tests uniquement)

En localDev, ces elements sont remplaces au minimum par:
- Spring Web public
- Spring Context public
- Spring Boot Starter Web public
- Swagger annotations publiques
- stubs locaux pour `Command`, `RequestContext`, `GraphQLController`, `ProxyEndpoints`

## Artefacts prives minimaux pour le build complet

Si tu veux lancer le build normal sans `-PlocalDev=true`, il faut au minimum que ces artefacts soient resolvables, via VPN/reseau interne ou `mavenLocal()`:

### Plugins Gradle prives
- `com.socgen.unibank.tools:unibank-gradle-plugin:1.3.10`
- `com.socgen.abs:foundation-gradle-plugin:2.0.5`

### Dependances applicatives privees
- `com.socgen.unibank.core:unibank-core-domain:3.7.79`
- `com.socgen.unibank.core:unibank-core-platform-model:3.7.79`
- `com.socgen.unibank.platform:unibank-platform-core:3.2.85`
- `com.socgen.unibank.platform:unibank-platform-springboot:3.2.85`
- `com.socgen.unibank.platform:unibank-platform-springboot-test:3.2.85`

## Limites du mode localDev

Le mode `localDev` sert surtout a:
- compiler localement;
- lancer une application Spring minimale;
- travailler sur le code sans acces au socle d entreprise.

Il ne reproduit pas integralement:
- les plugins QA/foundation;
- les comportements du socle Unibank;
- les endpoints proxifies par le framework interne;
- les integrations exactes de la plateforme d entreprise.
