# Wisper Vault 🔐

**Wisper Vault** is a secure journaling app built with **Spring Boot** and **AES encryption**.  
Every journal entry is encrypted using a password-derived key, ensuring only the rightful user can decrypt their private thoughts.

---

## ✨ Features

- 🔐 AES-GCM encryption for maximum confidentiality
- 🔑 JWT-based authentication
- 👤 User-specific encrypted storage
- 🌐 RESTful API with full CRUD support

---

## ⚙️ Tech Stack

- 🧠 Java 17
- 🧰 Spring Boot
- 🗄️ MySQL Database
- 🛡️ JWT (JSON Web Token)
- 🔐 AES Encryption with PBKDF2

---

## 📫 Postman API Collection

Use [Postman](https://www.postman.com/) to test all API endpoints:

### 🔑 Auth Endpoints

| Method | Endpoint         | Description        |
|--------|------------------|--------------------|
| POST   | `/login`         | Authenticate user and return JWT |
| POST   | `/register`      | Register a new user |

### 📓 Journal Endpoints (Require JWT)

| Method | Endpoint              | Description          |
|--------|-----------------------|----------------------|
| POST   | `/entries`            | Create new entry     |
| GET    | `/entries`            | Get all entries      |
| GET    | `/entries/{id}`       | Get entry by ID      |
| PUT    | `/entries/{id}`       | Update an entry      |
| DELETE | `/entries/{id}`       | Delete an entry      |

> 🔐 Add the JWT token to the `Authorization` header as:  
> `Bearer YOUR_TOKEN_HERE`

---

## 🧠 Future Enhancements

- 🌈 UI for journal entry viewing/decryption
- 📦 Secure backup and export
- 🧾 Entry version history

---

## 👩‍💻 Author

**Lalitha Anjali Aruna**  
_Engineering Student – CSE Cyber Security_  
[GitHub](https://github.com/lalithanjaliaruna123)

---

## 📂 Project Setup

```bash
git clone https://github.com/lalithanjaliaruna123/Wisper-Vault.git
cd Wisper-Vault
./mvnw spring-boot:run
