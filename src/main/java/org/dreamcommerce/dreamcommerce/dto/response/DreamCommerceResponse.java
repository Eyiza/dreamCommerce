package org.dreamcommerce.dreamcommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Any field that is null will not be included in the response body
@Getter
@Setter
public class DreamCommerceResponse<T> {
    private String message;
    private T data;
    private boolean success;
    private List<String> errors;
}
