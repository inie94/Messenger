package ru.anani.messenger.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anani.messenger.entities.BlockedDialog;
import ru.anani.messenger.entities.Dialog;
import ru.anani.messenger.entities.HiddenDialog;
import ru.anani.messenger.entities.User;
import ru.anani.messenger.repositories.BlockedDialogRepository;
import ru.anani.messenger.repositories.DialogRepository;
import ru.anani.messenger.repositories.HiddenDialogsRepository;
import ru.anani.messenger.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DialogService {

    public final DialogRepository dialogRepository;
    public final HiddenDialogsRepository hiddenDialogsRepository;
    public final UserRepository userRepository;
    public final BlockedDialogRepository blockedDialogRepository;

    @Autowired
    public DialogService(DialogRepository dialogRepository, HiddenDialogsRepository hiddenDialogsRepository, UserRepository userRepository, BlockedDialogRepository blockedDialogRepository) {
        this.dialogRepository = dialogRepository;
        this.hiddenDialogsRepository = hiddenDialogsRepository;
        this.userRepository = userRepository;
        this.blockedDialogRepository = blockedDialogRepository;
    }

    public Dialog create(User userA, User userB) {
        if(dialogRepository.existsByUserAAndUserBOrUserAAndUserB(userA, userB, userB, userA)) {
            return dialogRepository.findByUserAAndUserBOrUserAAndUserB(userA, userB, userB, userA);
        } else {
            Dialog dialog = new Dialog(userA, userB);
            return dialogRepository.save(dialog);
        }
    }

    public Dialog hideToUser(Dialog dialog, User user) {
        if (hiddenDialogsRepository.existsByUserAndDialog(user, dialog)) {
            return hiddenDialogsRepository.findByUserAndDialog(user, dialog).getDialog();
        } else {
            HiddenDialog hiddenDialog = new HiddenDialog(user, dialog);
            return hiddenDialogsRepository.save(hiddenDialog).getDialog();
        }
    }

    public void showToUser(Dialog dialog, User user) {
        if (hiddenDialogsRepository.existsByUserAndDialog(user, dialog)) {
            hiddenDialogsRepository.deleteByUserAndDialog(user, dialog);
        }
    }

    public List<Dialog> getUserDialogs(User user) {
        List<Dialog> dialogs = dialogRepository.findAllByUserAOrUserB(user, user);
        List<Dialog> blockedDialogs = new ArrayList<>();
        dialogs.forEach(dialog -> {
            if(blockedDialogRepository.existsByDialog(dialog)) {
                blockedDialogs.add(dialog);
            }
        });
        List<Dialog> hiddenDialogs = getUserHideDialogs(user);
        dialogs.removeAll(blockedDialogs);
        dialogs.removeAll(hiddenDialogs);
        return dialogs;
    }

    public List<Dialog> getUserBlockedDialogs(User user) {
        List<Dialog> dialogs = dialogRepository.findAllByUserAOrUserB(user, user);
        List<Dialog> blockedDialogs = new ArrayList<>();
        dialogs.forEach(dialog -> {
            if(blockedDialogRepository.existsByDialog(dialog)) {
                blockedDialogs.add(dialog);
            }
        });
        return blockedDialogs;
    }

    public List<Dialog> getUserHideDialogs(User user) {
        List<HiddenDialog> hiddenDialogs = hiddenDialogsRepository.findAllByUser(user);
        List<Dialog> dialogs = new ArrayList<>();
        hiddenDialogs.forEach(hiddenDialog -> {
            dialogs.add(hiddenDialog.getDialog());
        });
        return dialogs;
    }

    public Dialog getDialogById(Long id) {
        return dialogRepository.findById(id).get();
    }

    public Dialog blockDialog(Dialog dialog) {
        return blockedDialogRepository.save(new BlockedDialog(dialog)).getDialog();
    }

    public void unblockDialog(Dialog dialog) {
        blockedDialogRepository.deleteByDialog(dialog);
        hideToUser(dialog, dialog.getUserA());
        hideToUser(dialog, dialog.getUserB());
    }

    public Dialog getDialogByUsers(User user, User companion) {
        return dialogRepository.findByUserAAndUserBOrUserAAndUserB(user, companion, companion, user);
    }
}
