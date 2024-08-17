package ecnic.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.modulith.Modulith;

@Modulith
@EnableConfigurationProperties
public class BizToolsSvcApplication {

  public static void main(String[] args) {
    SpringApplication.run(BizToolsSvcApplication.class, args);
  }
}
