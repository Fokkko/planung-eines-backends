package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.QualificationDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Data
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final RestTemplate restTemplate;

    public Boolean checkEmployeeExists(Integer employeeId, String token) {
        String url = "https://employee.szut.dev/employees/" + employeeId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            QualificationDTO result = restTemplate.exchange(url, HttpMethod.GET, entity, QualificationDTO.class).getBody();

            return result != null;
        } catch (RestClientException e) {
            throw new RuntimeException("Mitarbeiter kann nicht abgerufen werden: " + e.getMessage(), e);
        }
    }

}

