package info.patriceallary.chatop.services;

import info.patriceallary.chatop.domain.dto.MessageDto;
import info.patriceallary.chatop.domain.dto.RegisterDto;
import info.patriceallary.chatop.domain.dto.TokenDto;
import info.patriceallary.chatop.domain.dto.UserDto;
import info.patriceallary.chatop.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DtoService {

    @Autowired
    private ModelMapper modelMapper;

    public User convertToUserEntity(RegisterDto registerDto) {
        return modelMapper.map(registerDto, User.class);
    }

    public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public TokenDto convertToTokenDto(String jwtToken) {
        return modelMapper.map(jwtToken, TokenDto.class);
    }

    public MessageDto convertToMessageDto(String message){
        return modelMapper.map(message, MessageDto.class);
    }


}
