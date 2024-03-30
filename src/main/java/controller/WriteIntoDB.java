package controller;
import Model.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class WriteIntoDB{
    private final File staffList;
    private final File customerList;
    private final File roomTypeList;

    public WriteIntoDB() throws IOException {
        staffList = new File("./src/main/resources/Data/Staff Info.txt");
        customerList = new File("./src/main/resources/Data/Customer Info.txt");
        roomTypeList = new File("./src/main/resources/Data/RoomType List.txt");

        if(!staffList.exists())
            staffList.createNewFile();
        if(!customerList.exists())
            customerList.createNewFile();
        if(!roomTypeList.exists())
            roomTypeList.createNewFile();
    }


}
