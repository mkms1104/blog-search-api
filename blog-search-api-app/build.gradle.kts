tasks {
    jar { enabled = false }
    bootJar { enabled = true }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.google.guava:guava:31.1-jre")
    implementation(project(":domains:domain-rds"))
    implementation(project(":modules:open-api"))
}