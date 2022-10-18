package cc.openhome.gossip.service;

import cc.openhome.gossip.model.Account;

public interface EmailService {
    void validationLink(Account account);

    void failedRegistration(String username, String email);
}
