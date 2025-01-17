package com.example.lab1_solov_kg_nechet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ColorConverterApp extends Application {

    private VBox hlsToRgbCmykBox;
    private VBox cmykToRgbHlsBox;
    private VBox rgbToHlsCmykBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();

        Tab hlsToRgbCmykTab = new Tab("HLS to RGB & CMYK");
        Tab cmykToRgbHlsTab = new Tab("CMYK to RGB & HLS");
        Tab rgbToHlsCmykTab = new Tab("RGB to HLS & CMYK");

        createHlsToRgbCmykBox();
        createCmykToRgbHlsBox();
        createRgbToHlsCmykBox();

        hlsToRgbCmykTab.setContent(hlsToRgbCmykBox);
        cmykToRgbHlsTab.setContent(cmykToRgbHlsBox);
        rgbToHlsCmykTab.setContent(rgbToHlsCmykBox);

        tabPane.getTabs().addAll(hlsToRgbCmykTab, cmykToRgbHlsTab, rgbToHlsCmykTab);

        primaryStage.setScene(new Scene(tabPane, 600, 400));
        primaryStage.setTitle("Color Converter");
        primaryStage.show();
    }

    private void createHlsToRgbCmykBox() {
        Label label1 = new Label("Hue:");
        Label label2 = new Label("Lightness:");
        Label label3 = new Label("Saturation:");

        Slider hue = new Slider(0, 360, 0);
        Slider lightness = new Slider(0, 1, 0);
        Slider saturation = new Slider(0, 1, 0);

        TextField textField1 = new TextField("0");
        TextField textField2 = new TextField("0");
        TextField textField3 = new TextField("0");

        TextField rgb = new TextField();
        TextField cmyk = new TextField();

        ColorPicker colorPicker = new ColorPicker();

        hue.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField1.setText(String.format("%.0f", newVal));
            updateColorsFromHls(hue, lightness, saturation, rgb, cmyk);
            updateColorPickerFromHls(hue, lightness, saturation, colorPicker);
        });
        lightness.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField2.setText(String.format("%.2f", newVal));
            updateColorsFromHls(hue, lightness, saturation, rgb, cmyk);
            updateColorPickerFromHls(hue, lightness, saturation, colorPicker);
        });
        saturation.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField3.setText(String.format("%.2f", newVal));
            updateColorsFromHls(hue, lightness, saturation, rgb, cmyk);
            updateColorPickerFromHls(hue, lightness, saturation, colorPicker);
        });

        colorPicker.setOnAction(event -> {
            Color color = colorPicker.getValue();
            int r = (int) (color.getRed() * 255);
            int g = (int) (color.getGreen() * 255);
            int b = (int) (color.getBlue() * 255);

            double[] hlsValues = rgbToHls(r, g, b);
            hue.setValue(hlsValues[0]);
            lightness.setValue(hlsValues[1]);
            saturation.setValue(hlsValues[2]);
        });

        HBox hbox1 = new HBox(10, label1, hue, textField1);
        HBox hbox2 = new HBox(10, label2, lightness, textField2);
        HBox hbox3 = new HBox(10, label3, saturation, textField3);
        HBox colorBox = new HBox(10, new Label("Color Picker:"), colorPicker);
        HBox rgbBox = new HBox(10, new Label("RGB:"), rgb);
        HBox cmykBox = new HBox(10, new Label("CMYK:"), cmyk);

        hlsToRgbCmykBox = new VBox(15, hbox1, hbox2, hbox3, colorBox, rgbBox, cmykBox);
    }

    private void createCmykToRgbHlsBox() {
        Label label1 = new Label("Cyan:");
        Label label2 = new Label("Magenta:");
        Label label3 = new Label("Yellow:");
        Label label4 = new Label("Key (Black):");

        Slider cyan = new Slider(0, 1, 0);
        Slider magenta = new Slider(0, 1, 0);
        Slider yellow = new Slider(0, 1, 0);
        Slider key = new Slider(0, 1, 0);

        TextField textField1 = new TextField("0");
        TextField textField2 = new TextField("0");
        TextField textField3 = new TextField("0");
        TextField textField4 = new TextField("0");

        TextField rgb = new TextField();
        TextField hls = new TextField();

        ColorPicker colorPicker = new ColorPicker();

        cyan.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField1.setText(String.format("%.2f", newVal));
            updateColorsFromCmyk(cyan, magenta, yellow, key, rgb, hls);
            updateColorPickerFromCmyk(cyan, magenta, yellow, key, colorPicker);
        });
        magenta.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField2.setText(String.format("%.2f", newVal));
            updateColorsFromCmyk(cyan, magenta, yellow, key, rgb, hls);
            updateColorPickerFromCmyk(cyan, magenta, yellow, key, colorPicker);
        });
        yellow.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField3.setText(String.format("%.2f", newVal));
            updateColorsFromCmyk(cyan, magenta, yellow, key, rgb, hls);
            updateColorPickerFromCmyk(cyan, magenta, yellow, key, colorPicker);
        });
        key.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField4.setText(String.format("%.2f", newVal));
            updateColorsFromCmyk(cyan, magenta, yellow, key, rgb, hls);
            updateColorPickerFromCmyk(cyan, magenta, yellow, key, colorPicker);
        });

        colorPicker.setOnAction(event -> {
            Color color = colorPicker.getValue();
            int r = (int) (color.getRed() * 255);
            int g = (int) (color.getGreen() * 255);
            int b = (int) (color.getBlue() * 255);

            double[] cmykValues = rgbToCmyk(r, g, b);
            cyan.setValue(cmykValues[0]);
            magenta.setValue(cmykValues[1]);
            yellow.setValue(cmykValues[2]);
            key.setValue(cmykValues[3]);
        });

        HBox hbox1 = new HBox(10, label1, cyan, textField1);
        HBox hbox2 = new HBox(10, label2, magenta, textField2);
        HBox hbox3 = new HBox(10, label3, yellow, textField3);
        HBox hbox4 = new HBox(10, label4, key, textField4);
        HBox colorBox = new HBox(10, new Label("Color Picker:"), colorPicker);
        HBox rgbBox = new HBox(10, new Label("RGB:"), rgb);
        HBox hlsBox = new HBox(10, new Label("HLS:"), hls);

        cmykToRgbHlsBox = new VBox(15, hbox1, hbox2, hbox3, hbox4, colorBox, rgbBox, hlsBox);
    }

    private void createRgbToHlsCmykBox() {
        Label label1 = new Label("Red:");
        Label label2 = new Label("Green:");
        Label label3 = new Label("Blue:");

        Slider red = new Slider(0, 255, 0);
        Slider green = new Slider(0, 255, 0);
        Slider blue = new Slider(0, 255, 0);

        TextField textField1 = new TextField("0");
        TextField textField2 = new TextField("0");
        TextField textField3 = new TextField("0");

        TextField hls = new TextField();
        TextField cmyk = new TextField();

        ColorPicker colorPicker = new ColorPicker();

        red.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField1.setText(String.format("%.0f", newVal));
            updateColorsFromRgb(red, green, blue, hls, cmyk);
            updateColorPickerFromRgb(red, green, blue, colorPicker);
        });
        green.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField2.setText(String.format("%.0f", newVal));
            updateColorsFromRgb(red, green, blue, hls, cmyk);
            updateColorPickerFromRgb(red, green, blue, colorPicker);
        });
        blue.valueProperty().addListener((obs, oldVal, newVal) -> {
            textField3.setText(String.format("%.0f", newVal));
            updateColorsFromRgb(red, green, blue, hls, cmyk);
            updateColorPickerFromRgb(red, green, blue, colorPicker);
        });

        colorPicker.setOnAction(event -> {
            Color color = colorPicker.getValue();
            red.setValue(color.getRed() * 255);
            green.setValue(color.getGreen() * 255);
            blue.setValue(color.getBlue() * 255);
        });

        HBox hbox1 = new HBox(10, label1, red, textField1);
        HBox hbox2 = new HBox(10, label2, green, textField2);
        HBox hbox3 = new HBox(10, label3, blue, textField3);
        HBox colorBox = new HBox(10, new Label("Color Picker:"), colorPicker);
        HBox hlsBox = new HBox(10, new Label("HLS:"), hls);
        HBox cmykBox = new HBox(10, new Label("CMYK:"), cmyk);

        rgbToHlsCmykBox = new VBox(15, hbox1, hbox2, hbox3, colorBox, hlsBox, cmykBox);
    }

    private int[] hlsToRgb(double h, double l, double s) {
        double c = (1 - Math.abs(2 * l - 1)) * s;
        double x = c * (1 - Math.abs((h / 60) % 2 - 1));
        double m = l - c / 2;
        double rPrime, gPrime, bPrime;

        if (h < 60) {
            rPrime = c;
            gPrime = x;
            bPrime = 0;
        } else if (h < 120) {
            rPrime = x;
            gPrime = c;
            bPrime = 0;
        } else if (h < 180) {
            rPrime = 0;
            gPrime = c;
            bPrime = x;
        } else if (h < 240) {
            rPrime = 0;
            gPrime = x;
            bPrime = c;
        } else if (h < 300) {
            rPrime = x;
            gPrime = 0;
            bPrime = c;
        } else {
            rPrime = c;
            gPrime = 0;
            bPrime = x;
        }

        int r = (int) ((rPrime + m) * 255);
        int g = (int) ((gPrime + m) * 255);
        int b = (int) ((bPrime + m) * 255);

        return new int[]{r, g, b};
    }

    private double[] rgbToHls(int r, int g, int b) {
        double rPrime = r / 255.0;
        double gPrime = g / 255.0;
        double bPrime = b / 255.0;

        double max = Math.max(rPrime, Math.max(gPrime, bPrime));
        double min = Math.min(rPrime, Math.min(gPrime, bPrime));
        double delta = max - min;

        double h = 0;
        if (delta != 0) {
            if (max == rPrime) {
                h = ((gPrime - bPrime) / delta) % 6;
            } else if (max == gPrime) {
                h = ((bPrime - rPrime) / delta) + 2;
            } else {
                h = ((rPrime - gPrime) / delta) + 4;
            }
            h *= 60;
            if (h < 0) {
                h += 360;
            }
        }

        double l = (max + min) / 2;
        double s = (delta == 0) ? 0 : delta / (1 - Math.abs(2 * l - 1));

        return new double[]{h, l, s};
    }

    private int[] cmykToRgb(double c, double m, double y, double k) {
        int r = (int) (255 * (1 - c) * (1 - k));
        int g = (int) (255 * (1 - m) * (1 - k));
        int b = (int) (255 * (1 - y) * (1 - k));
        return new int[]{r, g, b};
    }

    private double[] rgbToCmyk(int r, int g, int b) {
        double rPrime = r / 255.0;
        double gPrime = g / 255.0;
        double bPrime = b / 255.0;

        double k = 1 - Math.max(rPrime, Math.max(gPrime, bPrime));
        double c = (k == 1) ? 0 : (1 - rPrime - k) / (1 - k);
        double m = (k == 1) ? 0 : (1 - gPrime - k) / (1 - k);
        double y = (k == 1) ? 0 : (1 - bPrime - k) / (1 - k);

        return new double[]{c, m, y, k};
    }

    private void updateColorsFromHls(Slider hue, Slider lightness, Slider saturation, TextField rgb, TextField cmyk) {
        int[] rgbValues = hlsToRgb(hue.getValue(), lightness.getValue(), saturation.getValue());
        rgb.setText(String.format("R: %d, G: %d, B: %d", rgbValues[0], rgbValues[1], rgbValues[2]));

        double[] cmykValues = rgbToCmyk(rgbValues[0], rgbValues[1], rgbValues[2]);
        cmyk.setText(String.format("C: %.2f, M: %.2f, Y: %.2f, K: %.2f", cmykValues[0], cmykValues[1], cmykValues[2], cmykValues[3]));
    }

    private void updateColorsFromCmyk(Slider cyan, Slider magenta, Slider yellow, Slider key, TextField rgb, TextField hls) {
        int[] rgbValues = cmykToRgb(cyan.getValue(), magenta.getValue(), yellow.getValue(), key.getValue());
        rgb.setText(String.format("R: %d, G: %d, B: %d", rgbValues[0], rgbValues[1], rgbValues[2]));

        double[] hlsValues = rgbToHls(rgbValues[0], rgbValues[1], rgbValues[2]);
        hls.setText(String.format("H: %.0f, L: %.2f, S: %.2f", hlsValues[0], hlsValues[1], hlsValues[2]));
    }

    private void updateColorsFromRgb(Slider red, Slider green, Slider blue, TextField hls, TextField cmyk) {
        int r = (int) red.getValue();
        int g = (int) green.getValue();
        int b = (int) blue.getValue();

        double[] hlsValues = rgbToHls(r, g, b);
        hls.setText(String.format("H: %.0f, L: %.2f, S: %.2f", hlsValues[0], hlsValues[1], hlsValues[2]));

        double[] cmykValues = rgbToCmyk(r, g, b);
        cmyk.setText(String.format("C: %.2f, M: %.2f, Y: %.2f, K: %.2f", cmykValues[0], cmykValues[1], cmykValues[2], cmykValues[3]));
    }

    private void updateColorPickerFromHls(Slider hue, Slider lightness, Slider saturation, ColorPicker colorPicker) {
        int[] rgbValues = hlsToRgb(hue.getValue(), lightness.getValue(), saturation.getValue());
        colorPicker.setValue(Color.rgb(rgbValues[0], rgbValues[1], rgbValues[2]));
    }

    private void updateColorPickerFromCmyk(Slider cyan, Slider magenta, Slider yellow, Slider key, ColorPicker colorPicker) {
        int[] rgbValues = cmykToRgb(cyan.getValue(), magenta.getValue(), yellow.getValue(), key.getValue());
        colorPicker.setValue(Color.rgb(rgbValues[0], rgbValues[1], rgbValues[2]));
    }

    private void updateColorPickerFromRgb(Slider red, Slider green, Slider blue, ColorPicker colorPicker) {
        colorPicker.setValue(Color.rgb((int) red.getValue(), (int) green.getValue(), (int) blue.getValue()));
    }
}
