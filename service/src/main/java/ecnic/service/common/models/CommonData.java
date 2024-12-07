package ecnic.service.common.models;

import ecnic.service.common.constants.BaseStatus.RecordStatus;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;

@Data
public class CommonData<K extends Enum<K>, T extends Enum<T>> implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 3655071074413047702L;
    
    private K key;
    private Map<T, Serializable> data;
    private RecordStatus status;
    private Integer version;
    
}
