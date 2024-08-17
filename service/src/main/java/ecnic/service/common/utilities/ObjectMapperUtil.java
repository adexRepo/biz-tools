package ecnic.service.common.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ObjectMapperUtil{

  private ObjectMapperUtil(){}
  
  public static String convertToJson(Object obj) {
    try{
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);
    }catch (JsonProcessingException e){
      return "";
    }
  }
}
