package in.sinew.enpass.locker;

import android.app.Activity;
import android.os.Handler;
import com.google.api.client.http.HttpStatusCodes;
import in.sinew.enpass.EnpassApplication;
import in.sinew.enpass.LoginActivity;
import in.sinew.enpass.keyboard.enpasskeyboard.EnpassInputMethodService;
import java.util.Date;

public class EnpassAutoLocker {
    final int NEVER = 10;
    private final int[] RealAutolockIntervals = new int[]{30, 60, HttpStatusCodes.STATUS_CODE_MULTIPLE_CHOICES, LoginActivity.TAB_WIDTH, 1800, 3600, 18000, 43200};
    int aMinTime = 0;
    EnpassApplication app = EnpassApplication.getInstance();
    Handler handler = new Handler();
    Handler mKeyboardHandler = new Handler();
    Runnable mKeyboardRunnable = null;
    Runnable runnable = null;

    public enum LoginScreenEnum {
        showPin,
        showPassword,
        showFingerScanner
    }

    public LoginScreenEnum getLoginScreen(long aSinceInactive) {
        long aPasswordTimeInterval = (long) this.RealAutolockIntervals[this.app.getAppSettings().getAutoLockInterval()];
        boolean quickUnlock = this.app.getAppSettings().getQuickUnlock();
        boolean fingerprintScanner = this.app.getAppSettings().getFingerprintScannerStatus();
        if (this.app.getAppSettings().getLockOnLeaving()) {
            if (aSinceInactive < 1) {
                return null;
            }
            if (quickUnlock) {
                return LoginScreenEnum.showPin;
            }
            if (fingerprintScanner) {
                return LoginScreenEnum.showFingerScanner;
            }
            return LoginScreenEnum.showPassword;
        } else if (quickUnlock && aPasswordTimeInterval < aSinceInactive) {
            return LoginScreenEnum.showPin;
        } else {
            if (fingerprintScanner && aPasswordTimeInterval < aSinceInactive) {
                return LoginScreenEnum.showFingerScanner;
            }
            if (quickUnlock || fingerprintScanner || aPasswordTimeInterval >= aSinceInactive) {
                return null;
            }
            return LoginScreenEnum.showPassword;
        }
    }

    public void appUnlocked(final Activity activityContext) {
        long idleTime = (new Date().getTime() - this.app.waiter.getLastUsedTime()) / 1000;
        long aMinTimeSec = (long) this.RealAutolockIntervals[this.aMinTime];
        if (idleTime >= aMinTimeSec) {
            this.app.showLogin(activityContext);
            if (this.runnable != null) {
                this.handler.removeCallbacks(this.runnable);
                return;
            }
            return;
        }
        long time = (aMinTimeSec - idleTime) * 1000;
        this.runnable = new Runnable() {
            public void run() {
                EnpassAutoLocker.this.appUnlocked(activityContext);
            }
        };
        this.handler.postDelayed(this.runnable, time);
    }

    public void keyboardUnlocked() {
        long idleTime = (new Date().getTime() - this.app.mKeyboardTimeTracker.getLastUsedTime()) / 1000;
        long aMinTimeSec = (long) this.RealAutolockIntervals[this.aMinTime];
        if (idleTime >= aMinTimeSec) {
            EnpassInputMethodService.getInstance().showLoginScreen(getLoginScreen(idleTime));
            if (this.mKeyboardRunnable != null) {
                this.mKeyboardHandler.removeCallbacks(this.mKeyboardRunnable);
                return;
            }
            return;
        }
        long time = (aMinTimeSec - idleTime) * 1000;
        this.mKeyboardRunnable = new Runnable() {
            public void run() {
                EnpassAutoLocker.this.keyboardUnlocked();
            }
        };
        this.mKeyboardHandler.postDelayed(this.mKeyboardRunnable, time);
    }

    public int getMinLockingTime() {
        int passwordInterval = this.app.getAppSettings().getAutoLockInterval();
        if (this.app.getAppSettings().getQuickUnlock()) {
            this.aMinTime = passwordInterval;
            this.app.setLoginScreen(LoginScreenEnum.showPin);
        } else if (this.app.getAppSettings().getFingerprintScannerStatus()) {
            this.aMinTime = passwordInterval;
            this.app.setLoginScreen(LoginScreenEnum.showFingerScanner);
        } else {
            this.aMinTime = passwordInterval;
            this.app.setLoginScreen(LoginScreenEnum.showPassword);
        }
        return this.aMinTime;
    }

    public void clear() {
        if (this.runnable != null) {
            this.handler.removeCallbacks(this.runnable);
        }
    }

    public void clearKeyboardHandler() {
        if (this.mKeyboardRunnable != null) {
            this.mKeyboardHandler.removeCallbacks(this.mKeyboardRunnable);
        }
    }
}
