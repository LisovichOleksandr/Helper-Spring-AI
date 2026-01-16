Helper Spring AI 🤖

Helper Spring AI is a simple Spring Boot application that demonstrates how to integrate Spring AI with a large language model (LLM) to build an HTTP API for AI-powered text responses.

The project exposes REST endpoints that allow you to send prompts to an AI model and receive generated responses, including configurable model options such as temperature and model size.

🚀 Features

Spring Boot 3 application

Integration with Spring AI

REST API for AI chat interaction

Support for: Basic prompt calls

Advanced prompt options (model, temperature)

Clean layered architecture (Controller → Service)

🛠️ Tech Stack: Java 17, Spring Boot, Spring AI, Mistral AI (via Spring AI integration), Lombok, Maven

🔌 API Endpoints
1️⃣ Ask AI (basic)
GET /ask-ai?prompt=Your question here


Description:
Sends a plain text prompt to the AI model and returns the generated response.

2️⃣ Ask AI with options
GET /ask-ai-options?prompt=Your question here


Description:
Sends a prompt with custom AI options:

Model: Mistral Large

Temperature: 0.5

This endpoint demonstrates how to configure AI behavior using Prompt and ChatOptions.

⚙️ Configuration

Make sure your AI provider credentials are configured (e.g. in application.yml or environment variables):

spring:
  ai:
    mistralai:
      api-key: YOUR_API_KEY

🎯 Use Cases

AI-powered helper services

Chatbots

AI integrations for backend systems

Learning and experimenting with Spring AI

📌 Future Improvements

Add conversation memory

Streaming responses

Vector database (pgvector) integration

Authentication & rate limiting

Frontend UI

👤 Author: Alexander Lisovych
