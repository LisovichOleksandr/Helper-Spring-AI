package li.ai.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatModel chatModel;
    private String prompt;

    public String getResponse(String prompt) {
        this.prompt = prompt;
        return chatModel.call(prompt);
    }

    public String getResponseOptions(String prompt) {
        ChatResponse call = chatModel.call(
                new Prompt(
                        prompt,
                        MistralAiChatOptions.builder()
                                .model(MistralAiApi.ChatModel.LARGE.getValue())
                                .temperature(0.5)
                                .build()
                )
        );
        return call.getResult().getOutput().getText();
    }
}
