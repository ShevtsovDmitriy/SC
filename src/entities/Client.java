package entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "client")
public class Client {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String fio;
    @DatabaseField
    private String phone;
    @DatabaseField
    private String url;
    @DatabaseField
    private String note;

    public Client() {
    }

    public Client(String fio) {
        this.fio = fio;
    }

    public Client(String fio, String phone) {
        this.fio = fio;
        this.phone = phone;
    }

    public Client(String fio, String phone, String url) {
        this.fio = fio;
        this.phone = phone;
        this.url = url;
    }

    public Client(String fio, String phone, String url, String note) {
        this.fio = fio;
        this.phone = phone;
        this.url = url;
        this.note = note;
    }

    public Client(int id, String fio, String phone, String url, String note) {
        this.id = id;
        this.fio = fio;
        this.phone = phone;
        this.url = url;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
