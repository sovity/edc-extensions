plugins {
    `java-library`
}

val edcVersion: String by project
val edcGroup: String by project

dependencies {
    // Control-Plane
    api("${edcGroup}:control-plane-core:${edcVersion}")
    api("${edcGroup}:management-api:${edcVersion}")
    api("${edcGroup}:api-observability:${edcVersion}")
    api("${edcGroup}:configuration-filesystem:${edcVersion}")
    api("${edcGroup}:control-plane-aggregate-services:${edcVersion}")
    api("${edcGroup}:http:${edcVersion}")
    api("${edcGroup}:dsp:${edcVersion}")
    api("${edcGroup}:json-ld:${edcVersion}")
    api("${edcGroup}:monitor-jdk-logger:${edcVersion}")

    // Data Management API Key
    api("${edcGroup}:auth-tokenbased:${edcVersion}")

    // sovity Extensions Package
    api(project(":extensions:sovity-edc-extensions-package"))
    api(project(":extensions:postgres-flyway"))

    // Control-plane to Data-plane
    api("${edcGroup}:transfer-data-plane:${edcVersion}")
    api("${edcGroup}:data-plane-selector-core:${edcVersion}")
    api("${edcGroup}:data-plane-selector-client:${edcVersion}")

    // Data-plane
    api("${edcGroup}:data-plane-http:${edcVersion}")
    api("${edcGroup}:data-plane-framework:${edcVersion}")
    api("${edcGroup}:data-plane-core:${edcVersion}")
    api("${edcGroup}:data-plane-util:${edcVersion}")

    // JDK Logger
    api("${edcGroup}:monitor-jdk-logger:${edcVersion}")
}

val sovityEdcGroup: String by project
group = sovityEdcGroup
