import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Carrier {
    public static void main(String[] argv) throws Exception {

        String EXCHANGE_NAME = "cosmicexchange";
        String[] services = new String[]{"human_transport","cargo_transport","satellite_placement"};
        String[] my_services=new String[]{"",""};
        int taken_service = -1;
        int chosen_service = -1;

        // info
        System.out.println("CARRIER\n");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        //service select
        for(int i =0; i<2;i++){
            System.out.println("Available services:");
            if (chosen_service != -1){
                taken_service=chosen_service;
            }
            for (int j=0; j< services.length;j++){
                if (j!=taken_service){
                    System.out.println(services[j]+": "+j);
                }
            }
            while(true){
                System.out.print("Choose type for service " + (i+1) + " > ");
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String input=br1.readLine();
                if("0".equals(input)){
                    chosen_service=Integer.parseInt(input);
                    if (chosen_service!=taken_service){
                        my_services[i] = services[chosen_service];
                        break;
                    }
                    else {
                        System.out.println("You've already chosen this service, retry...");
                    }
                }
                else if("1".equals(input)){
                    chosen_service=Integer.parseInt(input);
                    if (chosen_service!=taken_service){
                        my_services[i] = services[chosen_service];
                        break;
                    }
                    else {
                        System.out.println("You've already chosen this service, retry...");
                    }
                }
                else if("2".equals(input)){
                    chosen_service=Integer.parseInt(input);
                    if (chosen_service!=taken_service){
                        my_services[i] = services[chosen_service];
                        break;
                    }
                    else {
                        System.out.println("You've already chosen this service, retry...");
                    }
                }
                else{
                    System.out.println("Invalid input, retry...");
                }
            }
        }


        // queues & binds
        System.out.println("\n");

        channel.queueDeclare(my_services[0], false,false,false,null);
        channel.queueBind(my_services[0], EXCHANGE_NAME, my_services[0]);
        System.out.println("Created queue: " + my_services[0]);

        channel.queueDeclare(my_services[1],false,false,false,null);
        channel.queueBind(my_services[1], EXCHANGE_NAME, my_services[1]);
        System.out.println("Created queue: " + my_services[1]);

        System.out.println("\n");

        // consumer (message handling)
        Consumer service1_handler = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                String service_type=envelope.getRoutingKey();
                System.out.println("\nReceived: " + message + " from " +service_type+"\n");
                String[] details = message.split(":");
                String response;
                System.out.println("Handling task [" + details[1] + "], commissioned by agency [" + details[0] + "]");
                response = "task [" + details[1] + "], commissioned by [" + details[0] + "] done";
                channel.basicPublish("", details[0], null, response.getBytes());
                System.out.println("Sent: " + response);
            }
        };

        // start listening to both service queues
        System.out.println("Waiting for messages..."+"\n");
        channel.basicConsume(my_services[0], true, service1_handler);
        channel.basicConsume(my_services[1], true, service1_handler);
    }
}
