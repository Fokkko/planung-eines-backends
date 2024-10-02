package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    @Autowired
    private RestTemplate restTemplate;
    private final String mainUrl = "https://employee.szut.dev/employees/";

    public EmployeeResponseDTO getEmployeeById(int employeeId) {
        String url = mainUrl + employeeId;
        return restTemplate.getForObject(url, EmployeeResponseDTO.class);
    }

//    public EmployeeResponseDTO getEmployeeById(){
//
//    }


}
