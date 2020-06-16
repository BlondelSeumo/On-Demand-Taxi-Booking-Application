package in.techware.lataxicustomer.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;

//import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class FileOp extends Activity {

    private static final String APP_TEMP_FOLDER = "/LaTaxi";
    private final Context context;
    private FileOutputStream fos;
    private OutputStreamWriter osw;
    private File file;
    private final String pathEx = Environment.getExternalStorageDirectory() + "/LaTaxi/";
    private String pathSP;
    private FileInputStream fis;
    private InputStreamReader isr;
    private BufferedReader br;
    private ObjectOutputStream oos;


    public FileOp(Context x) {
        context = x;
        createProjectFolder();
    }

    public FileOp() {
        context = getApplicationContext();
        createProjectFolder();
    }

    private void createProjectFolder() {

        if (Environment.getExternalStorageState() != null) {
            file = new File(pathEx);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    public boolean deleteDebug() {
        file = new File(pathEx + "Debug.txt");
        return file.delete();
    }

    public String readDebug() {
        String result = "";
        try {
            fis = context.openFileInput("Debug.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (String) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void writeDebug(String debug) {
        try {
            String temp = readDebug();
            fos = context.openFileOutput("Debug.txt", MODE_WORLD_READABLE);
            osw = new OutputStreamWriter(fos);
            osw.append(temp).append("\n\n").append(debug);
            //			osw.write(today);
            osw.flush();
            osw.close();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>DEBUG FILE WRITTEN>>>>>>>>>>>>>>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Environment.getExternalStorageState() != null) {

            file = new File(pathEx + "Debug.txt");
            File fileInternal = new File(context.getFilesDir() + "/" + "Debug.txt");
            copyFile(fileInternal, file);
            fileInternal.delete();
        }

    }


    private static void copyFile(File src, File dst) {
        try {
            FileChannel inChannel = new FileInputStream(src).getChannel();
            FileChannel outChannel = new FileOutputStream(dst).getChannel();
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            } finally {
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void writeBitmapToFile(String filename, Bitmap bitmap) {
        System.out.println("SAVED FILE : " + filename);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                bitmap.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPostID() {

        String imagePathTemp = "";
        boolean flag = false;
        int i = 0;
        for (i = 0; i < getPostImagesCount(); i++) {
            imagePathTemp = Environment.getExternalStorageDirectory() +
                    File.separator + APP_TEMP_FOLDER + File.separator + "post_" + i + ".jpg";

            if (!new File(imagePathTemp).exists()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            return String.valueOf(i);
        } else {
            return String.valueOf(getPostImagesCount());
        }
    }

    private static int getPostImagesCount() {

        String[] filesIn;
        File dir = new File(Environment.getExternalStorageDirectory() +
                File.separator + APP_TEMP_FOLDER + File.separator);
        filesIn = dir.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }

        });
        int countIn;
        try {
            countIn = filesIn.length;
        } catch (Exception e1) {
            countIn = 0;
            e1.printStackTrace();
        }
        return countIn;
    }

    public static String getPhotoID() {

        String imagePathTemp = "";
        boolean flag = false;
        int i = 0;
        for (i = 0; i < getPhotoCount(); i++) {
            imagePathTemp = Environment.getExternalStorageDirectory() +
                    File.separator + APP_TEMP_FOLDER + File.separator + "photo_" + i + ".jpg";

            if (!new File(imagePathTemp).exists()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            return String.valueOf(i);
        } else {
            return String.valueOf(getPhotoCount());
        }
    }

    private static int getPhotoCount() {

        String[] filesIn;
        File dir = new File(Environment.getExternalStorageDirectory() +
                File.separator + APP_TEMP_FOLDER + File.separator);
        filesIn = dir.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }

        });
        int countIn;
        try {
            countIn = filesIn.length;
        } catch (Exception e1) {
            countIn = 0;
            e1.printStackTrace();
        }
        return countIn;
    }


//    Hashing Function

    public boolean checkSPHash() {

        pathSP = context.getFilesDir() + "/shared_prefs/" + AppConstants.PREFERENCE_NAME_SESSION + ".xml";

        String strSP = getSPHash();
        String savedHash = readHash();

        return !(strSP == null || strSP.equals("") || savedHash == null || savedHash.equals("")) && strSP.equals(savedHash);
    }

    private String getSPHash() {

        pathSP = context.getFilesDir().getParent() + "/shared_prefs/" + AppConstants.PREFERENCE_NAME_SESSION + ".xml";
//	pathSP="/data/data/com.abstractbc.wayay/shared_prefs/"+AppConstants.PREFERENCE_NAME_SESSION+".xml";

        String strSP = "";
        StringBuilder stringBuilder;
        try {
            fis = new FileInputStream(pathSP);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            stringBuilder = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                stringBuilder.append(temp);
            }
            fis.close();
            strSP = stringBuilder.toString();

            String android_id = "";
            try {
                android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
//                try {
//                    android_id = AdvertisingIdClient.getAdvertisingIdInfo(context.getApplicationContext()).getId();
                    android_id = "";
                    System.out.println("ANDROID_ID NOT AVAILABLE");
//                } catch (IOException|
//                        GooglePlayServicesNotAvailableException |
//                        GooglePlayServicesRepairableException | IllegalStateException e1){
//                    e1.printStackTrace();
//                }
                e.printStackTrace();
            }
            String device_id = null;
            try {
                TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                device_id = tManager.getDeviceId();
            } catch (Exception e) {
                device_id = "";
                System.out.println("DEVICE_ID NOT AVAILABLE OR DISABLED");
            }

            String str1 = Build.BOARD + Build.BRAND + Build.CPU_ABI + Build.DEVICE +
                    Build.DISPLAY + Build.FINGERPRINT + Build.HOST + Build.ID + Build.MANUFACTURER
                    + Build.MODEL + Build.PRODUCT + Build.TAGS + Build.TYPE + Build.USER;
            String key2 = makeSHA1Hash(strSP + str1 + device_id + android_id);

            return key2;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    private String makeSHA1Hash(String input) {
        MessageDigest md;
        String hexStr = "";
        try {
            md = MessageDigest.getInstance("SHA1");

            md.reset();
            byte[] buffer = input.getBytes();
            md.update(buffer);
            byte[] digest = md.digest();

            for (byte aDigest : digest) {
                hexStr += Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1);
            }
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return hexStr;
    }

    private String readHash() {
        String strHash = "";
        try {
            fis = context.openFileInput("Hash");
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            strHash = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strHash;
    }

    public void writeHash() {
        String strHash = getSPHash();
        try {
            fos = context.openFileOutput("Hash", context.MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            osw.write(strHash);
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
