tasks {
    jar { enabled = true }
    bootJar { enabled = false }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":domains:domain-rds"))
    implementation(project(":modules:open-api"))
}