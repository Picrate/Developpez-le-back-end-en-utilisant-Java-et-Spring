package info.patriceallary.chatop.repository;

import info.patriceallary.chatop.domain.model.Message;
import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    public Message findMessageByRentalAndUser(Rental rental, User user);
    public Message findMessageById(Integer id);
    public List<Message> getAllByRental(Rental rental);

}
