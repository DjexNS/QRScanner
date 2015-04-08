package com.example.djex.qrscanner;

/**
 * Created by Djex on 07/04/2015.
 */

import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;


public class CalendarHelper
{
    public Intent createEntryObject(String titleText,
                                    String contentText,
                                    long beginTime,
                                    long endTime,
                                    String recurData,
                                    int reminderMinutes)
    {

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setType("vnd.android.cursor.item/event")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false) // just included for completeness
                .putExtra(Events.TITLE, titleText)
                .putExtra(Events.DESCRIPTION, contentText);

        if(recurData != null && !recurData.isEmpty())
        {
            intent.putExtra(Events.RRULE, recurData) ;
        }

        intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
        intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);

        return intent;

    }

}

