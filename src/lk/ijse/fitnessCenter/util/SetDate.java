package lk.ijse.fitnessCenter.util;

import javafx.application.Platform;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;

public class SetDate {
    static Thread thread;
    public static void setDateTime(Text lblTime, Text lblDate) {
        thread=new Thread(()->{


            while (true){
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat date=new SimpleDateFormat("yyyy:MM:dd");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final
                String time=sdf.format(new java.util.Date());
                String date1=date.format(new java.util.Date());
                Platform.runLater(()->{
                    lblTime.setText(time);
                    lblDate.setText(date1);

                });
            }

        });
        thread.start();
    }
}
