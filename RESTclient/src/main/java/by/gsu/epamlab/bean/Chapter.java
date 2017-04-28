package main.java.by.gsu.epamlab.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Chapter {
    private int id;
    private int chapterNumber;
    private int numberPage;

    public Chapter() {
        super();
    }

    public Chapter(int id, int chapterNumber, int numberPage) {
        super();
        this.id = id;
        this.chapterNumber = chapterNumber;
        this.numberPage = numberPage;
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    @XmlElement
    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public int getNumberPage() {
        return numberPage;
    }

    @XmlElement
    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Chapter other = (Chapter) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Chapter [id=" + id + ", chapterNumber=" + chapterNumber + ", numberPage=" + numberPage + "]";
    }
}
