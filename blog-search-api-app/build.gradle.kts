tasks {
    jar { enabled = false }
    bootJar { enabled = true }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.google.guava:guava:31.1-jre")
    implementation(project(":domains:domain-rds"))
    implementation(project(":modules:open-api"))
}