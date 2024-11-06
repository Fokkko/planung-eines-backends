package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.QualificationDTO;
import de.szut.lf8_starter.project.ProjectService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Data
@Service
public class EmployeeService {

    private final RestTemplate restTemplate;
    private final ProjectService projectService;

    public EmployeeService(@Lazy ProjectService projectService, RestTemplate restTemplate) {
        this.projectService = projectService;
        this.restTemplate = restTemplate;
    }

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

    public boolean isQualifiedInService(Integer employeeId, Integer qualificationId, String token) {
        String url = "https://employee.szut.dev/employees/" + employeeId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            QualificationDTO result = restTemplate.exchange(url, HttpMethod.GET, entity, QualificationDTO.class).getBody();
            if (result == null) return false;
            var skillSet = result.getSkillSet();
            for (int i = 0; i < skillSet.size(); i++){
                if (result.getSkillSet().get(i).getId().equals(qualificationId)) return true;
            }
            return false;
        } catch (RestClientException e) {
            throw new RuntimeException("Fehler beim Abrufen des Mitarbeiters: " + e.getMessage(), e);
        }
    }
}

