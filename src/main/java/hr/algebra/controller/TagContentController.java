package hr.algebra.controller;

import hr.algebra.dto.TagContentDto;
import hr.algebra.dto.TagDto;
import hr.algebra.mapper.TagContentMapper;
import hr.algebra.mapper.TagMapper;
import hr.algebra.model.TagContent;
import hr.algebra.service.TagContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/tag-content")
@Secured({"USER", "ADMIN"})
public class TagContentController {

    private final TagContentService tagContentService;

    private final TagContentMapper tagContentMapper;

    private final TagMapper tagMapper;

    @Autowired
    public TagContentController(TagContentService tagContentService, TagContentMapper tagContentMapper, TagMapper tagMapper) {
        this.tagContentService = tagContentService;
        this.tagContentMapper = tagContentMapper;
        this.tagMapper = tagMapper;
    }

    @GetMapping("/tags")
    public ResponseEntity<List<TagDto>> getAllTags() {
        return new ResponseEntity<>(tagMapper.mapToDto(tagContentService.getAllTags()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TagContentDto>> getAllTagContent() {
        return new ResponseEntity<>(tagContentMapper.mapToDto(tagContentService.getAllTagsContent()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TagContentDto> addNewTag(@RequestBody TagContentDto tagContentDto) {
        TagContent tagContent = tagContentMapper.to(tagContentDto);
        return new ResponseEntity<>(tagContentMapper.from(tagContentService.addTagContent(tagContent)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TagContentDto> updateTagContent(@RequestBody TagContentDto tagContentDto) {
        TagContent tagContent = tagContentMapper.to(tagContentDto);
        return new ResponseEntity<>(tagContentMapper.from(tagContentService.removeTagContent(tagContent)), HttpStatus.OK);
    }
}
