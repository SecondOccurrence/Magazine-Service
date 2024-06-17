/*
 * -- FileUtils.java --
 *
 * AUTHOR: Josh S
 * STUDENT NUMBER: 34195182
 * DATE: 25/05/2024
 *
 * DESCRIPTION: Utility functions for file manipulation
 *  used in the Main Application.
 *
 */

package FileUtils;

import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.FilenameFilter;

import Supplement.SupplementController;
import Customer.CustomerController;
import Customer.AssociateCustomer.AssociateCustomerController;
import Customer.AssociateCustomer.AssociateCustomer;
import Customer.PayingCustomer.PayingCustomerController;
import Magazine.MagazineController;

/**
 * Utility class for file manipulation functions
 *
 * @author Josh Smith - 34195182
 */
public final class FileUtils {
    /**
     * Constructor. set private to ensure the class is never created this way
     */
    private FileUtils() {}

    /**
     * @param path to the file to delete
     * @param name of the file to delete
     *
     * @return deletes the file matching parameters
     */
    public static void deleteSerializedFile(String filePath, String fileName) {
        File fileToDelete = new File(filePath + fileName + ".dat");
        if(fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    /**
     * @param path where supplements are located
     *
     * @return returns a list of all deserialized supplements
     */
    public static List<SupplementController> fetchSupplements(final String path) {
        List<SupplementController> list = new ArrayList<>();
        File directory = new File(path);
        if(!directory.isDirectory()) {
            return list;
        }

        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".dat");
            }
        });

        for(File file: files) {
            list.add(new SupplementController());
            list.get(list.size() - 1).deSerializeSupplement(path + file.getName());
        }

        return list;
    }

    /**
     * @param path where customers are located
     *
     * @return returns a list of all deserialized customers. associate and paying
     */
    public static List<CustomerController> fetchCustomers(final String partialPath) {
        List<CustomerController> associateList = fetchAssociateCustomers(partialPath);
        List<CustomerController> payingList = fetchPayingCustomers(partialPath);
        associateList.addAll(payingList);
        return associateList;
    }

    public static List<MagazineController> fetchMagazines(final String path) {
        List<MagazineController> list = new ArrayList<>();
        File directory = new File(path);
        if(!directory.isDirectory()) {
            return list;
        }

        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".dat");
            }
        });

        for(File file: files) {
            list.add(new MagazineController());
            list.get(list.size() - 1).deSerializeMagazine(path + file.getName());
        }

        return list; 
    }

    /**
     * @param partial path to the files. excluding the class folder
     *
     * @return returns a list of all deserialized associate customers
     */
    private static List<CustomerController> fetchAssociateCustomers(String partialPath) {
        String fullPath = partialPath + "associate_customers/";
        List<CustomerController> list = new ArrayList<>();
        File directory = new File(fullPath);
        if(!directory.isDirectory()) {
            return list;
        }

        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".dat");
            }
        });

        for(File file: files) {
            list.add(new AssociateCustomerController());
            list.get(list.size() - 1).deSerializeCustomer(fullPath + file.getName());
        }

        return list;
    }

    /**
     * @param partial path to the files. excluding the class folder
     *
     * @return returns a list of all deserialized paying customers
     */
    private static List<CustomerController> fetchPayingCustomers(final String partialPath) {
        String fullPath = partialPath + "paying_customers/";
        List<CustomerController> list = new ArrayList<>();
        File directory = new File(fullPath);
        if(!directory.isDirectory()) {
            return list;
        }

        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".dat");
            }
        });

        for(File file: files) {
            list.add(new PayingCustomerController());
            list.get(list.size() - 1).deSerializeCustomer(fullPath + file.getName());
        }

        return list;
    }

    /**
     * Deserializes a file into a supplement class
     *
     * @param the file to add to the program (assumed to be a file that exists)
     * @return the new SupplementController class
     *
     */
    public static SupplementController fetchSupplement(final String path) {
        SupplementController newSupplement = new SupplementController();
        newSupplement.deSerializeSupplement(path);
        return newSupplement;
    }

    public static CustomerController fetchCustomer(final String path) {
        AssociateCustomerController ac = new AssociateCustomerController();
        return ac;
    }

    /**
     * recursively deletes all contents in the folder
     *
     * @param path to the folder to delete
     *
     * @return deletes folders contents
     */
    public static void deleteFolderContents(String folderPath) {
        File folder = new File(folderPath);

        if(folder.isDirectory()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".dat");
                }
            });

            for(File file: files) {
                if(file.isFile()) {
                    file.delete();
                }
                else if(file.isDirectory()) {
                    deleteFolderContents(file.getAbsolutePath());
                }
            }

        }
    }
}
