package ecnic.service.document.domain;

import static ecnic.service.document.domain.DocumentMapper.convertToDocument;
import static ecnic.service.document.domain.DocumentMapper.convertToEntity;
import static ecnic.service.document.domain.DocumentMapper.updateEntity;

import ecnic.service.common.models.PagedResult;
import ecnic.service.document.DocumentService;
import ecnic.service.document.domain.exception.DocumentNotFoundException;
import ecnic.service.document.domain.models.Document;
import ecnic.service.document.domain.models.UpdateDocument;
import ecnic.service.document.domain.models.UploadDocument;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
class DocumentServiceImpl implements DocumentService {

  private final DocumentRepository documentRepository;

  @Override
  public PagedResult<Document> getDocuments(Pageable pageable) {
    Page<Document> documentPage = documentRepository.findAllBy(pageable);
    return new PagedResult<>(documentPage);
  }

  @Override
  public Document getDocument(Long id) {
    Optional<DocumentEntity> documentEntityOptional = documentRepository.findById(id);
    if (documentEntityOptional.isEmpty()) {
      throw DocumentNotFoundException.forDocumentId(id);
    }
    return convertToDocument(documentEntityOptional.get());
  }

  @Override
  public Document uploadDocument(UploadDocument uploadDocument, MultipartFile file)
      throws IOException {
    DocumentEntity documentEntity = convertToEntity(file, uploadDocument);
    documentEntity = documentRepository.saveAndFlush(documentEntity);
    return convertToDocument(documentEntity);
  }

  @Override
  public Document updateDocument(UpdateDocument uploadDocument) {
    Optional<DocumentEntity> documentEntityOptional = documentRepository.findById(
        uploadDocument.id());
    if (documentEntityOptional.isEmpty()) {
      throw DocumentNotFoundException.forDocumentId(uploadDocument.id());
    }

    updateEntity(documentEntityOptional.get(), uploadDocument);
    documentRepository.saveAndFlush(documentEntityOptional.get());
    return convertToDocument(documentEntityOptional.get());
  }

  @Override
  public void deleteDocument(Long id) {
    Optional<DocumentEntity> documentEntityOptional = documentRepository.findById(id);
    if (documentEntityOptional.isEmpty()) {
      throw DocumentNotFoundException.forDocumentId(id);
    }
    documentRepository.deleteById(id);
  }
}
