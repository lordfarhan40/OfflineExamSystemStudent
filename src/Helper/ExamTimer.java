package Helper;

import Model.TaskEnder;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Farhan on 07-08-2015.
 */
public class ExamTimer {
    TimerTask timerTask;
    private Timer timer;
    private long hours, minutes, seconds;
    private int rawSeconds;
    private String timeCalculated;
    private TaskEnder taskEnder;
    private Thread thread;

    public ExamTimer(int timeMinutes, Label label, TaskEnder taskEnder) {
        this.taskEnder = taskEnder;
        rawSeconds = timeMinutes * 60;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (subtractSecond()) {
                    timeCalculated = ExamTimer.this.toString();
                    Platform.runLater(() -> label.setText(timeCalculated));
                } else {
                    taskEnder.endTask();
                }
            }
        };
        setMins();
    }

    public void startTimer() {

        thread = new Thread() {
            @Override
            public void run() {
                timer.scheduleAtFixedRate(timerTask, 0, 1000);
            }
        };
        thread.start();
    }

    public void stopTimer() {
        timer.cancel();
    }

    private void setMins() {
        hours = rawSeconds / 3600;
        rawSeconds = rawSeconds % 3600;
        minutes = rawSeconds / 60;
        rawSeconds = rawSeconds % 60;
        seconds = rawSeconds;
        if (hours < 1) {
            timeCalculated = numberFormatter(minutes) + ":" + numberFormatter(seconds);
        } else {
            timeCalculated = numberFormatter(hours) + ":" + numberFormatter(minutes);
        }
    }

    public String toString() {
        String timeCalculated;
        if (hours < 1) {
            timeCalculated = numberFormatter(minutes) + ":" + numberFormatter(seconds);
        } else {
            timeCalculated = numberFormatter(hours) + ":" + numberFormatter(minutes);
        }
        return timeCalculated;
    }

    private boolean subtractSecond() {
        if (seconds >= 1) {
            seconds--;
            return true;
        }
        if (subtractMinute() == true) {
            --seconds;
            return true;
        } else
            return false;
    }

    private boolean subtractMinute() {
        if (minutes >= 1) {
            minutes--;
            seconds += 60;
            return true;
        }
        if (subtractHour() == true) {
            minutes--;
            seconds += 60;
            return true;
        } else
            return false;
    }

    private boolean subtractHour() {
        if (hours < 1)
            return false;
        else {
            hours = hours - 1;
            minutes = +60;
            return true;
        }

    }

    private String numberFormatter(long i) {
        if (i / 10 != 0) {
            return "" + i;
        } else
            return "0" + i;
    }
}
