package hr.algebra.mapper;

import hr.algebra.dto.TagDto;
import hr.algebra.model.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag to(TagDto source);

    TagDto from(Tag destination);

    List<TagDto> mapToDto(List<Tag> packages);

    List<Tag> map(List<TagDto> employees);
}
