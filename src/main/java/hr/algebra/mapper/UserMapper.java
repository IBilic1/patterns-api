package hr.algebra.mapper;

import hr.algebra.dto.UserDto;
import hr.algebra.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User to(UserDto source);

    UserDto from(User destination);

    List<UserDto> mapToDto(List<User> packages);

    List<User> map(List<UserDto> employees);
}
