import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.10"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	id("org.openapi.generator").version("6.0.1") // this is for the open api generator
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "technical.assessment"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2021.0.1"


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("mysql:mysql-connector-java:8.0.28")
	implementation("com.h2database:h2")

	implementation("io.swagger:swagger-annotations:1.6.7")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.11")
	implementation("org.openapitools:jackson-databind-nullable:0.2.3")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generate_stub"){
	generatorName.set("spring")
	library.set("spring-cloud")
	inputSpec.set("$rootDir/src/main/resources/api-spec.yaml")
	outputDir.set("$buildDir/generated/spec")
	modelPackage.set("technical.assessment.todo")
	apiPackage.set("technical.assessment.todo")
	invokerPackage.set("technical.assessment.todo")
}

tasks.compileKotlin {
	dependsOn(tasks.named("generate_stub"))
}

configure<SourceSetContainer> {
	named("main") {
		java.srcDir("$buildDir/generated/spec/src/main/java")
	}
}

