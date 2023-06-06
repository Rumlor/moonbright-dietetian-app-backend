package com.moonbright.infrastructure.mapping;

import com.moonbright.infrastructure.persistence.entity.FileDoc;
import com.moonbright.infrastructure.record.FileDocRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface FileDocMapper {

    @Mapping(source = "fileName",target = "fileName")
    @Mapping(source = "id",target = "fileId")
    FileDocRecord toFileDocRecord(FileDoc fileDoc);

}
