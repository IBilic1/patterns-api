package hr.algebra.mapper;

import hr.algebra.dto.UserPackageDto;
import hr.algebra.model.UserPackage;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, PackageDtoMapper.class})
public interface UserPackageMapper {

    UserPackage to(UserPackageDto source);

    UserPackageDto from(UserPackage destination);

    List<UserPackageDto> mapToDto(List<UserPackage> packages);

    List<UserPackage> map(List<UserPackageDto> employees);
}
