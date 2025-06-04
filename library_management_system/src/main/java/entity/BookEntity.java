package entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookEntity {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private Integer totalCopies;
    private Integer availableCopies;
    private String availabilityStatus;
}
