package domain.model.Cron;

import java.util.TimerTask;

public class MyTask extends TimerTask {
    public MyTask(){
    }

    @Override
    public void run() {
        System.out.println("Hi see you after 10 seconds");
    }
}
