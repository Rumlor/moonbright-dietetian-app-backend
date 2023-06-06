package com.moonbright.infrastructure.persistence.entity;

import com.moonbright.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "location")
    private String location;

    @Column(name ="district")
    private String district;

    @Column(name = "education")
    private String education;

    @Column(name = "is_professional_user")
    private Boolean isProfessionalUser;
}
