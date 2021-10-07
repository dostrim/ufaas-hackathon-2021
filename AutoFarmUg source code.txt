#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <Servo.h>

//constants
#define DHTPIN 7 // pin for reading
#define DHTTYPE DHT22// DHT(AM2302)
DHT dht(DHTPIN, DHTTYPE);//initialize DHT sensor for 16mhz Arduino
float hum;//stores humidity value
float temp;//stores temperature value

LiquidCrystal_I2C lcd(0x27,16,2);// initialize lcd

Servo servoT; // servo for filling tank
Servo servoI; // servo for irrigating


//flag variables
int flagTR = 0; 
int flagTL = 0;
int flagIR = 0; 
int flagIL = 0;
int count = 0;// times irrigation has occurred

int pos = 0; // initialise servo position

int maxThreshold = 500;
int minThreshold = 450; //soil moisture threshold
int sensor_pin = A2;// reading pin for moisture sensor
int sensor_value;


int trigPin = 9;
int echoPin = 8;
int duration;
int dCm, dInch;

int redLed = 12;//Low water level in tank
int blueLed = 11;//okay water level in tank
int greenLed = 10;//full water level in tank

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  lcd.init();
  lcd.begin(16, 2);
  lcd.backlight();

  dht.begin();
  
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);

  pinMode(redLed, OUTPUT);
  pinMode(blueLed, OUTPUT);
  pinMode(greenLed, OUTPUT);

  servoT.attach(2);//signal pin for tank servo
  servoI.attach(4);//signal pin for irrigating servo
  servoT.write(0);
  servoI.write(0);
}

void loop() {
  // put your main code here, to run repeatedly:
  //read and store values into variables for DHT and Moisture Sensor
  hum = dht.readHumidity();
  temp = dht.readTemperature();
  sensor_value = analogRead(sensor_pin);
  
  //utrasonic sensor readings
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);
  dCm = duration*0.034/2;
  dInch = duration*0.0133/2;

  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("MOS");
  lcd.setCursor(0, 1);
  lcd.print(analogRead(sensor_pin));
 
  lcd.setCursor(4, 0);
  lcd.print("TEMP");
  lcd.setCursor(4, 1);
  lcd.print(temp);
  lcd.setCursor(8, 1);
  lcd.print("\xDF");// character representation fordegree symbol
  
  lcd.setCursor(9,0);
  lcd.print("DCM");
  lcd.setCursor(9, 1);
  lcd.print(dCm);
  
  //tank monitoring
  lcd.setCursor(13, 0);
  lcd.print("TNK");
  lcd.setCursor(13, 1);
  
  if(dCm >= 9){
  lcd.print("LOW");
  digitalWrite(greenLed, LOW);
  digitalWrite(blueLed, LOW);
  digitalWrite(redLed, HIGH);
  delay(500);
  digitalWrite(redLed, LOW);
  delay(500);
  digitalWrite(redLed, HIGH);
    while(flagTR == 0){
      moveTR();
      delay(1000);
      flagTR = 1;
      flagTL = 1;
    }
  }
  else if(dCm >= 4){
  lcd.print("OK");
  digitalWrite(greenLed, LOW);
  digitalWrite(redLed, LOW);
  digitalWrite(blueLed, HIGH);
  irrigates();
  }
  else if(dCm >= 0){
  lcd.print("FULL");
  digitalWrite(blueLed, LOW);
  digitalWrite(redLed, LOW);
  digitalWrite(greenLed, HIGH);
  delay(500);
  digitalWrite(greenLed, LOW);
  delay(500);
  digitalWrite(greenLed, HIGH);
  irrigates();
    while(flagTL == 1){
      moveTL();
      delay(1000);
      flagTL = 0;
      flagTR = 0;
    }
  } 

  delay(1000);
}

//user defined functions
 //enabling irrigation
 void irrigates(){
  if(analogRead(sensor_pin) >= 500){
       while(flagIR == 0){
        moveIR();// opening irrigation
        delay(1000);
        count++;
       
        flagIR = 1;
        flagIL = 1;
      }
  }

  else if(analogRead(sensor_pin) <= 460){
     
    while(flagIL == 1){
     moveIL();// closing irrigation
     delay(1000);
     flagIL = 0;
     flagIR = 0;
    } 
  }

 }

//function for tank servo's movement
 void moveTR(){ //opening tank filling tap
  for(pos = 0; pos < 180; pos += 1)
  {
    servoT.write(pos);
    delay(15);
  }
 }

 void moveTL(){ //closing tank filling tap
  for(pos = 180; pos > 0; pos -= 1)
  {
    servoT.write(pos);
    delay(15);
  }
 }

//function for drainage servo's movement
 void moveIR(){ //opening draining tap
  for(pos = 0; pos < 180; pos += 1)
  {
    servoI.write(pos);
    delay(15);
  }
 }

 void moveIL(){ //closing draining tap
  for(pos = 180; pos > 0; pos -= 1)
  {
    servoI.write(pos);
    delay(15);
  }
 }
