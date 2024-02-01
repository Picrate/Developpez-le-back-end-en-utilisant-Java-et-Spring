package info.patriceallary.chatop.services.dto;

import info.patriceallary.chatop.domain.dto.LoginDto;
import info.patriceallary.chatop.domain.dto.RegisterDto;
import info.patriceallary.chatop.domain.dto.TokenDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LoginAndRegisterDtoService {

    public TokenDto convertToTokenDto(String token) {
        return new TokenDto(token);
    }

    public LoginDto convertRegisterDtoToLoginDto(RegisterDto registerDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(registerDto, LoginDto.class);
    }

}
