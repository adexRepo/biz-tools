package ecnic.service.common.constants;

import static ecnic.service.common.exceptions.BaseExceptionEnum.LAST_PROCESS;

import ecnic.service.common.exceptions.GenericBizException;

public class BaseStatus {
  
  public enum RecordStatus {
    ACTIVE,
    INACTIVE,
    DELETED
  }
  
  public enum ResultStatus {
    SUCCESS,
    FAILED
  }
  
  public enum ApprovalStatus {
    APPROVED,
    REJECTED
  }
  
  public enum ProcessingStatus {
    NEW {
      @Override
      public ProcessingStatus next() {
        return PROCESSING;
      }
    },
    PROCESSING {
      @Override
      public ProcessingStatus next() {
        return COMPLETED;
      }
    },
    COMPLETED {
      @Override
      public ProcessingStatus next() {
        throw new GenericBizException(LAST_PROCESS, LAST_PROCESS.getDescription());
      }
    };
    
    public abstract ProcessingStatus next();
  }
  
}
