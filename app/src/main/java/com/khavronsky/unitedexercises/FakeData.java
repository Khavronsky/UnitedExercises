package com.khavronsky.unitedexercises;


import com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel.TYPE_NOT_SPECIFY;

public class FakeData {

    private static List<ExerciseModel> sDefaultCatalog;

    private static List<ExerciseModel> sCustomCatalog;

    private static List<ModelOfExercisePerformance> sExercisePerformances;

    static {
        createDefCatalog();
        createCustomCatalog();
        createExPerformanceCatalog();
    }

    static void createDefCatalog() {
        Random random = new Random();
        sDefaultCatalog = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sDefaultCatalog.add(new CardioExerciseModel()
                    .setCountCalMethod(i % 2 == 0 ? CardioExerciseModel.METHOD_CAL_PER_HOUR
                            : CardioExerciseModel.METHOD_MET_VALUES)
                    .setIntensityType(
                            i % 3 == 0 ? TYPE_NOT_SPECIFY : CardioExerciseModel.TYPE_SPECIFY)
                    .setDefValue(i * random.nextInt(3))
                    .setLow(random.nextInt(5))
                    .setMiddle(random.nextInt(5) + 5)
                    .setHigh(random.nextInt(5) + 10)
                    .setCustomExercise(false)
                    .setTitle("Default cardio exercise " + i)
            );
        }
        for (int i = 0; i < 10; i++) {
            sDefaultCatalog.add(new PowerExerciseModel()
                    .setSets(random.nextInt(5))
                    .setRepeats(random.nextInt(5) + 10)
                    .setWeight((random.nextInt(10) + 10) * i)
                    .setCustomExercise(false)
                    .setTitle("Default power exercise " + (i + 10))
            );
        }
    }

    static void createCustomCatalog() {
        Random random = new Random();
        sCustomCatalog = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sCustomCatalog.add(new CardioExerciseModel()
                    .setCountCalMethod(i % 2 == 0 ? CardioExerciseModel.METHOD_CAL_PER_HOUR
                            : CardioExerciseModel.METHOD_MET_VALUES)
                    .setIntensityType(
                            i % 3 == 0 ? TYPE_NOT_SPECIFY : CardioExerciseModel.TYPE_SPECIFY)
                    .setDefValue(i * random.nextInt(3))
                    .setLow(random.nextInt(5))
                    .setMiddle(random.nextInt(5) + 5)
                    .setHigh(random.nextInt(5) + 10)
                    .setCustomExercise(true)
                    .setTitle("Custom cardio exercise " + i)
            );
        }
        for (int i = 0; i < 10; i++) {
            sCustomCatalog.add(new PowerExerciseModel()
                    .setSets(random.nextInt(5))
                    .setRepeats(random.nextInt(5) + 10)
                    .setWeight((random.nextInt(10) + 10) * i)
                    .setCustomExercise(true)
                    .setTitle("Custom power exercise " + (i + 10))
            );
        }
    }

    static void createExPerformanceCatalog() {
        Random random = new Random();
        sExercisePerformances = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int ran = random.nextInt(20);
            sExercisePerformances.add(new ModelOfExercisePerformance(sDefaultCatalog.get(ran))
                    .setStartTime(Calendar.getInstance().getTimeInMillis())
                    .setDuration(60)
                    .setNote("Note " + i)
                    .setCurrentKcalPerHour(((CardioExerciseModel)sDefaultCatalog.get(ran)).getIntensityType() ==
                            TYPE_NOT_SPECIFY? ((CardioExerciseModel)sDefaultCatalog.get(ran)).getDefValue(): (
                                    (CardioExerciseModel)sDefaultCatalog.get(ran)).getLow())
            );
            ran = random.nextInt(20);
            sExercisePerformances.add(new ModelOfExercisePerformance(sCustomCatalog.get(ran))
                    .setStartTime(Calendar.getInstance().getTimeInMillis())
                    .setDuration(45)
                    .setNote("Enot " + i)
                    .setCurrentKcalPerHour(((CardioExerciseModel)sDefaultCatalog.get(ran)).getIntensityType() ==
                            TYPE_NOT_SPECIFY? ((CardioExerciseModel)sDefaultCatalog.get(ran)).getDefValue(): (
                            (CardioExerciseModel)sDefaultCatalog.get(ran)).getLow())
            );
        }
    }

    public static List<ExerciseModel> getDefaultCatalog() {

        return sDefaultCatalog;
    }

    public static List<ExerciseModel> getCustomCatalog() {
        return sCustomCatalog;
    }

    public static List<ModelOfExercisePerformance> getExercisePerformances() {
        return sExercisePerformances;
    }
}
