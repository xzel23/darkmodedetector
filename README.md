Darkmode Detector for Java 25+ (POC)
====================================

A minimal proof of concept for a working dark mode detector intended to be submitted to OpenJDK.

Features
--------

- pure Java; no third party libraries
- implemented using the Foreign Function and Memory API (JEP 454)
- uses the Java platform logger for outputting warning message
- query system dark mode setting
- detect changes to the system settings
- implementated for Windows, MacOS, and Linux
- if detection is not possible, light mode is assumed
- look at the samples for examples on how to use in both Swing and JavaFX

Building
--------

- make sure Java 25 and JavaFX is set up (I did not use the JavaFX Gradle plugin to keep this minimal)
- run `./gradlew build` in the project root or build from within your IDE and run the samples

Notes
-----

This is not intended as a standalone library. The code is taken from one of my OSS libraries, logging
replaced by the platform logger and stripped of all other dependencies.

I migth create a small standalone and ready-to-use library under ASL-2 at a later time.

License
-------

I release this under GPL 2 with Classpath Exception to make it possible to be included in OpenJDK and/or JavaFX.
