plugins {
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

group = "com.github.redreaperlp"
version = "1.0.0"
description = "MySQL_API"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

// configure publish data
publishData {
    // We use the eldo nexus default repositories with "main" as our stable branch
    useEldoNexusRepos()
    publishData.getVersion(true)
    // This would use "master" as our stable branch
    // useEldoNexusRepos()

    // We publish everything of the java component, which includes our compiled jar, sources and javadocs
    publishComponent("java")
}

publishing {
    publications.create<MavenPublication>("maven") {
        // Configure our maven publication
        publishData.configurePublication(this)
    }

    repositories {
        // We add EldoNexus as our repository. The used url is defined by the publish data.
        maven {
            authentication {
                credentials(PasswordCredentials::class) {
                    // Those credentials need to be set under "Settings -> Secrets" in your repository
                    username = System.getenv("NEXUS_USERNAME")
                    password = System.getenv("NEXUS_PASSWORD")
                }
            }

            name = "EldoNexus"
            setUrl(publishData.getRepository())
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
