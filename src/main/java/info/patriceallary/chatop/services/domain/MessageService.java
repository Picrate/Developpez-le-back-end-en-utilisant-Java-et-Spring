package info.patriceallary.chatop.services.domain;

import info.patriceallary.chatop.domain.model.Message;
import info.patriceallary.chatop.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void save(Message message) {
        this.messageRepository.save(message);
    }
}
