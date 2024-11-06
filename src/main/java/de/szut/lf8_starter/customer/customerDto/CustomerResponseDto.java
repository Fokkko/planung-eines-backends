package de.szut.lf8_starter.customer.customerDto;

import lombok.Data;

@Data
public class CustomerResponseDto {
    private Integer customerId;
    private String customerName;
    private String customerContactName;
}
