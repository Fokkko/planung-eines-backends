//package de.szut.lf8_starter.employeeTest;
//
//import de.szut.lf8_starter.employeeTest.dto.EmployeeResponseDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class TestEmployee {
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
//    @Test
//    public void testGetEmployeeById_CorrectIdIsUsed() {
//        // Arrange
//        Integer employeeId = 299;
//        String token = "Bearer token";
//        EmployeeResponseDTO mockEmployee = new EmployeeResponseDTO();
//        mockEmployee.setId(employeeId);
//
//        // Definiere das Verhalten des Mocks
//        when(employeeService.getEmployeeById(anyInt(), eq(token), anyString())).thenReturn(mockEmployee);
//
//        // Act
//        ResponseEntity<EmployeeResponseDTO> response = employeeController.getEmployeeById(employeeId, token);
//
//        // Assert
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(mockEmployee, response.getBody());
//
//        // Capture the ID passed to the service
//        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
//        verify(employeeService).getEmployeeById(idCaptor.capture(), eq(token), anyString());
//
//        // Überprüfen, ob die richtige ID verwendet wurde
//        assertEquals(employeeId, idCaptor.getValue());
//    }
//
//    @Test
//    public void testGetEmployeeById_NotFound() {
//        // Arrange
//        Integer employeeId = 299;
//        String token = "Bearer token";
//
//        // Definiere das Verhalten des Mocks
//        when(employeeService.getEmployeeById(employeeId, token, "https://employee.szut.dev/employees/" + employeeId))
//                .thenReturn(null);
//
//        // Act
//        ResponseEntity<EmployeeResponseDTO> response = employeeController.getEmployeeById(employeeId, token);
//
//        // Assert
//        assertEquals(404, response.getStatusCodeValue());
//
//        // Überprüfe, ob der Service-Mock mit den richtigen Parametern aufgerufen wurde
//        verify(employeeService).getEmployeeById(employeeId, token, "https://employee.szut.dev/employees/" + employeeId);
//    }
//}
