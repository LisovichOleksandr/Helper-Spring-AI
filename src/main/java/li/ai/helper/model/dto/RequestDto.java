package li.ai.helper.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestDto {
    private UUID chatId;
    private String question;
}
