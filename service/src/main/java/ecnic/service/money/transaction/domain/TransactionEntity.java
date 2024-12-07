package ecnic.service.money.transaction.domain;

import static ecnic.service.common.constants.DatabaseConstant.DB_SCHEMA_MONEY;

import ecnic.service.common.models.BaseEntity;
import ecnic.service.money.transaction.domain.models.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(schema = DB_SCHEMA_MONEY, name = "t_transaction")
@EqualsAndHashCode(callSuper = false)
class TransactionEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long walletId;
  @Enumerated(EnumType.STRING)
  private TransactionType type;
  private ZonedDateTime at;
  private String item;
  private String remark; // main item
  private BigDecimal originalAmount; // main item
  private BigDecimal otherAmounts; // admin or fee
  private BigDecimal exchangeRate;
}
