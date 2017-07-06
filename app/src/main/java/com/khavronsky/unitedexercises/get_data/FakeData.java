package com.khavronsky.unitedexercises.get_data;


import com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.ExerciseModel;
import com.khavronsky.unitedexercises.exercises_models.IModel;
import com.khavronsky.unitedexercises.exercises_models.ModelOfExercisePerformance;
import com.khavronsky.unitedexercises.exercises_models.PowerExerciseModel;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel.METHOD_CAL_PER_HOUR;
import static com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel.METHOD_MET_VALUES;
import static com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel.TYPE_NOT_SPECIFY;
import static com.khavronsky.unitedexercises.exercises_models.CardioExerciseModel.TYPE_SPECIFY;

public class FakeData {

    //region FIELDS
    private static List<ExerciseModel> sDefaultCatalog;

    private static List<ExerciseModel> sCustomCatalog;

    private static List<ModelOfExercisePerformance> sExercisePerformances;

    private static List<Long> allID = new ArrayList<>();
    //endregion

    static {
        createDefCatalog();
        createCustomCatalog();
        createExPerformanceCatalog();
        for (long l :
                allID) {

            Log.d("ALLID", "static initializer: " + l);
        }
    }

    private static void createDefCatalog() {
        Random random = new Random();
        sDefaultCatalog = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sDefaultCatalog.add(new CardioExerciseModel()
                    .setCountCalMethod(i % 2 == 0 ? METHOD_CAL_PER_HOUR
                            : METHOD_MET_VALUES)
                    .setIntensityType(
                            i % 3 == 0 ? TYPE_NOT_SPECIFY : TYPE_SPECIFY)
                    .setDefValue(i * random.nextInt(3) + 1)
                    .setLow(random.nextInt(5))
                    .setMiddle(random.nextInt(5) + 5)
                    .setHigh(random.nextInt(5) + 10)
                    .setCustomExercise(false)
                    .setTitle(i + " Default cardio exercise ")
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
        sDefaultCatalog.add(new PowerExerciseModel()
                .setSets(random.nextInt(5))
                .setRepeats(random.nextInt(5) + 10)
                .setWeight((random.nextInt(10) + 10) * 7)
                .setCustomExercise(false)
                .setTitle("Power exercise ")
        );
        for (IModel model :
                sDefaultCatalog) {
            setID(model);
        }
    }

    private static void createCustomCatalog() {
        Random random = new Random();
        sCustomCatalog = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sCustomCatalog.add(new CardioExerciseModel()
                    .setCountCalMethod(i % 2 == 0 ? METHOD_CAL_PER_HOUR
                            : METHOD_MET_VALUES)
                    .setIntensityType(
                            i % 3 == 0 ? TYPE_NOT_SPECIFY : TYPE_SPECIFY)
                    .setDefValue(i * random.nextInt(3))
                    .setLow(random.nextInt(5))
                    .setMiddle(random.nextInt(5) + 5)
                    .setHigh(random.nextInt(5) + 10)
                    .setCustomExercise(true)
                    .setTitle(i + " Custom cardio exercise ")
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
        for (IModel model :
                sCustomCatalog) {
            setID(model);
        }
    }

    private static void createExPerformanceCatalog() {
        Random random = new Random();
        sExercisePerformances = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int ran = random.nextInt(20);
            sExercisePerformances.add(new ModelOfExercisePerformance(sDefaultCatalog.get(ran))
                            .setStartTime(Calendar.getInstance().getTimeInMillis())
                            .setDuration(60)
                            .setNote("Note " + i)
            );
            ran = random.nextInt(20);
            sExercisePerformances.add(new ModelOfExercisePerformance(sCustomCatalog.get(ran))
                            .setStartTime(Calendar.getInstance().getTimeInMillis())
                            .setDuration(45)
                            .setNote("Enot " + i)
            );
        }
        setCurrentBurnedKcal();

        for (ModelOfExercisePerformance model :
                sExercisePerformances) {
            setID(model);
            model.setLastChangedTime(Calendar.getInstance().getTimeInMillis());
        }
    }

    private static void setCurrentBurnedKcal() {
        for (ModelOfExercisePerformance model :
                sExercisePerformances) {
            if (model.getExercise().getType() == ExerciseModel.ExerciseType.CARDIO) {
                CardioExerciseModel cardioModel = (CardioExerciseModel) model.getExercise();
                model.setCurrentKcalPerHour(cardioModel.getIntensityType() == TYPE_NOT_SPECIFY ?
                        cardioModel.getDefValue()
                        : cardioModel.getLow());
            }
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

    public static boolean addCustomExercise(ExerciseModel exerciseModel) {
        sCustomCatalog.add(exerciseModel);
        return true;
    }

    public static boolean addExPerformance(ModelOfExercisePerformance exercisePerformance) {
        sExercisePerformances.add(exercisePerformance);
        return true;
    }

    public static boolean editCustomExercise(ExerciseModel exerciseModel) {
        List<ExerciseModel> list = new ArrayList<>(sCustomCatalog);
        for (ExerciseModel model :
                list) {

            if (model.getId() == exerciseModel.getId()) {
                int a = sCustomCatalog.indexOf(model);
                sCustomCatalog.remove(a);
                sCustomCatalog.add(a, exerciseModel);
            }
        }
        return true;
    }

    public static boolean editExercisePerformance(ModelOfExercisePerformance exercisePerformance) {
        List<ModelOfExercisePerformance> list = new ArrayList<>(sExercisePerformances);
        for (ModelOfExercisePerformance model :
                list) {
            if (model.getId() == exercisePerformance.getId()) {
                int a = sExercisePerformances.indexOf(model);
                sExercisePerformances.remove(a);
                sExercisePerformances.add(a, exercisePerformance);
            }
        }
        return true;
    }

    public static boolean delCustomExercise(long id) {
        List<ExerciseModel> list = new ArrayList<>(sCustomCatalog);
        for (ExerciseModel model :
                list) {
            if (model.getId() == id) {
                sCustomCatalog.remove(model);
            }
        }
        return true;
    }

    public static boolean delExercisePerformance(long id) {
        List<ModelOfExercisePerformance> list = new ArrayList<>(sExercisePerformances);
        for (ModelOfExercisePerformance model :
                list) {
            if (model.getId() == id) {
                sExercisePerformances.remove(model);
            }
        }
        return true;
    }

    public static void setID(IModel model) {
        long max = 0;
        for (long l :
                allID) {
            if (l > max) {
                max = l;
            }
        }
        max++;
        allID.add(max);
        model.setId(max);
    }
}
