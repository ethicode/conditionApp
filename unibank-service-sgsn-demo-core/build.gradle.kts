val localDev = providers.gradleProperty("localDev").orNull == "true"

plugins {
    `java-library`
}

if (!localDev) {
    apply(plugin = "unibank.springboot.platform")
    apply(plugin = "unibank.maven-publish")
    apply(plugin = "unibank.junit5")
    apply(plugin = "foundation-qa-coverage-silver")
}

dependencies {
    if (localDev) {
        implementation("org.springframework:spring-context:6.1.14")
        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    } else {
        api("com.socgen.unibank.platform:unibank-platform-core:${property("unibank.platform_version")}")
        api("com.socgen.unibank.platform:unibank-platform-springboot:${property("unibank.platform_version")}")
        testImplementation("com.socgen.unibank.platform:unibank-platform-springboot-test:${property("unibank.platform_version")}")
        compileOnly("org.junit.jupiter:junit-jupiter-api:5.6.2")
        implementation("org.projectlombok:lombok:1.18.26")
        annotationProcessor("org.projectlombok:lombok:1.18.20")
    }
    implementation(project(":unibank-service-sgsn-demo-api"))
}
