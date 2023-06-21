package hr.algebra.service;

import hr.algebra.model.Package;
import hr.algebra.repository.PackageRepository;
import hr.algebra.service.impl.PackageServiceImpl;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    @Mock
    PackageRepository packageRepository;

    @InjectMocks
    PackageServiceImpl packageService;

    @Test
    void when_get_all_package_is_should_return_all_packages() {
        List<Package> packages = new ArrayList<>();
        packages.add(new Package(15L, "", 15000, 10, 10));
        when(packageRepository.findAll()).thenReturn(packages);

        List<Package> servicePackages = packageService.getPackages();
        assertThat(packages.size()).isSameAs(servicePackages.size());
    }
}
