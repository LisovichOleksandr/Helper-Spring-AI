package li.ai.helper.ai;

import li.ai.helper.service.AIChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ChatClientConfig {

    private final AIChatService aiChatService;

//    @Value("${app.max-messages}")
    private final int maxMessages = 10;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultAdvisors(
                        addChatMemory(1)
                )
                .defaultOptions(
                        MistralAiChatOptions.builder()
                                .temperature(0.3)
                                .build()
                )
                .build();
    }

    private Advisor addChatMemory(int order) {
        return MessageChatMemoryAdvisor.builder(getPostgresChatMemory())
                .order(order)
                .build();
    }

    private ChatMemory getPostgresChatMemory() {
        return CustomPostgresChatMemory.builder()
                .maxMessages(maxMessages)
                .aiChatService(aiChatService)
                .build();
    }
}
