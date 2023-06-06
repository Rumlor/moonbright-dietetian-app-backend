package com.moonbright.infrastructure.persistence.entity.valuetypes;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.OffsetDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionDuration {
    private OffsetDateTime start;
    private OffsetDateTime end;
}
