package com.moonbright.professionals.entity;

import com.moonbright.infrastructure.persistence.entity.FileDoc;
import com.moonbright.professionals.record.AppFileDocRecord;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "application_file_doc")
@Getter
@NoArgsConstructor
public class ApplicationFileDoc extends FileDoc {
    @Column(name = "is_mandatory",columnDefinition = "boolean not null default 0")
    private Boolean isMandatory;

    @Builder(builderMethodName = "applicationFileDocBuilder")
    public ApplicationFileDoc(byte[] content,String fileFormat,String fileName,Boolean isMandatory,String relatedUserId){
        super(content,fileFormat,fileName,relatedUserId);
        this.isMandatory = isMandatory;
    }

    public AppFileDocRecord toAppFileDocRecord(){
        return new AppFileDocRecord(this.isMandatory,toFileDocRecord());
    }
}
