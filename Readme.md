# Spring Boot - JWT Based Hospital Management System

Spring Boot, Spring Security, RabbitMQ gibi teknolojiler kullanılarak geliştirilmiş bu uygulamada; authenticate olmuş kayıtlı bir kullanıcının barkod, ilaç, muayene, doktor, hasta gibi servisleri kullanarak örnek bir hastanenin otomasyonunu uçtan uca yönetmesi planlanmıştır.

#### Frontend (React) uygulaması için: [JWT Based Hospital Management System - Frontend](https://github.com/bilalmuzafferkose/hospital-management-system-ui)

## Başlangıç

Projeyi başlatmak ve yerel makinenizde çalıştırmak için aşağıdaki adımları takip edin.

### Gereksinimler

Aşağıdaki yazılımların yüklü olduğundan emin olun:
- Docker
- Docker Compose
- Java (min. JDK 18)

### Kullanılan Teknolojiler
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- Docker ve Docker Compose
- MySQL
- RabbitMQ

### Kurulum

1. Repository'yi klonlayın:
   ```bash
   git clone https://github.com/bilalmuzafferkose/spring-hospital-management-system
   ```
2. Proje dizinine gidin:
    ```bash
    cd spring-hospital-management-system
    ```
3. Spring Boot uygulamasını başlatın:
    ```bash
    ./mvnw spring-boot:run
    ```
4. Docker Compose ile MySQL ve RabbitMQ servislerini başlatın
    ```bash
    docker-compose up -d
    ```