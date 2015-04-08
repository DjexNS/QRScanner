package com.example.djex.qrscanner;

/**
 * Created by Djex on 07/04/2015.
 */

public class CalendarParser
{
    public static CalendarEntity calendarParse (String qrContent)
    {
        String[] arrayString = qrContent.split("%%");
        if(arrayString.length == 6)
        {
            String title = arrayString[0];
            String content = arrayString[1];
            String startDate = arrayString[2];
            String endDate = arrayString[3];
            String recurrence = arrayString[4];
            String countStr = arrayString[5];

            int count = 0;
            if(countStr != null && !countStr.isEmpty())
            {
                count = Integer.parseInt(countStr);
            }
            CalendarEntity calendarEntity = new CalendarEntity(title, content, Long.parseLong(startDate), Long.parseLong(endDate), recurrence, count);
            return calendarEntity;
        }
        else
        {
            return null;
        }
    }

}

