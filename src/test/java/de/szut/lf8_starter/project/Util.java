package de.szut.lf8_starter.project;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class Util {

    protected static String fetchAccessToken() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://keycloak.szut.dev/auth/realms/szut/protocol/openid-connect/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "employee-management-service");
        params.add("username", "user");
        params.add("password", "test");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(params, headers), String.class);
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }


    static void createProjectTestData(ProjectRepository projectRepository, int testProjectId) throws Exception {
        ProjectEntity project1 = new ProjectEntity(
                testProjectId,
                "ERP System Implementation",
                299,
                5003,
                "Tech Innovators Ltd.",
                "John Doe",
                "Core project for business automation",
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2025, 3, 30),
                null,
                Arrays.asList(300, 208, 103),
                Arrays.asList(10, 207)
        );

        projectRepository.save(project1);

        ProjectEntity project2 = new ProjectEntity(
                2,
                "HPG: SAP Einführung Warenwirtschaft",
                297,
                5002,
                "Happy People GmbH",
                "Jane Smith",
                "High priority project",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2025, 4, 30),
                null,
                Arrays.asList(297, 300),
                Arrays.asList(10)
        );

        projectRepository.save(project2);
    }


}
