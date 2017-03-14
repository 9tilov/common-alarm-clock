package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MainGenerator {

    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.moggot.commonalarmclock.alarm");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            /* Use forward slashes if you're on macOS or Unix, i.e. "/app/src/main/java"  */
            new DaoGenerator().generateAll(schema, PROJECT_DIR + "/app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        Entity user = addUser(schema);
    }

    private static Entity addUser(final Schema schema) {
        Entity alarm = schema.addEntity("Alarm");
        alarm.addIdProperty().primaryKey().autoincrement();
        alarm.addDateProperty("date").notNull();
        alarm.addStringProperty("requestCodes").notNull();
        alarm.addBooleanProperty("isSnoozeEnable");
        alarm.addBooleanProperty("isMathEnable");
        alarm.addStringProperty("name");
        alarm.addStringProperty("musicPath").notNull();
        alarm.addIntProperty("musicType").notNull();
        alarm.addBooleanProperty("state").notNull();

        return alarm;
    }

}