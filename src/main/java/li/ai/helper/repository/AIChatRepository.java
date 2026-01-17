package li.ai.helper.repository;

import li.ai.helper.model.entity.AIChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AIChatRepository extends JpaRepository<AIChat, UUID> {
}
