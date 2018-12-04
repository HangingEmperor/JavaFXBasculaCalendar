/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cerberus
 */
public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label labelTitle = new Label();
        labelTitle.setText("Peso");
        labelTitle.setFont(new Font("Segoe UI", 20));

        Label labelKG = new Label();
        labelKG.setFont(new Font("Segoe UI", 20));
        labelKG.setText("Kilogramos");

        Label labelLB = new Label();
        labelLB.setText("Libras");
        labelLB.setFont(new Font("Segoe UI", 20));

        Button btnGuardar = new Button();
        btnGuardar.setText("Guardar");
        btnGuardar.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   String nameFile = "log.txt";
                   File file = new File("log.txt");
                   if (!file.exists()) {
                       try {
                           file.createNewFile();
                       } catch (IOException ex) {
                           Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                   try {
                       Date ahora = new Date();

                       SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

                       String aux = "";
                       String oldData = "";
                       FileReader hoja = new FileReader(file);
                       BufferedReader lee = new BufferedReader(hoja);
                       while ((aux = lee.readLine()) != null) {
                           oldData += aux + "\n";
                       }
                       lee.close();

                       FileWriter archivo = new FileWriter(file);
                       archivo.append(oldData);
                       archivo.append(formateador.format(ahora));
                       archivo.append(" | KG:" + labelKG.getText() + ",LB:" + labelLB.getText());
                       archivo.close();
                   } catch (IOException ex) {
                       System.out.println("Hubo un error");
                   }
               }
           }
        );

        Slider slKG = new Slider();

        slKG.setMin(0);
        slKG.setMax(200);
        slKG.setValue(0);
        slKG.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                labelKG.setText((float) slKG.getValue() + " kg");
                labelLB.setText((float) (slKG.getValue() * 2.20462) + " lb");
                }
            }
        );

        Slider slLB = new Slider();

        slLB.setMin(0);
        slLB.setMax(500);
        slLB.setValue(0);
        slLB.valueProperty().addListener(new ChangeListener<Number>() {
             @Override
             public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue
             ) {
                 labelLB.setText((float) slLB.getValue() + " lb");
                 labelKG.setText((float) (slLB.getValue() * 0.453592) + " kg");
             }
         }
        );

        TextField textKG = new TextField();

        textKG.setOnKeyPressed(new EventHandler<KeyEvent>() {
               @Override
               public void handle(KeyEvent event
               ) {
                   if (event.getCode() == KeyCode.ENTER) {
                       if (textKG.getText().matches("[a-zA-Z]+")
                               || textKG.getText().matches("[0-9]+[a-zA-Z]+")
                               || textKG.getText().matches("[a-zA-Z]+[0-9]+")) {
                           JOptionPane.showMessageDialog(null, "Solo se admiten digitos.");
                           textKG.setText("");
                       } else {
                           slKG.setValue(Double.parseDouble(textKG.getText()));
                           labelKG.setText(textKG.getText() + " kg");
                       }
                   }
               }
           }
        );

        TextField textLB = new TextField();

        textLB.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event
                    ) {
                        if (event.getCode() == KeyCode.ENTER) {
                            if (textLB.getText().matches("[a-zA-Z]+")
                                    || textLB.getText().matches("[0-9]+[a-zA-Z]+")
                                    || textLB.getText().matches("[a-zA-Z]+[0-9]+")) {
                                JOptionPane.showMessageDialog(null, "Solo se admiten digitos.");
                                textLB.setText("");
                            } else {
                                slLB.setValue(Double.parseDouble(textLB.getText()));
                                labelLB.setText(textLB.getText() + " lb");
                            }
                        }
                    }
                }
        );

        FlowPane flow = new FlowPane(Orientation.VERTICAL);

        flow.setVgap(20);
        flow.setPadding(
                new Insets(30));
        flow.getChildren().addAll(labelTitle, labelKG, slKG, textKG, labelLB,
                slLB, textLB, btnGuardar);

        Scene scene = new Scene(flow, 200, 500);

        primaryStage.setTitle("Bascula");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}