package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class DtoCartItemIn {
	
	@JsonProperty("gtin")
	@NotNull(message = "El gtin es obligatorio")
	@Pattern(regexp = "^\\d{13}$", message = "El gtin debe tener 13 dígitos")
	private String gtin;


    @JsonProperty("quantity")
    @NotNull(message = "La quantity es obligatoria")
    @Min(value = 1, message = "La quantity debe ser mínimo 1")
    private Integer quantity;

    public String getGtin() {
        return gtin;
    }

    public Integer getQuantity() {
        return quantity;
    }

}
