rootProject.name = "unibank-service-sgsn-demo"

listOf(
    "unibank-service-sgsn-demo-gateways",
    "unibank-service-sgsn-demo-api",
    "unibank-service-sgsn-demo-core"
).forEach {
    include(":${it}")
}
