package bierbest.model;

import javafx.concurrent.Task;
import javassist.bytecode.stackmap.TypeData;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConnection extends Task<ArrayList<Response>> {
    private static final Logger LOGGER = Logger.getLogger(TypeData.ClassName.class.getName());
    private int port;
    private String serverAddress;
    private ArrayList<Request> requests;

    public ServerConnection(String serverAddress, int port) {
        this.port = port;
        this.serverAddress = serverAddress;
        requests = new ArrayList<>();
    }

    public void addRequests(ArrayList<Request> requests) {
        this.requests.addAll(requests);
    }

    public void addSingleRequest(Request request) {
        requests.add(request);
    }

    @Override
    protected ArrayList<Response> call() {
        LOGGER.log(Level.INFO, "Starting connection at: " + serverAddress + ":" + port);
        ArrayList<Response> responses = new ArrayList<>(requests.size());

        // Dumb trust manager that does not verify if we trust certificate's CA - temporary
        final TrustManager[] trustAllCertificates = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        }};

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustAllCertificates, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        try (
                Socket client = sslContext.getSocketFactory().createSocket(serverAddress, port);
                ObjectOutputStream outToServer = new ObjectOutputStream(client.getOutputStream());
                BierBestObjectInputStream inFromServer = new BierBestObjectInputStream(client.getInputStream());
            )
        {
            LOGGER.log(Level.INFO, "Connected to: " + client.getRemoteSocketAddress());
            for (Request r : requests) {
                outToServer.writeObject(r);
            }
            int requestsLeft = requests.size();
            Response tempResponse;
            while (requestsLeft>0 && (tempResponse = (Response) inFromServer.readObject()) != null) {
                LOGGER.log(Level.INFO, tempResponse.toString());
                responses.add(tempResponse);
                --requestsLeft;
            }
            client.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ServerConnection error");
            e.printStackTrace();
        }
        return responses;
    }
}
