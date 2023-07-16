import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Agency {
    public static void main(String[] argv) throws Exception {
        String EXCHANGE_NAME = "cosmicexchange";
        String[] services = new String[]{"human_transport", "cargo_transport", "satellite_placement"};

        // info
        System.out.println("AGENCY\n");
        BufferedReader agency_br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input agency name: ");
        String name = agency_br.readLine();

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // agency queue
        channel.queueDeclare(name, false, false, false, null);

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // service information
        System.out.println("Available services:");

        for (int i = 0; i < services.length; i++) {
            System.out.println(services[i] + ": " + i);
        }
        System.out.println("\nFor exit type 'quit'...\n");

        //carrier msg listener
        Consumer carrier_response = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("\nCarrier response: " + message + "\n");

            }
        };
        channel.basicConsume(name, true, carrier_response);

        // order services
        while (true) {

            // read msg
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Choose service number: 0, 1, 2 > ");
            String service_num = br.readLine();

            // break condition
            if ("quit".equals(service_num)) {
                channel.close();
                connection.close();
                break;
            }

            // publish
            try {
                String chosen_service = services[Integer.parseInt(service_num)];
                String message = name.concat(":").concat(service_num);
                channel.basicPublish(EXCHANGE_NAME, chosen_service, null, message.getBytes("UTF-8"));
                System.out.println("Sent: " + message + "\n");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!\n");
            }


        }
    }
}
