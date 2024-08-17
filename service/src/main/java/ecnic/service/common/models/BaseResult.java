package ecnic.service.common.models;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseResult<T> {

  private T result;
  private List<T> resultList;
  private String status;
  private Long duration;
}
