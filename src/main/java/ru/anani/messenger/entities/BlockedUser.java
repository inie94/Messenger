package ru.anani.messenger.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "blacklist")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BlockedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "companion_id", nullable = false)
    private User companion;

    public BlockedUser(User user, User companion) {
        this.user = user;
        this.companion = companion;
    }
}
