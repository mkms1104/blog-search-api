tasks {
    jar { enabled = true }
    bootJar { enabled = false }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-jdbc")
}