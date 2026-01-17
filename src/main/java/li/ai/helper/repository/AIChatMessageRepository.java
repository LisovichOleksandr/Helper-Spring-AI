package li.ai.helper.repository;

import li.ai.helper.model.entity.AIChat;
import li.ai.helper.model.entity.AIChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AIChatMessageRepository extends JpaRepository<AIChatMessage, UUID> {
}
