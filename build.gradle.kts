plugins{
    java
    id("de.chojo.publishdata") version "1.0.8"
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.31")
}

shadow{
}
tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

publishData {
    // only if you want to publish to the eldonexus. If you call this you will not need to manually add repositories
    useEldoNexusRepos()
    publishTask("jar")
    publishTask("sourcesJar")
    publishTask("javadocJar")
}

publishing {
    publications.create<MavenPublication>("maven") {
        // configure the publication as defined previously.
        publishData.configurePublication(publication = this)
    }

    repositories {
        maven {
            authentication {
                credentials(PasswordCredentials::class) {
                    // Those credentials need to be set under "Settings -> Secrets" in your repository
                    username = System.getenv("NEXUS_USERNAME")
                    password = System.getenv("NEXUS_PASSWORD")
                }
            }

            name = "MySQL_API"
            // Get the detected repository from the publish data
            url = uri(publishData.getRepository())
        }
    }
}

tasks{
    compileJava {
        options.encoding = "UTF-8"
    }

    javadoc {
        options.encoding = "UTF-8"
    }
}

