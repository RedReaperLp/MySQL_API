pluginManagement {
    repositories {
        gradlePluginPortal()
        // Use eldo nexus for gradle plugins
        maven {
            url = uri("https://eldonexus.de/repository/maven-public/")
        }
    }
}
rootProject.name = "MySQL_API"
