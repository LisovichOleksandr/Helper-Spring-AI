package li.ai.helper.controller;

import li.ai.helper.model.dto.RequestDto;
import li.ai.helper.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/load")
    public ResponseEntity<String> load() {
        documentService.loadDocument();
        return ResponseEntity.ok("Document loaded.");
    }
}
