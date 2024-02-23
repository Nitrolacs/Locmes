package com.example.locmes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

    private EditText inputNoteTitle, inputNoteText;
    private TextView textDateTime;

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

        ImageView imageSave = findViewById(R.id.imageSave);
        imageSave.setOnClickListener(v -> saveNote());
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
}