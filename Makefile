# Android Studio plugin https://plugins.jetbrains.com/plugin/9333-makefile-language
# Build app command line https://developer.android.com/studio/build/building-cmdline
# Gradle https://docs.gradle.org/current/userguide/userguide.html

tasks:
	./gradlwe tasks

.PHONY: run-devDebug
run-devDebug:
	./gradlew installDevDebug
	adb shell am start -n com.example.moviedb.dev/com.example.moviedb.compose.main.ComposeActivity

.PHONY: run-devRelease
run-devRelease:
	./gradlew installDevRelease
	adb shell am start -n com.example.moviedb.dev/com.example.moviedb.compose.main.ComposeActivity

.PHONY: run-prdDebug
run-prdDebug:
	./gradlew installPrdDebug
	adb shell am start -n com.example.moviedb.dev/com.example.moviedb.compose.main.ComposeActivity

.PHONY: run-prdRelease
run-prdRelease:
	./gradlew installPrdRelease
	adb shell am start -n com.example.moviedb.dev/com.example.moviedb.compose.main.ComposeActivity

.PHONY: build-apk-devDebug
build-apk-devDebug:
	./gradlew :app:assembleDevDebug
	open app/build/outputs/apk/dev/debug

.PHONY: build-bundle-prdRelease
build-bundle-prdRelease:
	./gradlew :app:bundlePrdRelease
	open app/build/outputs/bundle/prdRelease