package texnobazar.texnobazar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellerDto {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
}
