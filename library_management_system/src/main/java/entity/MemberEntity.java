package entity;

import lombok.*;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberEntity {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String contactNumber;
    private LocalDate membershipDate;
}
