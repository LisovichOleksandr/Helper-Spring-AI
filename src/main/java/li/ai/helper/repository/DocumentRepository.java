package li.ai.helper.repository;

import li.ai.helper.model.entity.LoadedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<LoadedDocument, UUID> {

    boolean existsByFileNameAndContentHash(String fileName, String contentHash);
}
