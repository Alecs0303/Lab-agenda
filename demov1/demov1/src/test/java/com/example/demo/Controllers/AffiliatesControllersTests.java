package com.example.demo.Controllers;

import com.example.demo.Models.AffiliatesModels;
import com.example.demo.Services.AffiliatesServices;
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
public class AffiliatesControllersTests implements Serializable {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    // It is a real object, it isn't a Mock, but is being prepared for Mocking it
    AffiliatesControllers affiliatesControllers = new AffiliatesControllers();

    @MockBean
    private AffiliatesServices affiliatesServicesMock;

    @Test
    void obtenerAffiliateStatusOk() throws Exception {
        List<AffiliatesModels> data = new ArrayList<>();
        data.add(AffiliatesModels.builder().name("Affiliate 1").age(10).mail("Affiliate1@gmail.com").build());
        data.add(AffiliatesModels.builder().name("Affiliate 2").age(20).mail("Affiliate2@gmail.com").build());
        data.add(AffiliatesModels.builder().name("Affiliate 3").age(30).mail("Affiliate3@gmail.com").build());
        data.add(AffiliatesModels.builder().name("Affiliate 4").age(40).mail("Affiliate4@gmail.com").build());

        given(affiliatesServicesMock.obtenerAffiliate()).willReturn(data);

        MvcResult response = mockMvc.perform(get("/api/controller/affiliates"))
                .andExpect(status().isOk()).andDo(print()).andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 200);
    }

    @Test
    void obtenerAffiliateStatusNoContent() throws Exception {
        List<AffiliatesModels> data = new ArrayList<>();

        given(affiliatesServicesMock.obtenerAffiliate()).willReturn(data);

        MvcResult response = mockMvc.perform(get("/api/controller/affiliates"))
                .andExpect(status().isNoContent()).andDo(print()).andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 204);
    }

    @Test
    void obtenerAffiliateByIDStatusOk() throws Exception {
        long id = 1L;
        AffiliatesModels affiliatesModels = AffiliatesModels.builder()
                .name("Affiliate 1")
                .age(10)
                .mail("Affiliate1@gmail.com")
                .build();

        given(affiliatesServicesMock.obtenerAffiliateByID(id)).willReturn(Optional.of(affiliatesModels));

        MvcResult response = mockMvc.perform(get("/api/controller/affiliates/{id}", id))
                .andExpect(status().isOk()).andDo(print()).andReturn();

        System.out.println(objectMapper.writeValueAsString(affiliatesModels));
        Assertions.assertEquals(response.getResponse().getStatus(), 200);
    }

    @Test
    void guardarAffiliateStatusCreated() throws Exception {
        AffiliatesModels affiliatesModels = AffiliatesModels.builder()
                .id(1L)
                .name("Affiliate 1")
                .age(10)
                .mail("Affiliate1@gmail.com")
                .build();

        given(affiliatesServicesMock.guardarAffiliate(any(AffiliatesModels.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        MvcResult response = mockMvc.perform(post("/api/controller/affiliates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(affiliatesModels)))
                .andDo(print()).andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 201);
    }

    @Test
    void actualizarAffiliateStatusCreated() throws Exception{
        long id = 1L;
        AffiliatesModels affiliateGuardado = AffiliatesModels.builder()
                .name("Affiliate 1")
                .age(10)
                .mail("Affiliate1@gmail.com")
                .build();

        AffiliatesModels affiliateActualizado = AffiliatesModels.builder()
                .name("Affiliate 2")
                .age(20)
                .mail("Affiliate2@gmail.com")
                .build();

        given(affiliatesServicesMock.obtenerAffiliateByID(id)).willReturn(Optional.of(affiliateGuardado));
        given(affiliatesServicesMock.actualizarAffiliate(any(AffiliatesModels.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        MvcResult result = mockMvc.perform(put("/api/controller/affiliates/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(affiliateActualizado)))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertEquals(result.getResponse().getStatus(), 201);
    }

    @Test
    void eliminarTestStatusOk() throws Exception {
        long id = 1L;

        willDoNothing().given(affiliatesServicesMock).eliminarAffiliate(id);

        MvcResult result = mockMvc.perform(delete("/api/controller/affiliates/{id}", id))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertEquals(result.getResponse().getStatus(), 200);
    }
}