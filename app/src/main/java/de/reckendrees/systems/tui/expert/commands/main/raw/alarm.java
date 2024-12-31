/**
 * Ref: https://developer.android.com/reference/android/provider/AlarmClock
 **/

package de.reckendrees.systems.tui.expert.commands.main.raw;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.AlarmClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

import de.reckendrees.systems.tui.expert.LauncherActivity;
import de.reckendrees.systems.tui.expert.R;
import de.reckendrees.systems.tui.expert.commands.CommandAbstraction;
import de.reckendrees.systems.tui.expert.commands.ExecutePack;
import de.reckendrees.systems.tui.expert.commands.main.MainPack;

public class alarm implements CommandAbstraction {
    @Override
    public String exec(ExecutePack pack) throws Exception {
        // Check for alarm setting permission
        if (ContextCompat.checkSelfPermission(pack.context, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) pack.context, new String[]{Manifest.permission.SET_ALARM}, LauncherActivity.COMMAND_REQUEST_PERMISSION);
            return pack.context.getString(R.string.output_waitingpermission);
        }

        ArrayList<String> args = pack.getList();
        if (args.size() < 1) {
            return "Alarm command requires at least hour and minute";
        }

        // Parse time input
        String timeInput = args.get(0);
        String[] timeParts = timeInput.split(":");

        if (timeParts.length != 2) {
            return "Invalid time format. Use HH:MM (24-hour format)";
        }

        int hour, minute;
        try {
            hour = Integer.parseInt(timeParts[0]);
            minute = Integer.parseInt(timeParts[1]);
        } catch (NumberFormatException e) {
            return "Invalid time format. Hours and minutes must be numbers";
        }

        // Validate time ranges
        if (hour < 0 || hour > 23) {
            return "Hour must be between 0 and 23";
        }
        if (minute < 0 || minute > 59) {
            return "Minute must be between 0 and 59";
        }

        // Set alarm title
        String alarmTitle = "Alarm from TUI";
        if (args.size() > 1) {
            alarmTitle = args.get(1);
        }

        // Optional: Add days of week
        int[] daysOfWeek = null;
        if (args.size() > 2) {
            try {
                daysOfWeek = parseDaysOfWeek(args.get(2));
            } catch (IllegalArgumentException e) {
                return e.getMessage();
            }
        }

        // Create alarm intent
        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minute)
                .putExtra(AlarmClock.EXTRA_MESSAGE, alarmTitle)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);

        // Add days of week if specified
        if (daysOfWeek != null) {
            alarmIntent.putExtra(AlarmClock.EXTRA_DAYS, daysOfWeek);
        }

        pack.context.startActivity(alarmIntent);
        return String.format("Alarm set for %02d:%02d - %s", hour, minute, alarmTitle);
    }

    // Helper method to parse days of week
    private int[] parseDaysOfWeek(String daysInput) {
        // Assumes input like "MON,WED,FRI" or specific format
        String[] days = daysInput.toUpperCase().split(",");
        int[] daysOfWeek = new int[days.length];

        for (int i = 0; i < days.length; i++) {
            switch (days[i]) {
                case "SUN": daysOfWeek[i] = 1; break;
                case "MON": daysOfWeek[i] = 2; break;
                case "TUE": daysOfWeek[i] = 3; break;
                case "WED": daysOfWeek[i] = 4; break;
                case "THU": daysOfWeek[i] = 5; break;
                case "FRI": daysOfWeek[i] = 6; break;
                case "SAT": daysOfWeek[i] = 7; break;
                default:
                    throw new IllegalArgumentException("Invalid day: " + days[i] +
                            ". Use SUN, MON, TUE, WED, THU, FRI, SAT");
            }
        }

        return daysOfWeek;
    }

    @Override
    public int[] argType() {
        return new int[] {CommandAbstraction.TEXTLIST};
    }

    @Override
    public int priority() {
        return 5;
    }

    @Override
    public int helpRes() {
        return R.string.help_alarm;
    }

    @Override
    public String onArgNotFound(ExecutePack pack, int indexNotFound) {
        MainPack info = (MainPack) pack;
        return info.res.getString(R.string.output_appnotfound);
    }

    @Override
    public String onNotArgEnough(ExecutePack pack, int nArgs) {
        MainPack info = (MainPack) pack;
        return info.res.getString(helpRes());
    }
}