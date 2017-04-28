package main.java.by.gsu.epamlab.restclient;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import main.java.by.gsu.epamlab.bean.Chapter;
import main.java.by.gsu.epamlab.bean.Document;

public class RestClient {
    private static final String LINK = "http://localhost:8080/ApacheCXF_REST_Server/documents";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";
    private static HttpURLConnection connection = null;
    private static Scanner sc = null;
    private static OutputStream os;
    private static StringWriter upload = new StringWriter();
    private static StringWriter update = new StringWriter();
    private static int updateCount = 3;
    private static int deleteCount = 5;
    private static int chapterNumber = 3;

    public static void main(String[] args) {
        Chapter chapUddate = new Chapter(0, 777777777, 99999999);
        Document doc = createDoc();
        JAXBContext contextDoc;
        JAXBContext contextChap;
        try {
            contextDoc = JAXBContext.newInstance(Document.class);
            contextDoc.createMarshaller().marshal(doc, upload);
            contextChap = JAXBContext.newInstance(Chapter.class);
            contextChap.createMarshaller().marshal(chapUddate, update);
        } catch (JAXBException e) {
            System.err.println(e);
        }
        System.out.println("Document before send:\n" + upload.toString() + "\n___________________________________________________________________________________________\n");
        methodPOST(upload.toString());
        methodGET();
        for (int i = 0; i < updateCount; i++) {
            methodPUT(update.toString());
        }
        System.out.println("\n___________________________________________________________________________________________");
        methodGET();
        for (int i = 0; i < deleteCount; i++) {
            methodDELETE();
            methodGET();
        }
        methodPUT(update.toString());
        methodGET();
        methodDELETE();
        methodGET();
    }

    public static void methodPOST(String upload) {
        connection = getConnection();
        try {
            connection.setRequestMethod(POST);
            os = connection.getOutputStream();
            os.write(upload.getBytes());
            os.flush();
            System.out.println("POST --> " + connection.getResponseCode() + " " + connection.getResponseMessage() + "\n");
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            connection.disconnect();
        }
    }

    public static void methodGET() {
        connection = getConnection();
        String response;
        try {
            connection.setRequestMethod(GET);
            response = null;
            if (connection.getResponseCode() != 200) {
                sc = new Scanner(connection.getErrorStream());
                response = "Error!";
            } else {
                sc = new Scanner(connection.getInputStream());
                response = "Response:";
            }
            System.out.println("GET --> " + connection.getResponseCode() + " " + connection.getResponseMessage());
            System.out.println(response);
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine() + "\n___________________________________________________________________________________________\n\n");
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            sc.close();
            connection.disconnect();
        }
    }

    public static void methodPUT(String update) {
        connection = getConnection();
        try {
            connection.setRequestMethod(PUT);
            os = connection.getOutputStream();
            os.write(update.getBytes());
            os.flush();
            System.out.println("Update --> " + connection.getResponseCode() + " " + connection.getResponseMessage() + "\n");
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            connection.disconnect();
        }
    }

    public static void methodDELETE() {
        connection = getConnection();
        try {
            connection.setRequestMethod(DELETE);
            System.out.println("DELETE --> " + connection.getResponseCode() + " " + connection.getResponseMessage() + "\n");
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            connection.disconnect();
        }
    }

    public static HttpURLConnection getConnection() {
        try {
            connection = (HttpURLConnection) new URL(LINK).openConnection();
        } catch (IOException e) {
            System.err.println(e);
        }
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/xml");
        return connection;
    }

    public static Document createDoc() {
        List<Chapter> chapters = new ArrayList<>();
        for (int i = 0; i < chapterNumber; i++) {
            chapters.add(new Chapter(i, i * 2, i * 2 + 1));
        }
        return new Document(0, "doc1", chapters);
    }
}
