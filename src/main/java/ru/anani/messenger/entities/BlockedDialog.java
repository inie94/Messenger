package ru.anani.messenger.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "blocked_dialogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlockedDialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dialog_id", unique = true, nullable = false)
    private Dialog dialog;

    public BlockedDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
