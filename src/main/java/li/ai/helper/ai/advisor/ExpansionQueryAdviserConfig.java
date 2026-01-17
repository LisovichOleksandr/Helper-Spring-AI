package li.ai.helper.ai.advisor;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpansionQueryAdviserConfig {

    @Bean
    ExpansionQueryAdviser expansionQueryAdviser(
            ChatModel chatModel,
            @Value("${app.expansionTemp}") double temperature,
            @Value("${app.expansionTopP}") double topP
    ) {
        return ExpansionQueryAdviser.builder(
                chatModel,
                temperature,
                topP
                )
                .order(1)
                .build();
    }
}
