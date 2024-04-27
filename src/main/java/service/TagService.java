package service;

import dto.request.TagRequest;
import dto.response.TagResponse;
import model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TagService extends DefaultService<TagRequest, Tag, TagResponse, TagResponse> {
    private static final Logger logger = LoggerFactory.getLogger(TagService.class);

    public TagService() {
        super(Tag.class);
    }
}
