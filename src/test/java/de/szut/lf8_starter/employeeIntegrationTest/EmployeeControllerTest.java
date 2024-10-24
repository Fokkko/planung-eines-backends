//package de.szut.lf8_starter.employee;
//
//import de.szut.lf8_starter.employeeTest.EmployeeController;
//import de.szut.lf8_starter.employeeTest.EmployeeService;
//import de.szut.lf8_starter.employeeTest.dto.EmployeeResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestClientException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class EmployeeControllerTest {
//
//    @InjectMocks
//    private EmployeeController employeeController;
//
//    @Mock
//    private EmployeeService employeeService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    // Arrange
//    Integer employeeId = 299;
//    String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE3Mjk1MDMzMTQsImlhdCI6MTcyOTQ5OTcxNCwianRpIjoiMzVmMGMxMTktODFlMy00MjgwLWFjMTQtZjJjY2EwOWU1MzY5IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiJlNGUxMmRjYS1kYzk4LTQyNTMtYmNiMC01MDBlMWRhOGM3NTMiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NDIwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsicHJvZHVjdF9vd25lciIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1zenV0IiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.ezEKFJ1gS6PO4A1Pn_kpdBwzu2siExaLavXEYJbdzNJSkiPb0ncUvvCAryZf5_gj51pI8blMkgEQZyJGVfWYKzzms8fn2PoDl1qRXBqAnrn5jcjdMngXfKQAtuOg3_bD2hiZ0-kIGZoHMsn5RynDhH3agupp-DRXyzUyT1eXONLTdI3ZBgAMJHuyAFyPtZt-rfzMFMJg4Qe07jT9o8L_YNAZr1kzUsLUYHGakJyNmE0LPvS0BkKzTJM0xC8tgyj9kYLBcS8Lxx3YsleQrmHJDxUX66neHPwVBY1woAPLvYYx-mDvWAs3yYhTmoL6tnEvo6oq7wMyU6pdDyM54aIxNQ";
//    String url = "https://employee.szut.dev/employees/" + employeeId;
//
//    @Test
//    public void testGetEmployeeById_ValidToken() {
//
//        EmployeeResponseDTO mockEmployee = new EmployeeResponseDTO();
//        mockEmployee.setId(employeeId);
//
//        // Mock-Service mit korrektem Bearer-Token und URL aufrufen
//        when(employeeService.getEmployeeById(employeeId, "Bearer " + token, url)).thenReturn(mockEmployee);
//
//        // Act: Aufruf des Controllers mit Bearer-Token
//        ResponseEntity<Boolean> response = employeeController.getEmployeeById(employeeId, "Bearer " + token);
//
//        // Assert: Überprüfen, ob die Antwort korrekt ist
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(mockEmployee, response.getBody());
//
//        // ArgumentCaptor für die Parameter der Service-Methode
//        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
//        ArgumentCaptor<String> tokenCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
//
//        // Verifizieren, dass der Service korrekt aufgerufen wurde
//        verify(employeeService).getEmployeeById(idCaptor.capture(), tokenCaptor.capture(), urlCaptor.capture());
//
//        // Überprüfen, ob die ID, das Token und die URL korrekt übergeben wurden
//        assertEquals(employeeId, idCaptor.getValue());
//        assertEquals("Bearer " + token, tokenCaptor.getValue());
//        assertEquals(url, urlCaptor.getValue());
//    }
//
//
//    @Test
//    public void testGetEmployeeById_InvalidToken() {
//
//        when(employeeService.getEmployeeById(employeeId, token, url))
//                .thenThrow(new RestClientException("Unauthorized"));
//
//        // Act & Assert
//        try {
//            employeeController.getEmployeeById(employeeId, "Bearer " + token);
//        } catch (RuntimeException e) {
//            assertEquals("Failed to fetch employee", e.getMessage());
//        }
//
//        verify(employeeService).getEmployeeById(employeeId, token, url);
//    }
//}
