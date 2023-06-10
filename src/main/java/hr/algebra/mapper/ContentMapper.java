package hr.algebra.mapper;

import hr.algebra.dto.ContentDto;
import hr.algebra.model.Content;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapper {

    Content to(ContentDto source);

    ContentDto from(Content destination);

    List<ContentDto> mapToDto(List<Content> packages);

    List<Content> map(List<ContentDto> employees);
}
