package li.ai.helper.controller;

import li.ai.helper.model.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AIController {

    private final ChatClient chatClient;

    @PostMapping("/ask-me")
    public String askMe(@RequestBody RequestDto requestDto) {
        return chatClient.prompt(requestDto.getQuestion()).call().content();
    }
}
