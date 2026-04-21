package s55_19001057.t_01.Khaled_Amr.repositories;

import s55_19001057.t_01.Khaled_Amr.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.InputStream;
import java.util.*;

@Repository
public class UserRepository {

    private List<User> users;
    private File jsonFile;

    public UserRepository() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/users.json");

            ObjectMapper objectMapper = new ObjectMapper();
            this.users = objectMapper.readValue(inputStream, new TypeReference<List<User>>() {});

            this.jsonFile = new File(getClass().getResource("/users.json").toURI());

        } catch (Exception e) {
            this.users = new ArrayList<>();
        }
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public User save(User user) {
        user.setId(UUID.randomUUID().toString());
        users.add(user);
        writeToFile();
        return user;
    }

    public Optional<User> update(String id, User updated) {
        Optional<User> existing = findById(id);

        if (existing.isPresent()) {
            User user = existing.get();
            user.setUsername(updated.getUsername());
            user.setEmail(updated.getEmail());
            writeToFile();
        }

        return existing;
    }

    public boolean deleteById(String id) {
        boolean removed = users.removeIf(u -> u.getId().equals(id));
        if (removed) writeToFile();
        return removed;
    }

    private void writeToFile() {
        try {
            new ObjectMapper().writeValue(jsonFile, users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}