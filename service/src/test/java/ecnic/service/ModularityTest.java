package ecnic.service;

import static java.lang.System.out;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest
class ModularityTest {
  static ApplicationModules modules = ApplicationModules.of(BizToolsSvcApplication.class);

  @Test
  void verifiesModularityStructure() {
    modules.verify();
  }

  @Test
  void printModuleSchema() {
    Assertions.assertNotNull(modules);
    modules.forEach(out::println);
  }

  @Test
  void createModuleDocumentation() {
    Assertions.assertNotNull(modules);
    new Documenter(modules).writeDocumentation();
  }

  @Test
  void writeDocumentationSnippets() {
    Assertions.assertNotNull(modules);

    new Documenter(modules)
        .writeModulesAsPlantUml()
        .writeIndividualModulesAsPlantUml();
  }
}
