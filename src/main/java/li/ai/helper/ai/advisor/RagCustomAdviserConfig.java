package li.ai.helper.ai.advisor;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagCustomAdviserConfig {

    @Bean
    public RagCustomAdviser ragCustomAdviser(
            VectorStore vectorStore,
            @Value("${app.topK}") int topK,
            @Value("${app.similarityThreshold}") double similarityThreshold
    ) {
        return RagCustomAdviser.builder()
                .vectorStore(vectorStore)
                .topK(topK)
                .similarityThreshold(similarityThreshold)
                .order(3)
                .build();
    }
}
