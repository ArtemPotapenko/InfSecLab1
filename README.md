# Lab1: REST API сервис с защитой и тестами

## Описание проекта

Проект представляет собой REST API для управления пользователями и конфигурациями. Реализованы следующие функции:

* Регистрация и авторизация пользователей
* Работа с JWT токенами для аутентификации
* CRUD операции с данными (пользователи, конфигурации, прошивки)

### Endpoints

| Метод | URL            | Описание                                       |
| ----- |----------------| ---------------------------------------------- |
| POST  | /auth/register | Регистрация нового пользователя                |
| POST  | /auth/login    | Авторизация, получение JWT                     |
| GET   | /data          | Получение списка пользователей (требуется JWT) |


Пример вызова с использованием JWT:

```bash
curl -H "Authorization: Bearer <JWT_TOKEN>" https://localhost/data
```


## Меры защиты

1. **Аутентификация**

    * Используется JWT токен с HMAC SHA256 подписью.
    * Токен содержит только username в payload.

2. **SQL Injection**

    * Все запросы к БД выполняются через Spring Data JPA.

3. **XSS**
   * Каждый ответ на запрос будет экранироваться через XssSanitizationAdvice.

## Отчеты SAST/SCA

Отчеты автоматически создаются в GitHub Actions при push в репозиторий.

* **SpotBugs**: `target/spotbugsXml.xml`
* **OWASP Dependency-Check**: `target/dependency-check-report.html`

  SportBugs
  ```xml
  <Errors missingClasses="0" errors="0"/>
  <FindBugsSummary num_packages="9" total_classes="17" total_size="259" clock_seconds="4.38" referenced_classes="185" vm_version="21.0.9+10-LTS" total_bugs="0" java_version="21.0.9" gc_seconds="0.07" alloc_mbytes="512.00" cpu_seconds="8.13" peak_mbytes="164.26" timestamp="Mon, 24 Nov 2025 00:34:44 +0000">
  ```

  OWASP
  ```yml
  dependency-check version: 12.1.9
  Report Generated On: Mon, 24 Nov 2025 00:35:46 GMT
  Dependencies Scanned: 77 (48 unique)
  Vulnerable Dependencies: 0
  Vulnerabilities Found: 0
  Vulnerabilities Suppressed: 0
  ```
