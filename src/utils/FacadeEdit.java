package utils;

import model.*;
import service.AccountManager;
import service.DrinksManager;
import service.SellManager;
import service.StaffManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class  FacadeEdit implements Serializable {
    public static ArrayList<Account> listAccount = (new AccountManager()).readDataAccountToFile(AccountManager.LINK_SAVE_OBJECT_ACCOUNT);
    public static ArrayList<Table> listTable = (new SellManager()).readDataTableToFile(SellManager.LINK_SAVE_OBJECT_TABLE);
    public static ArrayList<CarriedAway> listCA =(new SellManager()).readDataCarriedAwayToFile(SellManager.LINK_SAVE_OBJECT_CARRIED_AWAY);
    public static ArrayList<Bill> listBills = (new SellManager()).readDataBillsToFile(SellManager.LINK_SAVE_OBJECT_BILL);

    public static void editAllObjOfIdStaff(String idStaffOld , String idStaffNew){
        for (Account account : listAccount){
            if(account.getOwnerId().equals(idStaffOld)){
                account.setOwnerId(idStaffNew);
                break;
            }
        }
        for(Table table : listTable){
            if (table.getIdStaffServing().equals(idStaffOld)){
              table.setIdStaffServing(idStaffNew);
            }
        }
        for (CarriedAway carriedAway : listCA){
            if(carriedAway.getIdStaffServing().equals(idStaffOld)){
                carriedAway.setIdStaffServing(idStaffNew);
            }
        }
        for (Bill bill: listBills){
            if (bill.getIdStaffServing().equals(idStaffOld)){
                bill.setIdStaffServing(idStaffNew);
            }
        }
        (new AccountManager()).writeDataAccountToFile(AccountManager.LINK_SAVE_OBJECT_ACCOUNT,listAccount);
        (new AccountManager()).writeDataAccountToFileCsv(AccountManager.LINK_SAVE_FORMAT_CSV_ACCOUNT,listAccount);
        upSellManager();

    }

    public static void editAllObjOfIdDrinks(String idDrinkOld, String idDrinksNew){
        for(Table table : listTable){
            if(table.getTreeOder().containsKey(idDrinkOld)){
                editTreeOder(table.getTreeOder(),idDrinkOld,idDrinksNew);
            }
        }
        for(CarriedAway carriedAway : listCA){
            if(carriedAway.getTreeOder().containsKey(idDrinkOld)){
                editTreeOder(carriedAway.getTreeOder(),idDrinkOld,idDrinksNew);
            }
        }
        for(Bill bill : listBills){
            if(bill.getTreeOder().containsKey(idDrinkOld)){
                editTreeOder(bill.getTreeOder(),idDrinkOld,idDrinksNew);
            }
        }
        upSellManager();
    }

    public static void editTreeOder(TreeMap<String,Integer> treeOder,String idDrinksOld, String idDrinksNew){
        int value = 0;
        for(String idDr : treeOder.keySet()){
            if(idDr.equals(idDrinksOld)){
                value = treeOder.get(idDr);
                treeOder.remove(idDr);
                treeOder.put(idDrinksNew,value);
                break;
            }
        }
    }
    public static void upSellManager(){
        (new SellManager()).writeDataCarriedAwayToFile(SellManager.LINK_SAVE_OBJECT_CARRIED_AWAY,listCA);
        (new SellManager()).writeDataOfCarriedAwayFromFileFormatToCsv(SellManager.LINK_SAVE_FORMAT_CSV_CA,listCA);
        (new SellManager()).writeDataBillsToFile(SellManager.LINK_SAVE_OBJECT_BILL,listBills);
        (new SellManager()).writeDataOfBillsFromFileFormatToCsv(SellManager.LINK_SAVE_FORMAT_CSV_BILLS,listBills);
        (new SellManager()).writeDataTableToFile(SellManager.LINK_SAVE_OBJECT_TABLE,listTable);
        (new SellManager()).writeDataOfTableFromFileFormatToCsv(SellManager.LINK_SAVE_FORMAT_CSV_TABLE,listTable);
    }



}
