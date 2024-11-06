// CustomerService.java
package de.szut.lf8_starter.customer;

import de.szut.lf8_starter.customer.customerDto.CustomerRequestDto;
import de.szut.lf8_starter.customer.customerDto.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final RestTemplate restTemplate;

    private static final String BASE_CUSTOMER_URL = "https://customer.szut.dev/customer/";

    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequest, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CustomerRequestDto> entity = new HttpEntity<>(customerRequest, headers);

        try {
            ResponseEntity<CustomerResponseDto> response = restTemplate.exchange(
                    BASE_CUSTOMER_URL, HttpMethod.POST, entity, CustomerResponseDto.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Kunde konnte nicht erstellt werden: " + e.getMessage(), e);
        }
    }

    public CustomerResponseDto getCustomerById(Integer customerId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<CustomerResponseDto> response = restTemplate.exchange(
                    BASE_CUSTOMER_URL + customerId, HttpMethod.GET, entity, CustomerResponseDto.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Kunde konnte nicht abgerufen werden: " + e.getMessage(), e);
        }
    }

    public CustomerResponseDto updateCustomer(Integer customerId, CustomerRequestDto customerRequest, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CustomerRequestDto> entity = new HttpEntity<>(customerRequest, headers);

        try {
            ResponseEntity<CustomerResponseDto> response = restTemplate.exchange(
                    BASE_CUSTOMER_URL + customerId, HttpMethod.PUT, entity, CustomerResponseDto.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Kunde konnte nicht aktualisiert werden: " + e.getMessage(), e);
        }
    }

    public void deleteCustomer(Integer customerId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(BASE_CUSTOMER_URL + customerId, HttpMethod.DELETE, entity, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Kunde konnte nicht gelöscht werden: " + e.getMessage(), e);
        }
    }
}
