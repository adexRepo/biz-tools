package ecnic.service.document.domain;

import ecnic.service.document.domain.models.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

  @Query("""
      select new ecnic.service.document.domain.models.Document(
          doc.id,
          doc.fileName,
          doc.contentType
      ) from DocumentEntity doc
      """)
  Page<Document> findAllBy(Pageable pageable);
}
