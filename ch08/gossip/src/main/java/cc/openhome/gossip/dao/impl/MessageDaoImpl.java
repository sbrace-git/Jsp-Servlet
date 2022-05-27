package cc.openhome.gossip.dao.impl;

import cc.openhome.gossip.dao.MessageDao;
import cc.openhome.gossip.model.Message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MessageDaoImpl implements MessageDao {

    private final String USERS;

    public MessageDaoImpl(String USERS) {
        this.USERS = USERS;
    }

    @Override
    public List<Message> getMessageByUsername(String username) {
        Path userHome = Paths.get(USERS, username);
        List<Message> messages = new ArrayList<>();
        try (DirectoryStream<Path> txts = Files.newDirectoryStream(userHome, "*.txt")) {
            for (Path txt : txts) {
                String millis = txt.getFileName().toString().replace(".txt", "");
                String blabla = Files.readAllLines(txt).stream().collect(Collectors.joining(System.lineSeparator()));
                messages.add(new Message(username, Long.valueOf(millis), blabla));
            }
            messages.sort(Comparator.comparing(Message::getMillis).reversed());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public void createMessage(Message message) {
        String username = message.getUsername();
        String blabla = message.getBlabla();
        Path txt = Paths.get(USERS, username, String.format("%s.txt", System.currentTimeMillis()));
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(txt)) {
            bufferedWriter.write(blabla);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMessage(String username, Long millis) {
        Path txt = Paths.get(USERS, username, String.format("%s.txt", millis));
        try {
            Files.delete(txt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
