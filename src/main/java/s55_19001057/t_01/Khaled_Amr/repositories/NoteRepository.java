package s55_19001057.t_01.Khaled_Amr.repositories;

import s55_19001057.t_01.Khaled_Amr.models.Note;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.InputStream;
import java.util.*;

@Repository
public class NoteRepository {

    private List<Note> notes;
    private File jsonFile;

    public NoteRepository() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/notes.json");

            ObjectMapper objectMapper = new ObjectMapper();
            this.notes = objectMapper.readValue(inputStream, new TypeReference<List<Note>>() {});

            this.jsonFile = new File(getClass().getResource("/notes.json").toURI());

        } catch (Exception e) {
            this.notes = new ArrayList<>();
        }
    }

    public List<Note> findAll() {
        return notes;
    }

    public Optional<Note> findById(String id) {
        return notes.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    public List<Note> findByUserId(String userId) {
        return notes.stream().filter(n -> n.getUserId().equals(userId)).toList();
    }

    public Note save(Note note) {
        note.setId(UUID.randomUUID().toString());
        notes.add(note);
        writeToFile();
        return note;
    }

    public Optional<Note> update(String id, Note updated) {
        Optional<Note> existing = findById(id);

        if (existing.isPresent()) {
            Note note = existing.get();
            note.setTitle(updated.getTitle());
            note.setContent(updated.getContent());
            note.setUserId(updated.getUserId());
            writeToFile();
        }

        return existing;
    }

    public boolean deleteById(String id) {
        boolean removed = notes.removeIf(n -> n.getId().equals(id));
        if (removed) writeToFile();
        return removed;
    }

    private void writeToFile() {
        try {
            new ObjectMapper().writeValue(jsonFile, notes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}