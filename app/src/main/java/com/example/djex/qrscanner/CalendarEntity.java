package com.example.djex.qrscanner;

/**
 * Created by Djex on 07/04/2015.
 */

import java.util.Date;
import android.annotation.SuppressLint;

public class CalendarEntity
{
    private String title;
    private String content;
    private long startDate;
    private long endDate;
    private String recurrenceType;
    private int count;

    public CalendarEntity()
    {
    };

    public CalendarEntity(String title, String content, long startDate, long endDate, String recurrenceType, int count)
    {
        this.title=title;
        this.content=content;
        this.startDate=startDate;
        this.endDate=endDate;
        this.recurrenceType=recurrenceType;
        this.count=count;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getStartDate() {
        return startDate;
    }
    public String getStartDateString() {
        return new Date(startDate).toLocaleString();
    }
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
    public long getEndDate() {
        return endDate;
    }
    public String getEndDateString() {
        return new Date(endDate).toLocaleString();
    }
    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
    public String getRecurrenceType() {
        return recurrenceType;
    }
    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    @SuppressLint("DefaultLocale") public String getReccurenceString()
    {
        String recurString = "";
        if (!getRecurrenceType().equals("One time event"))
        {
            recurString += "FREQ=" + getRecurrenceType().toUpperCase() + ";COUNT=" + getCount();
        }
        return recurString;
    }

    @Override
    public String toString()
    {
        String retVal = "Title: " + getTitle() + "\nContent: " + getContent() +"\nStart date: " + getStartDateString() + "\nEnd date: " + getEndDateString() +
                "\nRecurrence: "+ getRecurrenceType();
        if(count != 0)
        {
            retVal+= "\nCount: "+ getCount();
        }
        return retVal;
    }
}


