rootProject.name = "edc-extensions"

include(":extensions:edc-ui-config")
include(":extensions:jwks")
include(":extensions:last-commit-info")
include(":extensions:policy-always-true")
include(":extensions:policy-referring-connector")
include(":extensions:policy-time-interval")
include(":extensions:postgres-flyway")
include(":extensions:sovity-edc-extensions-package")
include(":extensions:test-backend-controller")
include(":extensions:wrapper:clients:java-client")
include(":extensions:wrapper:clients:java-client-example")
include(":extensions:wrapper:wrapper")
include(":extensions:wrapper:wrapper-api")
include(":extensions:wrapper:wrapper-common-api")
include(":extensions:wrapper:wrapper-common-mappers")
include(":extensions:wrapper:wrapper-ee-api")
include(":launchers:common:auth-daps")
include(":launchers:common:auth-mock")
include(":launchers:common:base")
include(":launchers:common:base-mds")
include(":launchers:common:observability")
include(":launchers:connectors:mds-ce")
include(":launchers:connectors:sovity-ce")
include(":launchers:connectors:sovity-dev")
include(":launchers:connectors:test-backend")
include(":tests")
include(":utils:catalog-parser")
include(":utils:json-and-jsonld-utils")
include(":utils:test-connector-remote")
include("extensions:jwks")
