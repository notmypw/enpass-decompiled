package in.sinew.enpass.fingerprint;

import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;

public interface FingerprintUiHelper$Callback {
    void onAuthenticated(AuthenticationResult authenticationResult);

    void onAuthenticationFailed();

    void onAuthenticationHelp(int i, CharSequence charSequence);

    void onError();

    void onManyAttempts();
}
