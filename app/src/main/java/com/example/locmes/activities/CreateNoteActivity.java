package com.example.locmes.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locmes.R;
import com.example.locmes.database.NotesDatabase;
import com.example.locmes.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_TO_MAP = 1;
    public static final int REQUEST_CODE_ADD_TO_CALENDAR = 2;

    private EditText inputNoteTitle, inputNoteText;
    private TextView textDateTime;
    private AlertDialog dialogDeleteNote;
    private Note alreadyAvailableNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            findViewById(R.id.backgroundImageBlurred).setRenderEffect(RenderEffect.createBlurEffect(80, 80, Shader.TileMode.CLAMP));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            findViewById(R.id.backgroundWhiteImageBlurred).setRenderEffect(RenderEffect.createBlurEffect(80, 80, Shader.TileMode.CLAMP));
        }

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(view -> onBackPressed());

        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteText = findViewById(R.id.inputNote);

        textDateTime = findViewById(R.id.textDateTime);
        String date = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", new Locale("ru")).format(new Date());
        textDateTime.setText(
                Character.toUpperCase(date.charAt(0)) + date.substring(1)
        );

        if (getIntent().getBooleanExtra("isViewOrUpdate", false)) {
            alreadyAvailableNote = (Note) getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();
        }

        ImageView imageSave = findViewById(R.id.imageSave);
        imageSave.setOnClickListener(v -> saveNote());

        ImageView imageAddToMap = findViewById(R.id.addToMap);
        imageAddToMap.setOnClickListener(view -> startActivityForResult(new Intent(getApplicationContext(),
                AddToMap.class), REQUEST_CODE_ADD_TO_MAP));

        ImageView imageAddToCalendar = findViewById(R.id.addToCalendar);
        imageAddToCalendar.setOnClickListener(view -> startActivityForResult(new Intent(getApplicationContext(),
                AddToCalendar.class), REQUEST_CODE_ADD_TO_CALENDAR));

        if (alreadyAvailableNote != null) {
            findViewById(R.id.deleteNote).setVisibility(View.VISIBLE);
            findViewById(R.id.deleteNote).setOnClickListener(v -> showDeleteNoteDialog());
        }
    }

    private void showDeleteNoteDialog() {
        if (dialogDeleteNote == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateNoteActivity.this);
            View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_delete_note,
                    (ViewGroup) findViewById(R.id.layoutDeleteNoteContainer)
            );
            builder.setView(view);
            dialogDeleteNote = builder.create();
            if (dialogDeleteNote.getWindow() != null) {
                dialogDeleteNote.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view.findViewById(R.id.textDeleteNote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    @SuppressLint("StaticFieldLead")
                    class DeleteNoteTask extends AsyncTask<Void, Void, Void> {

                        @Override
                        protected Void doInBackground(Void... voids) {
                            NotesDatabase.getDatabase(getApplicationContext()).noteDao()
                                    .deleteNote(alreadyAvailableNote);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            super.onPostExecute(unused);
                            Intent intent = new Intent();
                            intent.putExtra("isNoteDeleted", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    new DeleteNoteTask().execute();
                }
            });

            view.findViewById(R.id.textCancel).setOnClickListener(v -> dialogDeleteNote.dismiss());
        }

        dialogDeleteNote.show();
    }

    private void saveNote() {
        if (inputNoteText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Название заметки не может быть пустым!", Toast.LENGTH_SHORT).show();
            return;
        } else if (inputNoteText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Заметка не может быть пустой!", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note = new Note();
        note.setTitle(inputNoteTitle.getText().toString());
        note.setNoteText(inputNoteText.getText().toString());
        note.setDateTime(textDateTime.getText().toString());

        if (alreadyAvailableNote != null) {
            note.setId(alreadyAvailableNote.getId());
        }

        class SaveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }

        new SaveNoteTask().execute();
    }

    private void setViewOrUpdateNote() {
        inputNoteTitle.setText(alreadyAvailableNote.getTitle());
        inputNoteText.setText(alreadyAvailableNote.getNoteText());
        textDateTime.setText(alreadyAvailableNote.getDateTime());
        // TODO: нужно тут сохранение геолокации и календаря.
    }
}