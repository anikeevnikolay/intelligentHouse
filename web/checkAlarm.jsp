<%@ page import="arduino.ArduinoAlarmController" %><%=ArduinoAlarmController.getAlarmMessage() != null ? ArduinoAlarmController.getAlarmMessage() : ""%>