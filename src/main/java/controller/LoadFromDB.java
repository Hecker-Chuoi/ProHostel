package controller;
import Model.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

public class LoadFromDB {
    private final File staffList;
    private final File customerList;
    private final File roomTypeList;
    private final File roomList;
    private final File accountList;

    public LoadFromDB() throws IOException {
        staffList = new File("./src/main/resources/Data/Staff Info.txt");
        customerList = new File("./src/main/resources/Data/Customer Info.txt");
        roomTypeList = new File("./src/main/resources/Data/RoomType List.txt");
        roomList = new File("./src/main/resources/Data/Room List.txt");
        accountList = new File("./src/main/resources/Data/Account.txt");

        if(!staffList.exists())
           staffList.createNewFile();
        if(!customerList.exists())
           customerList.createNewFile();
        if(!roomTypeList.exists())
           roomTypeList.createNewFile();
        if(!roomList.exists())
           roomList.createNewFile();
        if(!accountList.exists())
           accountList.createNewFile();
    }

    public boolean isAccountTypedExists(String usernameInput, String passwordInput) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(accountList));
        String username = null, password;
        while((username = reader.readLine()) != null){
            password = reader.readLine();
            if(username.equals(usernameInput) && password.equals(passwordInput))
                return true;
        }
        return false;
    }

    public void inputStaffList(List<Staff> list) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(staffList));

    }
    
    public void inputCustomerList() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(customerList));

    }
    
    public void inputRoomTypeList() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(roomTypeList));

    }
    
    public void inputRoomList() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(roomList));

    }

    public static void main(String[] args) throws IOException {
        LoadFromDB loadFromDB = new LoadFromDB();
        System.out.println(loadFromDB.isAccountTypedExists("admin", "12345678"));
    }
}
