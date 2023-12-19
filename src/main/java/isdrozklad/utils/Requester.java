package isdrozklad.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Легаси-код для буфферизации данных в файл. На данный момент бесполезен.
@Deprecated
public class Requester {
    private int GROUP_ID = 9999;
    private final String GET_REQUEST;
    private static String regex = ".*(group=\\S*?\\d+).*";
    private static Pattern pattern = Pattern.compile(regex);

    public Requester(String GET_REQUEST) {
        this.GET_REQUEST = GET_REQUEST;
        Matcher matcher = pattern.matcher(GET_REQUEST);
        if (matcher.find()) {
            String groupValue = matcher.group(1);
            GROUP_ID = Integer.parseInt(groupValue.substring(6));
        }
    }

    public void getData() {
        URLConnection connection;
        try {
            connection = new URL(GET_REQUEST).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            File file = new File("tablesDB\\1634.txt");
            file.getParentFile().mkdirs();
            file.createNewFile();
            while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
            }
            Files.write(Path.of(file.getPath()), result.toString().getBytes());
            reader.close();
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public int getGroupId() {
        return GROUP_ID;
    }
}
