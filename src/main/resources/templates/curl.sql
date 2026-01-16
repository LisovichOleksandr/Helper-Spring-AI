curl -i http://localhost:8080/ask-ai?prompt=What-up

curl -i http://localhost:8080/ask-ai-options?prompt=What-up

curl -i -X POST http://localhost:8080/ask-me -H "Content-Type: application/json" -d '{"chatId": "7b9c2d5e-8d6f-4a2c-9b3e-1a4c7b8d9f10", "question": "What do you think about football?"}'
