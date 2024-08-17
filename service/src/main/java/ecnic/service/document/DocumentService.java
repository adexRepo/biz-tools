package ecnic.service.document;

import ecnic.service.common.models.PagedResult;
import ecnic.service.document.domain.models.Document;
import ecnic.service.document.domain.models.UpdateDocument;
import ecnic.service.document.domain.models.UploadDocument;
import java.io.IOException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

  PagedResult<Document> getDocuments(Pageable pageable);

  Document getDocument(Long id);

  Document uploadDocument(UploadDocument uploadDocument, MultipartFile file) throws IOException;

  Document updateDocument(UpdateDocument uploadDocument);

  void deleteDocument(Long id);

}
