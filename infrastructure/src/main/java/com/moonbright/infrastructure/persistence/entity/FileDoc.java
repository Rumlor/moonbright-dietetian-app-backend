package com.moonbright.infrastructure.persistence.entity;

import com.moonbright.infrastructure.persistence.BaseEntity;
import com.moonbright.infrastructure.record.FileDocRecord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_doc",uniqueConstraints = {@UniqueConstraint(columnNames = {"file_name","_uid"})})
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "file_doc_type")
@NoArgsConstructor
public class FileDoc extends BaseEntity {
    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "file_format")
    private String fileFormat;

    @Column(name = "file_name")
    private String fileName;

    public FileDoc(byte[] content, String fileFormat,String fileName,String relatedUserId) {
        super(relatedUserId);
        this.content = content;
        this.fileFormat = fileFormat;
        this.fileName = fileName;
    }
    public FileDoc(String fileFormat,String fileName,String relatedUserId) {
        super(relatedUserId);
        this.fileFormat = fileFormat;
        this.fileName = fileName;
    }
    public FileDocRecord toFileDocRecord(){
        return new FileDocRecord(this.getId(),this.fileName);
    }
}
