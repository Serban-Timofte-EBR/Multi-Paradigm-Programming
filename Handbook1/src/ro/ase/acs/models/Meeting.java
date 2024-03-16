package ro.ase.acs.models;

public class Meeting implements Cloneable {
    private String name;
    private int startTime;
    private int endTime;
    private Priority priority;
    String[] participants = null;

    public Meeting() {
        this.name = "";
        this.startTime = 0;
        this.endTime = 0;
        this.priority = Priority.LOW;
    }

    public Meeting(String name, int startTime, int endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = Priority.LOW;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String[] getParticipants() {
        String[] copy = new String[participants.length];
        System.arraycopy(participants, 0, copy, 0, participants.length);
        return copy;
    }

    public void setParticipants(String[] participants) {
        this.participants = new String[participants.length];
        System.arraycopy(participants, 0, this.participants, 0, participants.length);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Meeting copy = (Meeting) super.clone();
        copy.name = this.name;
        copy.startTime = this.startTime;
        copy.endTime = this.endTime;
        copy.setParticipants(this.participants);
        return copy;
    }

    public boolean checkParticipant(String part) {
        for(String participant:this.participants) {
            if(participant.equals(part)) {
                return true;
            }
        }
        return false;
    }

    public void concatenate(Meeting meet) {
        this.name = this.name + "/" +meet.name;
        this.startTime = Math.min(this.startTime, meet.startTime);
        this.endTime = Math.max(this.endTime, meet.endTime);
        if(meet.priority == Priority.HIGH) {
            this.priority = Priority.HIGH;
        } else if (meet.priority == Priority.MEDIUM && this.priority == Priority.LOW) {
            this.priority = meet.priority;
        }
        for(String partMeet:meet.participants) {
            if(!checkParticipant(partMeet)) {
                String[] copy = new String[this.participants.length + 1];
                System.arraycopy(this.participants, 0, copy, 0, this.participants.length);
                copy[this.participants.length] = partMeet;
                this.setParticipants(copy);
            }
        }
    }
}
