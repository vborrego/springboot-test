package hello;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

@Component
public class ThreadTimer extends Thread {
  private int delaySeconds;
  private Logger logger;
  private boolean running;
  private Object monitor=new Object();
  private ArrayList<Object> subscribers;

  public ThreadTimer() {
    this.logger = LoggerFactory.getLogger(ThreadTimer.class);
    logger.info("Created instance of " + this.getClass().getSimpleName());
    this.running = true;
    this.delaySeconds = 5 * 1000;
    this.setName(this.getClass().getSimpleName() + "_" + this.getName());
    this.subscribers = new ArrayList<Object>();
  }

  public void addSubscriber(Object subscriber){
    this.subscribers.add(subscriber);
  }

  @PostConstruct
  public void init() {
    logger.info("Starting the thread");
    this.start();
  }

  @Override
  public void run() {
    while (running) {
      try {
        Thread.sleep(this.delaySeconds);
        logger.info("Delay " + this.getClass().getSimpleName());

        for(Object o: this.subscribers){
          synchronized(o){
            o.notify();
          }
        }
      }
      catch (InterruptedException e) {
          logger.info("ThreadTimer interrupted exception:" + e.getMessage() );
      }
      catch (Exception e) {
          e.printStackTrace();
          logger.info("ThreadTimer exception:" + e.getMessage() );
          stopRunning();
      }
    }
    logger.info("Exited " + this.getClass().getSimpleName());
  }

  public void startRunning() {
    this.running = true;
  }

  public void stopRunning() {
    this.running = false;
  }

  public void destroy(){
    logger.info("Called destroy");
    this.stopRunning();
    this.interrupt();
  }
}
