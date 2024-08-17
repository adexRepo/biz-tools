package ecnic.service.document.domain.exception;

public class DocumentNotFoundException extends RuntimeException {

  public DocumentNotFoundException(String message) {
    super(message);
  }

  public static DocumentNotFoundException forDocumentId(Long documentId) {
    return new DocumentNotFoundException("Document with ID Number " + documentId + " not found");
  }
}
