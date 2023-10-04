package texnobazar.texnobazar.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private String field;
    private String error;
}
