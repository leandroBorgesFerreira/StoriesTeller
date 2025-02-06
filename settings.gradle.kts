// enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "Writeopia"

include(":writeopia")
include(":writeopia_models")
include(":writeopia_ui")
include(":plugins:writeopia_persistence_room")
include(":plugins:writeopia_serialization")
include(":plugins:writeopia_network")
include(":plugins:writeopia_export")
include(":plugins:writeopia_import_document")
include(":plugins:writeopia_persistence_core")
include(":plugins:writeopia_persistence_sqldelight")
include(":application:core:resources")
include(":application:core:auth_core")
include(":application:core:utils")
include(":application:core:common_ui")
include(":application:core:common_ui_tests")
include(":application:core:persistence_room")
include(":application:core:persistence_sqldelight")
include(":application:core:persistence_bridge")
include(":application:features:account")
include(":application:features:editor")
include(":application:features:global_shell")
include(":application:features:note_menu")
include(":application:features:auth")
include(":application:common_flows:wide_screen_common")
include(":application:web")
include(":libraries:dbtest")
include(":backend:editor:api_editor")
include(":backend:editor:api_editor_socket")
include(":backend:documents:documents_spring")
include(":common:endpoints")
include(":application:core:theme")
include(":application:core:navigation")
include(":application:features:search")
include(":application:features:notifications")
include(":plugins:writeopia_presentation")
include(":application:core:models")
include(":application:composeApp")
include(":application:core:folders")
include(":application:core:connection")
include(":application:core:ollama")
include(":backend:documents:documents")
