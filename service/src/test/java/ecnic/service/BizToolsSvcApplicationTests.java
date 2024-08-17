package ecnic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest
class BizToolsSvcApplicationTests {

  static ApplicationModules modules = ApplicationModules.of(BizToolsSvcApplication.class);

  @Test
  void testContext(){
    Assertions.assertNotNull(modules);
  }

}
