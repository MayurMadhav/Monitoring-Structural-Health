package Subscriber.MQTT_ClientSub;

        import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
        import org.eclipse.paho.client.mqttv3.MqttCallback;
        import org.eclipse.paho.client.mqttv3.MqttClient;
        import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
        import org.eclipse.paho.client.mqttv3.MqttException;
        import org.eclipse.paho.client.mqttv3.MqttMessage;
        import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

        public class PahoSubscriber implements MqttCallback {
        
        public static void main(String[] args) {
        	String topic1        = "ADXL";
        	String topic          = "DHT_H";
        	String dangerDHT;
            //String content      = "Message from MqttPublishSample";
           // int qos             = 2;
            String broker       = "tcp://192.168.43.202:1883";
            String clientId     = "JavaSample1";
            MemoryPersistence persistence = new MemoryPersistence();
        	
            //MqttClient sampleClient=null;
            
        	
   		 //sampleClient.setCallback(this);

            try {
            	PahoSubscriber sub= new PahoSubscriber();
                MqttClient sampleClient= new MqttClient(broker, clientId, persistence);
                sampleClient.setCallback(sub);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                System.out.println("Connecting to broker: "+broker);
                sampleClient.connect(connOpts);
                System.out.println("Connected");
                sampleClient.subscribe(topic1);
                sampleClient.subscribe(topic);
                /*System.out.println("Publishing message: "+content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                sampleClient.publish(topic, message);
                System.out.println("Message published");
                sampleClient.disconnect();
                System.out.println("Disconnected");
                System.exit(0); */
                
            } catch(MqttException me) {
                System.out.println("reason "+me.getReasonCode());
                System.out.println("msg "+me.getMessage());
                System.out.println("loc "+me.getLocalizedMessage());
                System.out.println("cause "+me.getCause());
                System.out.println("excep "+me);
                me.printStackTrace();
            }
            
            
        }
       public void messageArrived(String topic, MqttMessage message) {
    	  MqttMessage a = message;
    	  System.out.println("Readings are : "+message);
    	  /* if(topic.equals("ADXL")) {
    		   if (message.equals(5)) {
    			   System.out.println("Danger");
    		   }
                System.out.println("Adxl is : "+message);
    	   		}
    	   else{
    		   System.out.println("DHT Readings are: "+message);
    	   }
    	   }
    	   
    	   			System.out.println("Humidity is : "+message);
    	   	*/
    	   
       }
    	   		
            
        public void connectionLost(Throwable t) {
        	System.out.println(t.getMessage());
        }
        public void deliveryComplete(IMqttDeliveryToken token) {
        	System.out.println(token.toString());
        }
        
    }