package catdany.android.tehgaem.net;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;

import catdany.android.tehgaem.AUtils;
import catdany.android.tehgaem.GamePlaceholderActivity;
import catdany.android.tehgaem.LLog;
import catdany.android.tehgaem.net.packet.LoginPacket;
import catdany.android.tehgaem.net.packet.TGPacket;

/**
 * Created by CatDany on 28.05.2016.
 */
public class TGClient implements Runnable, Closeable {

    public static final int SERVER_PORT = 10200;
    public static TGClient instance;

    public final Thread threadClient;
    public String serverAddressStr;
    public InetAddress serverAddress;
    public UUID id;
    public String password;
    public Socket socket;

    public PrintWriter writer;
    public BufferedReader reader;

    public TGClient() {
        threadClient = new Thread(this, "Client");
    }

    /**
     * Attemps to connect to server. If successful, start a thread to keep the connection.
     * @param serverAddress
     * @param id
     * @param password
     */
    public void connect(String serverAddress, UUID id, String password) {
        LLog.v("Parsing server address. Attempting to initiate connection.");
        this.serverAddressStr = serverAddress;
        this.password = password;
        this.id = id;
        threadClient.start();
    }

    @Override
    public void run() {
        LLog.v("Client thread started.");
        // Initializing the connection
        try {
            this.serverAddress = InetAddress.getByName(serverAddressStr);
            this.socket = new Socket(serverAddress, AUtils.SERVER_PORT);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), AUtils.SOCKET_CHARSET));
            this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), AUtils.SOCKET_CHARSET), true);
            sendPacket(new LoginPacket(id, password));
        } catch (UnknownHostException t) {
            LLog.e(t, "Invalid server address: %s", serverAddress);
            AUtils.toastShort("Invalid address");
        } catch (IOException t) {
            LLog.e(t, "Couldn't connect.");
            AUtils.toastShort("I/O Error");
        }
        // Keeping the connection
        try {
            String read;
            while ((read = reader.readLine()) != null) {
                //LLog.v("Received message: %s", read);
                TGPacket packet = parsePacket(read);
                packet.handleReception();
            }
            LLog.e("Connection lost.");
            AUtils.toastShort("Connection lost");
        } catch (SocketException t) {
            LLog.e(t, "Socket error occurred, probably because it was closed.");
        } catch (IOException t) {
            LLog.e(t, "Connection error.");
            AUtils.toastShort("I/O Error");
        }
        GamePlaceholderActivity.finishOnUI();
    }

    public void sendPacket(TGPacket packet) {
        try {
            String s = packet.toJson().toString();
            writer.println(s);
            LLog.v("Sent packet: %s", s);
        } catch (JSONException t) {
            LLog.w(t, "Unable to send a packet, couldn't serialize.");
        }
    }

    public TGPacket parsePacket(String s) {
        try {
            JSONObject json = new JSONObject(s);
            return TGPacket.fromJson(json);
        } catch (JSONException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassCastException t) {
            LLog.e(t, "Unable parse packet: %s", s);
            return null;
        }
    }

    public void close() throws IOException {
        socket.close();
        LLog.i("TGClient closed.");
    }
}
