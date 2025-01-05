package paagbi.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Ftp {
    private final String USERNAME, PASSWORD, IP;
    private final int PORT;
    private FTPClient ftpClient = new FTPClient();

    public Ftp(String USERNAME, String PASSWORD, String IP, int PORT) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.IP = IP;
        this.PORT = PORT;
    }

    public FTPClient connect() throws IOException {
        ftpClient.connect(IP, PORT);
        ftpClient.login(USERNAME, PASSWORD);

        return ftpClient;
    }

    public void disconnect() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    public FTPFile[] getListFile() throws IOException {
        return ftpClient.listFiles();
    }

    public boolean uploadFile(FileInputStream path, String name) throws IOException {
        return ftpClient.storeFile(name, path);
    }

    public boolean downloadFile(FileOutputStream path, String name) throws IOException {
        return ftpClient.retrieveFile(name, path);
    }

    @Override
    public String toString() {
        return "Konexioa --> Ftp [USERNAME=" + USERNAME + ", PASSWORD=" + PASSWORD + ", IP=" + IP + ", PORT=" + PORT + "]";
    }
}
