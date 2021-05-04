package com.company;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import java.util.List;

public class seconf_f1 {
    public static String DB4OFILENAME = "C:\\Users\\maria\\IdeaProjects\\DB4O_2\\data\\db.txt";


    public static void main(String[] args) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded
                .newConfiguration(), DB4OFILENAME);
        try {
            storePilots(db);
            retrieveComplexSODA(db);
            retrieveComplexNQ(db);
            retrieveArbitraryCodeNQ(db);
            clearDatabase(db);
        } finally {
            db.close();
        }
    }

    /**
     *
     * @param db
     */
    public static void storePilots(ObjectContainer db) {
        db.store(new Pilot("Michael Schumacher", 100));
        db.store(new Pilot("Rubens Barrichello", 99));
    }

    /**
     *
     * @param db
     */
    public static void retrieveComplexSODA(ObjectContainer db) {
        Query query = db.query();
        query.constrain(Pilot.class);
        Query pointQuery = query.descend("points");
        query.descend("name").constrain("Rubens Barrichello")
                .or(pointQuery.constrain(99).greater()
                        .and(pointQuery.constrain(199).smaller()));
        ObjectSet result = query.execute();
        listResult(result);
    }

    /**
     *
     * @param db
     */
    public static void retrieveComplexNQ(ObjectContainer db) {
        List<Pilot> result = db.query(new Predicate<Pilot>() {
            public boolean match(Pilot pilot) {
                return pilot.getPoints() > 99
                        && pilot.getPoints() < 199
                        || pilot.getName().equals("Rubens Barrichello");
            }
        });
        listResult(result);
    }

    /**
     *
     * @param db
     */
    public static void retrieveArbitraryCodeNQ(ObjectContainer db) {
        final int[] points = {1, 100};
        List<Pilot> result = db.query(new Predicate<Pilot>() {
            public boolean match(Pilot pilot) {
                for (int point : points) {
                    if (pilot.getPoints() == point) {
                        return true;
                    }
                }
                return pilot.getName().startsWith("Rubens");
            }
        });
        listResult(result);
    }

    /**
     *
     * @param db
     */
    public static void clearDatabase(ObjectContainer db) {
        ObjectSet result = db.queryByExample(Pilot.class);
        while (result.hasNext()) {
            db.delete(result.next());
        }
    }

    /**
     * @param result List
     */
    public static void listResult(List<?> result) {
        System.out.println(result.size());
        for (Object o : result) {
            System.out.println(o);
        }
    }
}