package com.thoughtworks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConferenceManagement
{

    int [] talkListArray;
    int [] sessionCount;

    int numberOfSession;

    int numberOfTalks;

    int theCounter;
    int talkInQueue;



    public void setTalksInArray(List<Integer> talkList)
    {
        talkListArray = new int[talkList.size()];

        for (int index =0;index<talkList.size();index++) {
            talkListArray[index] = talkList.get(index);
        }

        numberOfTalks = talkList.size();
    }

    public void setSessionTimeLimits(int morningSessionTimeLimit, int eveningSessionTimeLimit)
    {
        sessionCount = new int[2];
        sessionCount[0] = morningSessionTimeLimit;
        sessionCount[1] = eveningSessionTimeLimit;
        numberOfSession = sessionCount.length;
    }

    public void allocateTalksToSessionInFirstFitManner(int counter, int talkIndex,List<String> talkList)
    {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        theCounter = counter;
        talkIndex = talkIndex;
        try{
            String startTimeOfFirstSessionOfTrack = "09:00 AM";
            String startTimeOfSecondSessionOfTrack = "01:00 PM";
            Date startTimeOfSession = dateFormat.parse(startTimeOfFirstSessionOfTrack);
            gregorianCalendar.setTime(startTimeOfSession);
        }catch (ParseException parseException){

        }

        do {
            if (talkListArray[talkIndex] > sessionCount[theCounter-1])
            {
                theCounter += 1;
            }
            else
            {



                // System.out.println("----------------------------------");
                System.out.println(dateFormat.format(gregorianCalendar.getTime()) + " " + talkList.get(talkIndex) + " " + theCounter);
                gregorianCalendar.add(Calendar.MINUTE, talkListArray[talkIndex]);
                sessionCount[theCounter - 1] = (sessionCount[theCounter - 1] - talkListArray[talkIndex]);
                //System.out.println("The time of Session " + theCounter + " is now " + sessionCount[theCounter-1]);
                theCounter = 1;
                talkIndex += 1;

            }

        }   while (theCounter <= numberOfSession && talkIndex < numberOfTalks);

        //System.out.println("----------------------------------");
        talkInQueue = talkIndex;
        List<Integer>  waitingList = new ArrayList<Integer>();
        for(int index=talkIndex;index<numberOfTalks;index++) {
            waitingList.add(talkListArray[talkIndex]);
            talkIndex++;
        }
        //  System.out.print(waitingList);
    }

    public static void main(String [] args)

    {
        int track = 1;
        ConferenceManagement conferenceManagement = new ConferenceManagement();
        List<String> talkList = new ArrayList<String>();
        talkList.add("Writing Fast Tests Against Enterprise Rails 60min");   //1
        talkList.add("Overdoing it in Python 45min");                        //2
        talkList.add("Lua for the Masses 30min");                            //3
        talkList.add("Ruby Errors from Mismatched Gem Versions 45min");      //4
        talkList.add("Common Ruby Errors 45min");                            //5
        talkList.add("Rails for Python Developers lightning");               //6
        talkList.add("Communicating Over Distance 60min");                   //7
        talkList.add("Accounting-Driven Development 45min");                 //8
        talkList.add("Woah 30min");                                          //9
        talkList.add("Sit Down and Write 30min");                            //10
        talkList.add("Pair Programming vs Noise 45min");                     //11
        talkList.add("Rails Magic 60min");                                   //12
        talkList.add("Ruby on Rails: Why We Should Move On 60min");          //13
        talkList.add("Clojure Ate Scala (on my project) 45min");             //14
        talkList.add("Programming in the Boondocks of Seattle 30min");       //15
        talkList.add("Ruby vs. Clojure for Back-End Development 30min");     //16
        talkList.add("Ruby on Rails Legacy App Maintenance 60min");          //17
        talkList.add("A World Without HackerNews 30min");                    //18
        talkList.add("User Interface CSS in Rails Apps 30min");              //19

        for (int index=0;index<talkList.size();index++) {
            if(talkList.get(index).contains("lightning")){
                String newTalk = talkList.get(index).concat(" 5min");
                talkList.set(index,newTalk);
            }
        }

        List<Integer> talkDurationList = new ArrayList<Integer>();
        for (int index=0;index<talkList.size();index++) {
            String newTalk = talkList.get(index).replaceAll("[^0-9]+", " ");
            int durationOfTalk = Integer.parseInt(newTalk.trim());
            talkDurationList.add(durationOfTalk);
        }

        for (int index=0;index<talkList.size();index++) {
            if (talkList.get(index).contains("lightning")) {
                String newTalkWithRemoved5min = talkList.get(index).replace(" 5min", "");
                talkList.set(index, newTalkWithRemoved5min);
            }
        }

        conferenceManagement.setTalksInArray(talkDurationList);
        conferenceManagement.setSessionTimeLimits(180, 240);
        System.out.println("Track :" + track);
        conferenceManagement.allocateTalksToSessionInFirstFitManner(1, 0,talkList);

        if (conferenceManagement.talkInQueue < conferenceManagement.numberOfTalks) {
            conferenceManagement.setSessionTimeLimits(180, 240);
            track++;
            System.out.println("--------------------------------------------------------");
            System.out.println("Track :" +track);
            conferenceManagement.allocateTalksToSessionInFirstFitManner(1, (conferenceManagement.talkInQueue),talkList);
        }

    }


}