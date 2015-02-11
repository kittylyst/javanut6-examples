package javanut6.ch09;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Set;

import static org.junit.Assert.*;

public class BirthdayDiaryTest {

    private BirthdayDiary birthdayDiary;

    @Before
    public void before() {
        birthdayDiary = new BirthdayDiary();
    }

    @Test
    public void create_a_birthday_for_henry_viii() {
        String expectedName = "Henry VIII";
        LocalDate birthday = birthdayDiary.addBirthday(expectedName, 28, 6, 1941);
        assertEquals(1941, birthday.getYear());
        assertEquals(Month.JUNE, birthday.getMonth());
        assertEquals(28, birthday.getDayOfMonth());
    }

    @Test
    public void can_retrieve_birthday_for_henry_viii() {
        String henry = "Henry VIII";
        birthdayDiary.addBirthday(henry, 28, 6, 1941);
        LocalDate expectedDate = LocalDate.of(1941, Month.JUNE, 28);
        LocalDate birthday = birthdayDiary.getBirthdayFor(henry);
        assertEquals(expectedDate, birthday);
    }

    @Test
    public void jim_is_30_in_2014() {
        String jim = "Jim";
        birthdayDiary.addBirthday(jim, 29, 9, 1984);
        int age = birthdayDiary.getAgeInYear(jim, 2014);
        assertEquals(30, age);
    }

    @Test
    public void find_people_who_are_30_in_2014() {
        birthdayDiary.addBirthday("Jim", 29, 9, 1984);
        birthdayDiary.addBirthday("Mark", 1, 1, 1984);
        birthdayDiary.addBirthday("Old John", 1, 1, 1930);
        Set<String> thirty = birthdayDiary.getFriendsOfAgeIn(30, 2014);
        assertTrue(thirty.contains("Jim"));
        assertTrue(thirty.contains("Mark"));
        assertFalse(thirty.contains("Old John"));
    }

    @Test
    public void how_many_days_until_jims_birthday() {
        birthdayDiary.addBirthday("Jim", 29, 9, 1984);
        int days = birthdayDiary.getDaysUntilBirthday("Jim");
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(1984, Month.SEPTEMBER, 29);
        Period until = today.until(birthday);
        assertEquals(until.getDays(), days);
    }

    @Test
    public void who_has_a_birthday_in_september() {
        birthdayDiary.addBirthday("Jim", 29, 9, 1984);
        birthdayDiary.addBirthday("Paul", 1, 9, 1991);
        birthdayDiary.addBirthday("Emily", 12, 1, 2013);
        Set<String> septemberBirthdays = birthdayDiary.getBirthdaysIn(Month.SEPTEMBER);
        assertTrue(septemberBirthdays.contains("Jim"));
        assertTrue(septemberBirthdays.contains("Paul"));
        assertFalse(septemberBirthdays.contains("Emily"));
    }

    @Test
    public void total_ages_of_contacts() {
        LocalDate twentyFive = LocalDate.now().minusYears(25);
        LocalDate thirty = LocalDate.now().minusYears(30);
        LocalDate ten = LocalDate.now().minusYears(10);
        birthdayDiary.addBirthday("Jim", thirty.getDayOfMonth(), thirty.getMonthValue(), thirty.getYear());
        birthdayDiary.addBirthday("Maggie", twentyFive.getDayOfMonth(), twentyFive.getMonthValue(), twentyFive.getYear());
        birthdayDiary.addBirthday("Trish", ten.getDayOfMonth(), ten.getMonthValue(), ten.getYear());

        int total = birthdayDiary.getTotalAgeInYears();
        assertEquals(65, total);
    }

    @Test
    public void total_age_zero_when_no_contacts() {
        assertEquals(0, birthdayDiary.getTotalAgeInYears());
    }
}
