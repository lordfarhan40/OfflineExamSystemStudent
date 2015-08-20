package Model;

import Helper.FileWorker;

import java.io.File;

/**
 * Created by Farhan on 05-08-2015.
 */
public class AppInitialise {
    private final int STEP_NO_EXAM_LOADED = 1, STEP_PASSWORD_PROTECTED = 2, STEP_NOT_PASSWORD_PROTECTED = 3;
    private int step;
    private File file;

    public AppInitialise(File file) {
        this.file = file;
        step = STEP_NO_EXAM_LOADED;
        setStep();
    }

    public void setStep() {
        if (file == null)
            return;
        if (!file.isFile()) {
            step = STEP_NO_EXAM_LOADED;
            return;
        } else {
            String file_type = FileWorker.readFirst(file);
            if (file_type.contains("SetPassword=true")) {
                step = STEP_PASSWORD_PROTECTED;
                return;
            } else {
                if (file_type.contains("SetPassword=false")) {
                    step = STEP_NOT_PASSWORD_PROTECTED;
                    return;
                }
            }
        }
        step = STEP_NO_EXAM_LOADED;
        return;
    }

    public boolean isExamLoaded() {
        return !(step == STEP_NO_EXAM_LOADED);
    }

    public boolean isPasswordProtected() {
        return step == STEP_PASSWORD_PROTECTED;
    }

    public boolean isPasswordNotProtected() {
        return step == STEP_NOT_PASSWORD_PROTECTED;
    }

    public void loadExam(File f) {
        if (f != null && checkExam(f)) {
            String fileString = FileWorker.readText(f);
            FileWorker.writeText(file, fileString);
        }
        setStep();
    }

    public void unloadExam() {
        file.delete();
        setStep();
    }

    public boolean checkExam(File f) {
        String firstLine = FileWorker.readFirst(f);
        if (firstLine.contains("SetPassword=false")) {
            if (FileWorker.readFromFile(f, null) != null) {
                return true;
            }
        } else {
            if (firstLine.contains("SetPassword=true")) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return ("Step is " + step);
    }
}
