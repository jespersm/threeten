/*
 * Copyright (c) 2008-2009, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.time.calendar.field;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.io.Serializable;

import javax.time.calendar.Calendrical;
import javax.time.calendar.CalendricalMatcher;
import javax.time.calendar.DateTimeFieldRule;
import javax.time.calendar.ISOChronology;
import javax.time.calendar.IllegalCalendarFieldValueException;
import javax.time.calendar.LocalDate;
import javax.time.calendar.LocalTime;
import javax.time.calendar.UnsupportedRuleException;
import javax.time.calendar.format.MockSimpleCalendrical;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test QuarterOfYear.
 *
 * @author Michael Nascimento Santos
 * @author Stephen Colebourne
 */
@Test
public class TestQuarterOfYear {

    private static final DateTimeFieldRule<Integer> RULE = ISOChronology.quarterOfYearRule();

    @BeforeMethod
    public void setUp() {
    }

    //-----------------------------------------------------------------------
    public void test_interfaces() {
        assertTrue(Calendrical.class.isAssignableFrom(QuarterOfYear.class));
        assertTrue(Serializable.class.isAssignableFrom(QuarterOfYear.class));
        assertTrue(Comparable.class.isAssignableFrom(QuarterOfYear.class));
        assertTrue(CalendricalMatcher.class.isAssignableFrom(QuarterOfYear.class));
    }

    //-----------------------------------------------------------------------
    public void test_rule() {
        assertEquals(QuarterOfYear.rule(), RULE);
    }

    //-----------------------------------------------------------------------
    public void test_factory_int_singleton() {
        for (int i = 1; i <= 4; i++) {
            QuarterOfYear test = QuarterOfYear.quarterOfYear(i);
            assertEquals(test.getValue(), i);
            assertSame(QuarterOfYear.quarterOfYear(i), test);
        }
    }

    @Test(expectedExceptions=IllegalCalendarFieldValueException.class)
    public void test_factory_int_valueTooLow() {
        QuarterOfYear.quarterOfYear(0);
    }

    @Test(expectedExceptions=IllegalCalendarFieldValueException.class)
    public void test_factory_int_valueTooHigh() {
        QuarterOfYear.quarterOfYear(5);
    }

    //-----------------------------------------------------------------------
    public void test_factory_Calendrical() {
        LocalDate date = LocalDate.date(2007, 1, 1);
        for (int i = 1; i <= 365; i++) {
            QuarterOfYear test = QuarterOfYear.quarterOfYear(date);
            assertEquals(test.getValue(), ((date.getMonthOfYear().getValue() - 1) / 3) + 1);
            date = date.plusDays(1);
        }
    }

    @Test(expectedExceptions=UnsupportedRuleException.class)
    public void test_factory_Calendrical_noData() {
        DayOfWeek.dayOfWeek(new MockSimpleCalendrical());
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_factory_nullCalendrical() {
        QuarterOfYear.quarterOfYear((Calendrical) null);
    }

    //-----------------------------------------------------------------------
    // next()
    //-----------------------------------------------------------------------
    public void test_next() {
        assertEquals(QuarterOfYear.Q1.next(), QuarterOfYear.Q2);
        assertEquals(QuarterOfYear.Q2.next(), QuarterOfYear.Q3);
        assertEquals(QuarterOfYear.Q3.next(), QuarterOfYear.Q4);
        assertEquals(QuarterOfYear.Q4.next(), QuarterOfYear.Q1);
    }

    //-----------------------------------------------------------------------
    // previous()
    //-----------------------------------------------------------------------
    public void test_previous() {
        assertEquals(QuarterOfYear.Q1.previous(), QuarterOfYear.Q4);
        assertEquals(QuarterOfYear.Q2.previous(), QuarterOfYear.Q1);
        assertEquals(QuarterOfYear.Q3.previous(), QuarterOfYear.Q2);
        assertEquals(QuarterOfYear.Q4.previous(), QuarterOfYear.Q3);
    }

    //-----------------------------------------------------------------------
    // matchesCalendrical(Calendrical)
    //-----------------------------------------------------------------------
    public void test_matchesCalendrical() {
        assertEquals(QuarterOfYear.Q1.matchesCalendrical(LocalDate.date(2008, 1, 1)), true);
        assertEquals(QuarterOfYear.Q2.matchesCalendrical(LocalDate.date(2008, 4, 1)), true);
        assertEquals(QuarterOfYear.Q3.matchesCalendrical(LocalDate.date(2008, 7, 1)), true);
        assertEquals(QuarterOfYear.Q4.matchesCalendrical(LocalDate.date(2008, 10, 1)), true);
    }

    public void test_matchesCalendrical_noData() {
        assertEquals(QuarterOfYear.Q1.matchesCalendrical(LocalTime.time(12, 30)), false);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_matchesCalendrical_null() {
        QuarterOfYear.Q1.matchesCalendrical(null);
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    public void test_toString() {
        assertEquals(QuarterOfYear.Q1.toString(), "QuarterOfYear=Q1");
        assertEquals(QuarterOfYear.Q2.toString(), "QuarterOfYear=Q2");
        assertEquals(QuarterOfYear.Q3.toString(), "QuarterOfYear=Q3");
        assertEquals(QuarterOfYear.Q4.toString(), "QuarterOfYear=Q4");
    }

    //-----------------------------------------------------------------------
    // generated methods
    //-----------------------------------------------------------------------
    public void test_enum() {
        assertEquals(QuarterOfYear.valueOf("Q4"), QuarterOfYear.Q4);
        assertEquals(QuarterOfYear.values()[0], QuarterOfYear.Q1);
    }

}