package hr.algebra.mapper;

import hr.algebra.dto.UserContentDto;
import hr.algebra.model.UserContent;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, ContentMapper.class})
public interface UserContentMapper {

    UserContent to(UserContentDto source);

    UserContentDto from(UserContent destination);

    List<UserContentDto> mapToDto(List<UserContent> packages);

    List<UserContent> map(List<UserContentDto> employees);
}
