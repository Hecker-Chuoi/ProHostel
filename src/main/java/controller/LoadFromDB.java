package controller;
import Model.*;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

public class LoadFromDB {
    private final File staffFile;
    private final File customerFile;
    private final File roomTypeFile;
    private final File roomFile;
    private final File accountFile;

    public LoadFromDB() throws IOException {
        staffFile = new File("./src/main/resources/Data/Staff Info.txt");
        customerFile = new File("./src/main/resources/Data/Customer Info.txt");
        roomTypeFile = new File("./src/main/resources/Data/RoomType List.txt");
        roomFile = new File("./src/main/resources/Data/Room List.txt");
        accountFile = new File("./src/main/resources/Data/Account.txt");

        if(!staffFile.exists())
           staffFile.createNewFile();
        if(!customerFile.exists())
           customerFile.createNewFile();
        if(!roomTypeFile.exists())
           roomTypeFile.createNewFile();
        if(!roomFile.exists())
           roomFile.createNewFile();
        if(!accountFile.exists())
           accountFile.createNewFile();
    }

    public boolean isAccountTypedExists(String usernameInput, String passwordInput) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(accountFile));
        String username = null, password;
        while((username = reader.readLine()) != null){
            password = reader.readLine();
            if(username.equals(usernameInput) && password.equals(passwordInput))
                return true;
        }
        return false;
    }

    public void inputStaffList(List<Staff> list) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(staffFile));
    }
    
    public void inputCustomerList() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(customerFile));

    }

    private void showDataErrorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Dữ liệu không hợp lệ");
        alert.setContentText("Nhập dữ liệu từ bộ nhớ bị lỗi, vui lòng kiểm tra lại");
        alert.showAndWait();
    }
    
    public void inputRoomTypeList() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(roomTypeFile));
        String tmp;
        while((tmp = reader.readLine()) != null){
            try{
                RoomType roomType = new RoomType();
                roomType.setName(tmp);

                String s = reader.readLine();
                String[] arr = s.split(" ");
                roomType.setId(arr[0]);
                roomType.setNumberOfBed(Integer.parseInt(arr[1]));
                roomType.setMaxPeople(Integer.parseInt(arr[2]));
                roomType.setPricePerHour(Double.parseDouble(arr[3]));
                roomType.setPricePerDay(Double.parseDouble(arr[4]));

                Main.roomTypeList.add(roomType);
            }
            catch (Exception e){
                showDataErrorAlert();
            }
        }
        reader.close();
    }
    
    public void inputRoomList() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(roomFile));
        String s;
        while((s = reader.readLine()) != null){
            Room room = new Room(s);
            Main.roomList.add(room);
        }
        reader.close();
    }
}
