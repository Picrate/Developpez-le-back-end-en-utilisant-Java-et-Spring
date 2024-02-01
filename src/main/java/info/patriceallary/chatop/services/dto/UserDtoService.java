package info.patriceallary.chatop.services.dto;

import info.patriceallary.chatop.domain.dto.RegisterDto;
import info.patriceallary.chatop.domain.dto.UserDto;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.services.domain.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserDtoService {

    private final UserService userService;

    public UserDtoService(UserService userService) {
        this.userService = userService;
    }

    public User convertToUserEntity(RegisterDto registerDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(registerDto, User.class);
    }

    public UserDto convertToUserDto(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUserDtoForUserId(Integer id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<User> optionalUser = this.userService.getUserById(id);
        if (optionalUser.isEmpty()) {
            throw new NoSuchElementException("User Not Found !");
        }
        return modelMapper.map(optionalUser.get(), UserDto.class);
    }


}
