plugins {
    kotlin("jvm") version "1.7.0"
    application
}

group = "we.iot.coursework"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.hadoop:hadoop-common:3.3.1")
    implementation("org.apache.hadoop:hadoop-mapreduce-client-core:3.3.1")
    implementation("org.apache.hadoop:hadoop-mapreduce-client-shuffle:3.3.1")
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
tasks {
    "run"(JavaExec::class) {
        environment("hadoop.home.dir","/")
    }
}
