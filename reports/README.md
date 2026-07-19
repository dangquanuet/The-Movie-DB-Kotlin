# Тестовые отчёты

Отчёты по автотестам проекта. Все наши тесты лежат в пакете
`com.example.moviedb.tests.*` — это отличает их от родных (легаси) тестов проекта.

## Содержимое

- `allure-unit/index.html` — Allure-отчёт по **юнит-тестам** (single-file, самодостаточный).
- `coverage.html` — отчёт о покрытии кода (JaCoCo) по юнит-тестам, **один файл**.

Оба файла самодостаточны и открываются напрямую из файловой системы:

```bash
open reports/allure-unit/index.html   # Allure
open reports/coverage.html            # покрытие
```

> GitHub не рендерит HTML в веб-интерфейсе (показывает исходник). Чтобы увидеть
> отчёт в браузере без скачивания — можно воспользоваться htmlpreview:
>
> - Allure: https://htmlpreview.github.io/?https://raw.githubusercontent.com/fuzzz93/The-Movie-DB-Kotlin/unit-tests/reports/allure-unit/index.html
> - Покрытие: https://htmlpreview.github.io/?https://raw.githubusercontent.com/fuzzz93/The-Movie-DB-Kotlin/unit-tests/reports/coverage.html

## Как запустить только наши тесты

Наши тесты изолированы в пакете `com.example.moviedb.tests.*`, поэтому запускаются фильтром.
Требуется JDK 21 (подойдёт JBR из Android Studio) и Android SDK.

```bash
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="$HOME/Library/Android/sdk"

# 1. Прогнать ТОЛЬКО наши юнит-тесты (флейвор dev = моковые данные)
./gradlew :app:testDevDebugUnitTest --tests "com.example.moviedb.tests.*"

# 2. Посчитать покрытие по этому же прогону (без повторного запуска тестов)
./gradlew :app:testDevDebugUnitTestCoverage -x testDevDebugUnitTest
```

## Как пересобрать отчёты

Allure CLI: https://allurereport.org/docs/install/

```bash
# Allure single-file по последнему прогону
allure generate app/build/allure-results --clean --single-file -o reports/allure-unit

# Покрытие: один HTML из JaCoCo XML (официальный HTML JaCoCo многофайловый)
python3 tools/jacoco_single_html.py \
    app/build/reports/jacoco/testDevDebugUnitTestCoverage/testDevDebugUnitTestCoverage.xml \
    > reports/coverage.html
```

Интерактивный просмотр Allure без пересборки файла:

```bash
allure serve app/build/allure-results
```
