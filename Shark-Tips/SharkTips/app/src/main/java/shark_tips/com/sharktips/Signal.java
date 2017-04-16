package shark_tips.com.sharktips;

/**
 * Created by liranelyadumi on 4/13/17.
 */

public class Signal {

    private String status,time,currency,action,price;
    private String sellStop,sl,tp1,tp2,note;
    public boolean isExpanded, isBuy, isOpen;


    public Signal() {
    }

    public Signal(String status, String time, String currency, String action, String price, String sellStop, String sl, String tp1, String tp2, String note, boolean isBuy, boolean isOpen) {
        this.status = status;
        this.time = time;
        this.currency = currency;
        this.action = action;
        this.price = price;
        this.sellStop = sellStop;
        this.sl = sl;
        this.tp1 = tp1;
        this.tp2 = tp2;
        this.note = note;
        this.isBuy = isBuy;
        this.isOpen = isOpen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellStop() {
        return sellStop;
    }

    public void setSellStop(String sellStop) {
        this.sellStop = sellStop;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getTp1() {
        return tp1;
    }

    public void setTp1(String tp1) {
        this.tp1 = tp1;
    }

    public String getTp2() {
        return tp2;
    }

    public void setTp2(String tp2) {
        this.tp2 = tp2;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        this.isBuy = buy;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }
}
