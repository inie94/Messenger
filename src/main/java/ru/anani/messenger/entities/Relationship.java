package ru.anani.messenger.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.anani.messenger.entities.enums.RelationshipStatus;

import javax.persistence.*;

@Entity
@Table(name = "relationships")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "companion_id")
    private User companion;

//    private TopicStatus topicStatus = topic.getStatus();

    private RelationshipStatus status;

    private Long updatedBy;
}
