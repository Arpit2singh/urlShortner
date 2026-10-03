# ShortLink — URL Shortener

A backend URL shortener built with **Spring Boot**, **PostgreSQL** and **Redis**.

The project focuses on building a production-oriented backend with URL shortening, caching, expiry handling, click analytics and per-IP token bucket rate limiting.

---

## Features

- Create short URLs from long URLs
- Base62-based short code generation
- Duplicate URL detection
- HTTP/HTTPS URL validation
- Custom URL expiry
- Redirect short URLs to original URLs
- Redis caching using the cache-aside pattern
- PostgreSQL as persistent storage
- Click count tracking
- Last accessed timestamp
- Atomic click count updates
- Per-IP token bucket rate limiting
- Redis-based rate limiter state
- Configurable rate limit capacity and refill rate
- Global exception handling

---

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Redis
- Spring Validation
- Maven
- REST API

---

## Architecture

```text
              Client
                |
                v
          Rate Limiter
                |
                v
           Controller
                |
                v
             Service
            /       \
           v         v
        Redis    PostgreSQL
        Cache        DB
```

---

## Project Structure

```text
src/
└── main/
    └── java/
        └── com/arpit/shortlink/
            │
            ├── config/
            │   └── RateLimiterProperties.java
            │
            ├── Controller/
            │   └── UrlController.java
            │
            ├── Model/
            │   ├── UrlMapping.java
            │   ├── URLRequest.java
            │   └── UserRateLimitDetails.java
            │
            ├── Repository/
            │   └── UrlRepository.java
            │
            ├── Resolver/
            │   └── ClientIpResolver.java
            │
            ├── service/
            │   ├── UrlServiceImpl.java
            │   ├── RateLimiterService.java
            │   └── IncreaseClickCount.java
            │
            ├── util/
            │   └── Base62Encoder.java
            │
            └── exception/
                ├── InvalidUrlException.java
                └── ...
```

---

## Configuration

`application.yml`

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  data:
    redis:
      url: ${REDIS_URL}

rate-limit:
  capacity: 5
  refill-rate: 1
  request-cost: 1
```

---

## API Endpoints

### 1. Create Short URL

`POST /api/shorten`

**Request**

```json
{
  "longurl": "https://example.com",
  "expireAt": 60
}
```

**Response**

```json
{
  "id": 1,
  "longUrl": "https://example.com",
  "shortCode": "1",
  "createdAt": "2026-10-03T12:00:00",
  "expireAt": "2026-10-03T13:00:00",
  "clickCount": 0
}
```

### 2. Get Original URL

`GET /get/longUrl/{shortCode}`

**Example**

```http
GET /get/longUrl/1
```

### 3. Redirect to Original URL

`GET /go/longUrl/{shortCode}`

Redirects the client to the original URL.

### 4. Get Client IP

`GET /getIp`

---

## Rate Limiting

The application implements a **token bucket** rate limiter using Redis.

**Current configuration**

```yaml
rate-limit:
  capacity: 5
  refill-rate: 1
  request-cost: 1
```

**Meaning**

- Maximum tokens = `5`
- `1` token is refilled every second
- Each request consumes `1` token

Redis stores the bucket using the client IP as the key:

```text
rate_limit:<IP>
```

**Example**

```text
rate_limit:127.0.0.1

tokenLeft       → 3
lastRefreshTime → 2026-10-03T12:00:00
```

### Token Bucket Flow

```text
Request
   |
   v
Get Client IP
   |
   v
Redis Bucket
   |
   v
Calculate elapsed time
   |
   v
Refill tokens
   |
   v
Cap tokens at capacity
   |
   v
Consume request cost
   |
   +---- Enough tokens ----> Allow
   |
   +---- Not enough --------> Reject
```

---

## Redis Caching

Redis is used as a cache in front of PostgreSQL (cache-aside pattern).

### Cache Hit

```text
Request
   |
   v
Redis
   |
   v
URL found
   |
   v
Return URL
```

### Cache Miss

```text
Request
   |
   v
Redis
   |
   v
Not found
   |
   v
PostgreSQL
   |
   v
Store in Redis
   |
   v
Return URL
```

For URLs with expiry, the Redis TTL is aligned with the remaining database expiry time.

---

## Click Analytics

Each URL stores:

- `clickCount`
- `lastAccessAt`
