package com.moonbright.infrastructure.persistence.entity.valuetypes;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NumericRange {
    private Integer lowerBound;
    private Integer upperBound;
}
