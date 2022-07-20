#include <ESP8266WiFi.h>
#include <PubSubClient.h>

#include <DHT.h>
//#include <DHT_U.h>

#define DHTTYPE DHT11
#define DHT11_PIN D5

DHT dht(DHT11_PIN, DHTTYPE);

void subscribeRecieve(char* topic, byte* payload, unsigned int length);

const char* ssid = "Mayur";                   // wifi ssid
const char* password =  "12345678";         // wifi password
//const char* mqttServer = "test.mosquitto.org";
const char* mqttServer = "192.168.43.202";    // IP adress Raspberry Pi
const int mqttPort = 1883;
//const char* mqttUser = "MqttBroker";      // if you don't have MQTT Username, no need input
//const char* mqttPassword = "12345678";  // if you don't have MQTT Password, no need input

//byte ip[] = { 192, 168, 43, 202 };

WiFiClient espClient;
PubSubClient client(mqttServer, mqttPort, espClient);

void setup() {

  Serial.begin(115200);
  //WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Connecting to WiFi..");
  }
  Serial.println("Connected to the WiFi network");
  Serial.println(WiFi.localIP());

  //client.setServer(mqttServer, mqttPort);
  client.setCallback(subscribeRecieve);
  
  if(client.connect("ESP8266Client")){

     Serial.println("Connection has been established");
     //client.setCallback(subscribeRecieve);
  }
  else{
    Serial.println("Connection Problem");
  }
}

void loop() {
    float h = dht.readHumidity();
   // float t = dht.readTemperature();

    char humString[8];
    //char tempString[8];
    
    dtostrf(h, 1, 2, humString);
    //dtostrf(t, 1, 2, tempString);
    
    client.loop();
    //client.subscribe("DHT_T");
    client.subscribe("DHT_H");
    
    if(client.publish("DHT_H", humString )) {
      Serial.println("Publish message success(H)");
    }
    
    /*if(client.publish("DHT_T", tempString )) {
      Serial.println("Publish message success(T)");
    }*/
    else {
      Serial.println("Could not send Message");
      }
    delay(3000);

    
}

void subscribeRecieve(char* topic, byte* payload, unsigned int length) {

 /* Serial.print("Message arrived in topic: ");
  Serial.println(topic);

  Serial.print("Message:");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }*/
Serial.println("");
}
