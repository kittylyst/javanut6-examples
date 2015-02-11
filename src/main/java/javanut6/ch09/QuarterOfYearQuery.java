package javanut6.ch09;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public class QuarterOfYearQuery implements TemporalQuery<Quarter> {

    @Override
    public Quarter queryFrom(TemporalAccessor temporal) {
        LocalDate now = LocalDate.from(temporal);

        if (now.isBefore(now.with(Month.APRIL).withDayOfMonth(1))) {
            return Quarter.FIRST;
        } else if (now.isBefore(now.with(Month.JULY).withDayOfMonth(1))) {
            return Quarter.SECOND;
        } else if (now.isBefore(now.with(Month.NOVEMBER).withDayOfMonth(1))) {
            return Quarter.THIRD;
        } else {
            return Quarter.FOURTH;
        }
    }
}
