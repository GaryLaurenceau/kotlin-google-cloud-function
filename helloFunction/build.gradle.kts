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
    // Test
    invoker("com.google.cloud.functions.invoker:java-function-invoker:1.2.0")
}

application {
    mainClassName = "com.example.HelloFunction"
}


tasks.named("build") {
    dependsOn(":helloFunction:shadowJar")
}

tasks.register<JavaExec>("runFunction") {
    main = "com.google.cloud.functions.invoker.runner.Invoker"
    classpath(invoker)
    inputs.files(configurations.runtimeClasspath, sourceSets["main"].output)
    args(
        "--target", "com.example.HelloFunction",
        "--port", 8080
    )
    doFirst {
        args("--classpath", files(configurations.runtimeClasspath, sourceSets["main"].output).asPath)
    }
}

tasks.register<Copy>("buildFunction") {
    dependsOn(":helloFunction:build")
    from("$projectDir/build/libs/" + projectDir.name + "-all.jar")
    into("$projectDir/build/deploy")
}