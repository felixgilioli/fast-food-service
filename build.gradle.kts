plugins {
	kotlin("jvm") version "1.9.25"
	id("org.sonarqube") version "7.2.2.6593"
	jacoco
}

allprojects {
	group = "br.com.felixgilioli"
	version = "0.0.1-SNAPSHOT"

	apply(plugin = "java")
	apply(plugin = "kotlin")

	java {
		toolchain {
			languageVersion = JavaLanguageVersion.of(21)
		}
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}

	dependencies {
		testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
		testImplementation("io.mockk:mockk:1.14.2")
	}

	tasks.test {
		useJUnitPlatform()
		finalizedBy(tasks.jacocoTestReport)
	}

	repositories {
		mavenCentral()
	}
}

sonar {
	properties {
		property("sonar.projectKey", "felixgilioli_fast-food-service")
		property("sonar.organization", "felixgilioli")
	}
}

jacoco {
	toolVersion = "0.8.13"
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
	}
}

