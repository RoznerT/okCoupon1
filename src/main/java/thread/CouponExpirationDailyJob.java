package thread;

import db_util.DataBaseUtils;
import java.sql.SQLException;


public class CouponExpirationDailyJob implements Runnable {
    private boolean quit = false;

    public CouponExpirationDailyJob() {
    }


    @Override
    public void run() {
        while (!quit) {
            try {
                DataBaseUtils.runQuery("DELETE FROM coupon_project.coupons WHERE DATE(end_date)<CURDATE()");
            } catch (SQLException err) {
                System.out.println(err.getMessage());
            } finally {
                try {
                    Thread.sleep(24 * 60 * 60 * 1000);
                } catch (InterruptedException err) {
                    System.out.println(err.getMessage());
                    stop();
                }
            }
        }
    }

    /**
     * interrupt the thread and stop the coupon expiration daily job
     */
    public void stop() {
        System.out.println("coupon expiration scan is about to pause");
        this.quit = true;
    }
}
