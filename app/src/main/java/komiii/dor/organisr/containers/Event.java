package komiii.dor.organisr.containers;

import java.sql.Date;
import java.sql.Time;

public class Event {

    public int id;
    public String name;
    public Date date;
    public Time hour;
    public int duration;
    public int repeatence;
    public String repeatence_unit;
    public int urgency;
    public String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRepeatence() {
        return repeatence;
    }

    public void setRepeatence(int repeatence) {
        this.repeatence = repeatence;
    }

    public String getRepeatenceUnit() {return repeatence_unit;}

    public void setRepeatenceUnit(String repeatence_unit) {this.repeatence_unit = repeatence_unit; }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
