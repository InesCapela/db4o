package com.company;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.List;

    public class Main {

        public static String DB4OFILENAME = "C:\\Users\\maria\\IdeaProjects\\DB4O_2\\data\\db.txt";

        public static void main(String[] args) {

            ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DB4OFILENAME);
            try {
            // storeFirstPilot
            Pilot pilot1 = new Pilot("Michael Schumacher", 100);
            db.store(pilot1);
            System.out.println("Stored " + pilot1);

            // storeSecondPilot
            Pilot pilot2 = new Pilot("Rubens Barrichello", 99);
            db.store(pilot2);
            System.out.println("Stored " + pilot2);

            System.out.println("retrieveAllPilotQBE \n");
            retrieveAllPilotsQBE(db);

            System.out.println("retrieveAllPilots \n");
            retrieveAllPilots(db);

            List <Pilot> pilots = db.query(Pilot.class);

            System.out.println("retrievePilotByName\n");
            Pilot proto1 = new Pilot("Michael Schumacher", 0);
            ObjectSet result2 = db.queryByExample(proto1);
            listResult(result2);

            System.out.println("retrievePilotByExactPoints\n");
            Pilot proto2 = new Pilot(null, 100);
            ObjectSet result3 = db.queryByExample(proto2);
            listResult(result3);

            ObjectSet result4 = db
                    .queryByExample(new Pilot("Michael Schumacher", 0));
            Pilot found = (Pilot) result4.next();
            found.addPoints(11);
            db.store(found);
            System.out.println("Added 11 points for " + found);
            retrieveAllPilots(db);


            } finally {
                db.close();
            }
        }

        /**
         *
         * @param db ObjectContainer
         */
        public static void retrieveAllPilotsQBE(ObjectContainer db) {
            Pilot proto = new Pilot(null, 0);
            ObjectSet result = db.queryByExample(proto);
            listResult(result);
        }
        public static void retrievePilotByExactPoints(List<?> result){

        }

        /**
         *
         * @param result List
         */
        public static void listResult(List<?> result) {
            System.out.println(result.size());
            for (Object o : result) {
                System.out.println(o);
            }
        }

        /**
         *
         * @param db ObjectContainer
         */
        public static void retrieveAllPilots(ObjectContainer db) {
            Pilot proto = new Pilot(null, 0);
            ObjectSet result = db.queryByExample(proto);
            listResult(result);
        }
    }
