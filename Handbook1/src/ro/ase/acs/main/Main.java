package ro.ase.acs.main;

import ro.ase.acs.models.Meeting;
import ro.ase.acs.models.Priority;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Meeting meet1 = new Meeting("Meet 1", 14, 15);
        meet1.setName("Brainstorming");
        System.out.println(meet1.getName());

        String[] participants = new String[] {"Ionel", "Gigel", "Vasile"};
        meet1.setParticipants(participants);

        System.out.println("Participants from meet 1 after set: ");
        String[] partipantsMeet1Get = meet1.getParticipants();
        for (String part:partipantsMeet1Get) {
            System.out.println(part);
        }
        partipantsMeet1Get[0] = "Alt Ionel";
        System.out.println("Ionel -> Alt Ionel");
        System.out.println("Participants from meet 1");
        String[] partipantsMeet1Get2 = meet1.getParticipants();
        for (String part:partipantsMeet1Get2) {
            System.out.println(part);
        }

        System.out.println();
        System.out.println();

        System.out.println("Clone: ");
        try {
            Meeting meet2 = (Meeting) meet1.clone();
            meet1.setName("Backlog");
            System.out.println("Meet 1 name: " + meet1.getName());
            System.out.println("Meet 2 name: " + meet2.getName());
        }
        catch (Exception e) {
            System.err.println(e);
        }

        System.out.println("Ionel in participants? " + meet1.checkParticipant("Ionel"));

        System.out.println();
        System.out.println();

        Meeting meetC1 = new Meeting("Meet 1", 13, 15);
        Meeting meetC2 = new Meeting("Meet 2", 12, 14);
        meetC2.setPriority(Priority.MEDIUM);
        String[] participant1 = new String[] {"Ionel", "Gigel", "Vasile"};
        meetC1.setParticipants(participant1);
        String[] participant2 = new String[] {"Ionel", "Cosmin", "Ionut", "Vasile", "Andrei"};
        meetC2.setParticipants(participant2);
        meetC1.concatenate(meetC2);
        System.out.println("Concatenated Meeting: ");
        System.out.println("Name: " + meetC1.getName());
        System.out.println("Start Time: " + meetC1.getStartTime());
        System.out.println("End Time: " + meetC1.getEndTime());
        System.out.println("Priority: " + meetC1.getPriority());
        String[] finalPart = meetC1.getParticipants();
        for(String part:finalPart) {
            System.out.println(part);
        }
    }
}