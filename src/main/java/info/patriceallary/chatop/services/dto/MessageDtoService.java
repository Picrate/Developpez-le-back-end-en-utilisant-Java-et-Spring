package info.patriceallary.chatop.services.dto;

import info.patriceallary.chatop.domain.dto.MessageDto;
import info.patriceallary.chatop.domain.model.Message;
import info.patriceallary.chatop.domain.model.Rental;
import info.patriceallary.chatop.domain.model.User;
import info.patriceallary.chatop.services.domain.RentalService;
import info.patriceallary.chatop.services.domain.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.stereotype.Service;

@Service
public class MessageDtoService {

    private final UserService userService;
    private final RentalService rentalService;

    public MessageDtoService(UserService userService, RentalService rentalService) {
        this.userService = userService;
        this.rentalService = rentalService;
    }
    public Message convertToMessage(MessageDto messageDto) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE);

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

}
