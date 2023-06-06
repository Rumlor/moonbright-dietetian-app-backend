package com.moonbright.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_deleted",columnDefinition = "boolean not null default 0")
    private Boolean isDeleted = Boolean.FALSE;

    @Column(name = "_uid")
    private String relatedUserId;

    public BaseEntity(String relatedUserId){
        this.relatedUserId = relatedUserId;
    }
}
