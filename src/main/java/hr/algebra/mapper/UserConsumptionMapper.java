package hr.algebra.mapper;

import hr.algebra.dto.UserConsumptionDto;
import hr.algebra.model.UserConsumption;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, PackageDtoMapper.class})
public interface UserConsumptionMapper {

    UserConsumption to(UserConsumptionDto source);

    UserConsumptionDto from(UserConsumption destination);

    List<UserConsumptionDto> mapToDto(List<UserConsumption> packages);

    List<UserConsumption> map(List<UserConsumptionDto> employees);
}
