package javanut6.ch08;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class NetExamples {

    private static NetExamples instance = null;

    // Constructor
    public NetExamples() {
        super();
    }

    private void run() throws IOException {
        URL url = new URL("http://127.0.0.1:1338/");
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get("output3.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void run2() throws IOException {
        URL url = new URL("http://www.google.com/");
        try {
            URLConnection conn = url.openConnection();

            String type = conn.getContentType();
            String encoding = conn.getContentEncoding();
            Date lastModified = new Date(conn.getLastModified());
            int len = conn.getContentLength();
            InputStream in = conn.getInputStream();
            System.out.println("Content-Type: " + type);
            System.out.println("Encoding: " + encoding);
            System.out.println("Last-Modified: " + lastModified);
            System.out.println("Length: " + len);
        } catch (IOException e) {
            // Handle exception
        }
    }

    private void run3() throws IOException {
        URL url = new URL("http://www.bbc.co.uk/search");

        String rawData = "q=java";
        String encodedData = URLEncoder.encode(rawData, "ASCII");
        String contentType = "application/x-www-form-urlencoded";

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));

        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write(encodedData.getBytes());

        int response = conn.getResponseCode();
        if (response == HttpURLConnection.HTTP_MOVED_PERM
                || response == HttpURLConnection.HTTP_MOVED_TEMP) {
            System.out.println("Moved to: " + conn.getHeaderField("Location"));
        } else {
            try (InputStream in = conn.getInputStream()) {
                Files.copy(in, Paths.get("bbc.txt"),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private void run4() throws IOException {
        String hostname = "www.jclarity.com";
        int port = 80;
        String filename = "/";

        try (Socket sock = new Socket(hostname, port);
                BufferedReader from = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter to = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));) {

            to.print("GET " + filename + " HTTP/1.1\r\nHost: " + hostname + "\r\n\r\n"); // The HTTP protocol
            to.flush();

            for (String l = null; (l = from.readLine()) != null;)
                System.out.println(l);
        }

    }

    private int run5(String host) throws IOException {
        URL url = new URL("htp://" + host + "/");
        try (InputStream in = url.openStream()) {
            return in.available();
        }
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        instance = new NetExamples();
        System.out.println("Bytes estimated: " + instance.run5("www.jclarity.com"));
        System.out.println("Bytes estimated: " + instance.run5("www.google.com"));
//		instance.run();
//		instance.run2();
//		instance.run3();
//		instance.run4();
    }

}
