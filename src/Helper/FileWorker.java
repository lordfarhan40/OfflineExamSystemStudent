package Helper;

import Model.AnswerSheet;
import Model.MainExam;
import com.google.gson.Gson;

import java.io.*;

/**
 * Created by Farhan on 05-08-2015.
 */
public class FileWorker {
    public static String PASSWORD_PROTECTED = "SetPassword=true";
    public static String NOT_PASSWORD_PROTECTED = "SetPassword=false";

    //Read text from file, under Test
    public static MainExam readFromFile(File f, String password) {
        try {
            Gson gson = new Gson();
            FileInputStream fin = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String passwordChecker = reader.readLine();
            if (passwordChecker.equals(PASSWORD_PROTECTED)) {
                String appender = new String();
                StringBuffer stringBuffer = new StringBuffer();
                while ((appender = reader.readLine()) != null) {
                    stringBuffer.append(appender);
                }
                String decrypted = EncryptDecrypter.decrypt(stringBuffer.toString(), password);
                reader.close();
                return gson.fromJson(decrypted, MainExam.class);
            } else {
                String appender = new String();
                StringBuffer stringBuffer = new StringBuffer();
                while ((appender = reader.readLine()) != null) {
                    stringBuffer.append(appender);
                }
                reader.close();
                return gson.fromJson(stringBuffer.toString(), MainExam.class);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String readFirst(File f) {
        if (f.exists()) {
            try {
                FileInputStream fin = new FileInputStream(f);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
                String string = reader.readLine();
                reader.close();
                return string;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String readText(File f) {
        if (f.exists()) {
            try {
                FileInputStream fin = new FileInputStream(f);
                byte[] readBytes = new byte[fin.available()];
                fin.read(readBytes);
                fin.close();
                return new String(readBytes);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }

    public static void writeText(File f, String args) {
        try {
            if (f.isFile())
                f.delete();
            FileOutputStream fout = new FileOutputStream(f);
            fout.write(args.getBytes());
            fout.close();
        } catch (Exception e) {
            System.out.println("File cannot be written");
            e.printStackTrace();
        }
    }

    //Write the answer sheet that gets evaluated
    public static void writeAnswerSheet(File f, AnswerSheet answerSheet) {
        Gson gson = new Gson();
        String gsonAnswer = gson.toJson(answerSheet, AnswerSheet.class);
        writeText(f, gsonAnswer);
    }


    //Write the Students copyh of answer sheet
    public static void writeAnswerCopy(File f, MainExam mainExam, AnswerSheet answerSheet) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            String append = "";
            int size = mainExam.getQuestionBank().size();
            for (int index = 0; index < size; ++index) {
                append += "\n";
                append += mainExam.getQuestionBank().get(index).getQuestion();
                append += "\n";
                append += "You selected: ";
                if (answerSheet.getAnswer(index) != -1) {
                    append += mainExam.getQuestionBank().get(index).getOptions().get(answerSheet.getAnswer(index));
                    append += "\n";
                } else {
                    append += "Left Unanswered\n";
                }
                stringBuffer.append(append);
                append = "";
            }
            FileOutputStream fout = new FileOutputStream(f);
            fout.write(stringBuffer.toString().getBytes());
            fout.close();
        } catch (Exception e) {
            System.out.println("File cannot be written");
            e.printStackTrace();
        }
    }
}
