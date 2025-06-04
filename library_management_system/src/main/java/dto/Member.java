package dto;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String contactNumber;
    private LocalDate membershipDate;
}
