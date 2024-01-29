/**
 * This Class manages Entities & DTO Conversion
 */
package info.patriceallary.chatop.services.dto;

import info.patriceallary.chatop.domain.dto.*;
import info.patriceallary.chatop.domain.model.Message;
import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.services.domain.RentalService;
import info.patriceallary.chatop.services.domain.UserService;
import info.patriceallary.chatop.services.storage.FileSystemStorageService;
import info.patriceallary.chatop.services.tools.PictureManager;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Optional;

@Service
@Slf4j
public class DtoService {
    private final ModelMapper modelMapper;
    private final RentalService rentalService;
    private final UserService userService;
    private final FileSystemStorageService storageService;
    private final PictureManager pictureManager;

    public DtoService(ModelMapper modelMapper, RentalService rentalService, UserService userService, FileSystemStorageService storageService, PictureManager pictureManager)
    {
        this.modelMapper = modelMapper;
        this.rentalService = rentalService;
        this.userService = userService;
        this.storageService = storageService;
        this.pictureManager = pictureManager;
    }

    /*
        LOGIN & TOKEN CONVERTERS
     */

    public TokenDto convertToTokenDto(String token) {
        return new TokenDto(token);
    }

    public LoginDto convertRegisterDtoToLoginDto(RegisterDto registerDto) {
        modelMapper.typeMap(RegisterDto.class, LoginDto.class).addMapping(
                RegisterDto::getEmail, LoginDto::setLogin
        );
        return modelMapper.map(registerDto, LoginDto.class);
    }

    /*
        USER & USERDTO CONVERTERS
     */

    public User convertToUserEntity(RegisterDto registerDto) {
        return modelMapper.map(registerDto, User.class);
    }

    public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    /*
        MESSAGEDTO -> MESSAGE CONVERTER
     */

    public Message convertToMessage(MessageDto messageDto) {

        Converter<Integer, User> userIdToUser = mappingContext -> userService.getUserById(mappingContext.getSource()).get();
        Converter<Integer, Rental> rentalIdToRental = mappingContext -> rentalService.getRentalById(mappingContext.getSource()).get();

        modelMapper.typeMap(MessageDto.class, Message.class).addMappings(
            mapper -> mapper.using(userIdToUser).map(MessageDto::getUser_id, Message::setUser)
        );
        modelMapper.typeMap(MessageDto.class, Message.class).addMappings(
                mapper -> mapper.using(rentalIdToRental).map(MessageDto::getRental_id, Message::setRental)
        );

        return modelMapper.map(messageDto, Message.class);
    }

    /*
        RENTAL & RENTALDTO CONVERTERS
     */

    public Iterable<RentalDto> getAllRentalDtos() {
        return this.rentalService.getAllRentals().stream().map(
                rental -> modelMapper.map(rental, RentalDto.class)
        ).toList();
    }

    public RentalDto getRentalDtoById(Integer id) {
        Optional<Rental> optionalRental = this.rentalService.getRentalById(id);
        return optionalRental.map(rental -> modelMapper.map(rental, RentalDto.class)).orElse(null);
    }
    public Rental convertToRental(RentalDto rentalDto) {
        return modelMapper.map(rentalDto, Rental.class);
    }

    public Rental convertFormRentalToRental(FormRentalDto formRentalDto, String requestURL) {

        String pictureURL = "N/A";

        if (Boolean.TRUE.equals(this.pictureManager.isValidPicture(formRentalDto.getPicture())))
        {
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
