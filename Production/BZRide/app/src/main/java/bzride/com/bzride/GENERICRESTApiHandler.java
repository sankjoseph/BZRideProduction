package bzride.com.bzride;

/**
 * Created by Santhosh.joseph on 29-06-2016.
 */
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by Santhosh.Joseph on 17-06-2016.
 */
public class GENERICRESTApiHandler {
    private static Reader mReader;
    private static String stringReadFromReader;


    public static String getData(String url) {
        InputStream in = null;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            connection.connect();
            Log.i(".RestApi", "Response code = " + connection.getResponseCode());
            if (connection.getResponseCode() == 200) {
                in = new BufferedInputStream(connection.getInputStream());
                mReader = new InputStreamReader(in);
                StringBuilder builder = new StringBuilder();
                int charsRead = -1;
                char[] chars = new char[100];
                do{
                    charsRead = mReader.read(chars,0,chars.length);
                    //if we have valid chars, append them to end of string.
                    if(charsRead>0)
                        builder.append(chars,0,charsRead);
                }while(charsRead>0);
                stringReadFromReader = builder.toString();
                System.out.println("String read = "+stringReadFromReader);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringReadFromReader;
    }

    /////postdata
    public static String postData(String url, String urlParams) {
        InputStream in = null;
        try {
            byte[] postData       = urlParams.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;

            URL httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();

            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setConnectTimeout(20000);
            connection.connect();

            try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
                wr.write( postData );
                wr.flush();
                wr.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if (connection.getResponseCode() == 200)
            {

            }


            in = new BufferedInputStream(connection.getInputStream());
            mReader = new InputStreamReader(in);

            StringBuilder builder = new StringBuilder();
            int charsRead = -1;
            char[] chars = new char[100];
            do{
                charsRead = mReader.read(chars,0,chars.length);
                //if we have valid chars, append them to end of string.
                if(charsRead>0)
                    builder.append(chars,0,charsRead);
            }while(charsRead>0);
            stringReadFromReader = builder.toString();
            System.out.println("String read = "+stringReadFromReader);
            // }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringReadFromReader;
    }

}
