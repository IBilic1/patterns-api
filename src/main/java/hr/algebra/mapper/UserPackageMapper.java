package hr.algebra.mapper;

import hr.algebra.dto.UserPackageDto;
import hr.algebra.model.UserPackage;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, PackageDtoMapper.class})
public interface UserPackageMapper {

    @Mappings({
            @Mapping(target = "customPackage", source = "package_"),
    })
    UserPackage to(UserPackageDto source);

    @Mappings({
            @Mapping(target = "package_", source = "customPackage"),
    })
    UserPackageDto from(UserPackage destination);

    List<UserPackageDto> mapToDto(List<UserPackage> packages);

    List<UserPackage> map(List<UserPackageDto> employees);
}
