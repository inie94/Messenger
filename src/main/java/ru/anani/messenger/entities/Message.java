package ru.anani.messenger.entities;

import lombok.*;
import org.springframework.boot.context.properties.ConstructorBinding;
import ru.anani.messenger.entities.enums.MessageStatus;
import ru.anani.messenger.entities.enums.MessageType;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @Column(nullable = false)
//    private MessageType type;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long createdBy;

    @Column(nullable = false)
    private MessageStatus status;

    public Message(User sender, User recipient, String content, Long createdBy, MessageStatus status) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.createdBy = createdBy;
        this.status = status;
    }
}
