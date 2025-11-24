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

    * Все запросы к БД выполняются через Spring Data JPA или подготовленные запросы.

3. **XSS**


## Отчеты SAST/SCA

Отчеты автоматически создаются в GitHub Actions при push в репозиторий.

* **SpotBugs**: `target/spotbugsXml.xml`
* **OWASP Dependency-Check**: `target/dependency-check-report.html`

Пример отчета:

![SAST Report](ci_reports/spotbugs-report.png)

![SCA Report](ci_reports/dependency-check-report.png)
