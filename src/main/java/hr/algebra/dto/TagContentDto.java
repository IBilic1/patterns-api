package hr.algebra.dto;

public class TagContentDto {

    private Long id;

    private TagDto tag;

    private ContentDto content;

    public TagContentDto() {
    }

    public TagContentDto(Long id, TagDto tag, ContentDto content) {
        this.id = id;
        this.tag = tag;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TagDto getTag() {
        return tag;
    }

    public void setTag(TagDto tag) {
        this.tag = tag;
    }

    public ContentDto getContent() {
        return content;
    }

    public void setContent(ContentDto content) {
        this.content = content;
    }
}
