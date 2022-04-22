package cc.openhome.gossip.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class UserService {
    private final String USERS;

    public UserService(String users) {
        this.USERS = users;
    }

    public void tryCreateUser(String email, String username, String password) throws IOException {
        Path userHome = Paths.get(USERS, username);
        if (Files.notExists(userHome)) {
            createUser(userHome, email, password);
        }
    }

    private void createUser(Path userHome, String email, String password) throws IOException {
        Files.createDirectories(userHome);
        int salt = ThreadLocalRandom.current().nextInt();
        String encrypt = String.valueOf(salt + password.hashCode());
        Path profile = userHome.resolve("profile");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(profile);) {
            bufferedWriter.write(String.format("%s\t%s\t%d", email, encrypt, salt));
        }
    }

    public boolean login(String username, String password) throws IOException {
        if (username != null && username.trim().length() != 0) {
            Path userHome = Paths.get(USERS, username);
            return Files.exists(userHome) && isCorrectPassword(password, userHome);
        }
        return false;
    }

    private boolean isCorrectPassword(String password, Path userHome) throws IOException {
        Path profile = userHome.resolve("profile");
        try (BufferedReader bufferedReader = Files.newBufferedReader(profile)) {
            String[] data = bufferedReader.readLine().split("\t");
            int encrypt = Integer.parseInt(data[1]);
            int salt = Integer.parseInt(data[2]);
            return password.hashCode() + salt == encrypt;
        }
    }

    public Map<Long, String> messages(String username) {
        Path userHome = Paths.get(USERS, username);
        Map<Long, String> messages = new TreeMap<>(Comparator.reverseOrder());

        try (DirectoryStream<Path> txts = Files.newDirectoryStream(userHome, "*.txt")) {
            for (Path txt : txts) {
                String millis = txt.getFileName().toString().replace(".txt", "");
                String blabla = Files.readAllLines(txt).stream().collect(Collectors.joining(System.lineSeparator()));
                messages.put(Long.parseLong(millis), blabla);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void addMessage(String username, String blabla) {
        Path txt = Paths.get(USERS, username, String.format("%s.txt", System.currentTimeMillis()));
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(txt)) {
            bufferedWriter.write(blabla);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(String username, String millis) throws IOException {
        Path txt = Paths.get(USERS, username, String.format("%s.txt", millis));
        Files.delete(txt);
    }
}
