package li.ai.helper.ai.advisor;

import li.ai.helper.dictionary.AITemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.mistralai.MistralAiChatOptions;

import java.util.Map;

@Slf4j
@Builder
public class ExpansionQueryAdviser implements BaseAdvisor {

    public static final String ORIGINAL_QUERY = "originalQuery";
    public static final String EXPANSION_QUERY = "expansionQuery";

    public final ChatClient chatClient;

    @Getter
    private final int order;

    private final double temperature;
    private final double topP;

    private static final PromptTemplate template = PromptTemplate.builder()
            .template(AITemplate.TEMPLATE_2.getTemplate())
             .build();

    public static ExpansionQueryAdviserBuilder builder(
            ChatModel chatModel,
            double temperature,
            double topP
    ) {
        return new ExpansionQueryAdviserBuilder()
                .chatClient(
                        ChatClient.builder(chatModel)
                                .defaultOptions(
                                        MistralAiChatOptions.builder()
                                                .temperature(temperature)
                                                .topP(topP)
                                                .build()
                                )
                                .build()
                )
                .temperature(temperature)
                .topP(topP);
    }

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        String originalQuery = chatClientRequest.prompt().getUserMessage().getText();

        log.info("Before query: {}", originalQuery);

        String expansionQuery = expand(originalQuery);

        log.info("expansionQuery>> Before query : {}", expansionQuery);


        return chatClientRequest.mutate()
                .context(ORIGINAL_QUERY, originalQuery)
                .context(EXPANSION_QUERY, expansionQuery)
                .build();
    }

    private String expand(String originalQuery) {
        try {
            String rendered = template.render(Map.of("question", originalQuery));

            return chatClient.prompt()
                    .user(rendered)
                    .call()
                    .content();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        return chatClientResponse;
    }
}
