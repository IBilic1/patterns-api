package hr.algebra.mapper;

import hr.algebra.dto.SignInUserDto;
import hr.algebra.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSignInUserMapper {

    SignInUserDto from(User destination);
}
