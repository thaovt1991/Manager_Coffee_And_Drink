package model;

import java.io.Serializable;
import java.util.TreeMap;

public class Table implements Serializable {
    private String idTable ;
    private TreeMap<String ,Integer> treeOder ;
    private String idStaffServing  ;
    private String timeInput ;
    private String timeOut ;
    private long totalMoney ;

    private Table(){}

    public Table(String idTable, TreeMap<String, Integer> treeOder, String idStaffServing, String timeInput, String timeOut,long totalMoney) {
        this.idTable = idTable;
        this.treeOder = treeOder;
        this.idStaffServing = idStaffServing;
        this.timeInput = timeInput;
        this.timeOut = timeOut;
        this.totalMoney = totalMoney ;

    }

    public long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTimeInput() {
        return timeInput;
    }

    public void setTimeInput(String timeInput) {
        this.timeInput = timeInput;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
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
}
