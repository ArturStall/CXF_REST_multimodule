package main.java.by.gsu.epamlab.rest;

import java.io.StringReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.json.JSONObject;

import main.java.by.gsu.epamlab.bean.Chapter;
import main.java.by.gsu.epamlab.bean.Document;

@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_JSON)
public class REST {
    private Document doc;

    @POST
    public final void addDocument(final String upload) {
        JAXBContext context;
        Unmarshaller unmarshaller;
        try {
            context = JAXBContext.newInstance(Document.class);
            unmarshaller = context.createUnmarshaller();
            doc = (Document) unmarshaller.unmarshal(new StringReader(upload));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @GET
    public final String getDocument() {
        return new JSONObject(doc).toString();
    }

    @PUT
    public final String updateDocument(final String update) {
        JAXBContext context;
        Unmarshaller unmarshaller;
        try {
            context = JAXBContext.newInstance(Document.class);
            unmarshaller = context.createUnmarshaller();
            Chapter updateChapter = (Chapter) unmarshaller.unmarshal(new StringReader(update));
            Chapter removeChapter = null;
            for (Chapter chapter : doc.getChapter()) {
                if (chapter.getId() == updateChapter.getId()) {
                    removeChapter = chapter;
                }
            }
            doc.getChapter().remove(removeChapter);
            doc.getChapter().add(updateChapter);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return new JSONObject(doc).toString();
    }

    @DELETE
    public final Response deleteFirstChapterDocument() {
        if (doc.getChapter().size() == 0) {
            return Response.status(403).type("text/plain").entity("There are no chapters in the document on the service").build();
        }
        doc.getChapter().remove(0);
        return Response.status(200).build();
    }
}
