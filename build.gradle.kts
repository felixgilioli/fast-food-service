plugins {
	kotlin("jvm") version "1.9.25"
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
	}

	repositories {
		mavenCentral()
	}
}

