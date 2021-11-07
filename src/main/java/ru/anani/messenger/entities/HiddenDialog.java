package ru.anani.messenger.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "hidden_dialogs")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HiddenDialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "dialog_id", nullable = false)
    private Dialog dialog;

    public HiddenDialog(User user, Dialog dialog) {
        this.user = user;
        this.dialog = dialog;
    }
}
