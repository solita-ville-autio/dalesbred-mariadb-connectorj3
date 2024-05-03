plugins {
    kotlin("jvm") version "1.9.23"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.dalesbred:dalesbred:1.3.5")

    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.testcontainers:junit-jupiter:1.19.7")
    testImplementation("org.testcontainers:mariadb:1.19.7")

    testImplementation("org.mariadb.jdbc:mariadb-java-client:3.3.3")
    testImplementation("ch.qos.logback:logback-classic:1.5.6")
}

kotlin {
    jvmToolchain(17)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()

            targets {
                all {
                    testTask.configure {
                        options {
                            testLogging {
                                outputs.upToDateWhen { false }
                                showStandardStreams = true
                            }
                        }
                    }
                }
            }
        }
    }
}

