tasks {
    jar { enabled = true }
    bootJar { enabled = false }
}

dependencies {
    implementation("org.springframework:spring-web")
    implementation("org.springframework.data:spring-data-commons")
}