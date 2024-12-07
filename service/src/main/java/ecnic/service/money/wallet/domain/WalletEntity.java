package ecnic.service.money.wallet.domain;

import static ecnic.service.common.constants.DatabaseConstant.DB_SCHEMA_MONEY;

import ecnic.service.common.models.BaseEntity;
import ecnic.service.money.wallet.domain.models.WalletType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(schema = DB_SCHEMA_MONEY, name = "t_wallet")
@EqualsAndHashCode(callSuper = false)
class WalletEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String walletName;
  private Long walletUserId;
  @Enumerated(EnumType.STRING)
  private WalletType walletType;
  private BigDecimal balance;
  private String defaultCurrency;

}
