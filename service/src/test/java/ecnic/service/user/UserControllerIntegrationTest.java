//package ecnic.service.user;
//
//import static ecnic.service.common.utilities.ObjectMapperUtil.convertToJson;
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import ecnic.service.common.models.RoleType;
//import ecnic.service.user.domain.models.CreateUser;
//import ecnic.service.user.domain.models.UpdateUser;
//import ecnic.service.user.domain.models.User;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.modulith.test.ApplicationModuleTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//@ApplicationModuleTest
//@AutoConfigureMockMvc
//@RequiredArgsConstructor
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class UserControllerIntegrationTest {
//
//  private final MockMvc mockMvc;
//
//  @Autowired
//  private ObjectMapper objectMapper;
//
//  private Long userId;
//
//  // Happy Test: save new user and return user dto
//  // Exception Test: user email must not be null case
//  // Exception Test: username is already registered case
//
//  // Happy Test: get list user dto use pageable
//  // Exception Test: wrong input size or page pageable
//
//  // Happy Test: get the user dto use the username or user ID
//  // Exception Test: user with ID or username not found case
//
//  // Happy Test: update information user
//  // Exception Test: user target updating not found case
//
//  // Happy Test: delete user by ID
//  // Exception Test: user target deleting not found!
//
//
//
//  @Test
//  @DisplayName("Should success get create User")
//  @Order(1)
//  void Create_User_Status200() throws Exception {
//    final CreateUser createUser = new CreateUser(
//        UUID.randomUUID().toString(),
//        "Ade",
//        "Joe",
//        "jack",
//        RoleType.MEMBER,
//        new ArrayList<>(List.of("street", "track")),
//        new ArrayList<>(List.of("0896897987988", "0624512211")),
//        new ArrayList<>(List.of("test@gmail.com", "jhonnie@gmail.com")), 1241L);
//
//    final String endpoint = "/api/users";
//    MvcResult mvcResult = mockMvc.perform(
//        post(endpoint)
//            .contentType(MediaType.APPLICATION_JSON_VALUE)
//            .content(convertToJson(new ArrayList<>(List.of(createUser))))
//    ).andExpect(status().isCreated()).andReturn();
//    String responseJson = mvcResult.getResponse().getContentAsString();
//
//    List<User> users = objectMapper.readValue(responseJson, new TypeReference<List<User>>() {
//    });
//    if (!users.isEmpty()) {
//      this.userId = users.get(0).id();
//    }
//  }
//
//  @Test
//  @DisplayName("Should success update user")
//  @Order(2)
//  void Update_User_Status200() throws Exception {
//    UpdateUser updateUser = new UpdateUser(
//        this.userId,
//        "Joni",
//        "Johan",
//        "LONAs",
//        new ArrayList<>(List.of("street", "track")),
//        new ArrayList<>(List.of("0896897987988", "0624512211")),
//        new ArrayList<>(List.of("test@gmail.com", "jhonnie@gmail.com")), 125L);
//
//    final String endpoint = "/api/users";
//    mockMvc.perform(
//            put(endpoint)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(convertToJson(new ArrayList<>(List.of(updateUser))))
//        )
//        .andExpect(status().isOk())
//        .andExpect(Assertions::assertNotNull)
//        .andExpect(jsonPath("$[0].id", is(4)))
//        .andExpect(jsonPath("$[0].firstName", is("Joni")))
//        .andExpect(jsonPath("$[0].middleName", is("Johan")));
//  }
//
//  @Test
//  @DisplayName("Should get user")
//  @Order(3)
//  void Get_User_ByUserId_Status200() throws Exception {
//    final String endpoint = "/api/users/" + this.userId;
//    mockMvc.perform(get(endpoint)).andExpect(status().isOk());
//  }
//
//  @Test
//  @DisplayName("Should get exception user not found")
//  @Order(4)
//  void Get_User_ByUserId_Status404() throws Exception {
//    final String endpoint = "/api/users/99999999999";
//    mockMvc.perform(get(endpoint)).andExpect(status().isNotFound());
//  }
//
//  @Test
//  @DisplayName("Should get page result")
//  @Order(5)
//  void Get_User_ByPageable_Status200() throws Exception {
//
//    final String endpoint = "/api/users?page=0&size=5&sort=id,asc";
//    mockMvc.perform(get(endpoint))
//        .andExpect(status().isOk())
//        .andExpect(jsonPath("$.data").isArray())
//        .andExpect(jsonPath("$.totalElements").isNumber())
//        .andExpect(jsonPath("$.pageNumber").isNumber())
//        .andExpect(jsonPath("$.totalPages").isNumber())
//        .andExpect(jsonPath("$.isFirst").isBoolean())
//        .andExpect(jsonPath("$.isLast").isBoolean())
//        .andExpect(jsonPath("$.hasNext").isBoolean())
//        .andExpect(jsonPath("$.hasPrevious").isBoolean());
//  }
//
//  @Test
//  @DisplayName("Should success delete user")
//  @Order(7)
//  void Delete_User_Status200() throws Exception {
//    final String endpoint = "/api/users/" + this.userId;
//    mockMvc.perform(delete(endpoint)).andExpect(status().isOk());
//  }
//
//
//}
