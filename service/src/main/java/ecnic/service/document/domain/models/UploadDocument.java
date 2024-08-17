package ecnic.service.document.domain.models;

import jakarta.validation.constraints.NotNull;

public record UploadDocument(
    @NotNull(message = "File Name is required") String fileName,
    @NotNull(message = "Content Type is required") String contentType,
    @NotNull(message = "Upload By is required") String uploadBy
) {

}
