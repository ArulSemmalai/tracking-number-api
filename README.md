# 🚀 Tracking Number Generator API

A scalable, concurrency-safe Spring Boot microservice for generating globally unique tracking numbers using Redis and Lua scripting, deployed on AWS EC2.

---

## 🌐 Live API Endpoint

**URL:**  
[http://51.20.91.146:8080/api/next-tracking-number](http://51.20.91.146:8080/api/next-tracking-number)

**Method:**  
`POST`

**Request Payload (Required):**
- `origin_country_id`
- `destination_country_id`
- `weight`
- `created_at`
- `customer_id`
- `customer_name`
- `customer_slug`

**Example: Request Payload**
```Request Payload
{
    "origin_country_id":"MY",
    "destination_country_id":"SG",
    "weight":1.120,
    "created_at":"2025-05-29T04:53:57.0158476+05:30",
    "customer_id":"de619854-b59b-425e-9db4-943979e1bd49",
    "customer_name":"REDBOX LOGISTICS",
    "customer_slug":"redbox-logistics"
}
```
**Example: Response Payload**
```
{
"tracking_number": "REDB6E815705D019",
"created_at": "2025-05-29T12:48:19.52342873Z"
}
```
**🛠 Features:**
✅ Thread-safe tracking number generation

✅ Global uniqueness using Redis with Lua scripting

✅ RESTful API built with Spring Boot

✅ Deployed via Docker on AWS EC2

✅ Scalable across multiple nodes

**🛠 ⚙️ Tech Stack:**

* Java 17

* Spring Boot

* Redis (in-memory key-value store)

* Docker

* AWS EC2

* Redis Lua Script (atomic uniqueness guarantee)

**🧑‍💻 Setup Instructions**

**🚀 1. Clone the project**

git clone https://github.com/ArulSemmalai/tracking-number-api

cd tracking-number-api

**2. 🐳 Docker Build & Run**

*Build Docker image:*

docker build -t tracking-number-api .

*Run container:*

docker run -d --name tracking-api -p 8080:8080 tracking-number-api

*Or push to ECR and pull in EC2:*

docker pull 402289716954.dkr.ecr.eu-north-1.amazonaws.com/microservices/tracking-number-api
docker run -d -p 8080:8080 tracking-number-api

**🧪 Testing**

Use Postman and request payload added above.

**🧠 How it Works**

The TrackingNumberService generates a tracking number using slug, UUID, and counter.

A Redis Lua script is executed to check if the number is unique and atomically store it.

Lua guarantees atomicity, so no two containers can create the same number even in high concurrency.

**🔐 Redis & Concurrency Handling**

Redis is used with EVAL Lua scripting for atomic uniqueness validation.

Redis ensures only one thread across the cluster inserts a given tracking number.

Java’s Spring Boot app uses RedisTemplate to execute the Lua script.


**Author : ArulKumar Semmalai**
