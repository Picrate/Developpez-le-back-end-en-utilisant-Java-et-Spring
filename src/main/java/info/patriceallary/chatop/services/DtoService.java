package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.dto.*;
import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoService {
    private final ModelMapper modelMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
    private final RentalService rentalService;

    public DtoService(ModelMapper modelMapper, RentalService rentalService)
    {
        this.modelMapper = modelMapper;
        this.rentalService = rentalService;
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
        LoginDto loginDto = new LoginDto();
        loginDto.setLogin(registerDto.getEmail());
        loginDto.setPassword(registerDto.getPassword());
        return loginDto;
    }

    public Rental convertToRental(RentalDto rentalDto) {return modelMapper.map(rentalDto, Rental.class);}

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
        return this.rentalService.getAllReantals().stream().map(rental -> modelMapper.map(rental, RentalDto.class)).collect(Collectors.toList());
    }

    public RentalDto getRentalDtoById(Integer id) {
        if (this.rentalService.getRentalById(id).isPresent())
            return modelMapper.map(this.rentalService.getRentalById(id).get(), RentalDto.class);
        else
            return null;
    }



}
