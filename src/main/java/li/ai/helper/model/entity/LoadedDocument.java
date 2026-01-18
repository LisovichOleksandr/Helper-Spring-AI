package li.ai.helper.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loaded_document")
public class LoadedDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "content_hash")
    private String contentHash;

    @Column(name = "chunk_count")
    private int chunkCount;

    @UpdateTimestamp
    @TimeZoneStorage(TimeZoneStorageType.NORMALIZE)
    private ZonedDateTime loadedAt;

}
