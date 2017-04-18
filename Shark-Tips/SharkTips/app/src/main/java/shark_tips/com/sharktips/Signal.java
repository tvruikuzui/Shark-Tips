package shark_tips.com.sharktips;

/**
 * Created by liranelyadumi on 4/13/17.
 */

public class Signal {

    // Check List Status
    private String status,action;
    public boolean isExpanded;

    // Signal

    boolean isOpen;
    int time;
    String currency;
    boolean isBuy;
    double price;
    double sellStop;
    double sl;
    double tp1;
    double tp2;
    String note;


    public Signal() {
    }

    public Signal(String note) {
        this.note = note;
    }

    public Signal(boolean isOpen, int time, String currency, boolean isBuy, double price, double sellStop, double sl, double tp1, double tp2, String note) {
        this.isOpen = isOpen;
        this.time = time;
        this.currency = currency;
        this.isBuy = isBuy;
        this.price = price;
        this.sellStop = sellStop;
        this.sl = sl;
        this.tp1 = tp1;
        this.tp2 = tp2;
        this.note = note;
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (isOpen == true){
            status = "open";
        }else {
            status = "close";
        }

        this.status = status;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        if (isBuy == true){
            action = "buy";
        }else {
            action = "sell";
        }
        this.action = action;
    }


}
