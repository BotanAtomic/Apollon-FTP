package org.apollon.core;

import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Scanner;

public class FTPClient {

    public FTPClient() throws Exception {
        Socket socket = new Socket("127.0.0.1", 2045);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext() && socket.isConnected()) {
            String line = scanner.nextLine();
            byte[] data = line.getBytes(Charset.forName("UTF-8"));

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            stream.write(new byte[]{0});
            stream.write(ByteBuffer.allocate(Integer.SIZE).putInt(data.length).array());
            stream.write(data);


            System.err.println("Size = " + data.length);
            System.err.println("Data = " + Arrays.toString(data));

            socket.getOutputStream().write(stream.toByteArray());

        }


    }

}
