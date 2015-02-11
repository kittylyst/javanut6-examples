package javanut6.ch09;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import static org.junit.Assert.assertEquals;

public class QuarterOfYearTest {

    @Test
    public void testFunctionalQuery() {
        LocalDate firstQuarterDate = LocalDate.now().with(Month.MARCH).with(TemporalAdjusters.lastDayOfMonth());
        LocalDate secondQuarterDate = LocalDate.now().with(Month.JUNE).with(TemporalAdjusters.lastDayOfMonth());
        LocalDate thirdQuarterDate = LocalDate.now().with(Month.SEPTEMBER).with(TemporalAdjusters.lastDayOfMonth());
        LocalDate fourthQuarterDate = LocalDate.now().with(Month.NOVEMBER).with(TemporalAdjusters.lastDayOfMonth());

        QuarterOfYearQuery temporalQuery = new QuarterOfYearQuery();

        assertEquals(Quarter.FIRST, firstQuarterDate.query(temporalQuery));
        assertEquals(Quarter.SECOND, secondQuarterDate.query(temporalQuery));
        assertEquals(Quarter.THIRD, thirdQuarterDate.query(temporalQuery));
        assertEquals(Quarter.FOURTH, fourthQuarterDate.query(temporalQuery));
    }

}
