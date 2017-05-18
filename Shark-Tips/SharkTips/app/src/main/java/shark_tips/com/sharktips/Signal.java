package shark_tips.com.sharktips;

/**
 * Created by liranelyadumi on 4/13/17.
 */

public class Signal {


    public boolean isExpanded;

    // Signal

    String currency,nameOfSl;
    boolean isBuy;
    double price;
    double sellStop;
    double sl;
    double tp1;
    double tp2;
    String note;
    boolean isOpen;
    Integer id;
    int time;


    public Signal() {
    }

    public Signal(boolean isOpen, int time,int id, String currency, boolean isBuy, double price, double sellStop, double sl, double tp1, double tp2, String note, String nameOfSl) {
        this.isOpen = isOpen;
        this.time = time;
        this.id = id;
        this.currency = currency;
        this.isBuy = isBuy;
        this.price = price;
        this.sellStop = sellStop;
        this.sl = sl;
        this.tp1 = tp1;
        this.tp2 = tp2;
        this.note = note;
        this.nameOfSl = nameOfSl;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSellStop() {
        return sellStop;
    }

    public void setSellStop(double sellStop) {
        this.sellStop = sellStop;
    }

    public double getSl() {
        return sl;
    }

    public void setSl(double sl) {
        this.sl = sl;
    }

    public double getTp1() {
        return tp1;
    }

    public void setTp1(double tp1) {
        this.tp1 = tp1;
    }

    public double getTp2() {
        return tp2;
    }

    public void setTp2(double tp2) {
        this.tp2 = tp2;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameOfSl() {
        return nameOfSl;
    }

    public void setNameOfSl(String nameOfSl) {
        this.nameOfSl = nameOfSl;
    }


}
