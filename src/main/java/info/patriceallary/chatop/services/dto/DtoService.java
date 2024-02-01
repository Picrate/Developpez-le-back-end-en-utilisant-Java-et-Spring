/**
 * This Class manages Entities & DTO Conversion
 */
package info.patriceallary.chatop.services.dto;

import info.patriceallary.chatop.domain.dto.*;
import info.patriceallary.chatop.domain.model.Message;
import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class DtoService {

    private final LoginAndRegisterDtoService loginAndRegisterDtoService;
    private final UserDtoService userDtoService;
    private final MessageDtoService messageDtoService;
    private final RentalDtoService rentalDtoService;

    public DtoService(LoginAndRegisterDtoService loginAndRegisterDtoService, UserDtoService userDtoService, MessageDtoService messageDtoService, RentalDtoService rentalDtoService) {
        this.loginAndRegisterDtoService = loginAndRegisterDtoService;
        this.userDtoService = userDtoService;
        this.messageDtoService = messageDtoService;
        this.rentalDtoService = rentalDtoService;
    }

    /*
        LOGIN & TOKEN CONVERTERS
     */

    public TokenDto convertToTokenDto(String token) {
        return this.loginAndRegisterDtoService.convertToTokenDto(token);
    }

    public LoginDto convertRegisterDtoToLoginDto(RegisterDto registerDto) {
        return this.loginAndRegisterDtoService.convertRegisterDtoToLoginDto(registerDto);
    }

    /*
        USER & USERDTO CONVERTERS
     */

    public User convertToUserEntity(RegisterDto registerDto) {
        return this.userDtoService.convertToUserEntity(registerDto);
    }

    public UserDto convertToUserDto(User user) {
        return this.userDtoService.convertToUserDto(user);
    }

    public UserDto getUserDtoForUserId(Integer id) {
        return this.userDtoService.getUserDtoForUserId(id);
    }

    /*
        MESSAGEDTO -> MESSAGE CONVERTER
     */

    public Message convertToMessage(MessageDto messageDto) {
        return this.messageDtoService.convertToMessage(messageDto);
    }

    /*
        RENTAL & RENTALDTO CONVERTERS
     */

    public RentalListDto getAllRentalDtos() {
        return this.rentalDtoService.getAllRentalDtos();
    }

    public RentalDto getRentalDtoById(Integer id) {
        return this.rentalDtoService.getRentalDtoById(id);
    }

    public Rental convertToRental(RentalDto rentalDto) {
        return this.rentalDtoService.convertToRental(rentalDto);
    }

    public Rental convertFormRentalToRental(FormRentalDto formRentalDto, String requestURL) {
        return this.rentalDtoService.convertFormRentalToRental(formRentalDto, requestURL);
    }
}
