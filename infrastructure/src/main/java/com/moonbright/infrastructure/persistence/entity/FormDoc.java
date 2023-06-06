package com.moonbright.infrastructure.persistence.entity;

import com.moonbright.infrastructure.persistence.BaseEntity;
import com.moonbright.infrastructure.persistence.entity.utils.MaritalStatus;
import com.moonbright.infrastructure.persistence.entity.valuetypes.NumericRange;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "form_doc")
@Getter
@Builder(builderMethodName = "formDocBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class FormDoc extends BaseEntity {
    @Column(name = "age")
    private Integer age;
    @Column(name = "height")
    private Integer height;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "diagnoes_health_condition")
    private String diagnosedHealthCondition;
    @Column(name = "food_allergies")
    private String foodAllergies;
    @Column(name = "waist_circumference")
    private Integer waistCircumference;
    @Column(name = "hip_circumference")
    private Integer hipCircumference;
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;
    @Column(name = "vocation")
    private String vocation;
    @Column(name = "prescribed_drugs")
    private String prescribedDrugs;
    @Column(name = "any_previous_diet_plan")
    private Boolean anyPreviousDietPlan;
    @Column(name = "previous_diet_plan_result")
    private String previousDietPlanResult;
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "lowerBound", column = @Column(name = "lower_targeted_weight")),
            @AttributeOverride(name = "upperBound", column = @Column(name = "upper_targeted_weight")),
    })
    private NumericRange targetedWeightRange;

}
