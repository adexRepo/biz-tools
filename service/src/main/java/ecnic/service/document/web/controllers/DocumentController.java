package ecnic.service.document.web.controllers;

import ecnic.service.common.models.PagedResult;
import ecnic.service.document.DocumentService;
import ecnic.service.document.domain.models.Document;
import ecnic.service.document.domain.models.UpdateDocument;
import ecnic.service.document.domain.models.UploadDocument;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/document")
@RequiredArgsConstructor
class DocumentController {

  private final DocumentService documentService;

  @GetMapping
  PagedResult<Document> documents(@PageableDefault(value = 2, page = 0) Pageable pageable) {
    return documentService.getDocuments(pageable);
  }

  @GetMapping("{id}")
  Document document(@PathVariable Long id) {
    return documentService.getDocument(id);
  }

  @PostMapping
  Document upload(
      @RequestBody @Valid UploadDocument uploadDocument,
      @RequestPart("data") @NotNull MultipartFile file) throws IOException {
    return documentService.uploadDocument(uploadDocument, file);
  }

  @PutMapping
  Document update(@RequestBody UpdateDocument updateDocument) {
    return documentService.updateDocument(updateDocument);
  }

  @DeleteMapping("{id}")
  void delete(@PathVariable Long id) {
    documentService.deleteDocument(id);
  }
}
