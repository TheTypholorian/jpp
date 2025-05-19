plugins {
    id("java")
}

group = "net.typho"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "net.typho.jpp.refactor.JPP"
    }
}