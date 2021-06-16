package com.flight.management.ticket.application;

import com.flight.management.ticket.infrastructure.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TicketManagerControllerInterface.class)
class TicketManagerControllerTest {
    @MockBean
    TicketDelegateInterface delegate;

    @Mock
    TicketRequest request;
    @Mock
    TicketResponse response;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void findTicketByIDTest() throws Exception {
        Mockito.when(delegate.findTicketById(ArgumentMatchers.anyLong())).thenReturn(response);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET,"/tickets/{ticket_id}","1");
        mvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());
    }

    @Test
    void findTicketByIDNotFoundTest() throws Exception {
        Mockito.when(delegate.findTicketById(ArgumentMatchers.anyLong())).thenThrow(ResourceNotFoundException.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET,"/tickets/{ticket_id}","1");
        mvc.perform(requestBuilder).andExpect(status().isNotFound());
    }

    @Test
    void addTicketTest() throws Exception {
        Mockito.when(delegate.saveTicket(ArgumentMatchers.any(TicketRequest.class))).thenReturn(1L);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/tickets")
                .content("{\n" +
                        "    \"departure_date\":\"2020-10-10\",\n" +
                        "    \"departure_time\":\"16:00\",\n" +
                        "    \"arrival_date\":\"2020-10-10\",\n" +
                        "    \"arrival_time\": \"20:00\",\n" +
                        "    \"source_city\": \"CANCUN\",\n" +
                        "    \"target_city\": \"CDMX\",\n" +
                        "    \"passenger_name\": \"Mario Luna\",\n" +
                        "    \"passenger_age\": 33\n" +
                        "}")
                .contentType("application/json");
        mvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());

    }
}