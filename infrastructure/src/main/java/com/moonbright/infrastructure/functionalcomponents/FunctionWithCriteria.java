package com.moonbright.infrastructure.functionalcomponents;

import java.util.Objects;
import java.util.function.Function;

public record FunctionWithCriteria<CRITERIA, RESULT>(CRITERIA criteria, Function<CRITERIA, RESULT> function) {

    @Override
    public CRITERIA criteria() {
        return Objects.requireNonNull(criteria, "criteria is null");
    }

    @Override
    public Function<CRITERIA, RESULT> function() {
        return Objects.requireNonNull(function, "function is null");
    }
}
