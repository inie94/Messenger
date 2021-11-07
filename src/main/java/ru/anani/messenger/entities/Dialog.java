package ru.anani.messenger.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.Mapping;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "dialogs")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_a_id")
    private User userA;

    @ManyToOne
    @JoinColumn(name = "user_b_id")
    private User userB;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "dialog")
    private Set<HiddenDialog> hiddenDialogs;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "dialog")
    private Set<BlockedDialog> blockedDialogs;

    public Dialog(User userA, User userB) {
        this.userA = userA;
        this.userB = userB;
    }

    public User getAnotherCompanion(User user) {
        if(userA.getId().equals(user.getId())) {
            return userB;
        } else {
            return userA;
        }
    }
}
