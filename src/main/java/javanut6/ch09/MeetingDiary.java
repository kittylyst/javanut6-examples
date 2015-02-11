package javanut6.ch09;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

public class MeetingDiary {

    private Set<Reminder> reminders;
    private Map<String, Meeting> meetings;

    public MeetingDiary() {
        reminders = new HashSet<>();
        meetings = new HashMap<>();
    }

    public Reminder createReminder(DayOfWeek dayOfWeek, int hour, int minute, String action) {
        LocalDateTime time = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.next(dayOfWeek)),
                LocalTime.of(hour, minute));
        Reminder reminder = new Reminder(action, time);
        reminders.add(reminder);
        return reminder;
    }

    public Set<Reminder> getRemindersFor(DayOfWeek dayOfWeek) {
        return reminders.stream()
                .filter(p -> p.getTime().getDayOfWeek() == dayOfWeek)
                .collect(Collectors.toSet());
    }

    public Set<Reminder> getRemindersOnDayBefore(DayOfWeek dayOfWeek, int hour, int minute) {
        return reminders.stream()
                .filter(p -> p.getTime().getDayOfWeek() == dayOfWeek)
                .filter(p -> p.getTime().toLocalTime().isBefore(LocalTime.of(hour, minute)))
                .collect(Collectors.toSet());
    }

    public Meeting bookMeeting(ZonedDateTime startTime, ZonedDateTime endTime, String title) {
        Meeting meeting = new Meeting();
        meeting.setStart(startTime);
        meeting.setEnd(endTime);
        meetings.put(title, meeting);
        return meeting;
    }

    public LocalTime getMeetingStartInTimeZone(String title, ZoneId zoneId) {
        Meeting meeting = meetings.get(title);
        if (meeting != null) {
            return meeting.getStart().withZoneSameInstant(zoneId).toLocalTime();
        }

        return null;
    }

    public long findFreeHoursBetweenMeetings(String firstMeetingTitle, String secondMeetingTitle) {
        Meeting firstMeeting = meetings.get(firstMeetingTitle);
        Meeting secondMeeting = meetings.get(secondMeetingTitle);

        Duration duration = Duration.between(firstMeeting.getEnd(), secondMeeting.getStart());
        return duration.toHours();
    }

    public Duration getMeetingDurationInMonth(Month month) {
        return meetings.values().stream()
                .filter(m -> month.equals(m.getStart().getMonth()))
                .map(m -> Duration.between(m.getStart(), m.getEnd()))
                .reduce(Duration.ofMinutes(0), (a, b) -> a.plus(b));
    }

    public String formatMeetingStartTime(String formatString, String title) {
        Meeting meeting = meetings.get(title);
        return meeting.getStart().format(DateTimeFormatter.ofPattern(formatString));
    }

    public Meeting bookMeeting(String formatString, String meetingToParse) throws Exception {
        String[] components = meetingToParse.split("--");
        if (components.length != 3) {
            throw new Exception("Could not parse String");
        }
        ZonedDateTime startTime = ZonedDateTime.parse(components[0], DateTimeFormatter.ofPattern(formatString));
        ZonedDateTime endTime = ZonedDateTime.parse(components[1], DateTimeFormatter.ofPattern(formatString));
        Meeting meeting = new Meeting();
        meeting.setStart(startTime);
        meeting.setEnd(endTime);
        meetings.put(components[2], meeting);
        return meeting;
    }
}
