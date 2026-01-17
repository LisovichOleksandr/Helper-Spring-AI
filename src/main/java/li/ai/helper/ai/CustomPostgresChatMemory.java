package li.ai.helper.ai;

import li.ai.helper.dictionary.AIRole;
import li.ai.helper.model.entity.AIChat;
import li.ai.helper.model.entity.AIChatMessage;
import li.ai.helper.service.AIChatService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.List;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
public class CustomPostgresChatMemory implements ChatMemory {

    private final AIChatService aiChatService;
    private final int maxMessages;


    @Override
    public void add(String conversationId, List<Message> messages) {
        AIChat aiChat = aiChatService.getAIChatById(UUID.fromString(conversationId));

        for (Message message : messages) {
            AIChatMessage aiChatMessage = AIChatMessage.builder()
                    .aiChat(aiChat)
                    .text(message.getText())
                    .AIRole(getAIRole(message))
                    .build();

            aiChat.getAiChatMessages().add(aiChatMessage);
        }
        aiChatService.saveAiChat(aiChat);
    }

    @Override
    public List<Message> get(String conversationId) {
        AIChat aiChat = aiChatService.getAIChatById(UUID.fromString(conversationId));

        return aiChat.getAiChatMessages().stream()
                .skip(Math.max(0, aiChat.getAiChatMessages().size() - maxMessages))
                .map(this::getMessage)
                .limit(maxMessages)
                .toList();

    }

    @Override
    public void clear(String conversationId) {

    }


    private AIRole getAIRole(Message message) {
        switch (message.getMessageType()) {
            case USER -> {
                return AIRole.USER;
            }
            case ASSISTANT -> {
                return AIRole.ASSISTANT;
            }
            case SYSTEM -> {
                return AIRole.SYSTEM;
            }
            default -> {
                return null;
            }
        }
    }

    private Message getMessage(AIChatMessage aiChatMessage) {
        switch (aiChatMessage.getAIRole()) {
            case USER -> {
                return new UserMessage(aiChatMessage.getText());
            }
            case ASSISTANT -> {
                return new AssistantMessage(aiChatMessage.getText());
            }
            case SYSTEM -> {
                return new AssistantMessage(aiChatMessage.getText());
            }
            default -> {
                return null;
            }
        }

    }
}
