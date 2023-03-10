plugins {
    id("org.springframework.boot") version "2.7.0"
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
    application
    java
}

val hadoop = "3.3.1"
val springBoot = "2.7.0"

group = "we.iot.coursework"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.hadoop:hadoop-common:$hadoop")
    implementation("org.apache.hadoop:hadoop-mapreduce-client-core:$hadoop")
    implementation("org.apache.hadoop:hadoop-mapreduce-client-shuffle:$hadoop")
    implementation(project(":common"))

    implementation("org.springframework.boot:spring-boot-starter-web:$springBoot") {
        exclude(group = "ch.qos.logback", module = "logback-classic")
    }
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:+")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBoot")
}
dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
}
application {
    mainClass.set("we.iot.coursework.dataservices.MainKt")
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
        archiveClassifier.set("standalone") // Naming the jar
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(fatJar) // Trigger fat jar creation during build
    }
}
