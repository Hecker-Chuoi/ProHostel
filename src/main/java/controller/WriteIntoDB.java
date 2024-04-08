package controller;
import Model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class WriteIntoDB{
    private final File staffFile;
    private final File customerFile;
    private final File roomTypeFile;
    private final File roomFile;
    private final File accountFile;
    private final File receiptFile;

    public WriteIntoDB() throws IOException {
        staffFile = new File("./src/main/resources/Data/Staff Info.txt");
        customerFile = new File("./src/main/resources/Data/Customer Info.txt");
        roomTypeFile = new File("./src/main/resources/Data/RoomType List.txt");
        roomFile = new File("./src/main/resources/Data/Room List.txt");
        accountFile = new File("./src/main/resources/Data/Account.txt");
        receiptFile = new File("./src/main/resources/Data/Receipt list.txt");

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
        if(!receiptFile.exists())
            receiptFile.createNewFile();
    }

    public void writeRoomTypeList() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(roomTypeFile));
        for(RoomType roomType : Main.roomTypeList){
            writer.write(roomType.toString());
            writer.newLine();
        }
        writer.close();
    }

    public void writeRoomList() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(roomFile));
        for(Room room : Main.roomList){
            writer.write(room.toString());
            writer.newLine();
        }
        writer.close();
    }

    public void writeCustomerList() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(customerFile));
        for(Customer customer : Main.customerList){
            writer.write(customer.toString());
            writer.newLine();
        }
        writer.close();
    }

    public void writeReceiptList() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(receiptFile));
        for(Receipt receipt : Main.receiptList){
            writer.write(receipt.toString());
            writer.newLine();
        }
        writer.close();
    }
}
