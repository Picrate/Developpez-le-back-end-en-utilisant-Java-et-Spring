package info.patriceallary.chatop.services.domain;

import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;

    public RentalService(RentalRepository rentalRepository, UserService userService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
    }

    public Optional<Rental> getRentalById(Integer id) {
        return this.rentalRepository.findById(id);
    }

    public List<Rental> getAllReantals() {
        return this.rentalRepository.findAll();
    }

    public void update(Integer rentalId, Rental rentalUpdated) {
        Rental rentalToUpdate = this.rentalRepository.getReferenceById(rentalId);
        rentalToUpdate.setName(rentalUpdated.getName());
        rentalToUpdate.setDescription(rentalUpdated.getDescription());
        rentalToUpdate.setPrice(rentalUpdated.getPrice());
        rentalToUpdate.setSurface(rentalUpdated.getSurface());
        rentalToUpdate.setUpdated_at(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
        this.rentalRepository.saveAndFlush(rentalToUpdate);
    }

    public void save(Rental rental, String principalName) {
        // Rental Already exists -> update
        if (rental.getId() != null) {
            rental.setUpdated_at(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
        }
        // Get Owner
        if (this.userService.getUserByEmail(principalName).isPresent()) {
            User owner = this.userService.getUserByEmail(principalName).get();
            rental.setOwner(owner);
        }
        this.rentalRepository.saveAndFlush(rental);
    }

    public boolean isOwner(Integer rentalId, String userName) {
        boolean isOwner = false;
        if(this.userService.getUserByEmail(userName).isPresent()) {
            User currentUser = this.userService.getUserByEmail(userName).get();
            if(getRentalById(rentalId).isPresent()) {
                Rental rental = getRentalById(rentalId).get();
                isOwner = rental.getOwner().equals(currentUser);
            }
        }
        return isOwner;
    }


}
