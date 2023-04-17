import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.5"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "com.workrise"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// Apache POI
	implementation("org.apache.poi:poi-ooxml:5.0.0")
	implementation("org.apache.poi:poi-ooxml:5.1.0")

	// logging
	implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
	implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")

	// swagger
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.3")

	// metrics
	implementation("io.micrometer:micrometer-registry-prometheus")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// testing
	testImplementation(kotlin("test"))
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
	testImplementation("org.mockito:mockito-inline:5.2.0")
	testImplementation("io.github.serpro69:kotlin-faker:1.13.0")

	//sentry
	implementation("io.sentry:sentry-logback:6.15.0")
	implementation("io.sentry:sentry-spring-boot-starter:6.15.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
