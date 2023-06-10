package hr.algebra.controller;

import hr.algebra.dto.UserConsumptionDto;
import hr.algebra.dto.UserContentDto;
import hr.algebra.mapper.UserConsumptionMapper;
import hr.algebra.mapper.UserContentMapper;
import hr.algebra.model.UserContent;
import hr.algebra.service.UserContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user-content")
@Secured({"USER"})
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class UserContentController {

    private final UserContentService userContentService;

    private final UserContentMapper userContentMapper;

    private final UserConsumptionMapper userConsumptionMapper;

    @Autowired
    public UserContentController(UserContentService userContentService, UserContentMapper userContentMapper, UserConsumptionMapper userConsumptionMapper) {
        this.userContentService = userContentService;
        this.userContentMapper = userContentMapper;
        this.userConsumptionMapper = userConsumptionMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserContentDto>> getAllContents() {
        return new ResponseEntity<>(userContentMapper.mapToDto(userContentService.getAllUsersContents()), HttpStatus.OK);
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<List<UserContentDto>> getUsersContents(@PathVariable(name = "id") Long id) {

        return new ResponseEntity<>(userContentMapper.mapToDto(userContentService.getUserContents(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserContentDto> addUserContent(@RequestBody UserContentDto userContentDto) {
        UserContent userContent = userContentMapper.to(userContentDto);
        return new ResponseEntity<>(userContentMapper.from(userContentService.addUsersContent(userContent)), HttpStatus.OK);
    }

    @GetMapping("/consumption")
    public ResponseEntity<List<UserConsumptionDto>> getConsumption() {
        return new ResponseEntity<>(userConsumptionMapper.mapToDto(userContentService.getConsumptions()), HttpStatus.OK);
    }

    @GetMapping("/consumption/user")
    public ResponseEntity<UserConsumptionDto> getConsumptionByUser() {
        return new ResponseEntity<>(userConsumptionMapper.from(userContentService.getConsumptionsByUser()), HttpStatus.OK);
    }

}
