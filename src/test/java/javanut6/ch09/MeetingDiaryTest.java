package javanut6.ch09;

import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MeetingDiaryTest {

    private MeetingDiary meetingDiary;

    @Before
    public void before() {
        meetingDiary = new MeetingDiary();
    }

    @Test
    public void create_reminder_at_9am_on_monday() {
        String expectedAction = "Get Coffee";
        Reminder reminder = meetingDiary.createReminder(DayOfWeek.MONDAY, 9, 00, expectedAction);
        assertEquals(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)), reminder.getTime().toLocalDate());
        assertEquals(LocalTime.of(9, 0), reminder.getTime().toLocalTime());
        assertEquals(expectedAction, reminder.getAction());
    }

    @Test
    public void find_all_reminders_on_monday() {
        Reminder reminder = meetingDiary.createReminder(DayOfWeek.MONDAY, 9, 00, "Get Coffee");
        Reminder reminder2 = meetingDiary.createReminder(DayOfWeek.MONDAY, 11, 00, "Get Snack");
        meetingDiary.createReminder(DayOfWeek.TUESDAY, 13, 00, "Get Lunch");
        Set<Reminder> expectedReminders = new HashSet<>();
        expectedReminders.add(reminder);
        expectedReminders.add(reminder2);

        Set<Reminder> mondayReminders = meetingDiary.getRemindersFor(DayOfWeek.MONDAY);
        assertEquals(expectedReminders, mondayReminders);
    }

    @Test
    public void find_all_reminders_on_monday_before_12() {
        Reminder reminder = meetingDiary.createReminder(DayOfWeek.MONDAY, 9, 00, "Get Coffee");
        Reminder reminder2 = meetingDiary.createReminder(DayOfWeek.MONDAY, 11, 00, "Get Snack");
        meetingDiary.createReminder(DayOfWeek.MONDAY, 13, 00, "Get Lunch");
        meetingDiary.createReminder(DayOfWeek.TUESDAY, 13, 00, "Get Lunch");
        Set<Reminder> expectedReminders = new HashSet<>();
        expectedReminders.add(reminder);
        expectedReminders.add(reminder2);

        Set<Reminder> mondayMorningReminders = meetingDiary.getRemindersOnDayBefore(DayOfWeek.MONDAY, 12, 0);
        assertEquals(expectedReminders, mondayMorningReminders);
    }

    @Test
    public void book_a_meeting_at_gmt_time_zone() {
        ZonedDateTime expectedStartTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(9, 0), ZoneId.of("Europe/London"));
        ZonedDateTime expectedEndTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(10, 0), ZoneId.of("Europe/London"));
        String expectedTitle = "Morning Catchup";
        Meeting meeting = meetingDiary.bookMeeting(expectedStartTime, expectedEndTime, expectedTitle);
        assertEquals(expectedStartTime, meeting.getStart());
        assertEquals(expectedEndTime, meeting.getEnd());
    }

    @Test
    public void retrieve_booked_meeting_in_paris_timezone() {
        ZonedDateTime startTime = ZonedDateTime.of(LocalDate.of(2014, Month.MARCH, 23), LocalTime.of(9, 0), ZoneId.of("Europe/London"));
        ZonedDateTime endTime = ZonedDateTime.of(LocalDate.of(2014, Month.MARCH, 23), LocalTime.of(10, 0), ZoneId.of("Europe/London"));
        String title = "Morning Catchup";
        meetingDiary.bookMeeting(startTime, endTime, title);
        LocalTime meetingStartTime = meetingDiary.getMeetingStartInTimeZone(title, ZoneId.of("Europe/Paris"));
        assertEquals(startTime.plusHours(1).toLocalTime(), meetingStartTime);
    }

    @Test
    public void find_time_between_two_meetings() {
        ZonedDateTime baseTime = ZonedDateTime.of(LocalDate.of(2014, Month.MARCH, 23), LocalTime.of(9, 0), ZoneId.of("Europe/London"));
        String firstMeeting = "First Meeting";
        String secondMeeting = "Second Meeting";
        meetingDiary.bookMeeting(baseTime, baseTime.plusHours(2), firstMeeting);
        meetingDiary.bookMeeting(baseTime.plusHours(5), baseTime.plusHours(6), secondMeeting);
        long hours = meetingDiary.findFreeHoursBetweenMeetings(firstMeeting, secondMeeting);
        assertEquals(3l, hours);
    }

    @Test
    public void total_duration_of_all_meetings_in_a_month() {
        ZonedDateTime baseTime = ZonedDateTime.of(LocalDate.of(2014, Month.MARCH, 23), LocalTime.of(9, 0), ZoneId.of("Europe/London"));

        meetingDiary.bookMeeting(baseTime, baseTime.plusHours(1), "one");
        meetingDiary.bookMeeting(baseTime.plusHours(5), baseTime.plusHours(8).plusMinutes(30), "three.5");
        meetingDiary.bookMeeting(baseTime.plusHours(9), baseTime.plusHours(11), "two");
        meetingDiary.bookMeeting(baseTime.plusMonths(1), baseTime.plusMonths(1), "out of month");
        Duration totalInMonth = meetingDiary.getMeetingDurationInMonth(Month.MARCH);
        assertEquals(6, totalInMonth.toHours());
        assertEquals(30, totalInMonth.minusHours(totalInMonth.toHours()).toMinutes());

    }

    @Test
    public void format_time_of_meeting() {
        String formatString = "yyyy-MMM-dd hh:mm a";
        String expectedOutput = "2014-Mar-29 09:00 AM";
        ZonedDateTime expectedStartTime = ZonedDateTime.of(LocalDate.of(2014, 3, 29), LocalTime.of(9, 0), ZoneId.of("Europe/London"));
        ZonedDateTime expectedEndTime = ZonedDateTime.of(LocalDate.now(), LocalTime.of(10, 0), ZoneId.of("Europe/London"));
        String expectedTitle = "Morning Catchup";
        meetingDiary.bookMeeting(expectedStartTime, expectedEndTime, expectedTitle);

        String formattedStartTime = meetingDiary.formatMeetingStartTime(formatString, expectedTitle);
        assertEquals(expectedOutput, formattedStartTime);
    }

    @Test
    public void insert_meeting_from_string() throws Exception {
        String meetingToParse = "2014-Mar-29 09:00 AM GMT--2014-Mar-29 10:00 AM GMT--Jims Meeting";
        String formatString = "yyyy-MMM-dd hh:mm a zzz";
        Meeting meeting = meetingDiary.bookMeeting(formatString, meetingToParse);
        ZonedDateTime startTime = ZonedDateTime.of(LocalDate.of(2014, 3, 29), LocalTime.of(9, 0), ZoneId.of("GMT"));
        ZonedDateTime endTime = ZonedDateTime.of(LocalDate.of(2014, 3, 29), LocalTime.of(10, 0), ZoneId.of("GMT"));
        assertEquals(startTime, meeting.getStart());
        assertEquals(endTime, meeting.getEnd());
    }
}
