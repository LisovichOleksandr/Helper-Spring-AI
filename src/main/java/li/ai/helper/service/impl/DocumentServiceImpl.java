package li.ai.helper.service.impl;

import li.ai.helper.model.entity.LoadedDocument;
import li.ai.helper.repository.DocumentRepository;
import li.ai.helper.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final ResourcePatternResolver resolver;
    private final VectorStore vectorStore;
    private final DocumentRepository documentRepository;

    @Value("${app.chunkSize}")
    private int chunksSize;

    @Value("${app.documentPath}")
    private String documentPath;

    @Override
    @SneakyThrows
    public void loadDocument() {
        List<Resource> documentResources = Arrays.stream(resolver.getResources(documentPath)).toList();

        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(chunksSize)
                .build();

        for (Resource documentResource : documentResources) {
            try {
                String filename = documentResource.getFilename();
                String contentHash = calculateHash(documentResource);

                if (documentRepository.existsByFileNameAndContentHash(filename, contentHash)) {
                    continue;
                }
                TextReader textReader = new TextReader(documentResource);

                List<Document> originDocument = textReader.read();

                List<Document> enrichedDocuments = originDocument.stream().map(document -> new Document(
                                document.getFormattedContent(),
                                Map.of("filename", Objects.requireNonNull(documentResource.getFilename()),
                                        "source", "internal_docs",
                                        "processed_at", LocalDate.now()

                                )
                        ))
                        .toList();

                List<Document> chunks = splitter.split(enrichedDocuments);

                vectorStore.add(chunks);

                LoadedDocument loadedDocument = LoadedDocument.builder()
                        .fileName(filename)
                        .contentHash(contentHash)
                        .chunkCount(chunksSize)
                        .build();

                documentRepository.save(loadedDocument);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SneakyThrows
    private String calculateHash(Resource documentResource) {
        return DigestUtils.md5DigestAsHex(documentResource.getInputStream());
    }
}
