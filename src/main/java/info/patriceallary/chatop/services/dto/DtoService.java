package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.dto.*;
import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
@Slf4j
public class DtoService {
    private final ModelMapper modelMapper;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    private final RentalService rentalService;

    private final FileSystemStorageService storageService;

    private final PictureManager pictureManager;

    public DtoService(ModelMapper modelMapper, RentalService rentalService, FileSystemStorageService storageService, PictureManager pictureManager)
    {
        this.modelMapper = modelMapper;
        this.rentalService = rentalService;
        this.storageService = storageService;
        this.pictureManager = pictureManager;
    }

    public User convertToUserEntity(RegisterDto registerDto) {
        return modelMapper.map(registerDto, User.class);
    }

    public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public TokenDto convertToTokenDto(String token) {
        return new TokenDto(token);
    }

    public LoginDto convertRegisterDtoToLoginDto(RegisterDto registerDto) {
        modelMapper.typeMap(RegisterDto.class, LoginDto.class).addMapping(
                RegisterDto::getEmail, LoginDto::setLogin
        );
        return modelMapper.map(registerDto, LoginDto.class);
    }

    public Rental convertToRental(RentalDto rentalDto, MultipartFile picture) {

    }

    public MessageDto convertToMessageDto(String message){
        return modelMapper.map(message, MessageDto.class);
    }

    public String convertToValidDate(Timestamp timestamp) {
        return formatter.format(timestamp.toLocalDateTime());
    }

    public Timestamp convertToTimestamp(String dtoDate) {
        return Timestamp.valueOf(dtoDate);
    }

    public Iterable<RentalDto> getAllRentalDtos() {
        return this.rentalService.getAllReantals().stream().map(rental -> modelMapper.map(rental, RentalDto.class)).toList();
    }

    public RentalDto getRentalDtoById(Integer id) {
        if (this.rentalService.getRentalById(id).isPresent())
            return modelMapper.map(this.rentalService.getRentalById(id).get(), RentalDto.class);
        else
            return null;
    }


    public Rental convertFormRentalDtoToRental(FormRentalDto formRentalDto) {

        String pictureURL = "N/A";

        if (Boolean.TRUE.equals(this.pictureManager.isValidPicture(formRentalDto.getPicture())))
        {
            String encodedFileName = null;
            try {
                encodedFileName = this.pictureManager.sanitizeAndEncodeFilename(formRentalDto.getPicture());
                this.storageService.store(formRentalDto.getPicture(), encodedFileName);

                pictureURL = this.storageService.getURI(encodedFileName).getPath();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        modelMapper.typeMap(FormRentalDto.class, Rental.class).
        return modelMapper.map(rentalDto, Rental.class);
    }
}
