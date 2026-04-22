package s55_19001057.t_01.Khaled_Amr.controllers;

import s55_19001057.t_01.Khaled_Amr.models.Note;
import s55_19001057.t_01.Khaled_Amr.services.NoteService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable String id) {
        return noteService.getNoteById(id);
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @GetMapping("/search")
    public List<Note> searchByTitle(@RequestParam String title) {
        return noteService.getAllNotes().stream()
                .filter(n -> n.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable String id, @RequestBody Note note) {
        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
    }
}