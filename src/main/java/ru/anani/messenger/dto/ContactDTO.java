package ru.anani.messenger.dto;

import lombok.*;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.entities.enums.UserStatus;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private Date dateOfBirth;
    private UserStatus status;
}
