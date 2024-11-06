package de.szut.lf8_starter.customer;

import de.szut.lf8_starter.customer.customerDto.CustomerResponseDto;
import de.szut.lf8_starter.employee.dto.SkillDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Data
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final RestTemplate restTemplate;

    public Boolean checkCustomerExists(Integer customerId, String token) {
        String url = "https://employee.szut.dev/customer/" + customerId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            CustomerResponseDto result = restTemplate.exchange(url, HttpMethod.GET, entity, CustomerResponseDto.class).getBody();

            return result != null;
        } catch (RestClientException e) {
            throw new RuntimeException("Kunde kann nicht abgerufen werden: " + e.getMessage(), e);
        }
    }
}
