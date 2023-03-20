tasks {
    jar { enabled = true }
    bootJar { enabled = false }
}

dependencies {
    implementation(project(":domains:domain-rds"))
    implementation(project(":api:api-kk"))
    implementation(project(":api:api-nv"))
}