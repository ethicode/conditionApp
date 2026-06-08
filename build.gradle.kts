import java.util.concurrent.TimeUnit

val localDev = providers.gradleProperty("localDev").orNull == "true"

buildscript {
    val buildscriptLocalDev = (findProperty("localDev") as String?) == "true"
    if (!buildscriptLocalDev) {
        repositories {
            mavenLocal()
            maven {
                name = "dsp-artifacts"
                setUrl("https://dsp-artifacts.fr.world.socgen:443/artifactory/maven-virtual-383c4220-356d-4be7-b010-8f937d99d86e")

                credentials {
                    username = (findProperty("jfrogUsername") ?: System.getenv("JFROG_USERNAME") ?: "").toString()
                    password = (findProperty("jfrogPassword") ?: System.getenv("JFROG_PASSWORD") ?: "").toString()
                }
            }
            mavenCentral()
        }
        dependencies {
            classpath("com.socgen.unibank.tools:unibank-gradle-plugin:1.3.10")
            classpath("com.socgen.abs:foundation-gradle-plugin:2.0.5")
        }
    }
}

allprojects {
    group = (findProperty("group") ?: group).toString()
    version = (findProperty("version") ?: version).toString()
}

if (!localDev) {
    apply(plugin = "org.sonarqube")
}

subprojects {
    if (!localDev) {
        apply(plugin = "foundation-qa")
        apply(plugin = "foundation-qa-coverage-silver")
        apply(plugin = "unibank.java17")
        apply(plugin = "unibank.lombok")
        apply(plugin = "unibank.maven-publish")
    }

    repositories {
        mavenLocal()
        if (!localDev) {
            maven {
                name = "dsp-artifacts"
                setUrl("https://dsp-artifacts.fr.world.socgen:443/artifactory/maven-virtual-383c4220-356d-4be7-b010-8f937d99d86e")

                credentials {
                    username = (findProperty("jfrogUsername") ?: System.getenv("JFROG_USERNAME") ?: "").toString()
                    password = (findProperty("jfrogPassword") ?: System.getenv("JFROG_PASSWORD") ?: "").toString()
                }
            }
        }
        mavenCentral()
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(10, TimeUnit.SECONDS)
}
