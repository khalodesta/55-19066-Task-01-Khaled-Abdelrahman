package s55_19001057.t_01.Khaled_Amr.controllers;

import s55_19001057.t_01.Khaled_Amr.models.User;
import s55_19001057.t_01.Khaled_Amr.services.UserService;
import s55_19001057.t_01.Khaled_Amr.models.Note;
import s55_19001057.t_01.Khaled_Amr.services.NoteService;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;
    private final NoteService noteService;

    public UserController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }
    // GET /users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET /users/{id}
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    // POST /users
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // PUT /users/{id}
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // DELETE /users/{id}
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    // GET /users/search?username=...
    @GetMapping("/search")
    public User getByUsername(@RequestParam String username) {
        return userService.getAllUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @GetMapping("/{id}/notes")
    public List<Note> getUserNotes(@PathVariable String id) {
        return noteService.getNotesByUserId(id);
    }
}
