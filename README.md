# Revista Backend

Backend Spring Boot para la plataforma de revistas virtuales.

## Deploy en Railway

Este proyecto esta preparado para leer configuracion desde variables de entorno.

### Variables requeridas

Define estas variables en Railway:

- `SPRING_PROFILES_ACTIVE=prod`
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`
- `CORS_ALLOWED_ORIGINS`

### Variables opcionales (correo)

Solo si usas envio de emails:

- `MAIL_HOST` (default: `smtp.gmail.com`)
- `MAIL_PORT` (default: `587`)
- `EMAIL_USERNAME`
- `EMAIL_PASSWORD`
- `MAIL_SMTP_AUTH` (default: `true`)
- `MAIL_SMTP_STARTTLS_ENABLE` (default: `true`)
- `MAIL_SMTP_STARTTLS_REQUIRED` (default: `true`)

## Comandos locales

Compilar:

```bash
./mvnw clean package
```

Ejecutar:

```bash
./mvnw spring-boot:run
```

En Windows PowerShell:

```powershell
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

## Seguridad

- No subas credenciales reales al repositorio.
- `src/main/resources/application-local.properties` ya esta ignorado por `.gitignore`.
- Si alguna credencial ya fue expuesta, debes rotarla antes del deploy.
