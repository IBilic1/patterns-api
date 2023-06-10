package hr.algebra.controller;

import hr.algebra.dto.PackageDto;
import hr.algebra.mapper.PackageDtoMapper;
import hr.algebra.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/package")
@Secured({"USER", "ADMIN"})
public class PackageController {

    private final PackageService packageService;

    private final PackageDtoMapper packageDtoMapper;

    @Autowired
    public PackageController(PackageService packageService, PackageDtoMapper packageDtoMapper) {
        this.packageService = packageService;
        this.packageDtoMapper = packageDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<PackageDto>> getAllPackages() {
        return ResponseEntity.ok().body(packageDtoMapper.mapToDto(packageService.getPackages()));
    }
}
