package li.ai.helper.service.impl;

import li.ai.helper.model.entity.AIChat;
import li.ai.helper.repository.AIChatRepository;
import li.ai.helper.service.AIChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AIChatServiceImpl implements AIChatService {

    private final AIChatRepository aiChatRepository;

    @Override
    public AIChat createAIChat(UUID id) {
        System.out.println("CREATE>>>");
        return aiChatRepository.save(
                AIChat.builder()
                        .id(id == null ? UUID.randomUUID() : id)
                        .build()
        );
    }

    @Override
    public AIChat getAIChatById(UUID id) {
        System.out.println("GET>>>");
        Optional<AIChat> byId = aiChatRepository.findById(id);

        return byId.orElseGet(() -> createAIChat(id));

    }

    @Override
    public void saveAiChat(AIChat aiChat) {
        aiChatRepository.save(aiChat);
    }
}
