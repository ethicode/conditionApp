import org.gradle.api.plugins.JavaApplication
import org.gradle.api.tasks.compile.JavaCompile

val localDev = providers.gradleProperty("localDev").orNull == "true"

plugins {
    `java-library`
}

if (!localDev) {
    apply(plugin = "unibank.service")
    apply(plugin = "foundation-qa-coverage-bronze")
    apply(plugin = "foundation-qa")
} else {
    apply(plugin = "application")
}

if (localDev) {
    configure<JavaApplication> {
        mainClass.set("com.socgen.unibank.services.ServiceUnibankServiceSgsnDemo")
    }
}

dependencies {
    if (localDev) {
        implementation("org.springframework.boot:spring-boot-starter-web:3.3.6")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    } else {
        api("com.socgen.unibank.core:unibank-core-domain")
        api("com.socgen.unibank.platform:unibank-platform-core")
    }
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.commons:commons-collections4:4.4")
    implementation(project(mapOf("path" to ":unibank-service-sgsn-demo-api")))
    if (localDev) {
        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")
    } else {
        compileOnly("org.projectlombok:lombok:1.18.20")
        annotationProcessor("org.projectlombok:lombok:1.18.20")
    }
    implementation(project(":unibank-service-sgsn-demo-core"))
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
}
