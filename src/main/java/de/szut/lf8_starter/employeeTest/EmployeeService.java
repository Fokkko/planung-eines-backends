package de.szut.lf8_starter.employeeTest;

import de.szut.lf8_starter.employeeTest.dto.SkillDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Data
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final RestTemplate restTemplate;

    public Boolean getEmployeeById(Integer employeeId, String token) {
        String url = "https://employee.szut.dev/employees/" + employeeId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            SkillDTO result = restTemplate.exchange(url, HttpMethod.GET, entity, SkillDTO.class).getBody();

            return result != null && employeeId.equals(result.getId());
        } catch (RestClientException e) {
            throw new RuntimeException("Mitarbeiter kann nicht abgerufen werden: " + e.getMessage(), e);
        }
    }

    public SkillDTO employeeForQualification(Integer employeeId, String token){
        String url = "https://employee.szut.dev/employees/" + employeeId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, SkillDTO.class).getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Qualifikation kann nicht abgerufen werden: " + e.getMessage(), e);
        }
    }

    private String getJwtToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) auth;
            Jwt jwt = jwtAuthenticationToken.getToken();
            String tokenValue = jwt.getTokenValue();
            return "Bearer " + tokenValue;
        }
        return "JWT Token Not found";
    }
}

