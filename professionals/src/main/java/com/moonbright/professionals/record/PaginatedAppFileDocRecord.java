package com.moonbright.professionals.record;

import java.util.List;

public record PaginatedAppFileDocRecord (Long pageCount, List<AppFileDocRecord> appFileDocRecordList){
}
