package info.patriceallary.chatop.domain.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RentalListDto {

    private List<RentalDto> rentals;

    public RentalListDto() {
        this.rentals = new ArrayList<>();
    }

    public RentalListDto(List<RentalDto> rentals) {
        this.rentals = rentals;
    }

    public void setRentals(List<RentalDto> rentals) {
        this.rentals = rentals;
    }

    public void addRentalDto(RentalDto rentalDto) {
        this.rentals.add(rentalDto);
    }

    public void removeRentalDto(RentalDto rentalDto) {
        this.rentals.remove(rentalDto);
    }

}
