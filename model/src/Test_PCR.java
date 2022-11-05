import java.util.Date;

public class Test_PCR {

    private int id;
    private Date validity_test;

    private String status;

    public void constructor__(){
        this.id=id;
        this.validity_test=validity_test;
        this.status =status;
    }
    public int getNumber() {
        return id;
    }

    public void setNumber(int number) {
        this.id = id;
    }

    public Date getValidity_test() {
        return validity_test;
    }

    public void setValidity_test(Date validity_test) {
        this.validity_test = validity_test;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}