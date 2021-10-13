package model;

import java.io.Serializable;
import java.util.TreeMap;

public class Bill implements Serializable {
    public String dateBill ;
    public String userName ;
    public String idOrder;
    public TreeMap<String,Integer> treeOder ;
    private String idStaffServing  ;
    public String timeIn ;
    public String timeOut ;
    public long totalMoney ;

    public Bill(){}

    public Bill(String dateBill, String userName, String idOrder, TreeMap<String, Integer> treeOder, String idStaffServing, String timeIn, String timeOut, long totalMoney) {
        this.dateBill = dateBill;
        this.userName = userName;
        this.idOrder = idOrder;
        this.treeOder = treeOder;
        this.idStaffServing = idStaffServing;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.totalMoney = totalMoney;
    }

    public String getDateBill() {
        return dateBill;
    }

    public void setDateBill(String dateBill) {
        this.dateBill = dateBill;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public TreeMap<String, Integer> getTreeOder() {
        return treeOder;
    }

    public void setTreeOder(TreeMap<String, Integer> treeOder) {
        this.treeOder = treeOder;
    }

    public String getIdStaffServing() {
        return idStaffServing;
    }

    public void setIdStaffServing(String idStaffServing) {
        this.idStaffServing = idStaffServing;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(long totalMoney) {
        this.totalMoney = totalMoney;
    }
}
