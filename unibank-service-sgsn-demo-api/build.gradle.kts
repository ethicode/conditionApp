val localDev = providers.gradleProperty("localDev").orNull == "true"

plugins {
    `java-library`
}

if (!localDev) {
    apply(plugin = "unibank.springboot.platform")
    apply(plugin = "unibank.maven-publish")
    apply(plugin = "unibank.kotlin")
}

dependencies {
    if (localDev) {
        api("org.springframework:spring-web:6.1.14")
        api("io.swagger.core.v3:swagger-annotations:2.2.28")
        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")
    } else {
        api("com.socgen.unibank.core:unibank-core-domain:${property("unibank.domain_version")}")
        api("com.socgen.unibank.core:unibank-core-platform-model:${property("unibank.domain_version")}")
        compileOnly("org.projectlombok:lombok:1.18.20")
        implementation("org.springframework.boot:spring-boot-starter-web")
        annotationProcessor("org.projectlombok:lombok:1.18.20")
    }
}
