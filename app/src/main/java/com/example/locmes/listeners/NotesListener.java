package com.example.locmes.listeners;

import com.example.locmes.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
