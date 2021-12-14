package gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface IDocumentListener extends DocumentListener {
    void update(DocumentEvent event);

    @Override
    default void insertUpdate(DocumentEvent e) {
        update(e);
    }

    @Override
    default void removeUpdate(DocumentEvent e) {
        update(e);
    }

    @Override
    default void changedUpdate(DocumentEvent e) {
        update(e);
    }
}