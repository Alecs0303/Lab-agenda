package com.example.demo.Controllers;

import com.example.demo.Models.TestsModels;
import com.example.demo.Services.TestsServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc // Injects a mock object fully configured
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TestsControllersTests implements Serializable {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    // It is a real object, it isn't a Mock, but is being prepared for Mocking it
    TestsControllers testsControllers = new TestsControllers();

    @MockBean
    private TestsServices testsServicesMock;

    @Test
    void obtenerTestStatusOk() throws Exception {
        List<TestsModels> data = new ArrayList<>();
        data.add(TestsModels.builder().name("Test 1").description("Primer Test").build());
        data.add(TestsModels.builder().name("Test 2").description("Segundo Test").build());
        data.add(TestsModels.builder().name("Test 3").description("Tercer Test").build());
        data.add(TestsModels.builder().name("Test 4").description("Cuarto Test").build());

        given(testsServicesMock.obtenerTest()).willReturn(data);

        MvcResult response = mockMvc.perform(get("/api/controller/test"))
                .andExpect(status().isOk()).andDo(print()).andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 200);
    }

    @Test
    void obtenerTestStatusNoContent() throws Exception {
        List<TestsModels> data = new ArrayList<>();

        given(testsServicesMock.obtenerTest()).willReturn(data);

        MvcResult response = mockMvc.perform(get("/api/controller/test"))
                .andExpect(status().isNoContent()).andDo(print()).andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 204);
    }

    @Test
    void obtenerTestByIDStatusOk() throws Exception {

        long id = 1L;
        TestsModels testsModels = TestsModels.builder()
                .name("Test")
                .description("Test 1")
                .build();

        given(testsServicesMock.obtenerTestByID(id)).willReturn(Optional.of(testsModels));

        MvcResult response = mockMvc.perform(get("/api/controller/test/{id}", id))
                .andExpect(status().isOk()).andDo(print()).andReturn();

        System.out.println(objectMapper.writeValueAsString(testsModels));
        Assertions.assertEquals(response.getResponse().getStatus(), 200);
    }

    /*@Test
    void obtenerTestByIDStatusNotFound() throws Exception {
        long testId = 1L;
        TestsModels testsModels = TestsModels.builder()
                .name("Test")
                .description("Test 1")
                .build();

        given(testsServicesMock.obtenerTestByID(testId)).willReturn(Optional.empty());

        MvcResult response = mockMvc.perform(get("/api/controller/test/{id}", testId))
                .andExpect(status().isNotFound()).andDo(print()).andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 404);
    }*/

    @Test
    void guardarTestStatusCreated() throws Exception {
        TestsModels testsModels = TestsModels.builder()
                .id(1L)
                .name("Test")
                .description("Test 1")
                .build();

        given(testsServicesMock.guardarTest(any(TestsModels.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        MvcResult response = mockMvc.perform(post("/api/controller/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testsModels)))
                .andDo(print()).andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 201);
    }

    /*@Test
    void guardarTestStatusNotFound() throws Exception {
        TestsModels testsModels = TestsModels.builder()
                .id(1L)
                .name("Test")
                .description("Test 1")
                .build();

        given(testsServicesMock.guardarTest(any()))
                .willAnswer((invocation) -> invocation.getArgument(0));

        MvcResult response = this.mockMvc.perform(post("/api/controller/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Optional.empty())))
                .andDo(print()).andExpect(status().isNotFound()).andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 404);
    }*/

    @Test
    void actualizarTestStatusCreated() throws Exception{
        long id = 1L;
        TestsModels testGuardado = TestsModels.builder()
                .name("Primer test")
                .description("Test 1")
                .build();

        TestsModels testActualizado = TestsModels.builder()
                .name("Segundo test")
                .description("Test 2")
                .build();

        //Map<String,Object> response = new HashMap<>();
        //response.put("Message", "Test edited");

        given(testsServicesMock.obtenerTestByID(id)).willReturn(Optional.of(testGuardado));
        given(testsServicesMock.actualizarTest(any(TestsModels.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        MvcResult result = mockMvc.perform(put("/api/controller/test/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testActualizado)))
                .andExpect(status().isCreated()).andReturn();
        //.andExpect(content().json(objectMapper.writeValueAsString(response))).andReturn();

        Assertions.assertEquals(result.getResponse().getStatus(), 201);
        //Assertions.assertEquals(result.getResponse().getContentAsString(), "{\"Message\":\"Test edited\"}");
    }

    /*@Test
    void actualizarTestStatusNotFound() throws Exception{

        long id = 1L;
        TestsModels testGuardado = TestsModels.builder()
                .name("Primer test")
                .description("Test 1")
                .build();

        TestsModels testActualizado = TestsModels.builder()
                .name("Segundo test")
                .description("Test 2")
                .build();

        //Map<String,Object> response = new HashMap<>();
        //response.put("Message", "Unable to edit test");

        given(testsServicesMock.obtenerTestByID(id)).willReturn(Optional.empty());
        given(testsServicesMock.actualizarTest(any(TestsModels.class))).
                willAnswer((invocationOnMock)-> invocationOnMock.getArgument(0));

        MvcResult result = mockMvc.perform(put("/api/controller/test/{id}", id))
                .andExpect(status().isNotFound()).andReturn();
                //.contentType(MediaType.APPLICATION_JSON)
                        //.content(objectMapper.writeValueAsString()))
                //.andExpect(status().isNotFound()).andReturn();
                //.andExpect(content().json(objectMapper.writeValueAsString(response))).andReturn();

        Assertions.assertEquals(result.getResponse().getStatus(), 404);
        //Assertions.assertEquals(result.getResponse().getContentAsString(), "{\"Message\":\"Unable to edit test\"}");
    }*/

    @Test
    void eliminarTestStatusOk() throws Exception {
        long id = 1L;

        willDoNothing().given(testsServicesMock).eliminarTest(id);

        MvcResult result = mockMvc.perform(delete("/api/controller/test/{id}", id))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertEquals(result.getResponse().getStatus(), 200);
    }

    /*@Test
    void eliminarTestN() throws Exception {
        long id = 1L;

        //Map<String,Object> response = new HashMap<>();
        //response.put("Message", "Unable to delete test");

        willDoNothing().given(testsServicesMock).eliminarTest(null);

        MvcResult result = mockMvc.perform(delete("/api/controller/test/2"))
                .andExpect(status().isNoContent())
                .andReturn();

        // System.out.println("Soy lo que quieren ver");
        // System.out.println(result.getResponse().getContentAsString());
        Assertions.assertEquals(result.getResponse().getStatus(), 204);
    }*/

}