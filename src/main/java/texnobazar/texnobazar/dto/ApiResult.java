package texnobazar.texnobazar.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult<T> extends RepresentationModel<ApiResult<T>> {
    private int code;
    private String message;
    private boolean success;
    private T data;
    private Set<ErrorDto> errors;
}
