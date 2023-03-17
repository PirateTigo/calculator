plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.5.0"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "ru.sibsutis.piratetigo"
version = "1.0.0"
description = "Калькулятор p-ичных чисел"

val calculatorMainClass = "$group.calculator.GUIStarter"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.mockito:mockito-core:5.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

javafx {
    version = "19"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainModule.set("calculator")
    mainClass.set(calculatorMainClass)
}

jlink {
    options.set(listOf(
            "--compress", "2",
            "--no-header-files",
            "--no-man-pages",
            "--verbose"
    ))
    launcher {
        name = "calculator"
        mainClass.set(calculatorMainClass)
    }
    jpackage {
        imageOptions = listOf(
                "--icon", "src/main/resources/icons/calculator.ico"
        )
        installerOptions = listOf(
                "--win-dir-chooser",
                "--win-menu",
                "--win-shortcut",
                "--win-shortcut-prompt",
                "--vendor", "SibSUTIs",
                "--description", "Calculator",
                "--copyright", "Artem Tarakanovskiy",
                "--verbose"
        )
        installerType = "exe"
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}