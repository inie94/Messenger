package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.HiddenDialog;
import ru.anani.messenger.entities.User;

import java.util.List;

@Repository
public interface HiddenDialogsRepository extends JpaRepository<HiddenDialog, Long> {
    boolean existsByUserAndDialog(User user, Dialog dialog);

    HiddenDialog findByUserAndDialog(User user, Dialog dialog);

    void deleteByUserAndDialog(User user, Dialog dialog);

    List<HiddenDialog> findAllByUser(User user);
}
