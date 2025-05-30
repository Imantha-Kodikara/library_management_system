package model;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String contactNumber;
    private LocalDate membershipDate;
}
