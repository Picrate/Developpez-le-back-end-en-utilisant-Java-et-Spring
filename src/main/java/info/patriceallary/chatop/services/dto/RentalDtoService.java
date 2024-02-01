package info.patriceallary.chatop.services.dto;

import info.patriceallary.chatop.domain.dto.FormRentalDto;
import info.patriceallary.chatop.domain.dto.RentalDto;
import info.patriceallary.chatop.domain.dto.RentalListDto;
import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.services.domain.RentalService;
import info.patriceallary.chatop.services.storage.FileSystemStorageService;
import info.patriceallary.chatop.services.tools.PictureManager;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RentalDtoService {

    private final RentalService rentalService;
    private final FileSystemStorageService storageService;
    private final PictureManager pictureManager;

    public RentalDtoService(RentalService rentalService, FileSystemStorageService storageService, PictureManager pictureManager) {
        this.rentalService = rentalService;
        this.storageService = storageService;
        this.pictureManager = pictureManager;
    }

    public RentalListDto getAllRentalDtos() {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);

        List<RentalDto> rentals = modelMapper.map(this.rentalService.getAllRentals(), new TypeToken<List<RentalDto>>(){}.getType());

        RentalListDto list = new RentalListDto();
        list.setRentals(rentals);
        return list;
    }

    public RentalDto getRentalDtoById(Integer id) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
        Optional<Rental> optionalRental = this.rentalService.getRentalById(id);
        return optionalRental.map(rental -> modelMapper.map(rental, RentalDto.class)).orElse(null);
    }

    public Rental convertToRental(RentalDto rentalDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);
        return modelMapper.map(rentalDto, Rental.class);
    }

    public Rental convertFormRentalToRental(FormRentalDto formRentalDto, String requestURL) {
        ModelMapper modelMapper = new ModelMapper();
        String pictureURL = "N/A";

        if (Boolean.TRUE.equals(this.pictureManager.isValidPicture(formRentalDto.getPicture()))) {
            String encodedFileName = null;
            try {
                encodedFileName = this.pictureManager.sanitizeAndEncodeFilename(formRentalDto.getPicture());
                this.storageService.store(formRentalDto.getPicture(), encodedFileName);

                pictureURL = pictureManager.getPictureUrl(encodedFileName, requestURL);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        modelMapper.typeMap(FormRentalDto.class, Rental.class).addMappings(
                mapper -> mapper.skip(Rental::setPicture)
        );
        Rental rental = modelMapper.map(formRentalDto, Rental.class);
        rental.setPicture(pictureURL);
        return rental;
    }


}
