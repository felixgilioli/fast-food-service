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

	tasks.withType<Test>().configureEach {
		useJUnitPlatform()
		finalizedBy(tasks.named("jacocoTestReport"))
	}

	repositories {
		mavenCentral()
	}
}

sonar {
	properties {
		property("sonar.projectKey", "felixgilioli_fast-food-service")
		property("sonar.organization", "felixgilioli")
		property(
			"sonar.coverage.jacoco.xmlReportPaths",
			"${layout.buildDirectory.get().asFile}/reports/jacoco/jacocoRootReport/jacocoRootReport.xml"
		)
	}
}

jacoco {
	toolVersion = "0.8.13"
}

// garante jacoco em todos os subprojetos (domain/application/infrastructure)
subprojects {
	apply(plugin = "jacoco")

	// usa o mesmo JUnit platform; e sempre tenta gerar report do próprio módulo
	tasks.withType<Test>().configureEach {
		useJUnitPlatform()
		finalizedBy(tasks.named("jacocoTestReport"))
	}

	tasks.named<JacocoReport>("jacocoTestReport") {
		dependsOn(tasks.named("test"))
		reports {
			xml.required.set(true)
			html.required.set(true)
			csv.required.set(false)
		}
	}
}

// relatório de cobertura agregado (todos os subprojetos)
val jacocoRootReport = tasks.register<JacocoReport>("jacocoRootReport") {
	group = "verification"
	description = "Gera um relatório JaCoCo agregado para todos os subprojetos."

	// executa os testes de todos os subprojetos antes de agregar
	dependsOn(subprojects.map { it.tasks.named("test") })

	// coleta os .exec de todos os subprojetos
	executionData.from(subprojects.map { p ->
		p.layout.buildDirectory.file("jacoco/test.exec")
	})

	// fontes e classes de todos os subprojetos
	val mainSourceSets = subprojects.mapNotNull { p ->
		p.extensions.findByType(org.gradle.api.plugins.JavaPluginExtension::class.java)
			?.sourceSets
			?.findByName("main")
	}

	sourceDirectories.from(mainSourceSets.map { it.allSource.srcDirs })
	classDirectories.from(mainSourceSets.map { it.output })

	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
	}
}

// faz o 'check' gerar também o relatório agregado
tasks.named("check") {
	dependsOn(jacocoRootReport)
}
