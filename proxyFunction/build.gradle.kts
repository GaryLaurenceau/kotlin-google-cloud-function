plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    application
}

repositories {
    mavenCentral()
}

val ktorVersion = "2.2.3"
val invoker by configurations.creating

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("com.google.cloud.functions:functions-framework-api:1.0.4")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    // Test
    invoker("com.google.cloud.functions.invoker:java-function-invoker:1.2.0")
}

application {
    mainClassName = "com.example.ProxyFunction"
}


tasks.named("build") {
    dependsOn(":proxyFunction:shadowJar")
}

tasks.register<JavaExec>("runFunction") {
    main = "com.google.cloud.functions.invoker.runner.Invoker"
    classpath(invoker)
    inputs.files(configurations.runtimeClasspath, sourceSets["main"].output)
    args(
        "--target", "com.example.ProxyFunction",
        "--port", 8080
    )
    doFirst {
        args("--classpath", files(configurations.runtimeClasspath, sourceSets["main"].output).asPath)
    }
}

tasks.register<Copy>("buildFunction") {
    dependsOn(":proxyFunction:build")
    from("$projectDir/build/libs/" + projectDir.name + "-all.jar")
    into("$projectDir/build/deploy")
}