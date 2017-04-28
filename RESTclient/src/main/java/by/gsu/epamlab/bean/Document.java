package main.java.by.gsu.epamlab.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Document {
    private int id;
    private String name;
    private List<Chapter> chapter;

    public Document() {
        super();
    }

    public Document(int id, String name, List<Chapter> chapter) {
        super();
        this.id = id;
        this.name = name;
        this.chapter = chapter;
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public List<Chapter> getChapter() {
        return chapter;
    }

    @XmlElement
    public void setChapter(List<Chapter> chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return "Document [id=" + id + ", name=" + name + ", chapters=" + chapter + "]";
    }
}
