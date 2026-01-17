package li.ai.helper.service;

import li.ai.helper.model.entity.AIChat;

import java.util.UUID;

public interface AIChatService {

    AIChat createAIChat(UUID uuid);
    AIChat getAIChatById(UUID uuid);
    void saveAiChat(AIChat AIChat);
}
