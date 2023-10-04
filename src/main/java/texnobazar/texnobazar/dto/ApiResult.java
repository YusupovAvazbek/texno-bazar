package texnobazar.texnobazar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> extends RepresentationModel<ApiResult<T>> {
    private int code;
    private String message;
    private boolean success;
    private T data;
    private Set<ErrorDto> errors;
}
