package ru.anani.messenger.dto;

import lombok.*;
import ru.anani.messenger.entities.enums.UserStatus;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String password;
    private Date dateOfBirth;
    private UserStatus status;
}
