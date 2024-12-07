package ecnic.service.document.domain;

import ecnic.service.document.domain.models.Document;
import ecnic.service.document.domain.models.UpdateDocument;
import ecnic.service.document.domain.models.UploadDocument;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

final class DocumentMapper {

  private DocumentMapper() {
  }

  static Document convertToDocument(DocumentEntity documentEntity) {
    return new Document(documentEntity.getId(), documentEntity.getFileName(),
        documentEntity.getContentType());
  }

  static DocumentEntity convertToEntity(MultipartFile file, UploadDocument uploadDocument)
      throws IOException {
    DocumentEntity documentEntity = new DocumentEntity();
    documentEntity.setFileName(uploadDocument.fileName());
    documentEntity.setContentType(uploadDocument.contentType());
    documentEntity.setData(file.getBytes());
    documentEntity.setCreatedBy(uploadDocument.uploadBy());
    documentEntity.setUpdatedBy(uploadDocument.uploadBy());
    return documentEntity;
  }

  static void updateEntity(DocumentEntity documentEntity, UpdateDocument updateDocument) {
    documentEntity.setUpdatedBy(updateDocument.updateBy());
    documentEntity.setFileName(updateDocument.fileName());
  }
}
