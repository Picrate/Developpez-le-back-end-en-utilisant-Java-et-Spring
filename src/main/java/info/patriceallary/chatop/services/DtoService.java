package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.dto.*;
import info.patriceallary.chatop.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class DtoService {
    private final ModelMapper modelMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    public DtoService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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

    public MessageDto convertToMessageDto(String message){
        return modelMapper.map(message, MessageDto.class);
    }

    public String convertToValidDate(Timestamp timestamp) {
        return formatter.format(timestamp.toLocalDateTime());
    }

    public Timestamp convertToTimestamp(String dtoDate) {
        return Timestamp.valueOf(dtoDate);
    }


}
