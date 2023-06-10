package hr.algebra.mapper;

import hr.algebra.dto.TagContentDto;
import hr.algebra.model.TagContent;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ContentMapper.class, TagMapper.class})
public interface TagContentMapper {

    TagContent to(TagContentDto source);

    TagContentDto from(TagContent destination);

    List<TagContentDto> mapToDto(List<TagContent> packages);

    List<TagContent> map(List<TagContentDto> employees);
}
