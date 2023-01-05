package com.example.demo.Controllers;

import com.example.demo.Models.AppointmentsModels;
import com.example.demo.Services.AppointmentsServices;
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
public class AppointmentsControllersTests implements Serializable {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    AppointmentsControllers appointmentsControllers = new AppointmentsControllers();

    @MockBean
    private AppointmentsServices appointmentsServicesMock;

    @Test
    void obtenerAppointmentStatusOk() throws Exception {
        List<AppointmentsModels> data = new ArrayList<>();
        data.add(AppointmentsModels.builder().date("01/11/10").hour("01:01").build());
        data.add(AppointmentsModels.builder().date("02/22/20").hour("02:02").build());
        data.add(AppointmentsModels.builder().date("03/33/30").hour("03:03").build());
        data.add(AppointmentsModels.builder().date("04/44/40").hour("04:04").build());

        given(appointmentsServicesMock.obtenerAppointment()).willReturn(data);

        MvcResult response = mockMvc.perform(get("/api/controller/appointments"))
                .andExpect(status().isOk()).andDo(print()).andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 200);
    }

    @Test
    void obtenerAppointmentStatusNoContent() throws Exception {
        List<AppointmentsModels> data = new ArrayList<>();

        given(appointmentsServicesMock.obtenerAppointment()).willReturn(data);

        MvcResult response = mockMvc.perform(get("/api/controller/appointments"))
                .andExpect(status().isNoContent()).andDo(print()).andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 204);
    }

    @Test
    void obtenerAppointmentByIDStatusOk() throws Exception {

        long id = 1L;
        AppointmentsModels appointmentsModels = AppointmentsModels.builder()
                .date("01/11/10")
                .hour("01:01")
                .build();

        given(appointmentsServicesMock.obtenerAppointmentByID(id)).willReturn(Optional.of(appointmentsModels));

        MvcResult response = mockMvc.perform(get("/api/controller/appointments/{id}", id))
                .andExpect(status().isOk()).andDo(print()).andReturn();

        System.out.println(objectMapper.writeValueAsString(appointmentsModels));
        Assertions.assertEquals(response.getResponse().getStatus(), 200);
    }

    @Test
    void obtenerAppointmentByIDStatusNotFound() throws Exception {

        long id = 1L;
        AppointmentsModels appointmentsModels = AppointmentsModels.builder()
                .date("01/11/10")
                .hour("01:01")
                .build();

        given(appointmentsServicesMock.obtenerAppointmentByID(id)).willReturn(Optional.empty());

        MvcResult response = mockMvc.perform(get("/api/controller/appointments/{id}", id))
                .andExpect(status().isNotFound()).andDo(print()).andReturn();

        System.out.println(objectMapper.writeValueAsString(appointmentsModels));
        Assertions.assertEquals(response.getResponse().getStatus(), 404);
    }

    /*@Test
    void obtenerAppointmentByAffiliatesID() throws Exception {

        long id_affiliate = 1L;
        AppointmentsModels appointmentsModels = AppointmentsModels.builder()
                .date("01/11/10")
                .hour("01:01")
                .build();

        given(appointmentsServicesMock.obtenerAppointmentByAffiliatesID(id_affiliate)).willReturn(Optional.of(appointmentsModels));

        MvcResult response = mockMvc.perform(get("/api/controller/appointments/{id_affiliate}", id_affiliate))
                .andExpect(status().isOk()).andDo(print()).andReturn();

        System.out.println(objectMapper.writeValueAsString(appointmentsModels));
        Assertions.assertEquals(response.getResponse().getStatus(), 200);
    }*/

    @Test
    void guardarAppointmentStatusCreated() throws Exception {
        AppointmentsModels appointmentsModels = AppointmentsModels.builder()
                .id(1L)
                .date("01/11/10")
                .hour("01:01")
                .build();

        given(appointmentsServicesMock.guardarAppointment(any(AppointmentsModels.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        MvcResult response = mockMvc.perform(post("/api/controller/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentsModels)))
                .andDo(print()).andExpect(status().isCreated())
                .andReturn();

        Assertions.assertEquals(response.getResponse().getStatus(), 201);
    }

    @Test
    void actualizarAppointmentStatusCreated() throws Exception{
        long id = 1L;
        AppointmentsModels appointmentsGuardado = AppointmentsModels.builder()
                .date("01/11/10")
                .hour("01:01")
                .build();

        AppointmentsModels appointmentsActualizado = AppointmentsModels.builder()
                .date("02/22/20")
                .hour("02:02")
                .build();

        given(appointmentsServicesMock.obtenerAppointmentByID(id)).willReturn(Optional.of(appointmentsGuardado));
        given(appointmentsServicesMock.actualizarAppointment(any(AppointmentsModels.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        MvcResult result = mockMvc.perform(put("/api/controller/appointments/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentsActualizado)))
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertEquals(result.getResponse().getStatus(), 201);
    }

    @Test
    void eliminarAppointmentStatusOk() throws Exception {
        long id = 1L;

        willDoNothing().given(appointmentsServicesMock).eliminarAppointment(id);

        MvcResult result = mockMvc.perform(delete("/api/controller/appointments/{id}", id))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertEquals(result.getResponse().getStatus(), 200);
    }
}