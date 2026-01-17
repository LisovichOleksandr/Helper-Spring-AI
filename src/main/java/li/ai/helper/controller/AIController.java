package li.ai.helper.controller;

import li.ai.helper.model.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class AIController {

    private final ChatClient chatClient;

    @PostMapping("/ask-me")
    public String askMe(@RequestBody RequestDto requestDto) {
        return chatClient.prompt()
                .advisors(advisorSpec -> {
                    advisorSpec.param(ChatMemory.CONVERSATION_ID, requestDto.getChatId());
                })
                .user(requestDto.getQuestion())
                .call()
                .content();
    }
}
