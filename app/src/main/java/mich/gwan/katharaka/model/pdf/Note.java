package mich.gwan.katharaka.model.pdf;

public class Note {

    private int id;
    private String note;
    private String timestamp;
    private String name;
    private int paid;
    private int unpaid;
    private int total;

    public Note() {
    }

    public Note(int id, String note, String timestamp, String name, int paid, int unpaid, int total) {
        this.id = id;
        this.note = note;
        this.timestamp = timestamp;
        this.name = name;
        this.paid = paid;
        this.unpaid = unpaid;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){return name;}
    public void setPaid(int paid){
        this.paid = paid;
    }
    public int getPaid(){return paid;}

    public void setUnpaid(int unpaid) {
        this.unpaid = unpaid;
    }
    public int getUnpaid(){return unpaid;}

    public void setTotal(int total) {
        this.total = total;
    }
    public int getTotal(){return total;}
}
