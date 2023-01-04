plugins {
    id("org.springframework.boot") version "2.7.0"
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
    application
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
    mainClass.set("we.iot.coursework.dataservices.WordCount")
}
