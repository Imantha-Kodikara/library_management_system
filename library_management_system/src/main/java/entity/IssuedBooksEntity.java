package entity;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IssuedBooksEntity {
    private Integer memberId;
    private Integer bookId;
    private LocalDate issueDate;
}
