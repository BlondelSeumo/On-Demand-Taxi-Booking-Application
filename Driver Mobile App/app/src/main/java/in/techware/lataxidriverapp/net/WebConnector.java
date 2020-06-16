package in.techware.lataxidriverapp.net;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import in.techware.lataxidriverapp.config.Config;
import in.techware.lataxidriverapp.listeners.ProgressListener;
import in.techware.lataxidriverapp.net.utils.WSConstants;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;


public class WebConnector {


    private static final String TAG = "WebConnector";
    private final String boundary = "-------------" + System.currentTimeMillis();
    private static final String LINE_FEED = "\r\n";
    private static final String TWO_HYPHENS = "--";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private WebConnectorActionListener webConnectorActionListener;

    private final StringBuilder url;
    private String protocol;
    private final HashMap<String, String> params;
    private final JSONObject postData;
    private ArrayList<String> fileList;
    private int count = 0;
    private DataOutputStream dos;
    private OkHttpClient client = Config.getInstance().getOkHttpClient();

    public WebConnector(StringBuilder url, String protocol,
                        HashMap<String, String> params, JSONObject postData) {
        super();
        this.url = url;
        this.protocol = protocol;
        this.params = params;
        this.postData = postData;
        createServiceUrl();
        //createPostDataJSON();
    }

    public WebConnector(StringBuilder url, String protocol,
                        HashMap<String, String> params, JSONObject postData, ArrayList<String> fileList) {
        super();
        this.url = url;
        this.protocol = protocol;
        this.params = params;
        this.postData = postData;
        this.fileList = fileList;
        createServiceUrl();
        //createPostDataJSON();
    }

