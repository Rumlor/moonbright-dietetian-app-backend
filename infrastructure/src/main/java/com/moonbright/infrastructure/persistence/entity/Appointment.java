package com.moonbright.infrastructure.persistence.entity;

import com.moonbright.infrastructure.persistence.BaseEntity;
import com.moonbright.infrastructure.persistence.entity.utils.Status;
import com.moonbright.infrastructure.persistence.entity.valuetypes.SessionDuration;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "appointment")
@Builder(builderMethodName = "appointmentBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends BaseEntity {

    @Column(name = "_pro_uid",nullable = false)
    private String appliedProfessionalId;

    @Embedded
    private SessionDuration sessionDuration;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    @JoinTable(name = "appointment_to_file_docs",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "file_doc_id"))
    private List<FileDoc> appointmentDocuments = new ArrayList<>();

    @Enumerated(value = EnumType.ORDINAL)
    private Status appointmentStatus = Status.PENDING;

    @Column(name = "appointment_create_date",nullable = false)
    private OffsetDateTime appointmentCreateDate;


    public void addToAppointmentDocuments(FileDoc fileDoc){
        this.appointmentDocuments.add(fileDoc);
    }
    @PrePersist
    public void setAppointmentCreateDateBeforePersisting(){
        this.appointmentCreateDate = OffsetDateTime.now();
    }
}
