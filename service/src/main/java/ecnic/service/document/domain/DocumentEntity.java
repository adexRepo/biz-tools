package ecnic.service.document.domain;

import static ecnic.service.common.constants.DatabaseConstant.DOCUMENT;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecnic.service.common.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(schema = DOCUMENT, name = "t_document")
@EqualsAndHashCode(callSuper = false)
class DocumentEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String fileName;
  private String contentType;
  @JsonIgnore
  private byte[] data;
}