    public String connectToPOST_service() {
        //JSONObject jsonData=createPostDataJSON();

        String strResponse = "";

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            System.out.println(">>>>>>>>>url : " + url);
                /* forming th java.net.URL object */
            urlConnection = (HttpURLConnection) new URL(url.toString()).openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
//            urlConnection.setRequestProperty("User-Agent", AppConstants.USER_AGENT_CCAVENUE);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase(""))
//                urlConnection.setRequestProperty("Authorization", /*"Bearer " +*/ Config.getInstance().getAuthToken());
                urlConnection.setRequestProperty("Auth", /*"Bearer " +*/ Config.getInstance().getAuthToken());
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            String encodedString = new String(postData.toString().getBytes("UTF-8"), "UTF-8");
            Log.i(TAG, "connectToPOST_service: encoded String : ");
            out.write(encodedString);
            out.close();

            int statusCode = 0;
            try {
                urlConnection.connect();
                statusCode = urlConnection.getResponseCode();
            } catch (EOFException e1) {
                if (count < 5) {
                    urlConnection.disconnect();
                    count++;
                    String temp = connectToPOST_service();
                    if (temp != null && !temp.equals("")) {
                        return temp;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

                /* 200 represents HTTP OK */
            if (statusCode == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                strResponse = readStream(inputStream);
            } else {
                System.out.println(urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
                inputStream = new BufferedInputStream(urlConnection.getErrorStream());
                strResponse = readStream(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream)
                    inputStream.close();
            } catch (IOException e) {
            }
        }
        try {
            webConnectorActionListener.actionCompletedSuccessfully(strResponse);
        } catch (Exception e) {
        }
        System.out.println(">>>>>>>>>>Stringbuilder response: " + strResponse);
        return strResponse;

    }


    public String connectToMULTIPART_POST_service(String postName) {

        //JSONObject jsonData=createPostDataJSON();

        System.out.println(">>>>>>>>>url : " + url);

        StringBuilder stringBuilder = new StringBuilder();
        String boundary = "-------------" + System.currentTimeMillis();

        String strResponse = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            client = builder.build();
            Config.getInstance().setOkHttpClient(client);
        }

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (postData != null && !postData.toString().equalsIgnoreCase("")) {
            builder.addFormDataPart(postName, postData.toString());
            Iterator<String> keys = postData.keys();
            while (keys.hasNext()) {
                try {
                    String id = String.valueOf(keys.next());
                    builder.addFormDataPart(id, "" + postData.get(id));
                    System.out.println(id + " : " + postData.get(id));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase(""))
            builder.addFormDataPart("auth_token", Config.getInstance().getAuthToken());
        switch (postName) {
            case "document_update":
                if (fileList != null && fileList.size() > 0 && !fileList.isEmpty()) {
                    File file = new File(fileList.get(0));
                    if (file != null)
                        builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
                    /*for (int i = 1; i < fileList.size(); i++) {
                        file = new File(fileList.get(i));
                        if (file != null)
                            builder.addFormDataPart("photos[" + i + "][image]", file.getHeight(),
                                    RequestBody.create(MediaType.parse("image/jpeg"), file));
//                    builder.addBinaryBody("photos[" + i + "][image]", file, ContentType.create("image/jpeg"), file.getHeight());
//                            addFilePart("photos[" + i + "][image]", file);
                    }*/
                }
                break;

            case "profile_photo":

                if (fileList != null && fileList.size() > 0 && !fileList.isEmpty()) {
                    File file = new File(fileList.get(0));
                    if (file != null)
                        builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
                    /*for (int i = 1; i < fileList.size(); i++) {
                        file = new File(fileList.get(i));
                        if (file != null)
                            builder.addFormDataPart("photos[" + i + "][image]", file.getHeight(),
                                    RequestBody.create(MediaType.parse("image/jpeg"), file));
//                    builder.addBinaryBody("photos[" + i + "][image]", file, ContentType.create("image/jpeg"), file.getHeight());
//                            addFilePart("photos[" + i + "][image]", file);
                    }*/
                }
                break;

            case "profile_update":

                if (fileList != null && fileList.size() > 0 && !fileList.isEmpty()) {
                    File file = new File(fileList.get(0));
                    if (file != null)
                        builder.addFormDataPart("profile_photo", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file));
                    /*for (int i = 1; i < fileList.size(); i++) {
                        file = new File(fileList.get(i));
                        if (file != null)
                            builder.addFormDataPart("photos[" + i + "][image]", file.getHeight(),
                                    RequestBody.create(MediaType.parse("image/jpeg"), file));
//                    builder.addBinaryBody("photos[" + i + "][image]", file, ContentType.create("image/jpeg"), file.getHeight());
//                            addFilePart("photos[" + i + "][image]", file);
                    }*/
                }
                break;
            /*case "multiple_photo_upload":
                if (fileList != null && fileList.size() > 0 && !fileList.isEmpty()) {
                    File file;
                    for (int i = 0; i < fileList.size(); i++) {
                        file = new File(fileList.get(i));
                        if (file != null)
                            builder.addFormDataPart("photos[" + i + "]", file.getName(),
                                    RequestBody.create(MediaType.parse("image/jpeg"), file));
//                    builder.addBinaryBody("photos[" + i + "][image]", file, ContentType.create("image/jpeg"), file.getWeights());
//                            addFilePart("photos[" + i + "][image]", file);
                    }
                }
                break;*/
        }

        RequestBody requestBody = builder.build();
        Request.Builder rBuilder = new Request.Builder().url(url.toString())
                .addHeader("Accept", "application/json")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Content-type", "multipart/form-data; boundary=" + boundary)
                .post(requestBody);
        if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase(""))
//            rBuilder.addHeader("Authorization", /*"Bearer " +*/ Config.getInstance().getAuthToken());
            rBuilder.addHeader("Auth", /*"Bearer " +*/ Config.getInstance().getAuthToken());


        Request request = rBuilder.build();
        int statusCode = 0;
        try {
            Response response = client.newCall(request).execute();
            statusCode = response.code();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                strResponse = response.body().string();
            } else {
                strResponse = response.body().string();
            }
        } catch (EOFException e1) {
            if (count < 5) {
                count++;
                String temp = connectToMULTIPART_POST_service(postName);
                if (temp != null && !temp.equals("")) {
                    return temp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(">>>>>>>>>>Stringbuilder response: " + strResponse);
        return strResponse;
        // 200 represents HTTP OK


    }

    public String connectToDELETE_service() {
        //JSONObject jsonData=createPostDataJSON();


        String strResponse = "";

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            System.out.println(">>>>>>>>>url : " + url);
                /* forming th java.net.URL object */
            urlConnection = (HttpURLConnection) new URL(url.toString()).openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Accept", "application/json");
            if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase(""))
//                urlConnection.setRequestProperty("Authorization", /*"Bearer " +*/ Config.getInstance().getAuthToken());
                urlConnection.setRequestProperty("Auth", /*"Bearer " +*/ Config.getInstance().getAuthToken());
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
            if (statusCode == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                strResponse = readStream(inputStream);
            } else {
                System.out.println(urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
                inputStream = new BufferedInputStream(urlConnection.getErrorStream());
                strResponse = readStream(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream)
                    inputStream.close();
            } catch (IOException e) {
            }
        }
        System.out.println(">>>>>>>>>>Stringbuilder response: " + strResponse);
        return strResponse;


    }

    public String connectToPUT_service() {
        //JSONObject jsonData=createPostDataJSON();

        String strResponse = "";

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            System.out.println(">>>>>>>>>url : " + url);
                /* forming th java.net.URL object */
            urlConnection = (HttpURLConnection) new URL(url.toString()).openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            urlConnection.setRequestProperty("Accept", "application/json");
            if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase(""))
//                urlConnection.setRequestProperty("Authorization", /*"Bearer " +*/ Config.getInstance().getAuthToken());
                urlConnection.setRequestProperty("Auth", /*"Bearer " +*/ Config.getInstance().getAuthToken());

            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            String encodedString = new String(postData.toString().getBytes("UTF-8"), "UTF-8");
            out.write(encodedString);
            out.close();

            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
            if (statusCode == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                strResponse = readStream(inputStream);
            } else {
                System.out.println(urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
                inputStream = new BufferedInputStream(urlConnection.getErrorStream());
                strResponse = readStream(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream)
                    inputStream.close();
            } catch (IOException e) {
            }
        }
        System.out.println(">>>>>>>>>>Stringbuilder response: " + strResponse);
        return strResponse;

    }

    public String connectToMULTIPART_PUT_service(String postName) {
        //JSONObject jsonData=createPostDataJSON();

        System.out.println(">>>>>>>>>url : " + url);
        String strResponse = "";

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        StringBuilder stringBuilder = new StringBuilder();
        String boundary = "-------------" + System.currentTimeMillis();

        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            client = builder.build();
            Config.getInstance().setOkHttpClient(client);
        }

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);


        RequestBody requestBody = builder.build();
        Request.Builder rBuilder = new Request.Builder().url(url.toString())
                .addHeader("Accept", "application/json")
                .addHeader("Content-type", "multipart/form-data; boundary=" + boundary)
                .put(requestBody);
        if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase(""))
//            rBuilder.addHeader("Authorization", /*"Bearer " +*/ Config.getInstance().getAuthToken());
            rBuilder.addHeader("Auth", /*"Bearer " +*/ Config.getInstance().getAuthToken());
        Request request = rBuilder.build();
        int statusCode = 0;
        try {
            Response response = client.newCall(request).execute();
            statusCode = response.code();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                strResponse = response.body().string();
            } else {
                strResponse = response.body().string();
            }
        } catch (EOFException e1) {
            if (count < 5) {
                count++;
                String temp = connectToMULTIPART_PUT_service(postName);
                if (temp != null && !temp.equals("")) {
                    return temp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(">>>>>>>>>>Stringbuilder response: " + strResponse);
        return strResponse;

    }

    public String connectToGET_service() {

        System.out.println(">>>>>>>>>url : " + url);

        StringBuilder stringBuilder = new StringBuilder();
        String boundary = "-------------" + System.currentTimeMillis();

        String strResponse = "";

        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            client = builder.build();
            Config.getInstance().setOkHttpClient(client);
        }

        Request.Builder rBuilder = new Request.Builder().url(url.toString())
                .addHeader("Accept", "application/json")
//                .addHeader("Connection", "Keep-Alive")
                .addHeader("Content-type", "application/json");
        if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase(""))
//            rBuilder.addHeader("Authorization", /*"Bearer " +*/ Config.getInstance().getAuthToken());
            rBuilder.addHeader("Auth", /*"Bearer " +*/ Config.getInstance().getAuthToken());

        rBuilder.get();

        Request request = rBuilder.build();
        int statusCode = 0;
        try {
            Response response = client.newCall(request).execute();
            statusCode = response.code();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                strResponse = response.body().string();
            } else {
                strResponse = response.body().string();
            }
        } catch (EOFException e1) {
            if (count < 5) {
                count++;
                String temp = connectToGET_service();
                if (temp != null && !temp.equals("")) {
                    return temp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(">>>>>>>>>>Stringbuilder response: " + strResponse);
        return strResponse;
        // 200 represents HTTP OK

        /*InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            System.out.println(">>>>>>>>>url : " + url);
                *//* forming th java.net.URL object *//*
            urlConnection = (HttpURLConnection) new URL(url.toString()).openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase(""))
                urlConnection.setRequestProperty("Authorization", *//*"Bearer " +*//* Config.getInstance().getAuthToken());

            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();
                *//* 200 represents HTTP OK *//*
            if (statusCode == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                strResponse = readStream(inputStream);
            } else {
                System.out.println(urlConnection.getResponseCode() + " " + urlConnection.getResponseMessage());
                inputStream = new BufferedInputStream(urlConnection.getErrorStream());
                strResponse = readStream(inputStream);
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inputStream)
                    inputStream.close();
            } catch (IOException e) {
            }
        }
        System.out.println(">>>>>>>>>>Stringbuilder response: " + strResponse);
        return strResponse;*/


    }

    public String connectToDOWNLOAD_service(final ProgressListener progressListener) {

        //JSONObject jsonData=createPostDataJSON();

        System.out.println(">>>>>>>>>url : " + url);

        String strResponse = "";

        Request.Builder rBuilder = new Request.Builder().url(url.toString())
                .addHeader("Accept", "application/json")
                .addHeader("Connection", "Keep-Alive")
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "Mozilla/5.0 ( compatible ) ");
        if (Config.getInstance().getAuthToken() != null && !Config.getInstance().getAuthToken().equalsIgnoreCase(""))
//            rBuilder.addHeader("Authorization", /*"Bearer " +*/ Config.getInstance().getAuthToken());
            rBuilder.addHeader("Auth", /*"Bearer " +*/ Config.getInstance().getAuthToken());

        Request request = rBuilder.build();

        client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();

        int statusCode = 0;
        try {
            Response response = client.newCall(request).execute();
            statusCode = response.code();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                strResponse = response.body().string();
            } else {
                strResponse = response.body().string();
            }
        } catch (EOFException e1) {
            if (count <= 5) {
                count++;
                String temp = connectToDOWNLOAD_service(progressListener);
                if (temp != null && !temp.equals("")) {
                    return temp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(">>>>>>>>>>Stringbuilder response: " + strResponse);
        return strResponse;
        // 200 represents HTTP OK

    }


    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    private void createServiceUrl() {
        if (null == params) {
            return;
        }
        final Iterator<Entry<String, String>> it = params.entrySet().iterator();
        boolean isParam = false;
        while (it.hasNext()) {
            final Entry<String, String> mapEnt = it.next();
            url.append(mapEnt.getKey());
            url.append("=");
            try {
                url.append(URLEncoder.encode(mapEnt.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException | NullPointerException e) {
                e.printStackTrace();
            }
            url.append(WSConstants.AMPERSAND);
            isParam = true;
        }
        if (isParam) {
            url.deleteCharAt(url.length() - 1);
        }
    }

	/*private JSONObject createPostDataJSON() {
        if(null==postData){
			return null;
		}
		final Iterator<Entry<String, String>> it = postData.entrySet().iterator();
		JSONObject json = new JSONObject();
		while (it.hasNext()) {
			final Map.Entry<String, String> mapEnt = (Map.Entry<String, String>) it.next();
			try {
				json.put(mapEnt.getKey(),URLEncoder.encode(mapEnt.getHeight(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			} catch (JSONException e) {

				e.printStackTrace();
			}
		}
		return json;
	}*/

    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
            /* Close Stream */
            if (null != in) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void addFormField(String fieldName, String value) {
        try {
            dos.writeBytes(TWO_HYPHENS + boundary + LINE_FEED);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + LINE_FEED + LINE_FEED/*+ value + LINE_FEED*/);
            dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + LINE_FEED);
            dos.writeBytes(value + LINE_FEED);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addFormField(String fieldName, String value, String contentType) {
        try {

            dos.writeBytes(TWO_HYPHENS + boundary + LINE_FEED);
            //dos.writeBytes("Content-Type: " + contentType + LINE_FEED);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\"" + LINE_FEED + LINE_FEED/*+ value + LINE_FEED*/);
            dos.writeBytes("Content-Type: " + contentType + "; charset=UTF-8" + LINE_FEED);
            dos.writeBytes(value + LINE_FEED);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addFilePart(String fieldName, File uploadFile) {
        try {
            dos.writeBytes(TWO_HYPHENS + boundary + LINE_FEED);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + fieldName + "\";filename=\"" + uploadFile.getName() + "\"" + LINE_FEED);
            dos.writeBytes("Content-Transfer-Encoding: binary" + LINE_FEED);
            dos.writeBytes(LINE_FEED);

            FileInputStream fStream = new FileInputStream(uploadFile);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;

            while ((length = fStream.read(buffer)) != -1) {
                dos.write(buffer, 0, length);
            }
            dos.writeBytes(LINE_FEED);
            dos.writeBytes(TWO_HYPHENS + boundary + TWO_HYPHENS + LINE_FEED);
            /* close streams */
            fStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeaderField(String name, String value) {
        try {
            dos.writeBytes(name + ": " + value + LINE_FEED);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int getFormFieldLength(String fieldName, String value) {
        int length = 0;
        String builder = TWO_HYPHENS + boundary + LINE_FEED +
                "Content-Disposition: form-data; name=\"" + fieldName + "\"" + LINE_FEED + LINE_FEED +
                value + LINE_FEED;
        //            builder.append("Content-Type: text/plain; charset=UTF-8" + LINE_FEED);
        length = builder.length();
        return length;
    }

    private int getFormFieldLength(String fieldName, String value, String contentType) {
        int length = 0;
        String builder = TWO_HYPHENS + boundary + LINE_FEED +
                "Content-Type: " + contentType + LINE_FEED +
                "Content-Disposition: form-data; name=\"" + fieldName + "\"" + LINE_FEED + LINE_FEED +
                value + LINE_FEED;
        //            builder.append("Content-Type: text/plain; charset=UTF-8" + LINE_FEED);
        length = builder.length();
        return length;
    }

    private int getFilePartLength(String fieldName, File uploadFile) {
        int length = 0;
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(TWO_HYPHENS).append(boundary).append(LINE_FEED);
            builder.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\";filename=\"").append(uploadFile.getName()).append("\"").append(LINE_FEED);
            builder.append(LINE_FEED);

            FileInputStream fStream = new FileInputStream(uploadFile);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length1 = -1;

            while ((length1 = fStream.read(buffer)) != -1) {
                dos.write(buffer, 0, length1);
                builder.append(Arrays.toString(buffer));
            }
            builder.append(LINE_FEED);
            builder.append(TWO_HYPHENS).append(boundary).append(TWO_HYPHENS).append(LINE_FEED);
            /* close streams */
            fStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        length = builder.toString().length();
        return length;
    }

    public int getHeaderFieldLength(String name, String value) {
        int length = 0;
        length = (name + ": " + value + LINE_FEED).length();
        return length;
    }

    public void build() {
        try {
            dos.writeBytes(LINE_FEED);
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public interface WebConnectorActionListener {
        void actionCompletedSuccessfully(String response);

        void actionFailed();
    }

    public WebConnectorActionListener getWebConnectorActionListener() {
        return webConnectorActionListener;
    }

    public void setWebConnectorActionListener(WebConnectorActionListener webConnectorActionListener) {
        this.webConnectorActionListener = webConnectorActionListener;
    }

}
