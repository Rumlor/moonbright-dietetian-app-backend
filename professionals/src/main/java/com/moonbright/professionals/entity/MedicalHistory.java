package com.moonbright.professionals.entity;

import com.moonbright.professionals.entity.utils.MedicalHistoryType;
import com.moonbright.infrastructure.persistence.BaseEntity;
import com.moonbright.infrastructure.persistence.entity.FileDoc;
import com.moonbright.infrastructure.persistence.entity.FormDoc;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "medical_history")
@Getter
@NoArgsConstructor
public class MedicalHistory extends BaseEntity {
    @Column(name = "type")
    private MedicalHistoryType type;

    @Column(name = "save_date")
    private LocalDateTime saveDate;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "file_doc_id")
    private FileDoc fileDoc;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "form_doc_id")
    private FormDoc formDoc;
}

