package com.nunezdev.shiftmaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Calendar;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class ShiftCalendarActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_page);

        calendarView = findViewById(R.id.calendarView);
        Button btnDTeam = findViewById(R.id.btnDTeam);

        btnDTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populatedDTeamShifts();
            }
        });


        MaterialCalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // Format the date as a string
                String selectedDate = date.getDay() + "/" + (date.getMonth() + 1) + "/" + date.getYear();

                // Show a dialog with the selected date
                new AlertDialog.Builder(ShiftCalendarActivity.this)
                        .setTitle("Selected Date")
                        .setMessage("You selected: " + selectedDate)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void populatedDTeamShifts() {
        calendarView.removeDecorators();

        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 2); // January 2nd
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31); // December 31st

        LocalDate day = startDate;
        boolean isDayShift = true;

        while (!day.isAfter(endDate)) {
            int shiftLength = isDayShift ? 3 : 4; // Day shifts are 3 days, night shifts are 4 days
            for (int i = 0; i < shiftLength; i++) {
                String shiftType = isDayShift ? "D Team Days" : "D Team Nights";
                calendarView.addDecorator(new ShiftDecorator(CalendarDay.from(day), shiftType));
                day = day.plusDays(1);
            }

            // Calculate off days
            int offDays = isDayShift ? (shiftLength == 3 ? 1 : 3) : 7; // 1 or 3 days off after day shift, 7 days off after night shift
            day = day.plusDays(offDays);

            isDayShift = !isDayShift; // Toggle between day and night shifts
        }

        calendarView.invalidateDecorators(); // Refresh the calendar view to show decorators
    }
}
