package hello;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WaitThread extends Thread {
  private Logger logger;
  private boolean running;
  private Object monitor;
  private ThreadTimer timerThread;

  public WaitThread(ThreadTimer timerThread) {
    //constructor injection (mandatory/required bean)  
    this.timerThread = timerThread;  
    this.logger = LoggerFactory.getLogger(WaitThread.class);
    logger.info("Created instance of " + this.getClass().getSimpleName());
    this.running = true;
    this.setName(this.getClass().getSimpleName() + "_" + this.getName());
    this.monitor=new Object();
  }

  @PostConstruct
  public void init() {
    this.timerThread.addSubscriber(this);
    logger.info("Starting the thread");
    this.start();
  }

  public void run() {
    while (running) {
      try {
        synchronized(this){
          this.wait();
          logger.info("Notification received.");
        }
      }
      catch (InterruptedException e) {
          logger.info("ThreadTimer interrupted exception:" + e.getMessage() );
      }
      catch (Exception e) {
          logger.info("WaitThread exception:" + e.getMessage() );
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