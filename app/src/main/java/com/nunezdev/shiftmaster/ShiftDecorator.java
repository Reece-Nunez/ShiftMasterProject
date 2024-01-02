package com.nunezdev.shiftmaster;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class ShiftDecorator implements DayViewDecorator {

    private final CalendarDay date;
    private final String shiftType;

    public ShiftDecorator(CalendarDay date, String shiftType) {
        this.date = date;
        this.shiftType = shiftType;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {

    }
}
