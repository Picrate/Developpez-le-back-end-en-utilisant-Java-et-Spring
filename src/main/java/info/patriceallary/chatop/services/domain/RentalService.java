package info.patriceallary.chatop.services.domain;

import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
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

    public List<Rental> getAllRentals() {
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

    /*
        Save a rental
     */
    public void save(Rental rental, String principalName) {
        // Rental Already exists -> update
        if (rental.getId() != null) {
            rental.setUpdated_at(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
        }
        // Get Owner
        Optional<User> optionalUser = this.userService.getUserByEmail(principalName);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("Rental Owner not found !");
        }
        User owner = optionalUser.get();
        rental.setOwner(owner);

        this.rentalRepository.saveAndFlush(rental);
    }

    /*
     * Check if a user is the owner of the rental
     */
    public boolean isOwner(Integer rentalId, String userName) {
        boolean isOwner = false;
        Optional<User> optionalUser = this.userService.getUserByEmail(userName);
        if(optionalUser.isEmpty()){
            throw new NoSuchElementException("User Not Found");
        }
        User currentUser = optionalUser.get();
        Optional<Rental> optionalRental = getRentalById(rentalId);
            if(optionalRental.isEmpty()) {
                throw new NoSuchElementException("Rental Not Found");
            }
            Rental rental = optionalRental.get();
            isOwner = rental.getOwner().equals(currentUser);

        return isOwner;
    }


}
