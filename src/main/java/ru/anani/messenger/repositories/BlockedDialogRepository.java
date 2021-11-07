package ru.anani.messenger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anani.messenger.entities.BlockedDialog;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.User;

import java.util.List;

@Repository
public interface BlockedDialogRepository extends JpaRepository<BlockedDialog, Long> {

    void deleteByDialog(Dialog dialog);

    BlockedDialog findByDialog(Dialog dialog);

    boolean existsByDialog(Dialog dialog);
}
