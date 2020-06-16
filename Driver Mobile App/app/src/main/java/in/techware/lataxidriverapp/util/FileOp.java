package in.techware.lataxidriverapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

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

import in.techware.lataxidriverapp.app.App;

//import com.google.android.gms.ads.identifier.AdvertisingIdClient;


public class FileOp {

    private static final String APP_FOLDER = "/LaTaxiDriver";
    private static final String TAG = "FileOp";
    private final Context context;
    private FileOutputStream fos;
    private OutputStreamWriter osw;
    private File file;
    private final String pathEx = Environment.getExternalStorageDirectory() + "/LaTaxiDriver/";
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
        context = App.getInstance().getApplicationContext();
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
            fos = context.openFileOutput("Debug.txt", Context.MODE_PRIVATE);
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

    public static String getProfilePhotoPath(Context context) {
        String path = context.getFilesDir() + "/images";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            Log.i(TAG, "getProfilePhotoPath: File Exists: " + file.exists());
        }
        path = context.getFilesDir() + "/images/profile_photo.jpg";
        return path;
    }

    public static String getDocumentPhotoPath(String document) {
        Context context = App.getInstance().getApplicationContext();
        String path = context.getFilesDir() + "/Documents";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            Log.i(TAG, "getDocumentPhotoPath: File Exists: " + file.exists());
        }
//        path = context.getFilesDir() + "/images/profile_photo.jpg";
        path = context.getFilesDir() + "/Documents/" + "Document_" + document + ".jpg";
        return path;
    }

    public static Bitmap getBitmapFromFile(String path) {
        Bitmap bitmap;

        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 6;
            // factor of downsizing the image
            FileInputStream inputStream = new FileInputStream(new File(path));

            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return bitmap;
    }

    public static void writeBitmapToFile(String originalPath, String savePath) {
        try {

            File file = new File(originalPath);

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file = new File(savePath);
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        } catch (Exception e) {
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
            } catch (Exception e) {
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
                    File.separator + APP_FOLDER + File.separator + "post_" + i + ".jpg";

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
                File.separator + APP_FOLDER + File.separator);
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
                    File.separator + APP_FOLDER + File.separator + "photo_" + i + ".jpg";

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
                File.separator + APP_FOLDER + File.separator);
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
