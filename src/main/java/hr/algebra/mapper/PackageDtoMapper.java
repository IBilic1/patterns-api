package hr.algebra.mapper;

import hr.algebra.dto.PackageDto;
import hr.algebra.model.Package;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PackageDtoMapper {

    Package to(PackageDto source);

    PackageDto from(Package destination);

    List<PackageDto> mapToDto(List<Package> packages);

    List<Package> map(List<PackageDto> employees);
}
