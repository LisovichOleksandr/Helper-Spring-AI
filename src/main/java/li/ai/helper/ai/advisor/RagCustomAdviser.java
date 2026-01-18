package li.ai.helper.ai.advisor;

import li.ai.helper.dictionary.AITemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Builder
public class RagCustomAdviser implements BaseAdvisor {
    public static final String ORIGINAL_QUERY = "originalQuery";
    public static final String EXPANSION_QUERY = "expansionQuery";

    public final VectorStore vectorStore;

    @Getter
    private final int order;

    private final int topK;
    private final double similarityThreshold;

    private static final PromptTemplate template = PromptTemplate.builder()
            .template(AITemplate.PROMPT_TEMPLATE_1.getTemplate())
            .build();

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        String originalQuery = chatClientRequest.prompt().getUserMessage().getText();
        String expansionQuery = chatClientRequest.context().getOrDefault(EXPANSION_QUERY, originalQuery)
                .toString();

        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(expansionQuery)
                        .topK(topK)
                        .similarityThreshold(similarityThreshold)
                        .build()
        );

        if (documents.isEmpty()) {
            log.info("No documents found for query {}",  originalQuery);
            return chatClientRequest;
        }

        // сформировать текстовый контекст на основе найденного
        String llmContext = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));

        log.info("RAG context size: {}", llmContext.length());
        log.info("RAG llmContext: {}", llmContext);
        log.info("RAG docs size: {}", documents.size());
        documents.forEach(d ->
                log.debug("Doc snippet: {}", d.getText().substring(0, Math.min(200, d.getText().length())))
        );


        String finalPrompt = template.render(
                Map.of("context", llmContext,
                        "question", expansionQuery
                )
        );
        log.info("RAG final prompt: {}", finalPrompt);

        return chatClientRequest.mutate()
                .context(ORIGINAL_QUERY, originalQuery)
                .context(EXPANSION_QUERY, expansionQuery)
                .prompt(
                        chatClientRequest.prompt().augmentSystemMessage(finalPrompt) // Ключевое место
                )
                .build();
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {

        return chatClientResponse;
    }
}
