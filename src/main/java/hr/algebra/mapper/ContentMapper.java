package hr.algebra.mapper;

import hr.algebra.dto.ContentDto;
import hr.algebra.model.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    @Mapping(target = "pictureContent", source = "content")
    Content to(ContentDto source);


    @Mapping(target = "content", source = "pictureContent")
    ContentDto from(Content destination);

    List<ContentDto> mapToDto(List<Content> packages);

    List<Content> map(List<ContentDto> employees);
}
