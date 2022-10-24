package cc.openhome.gossip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:path.properties")
public class GossipApplication {

    public static void main(String[] args) {
        SpringApplication.run(GossipApplication.class, args);
    }

}
