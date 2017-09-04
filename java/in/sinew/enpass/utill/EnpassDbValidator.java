package in.sinew.enpass.utill;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import in.sinew.enpassengine.Card.DBValidationResult;
import in.sinew.enpassengine.Keychain;
import in.sinew.enpassengine.Keychain4;
import in.sinew.enpassengine.Utils;
import io.enpass.app.R;

public class EnpassDbValidator {

    public interface IDbValidationResult {
        void DBResult(DBValidationResult dBValidationResult);
    }

    public static void ValidateDb(String filename, Context context, IDbValidationResult listener) {
        if (Utils.isDbTypeIsSqlCipher(filename)) {
            askPasswordAndValidateFile(context, filename, listener);
        } else {
            validateDatabase(false, context, filename, null, listener);
        }
    }

    private static void askPasswordAndValidateFile(Context context, String filename, IDbValidationResult listener) {
        Context localContext = context;
        EditText userInput = new EditText(context);
        userInput.setClickable(true);
        userInput.setFocusable(true);
        userInput.requestFocus();
        userInput.setInputType(524433);
        userInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        ((InputMethodManager) context.getSystemService("input_method")).showSoftInput(userInput, 1);
        Builder alertDialogBuilder = new Builder(localContext);
        alertDialogBuilder.setView(userInput);
        alertDialogBuilder.setCancelable(false).setMessage(R.string.provide_password).setTitle(R.string.app_name).setPositiveButton(R.string.ok, new 2(userInput, localContext, filename, listener)).setNegativeButton(R.string.cancel, new 1());
        alertDialogBuilder.create().show();
    }

    private static void validateDatabase(boolean isSqlCipherFile, Context context, String filename, char[] password, IDbValidationResult listener) {
        DBValidationResult result;
        if (isSqlCipherFile) {
            result = Keychain.isValidDatabase(context, filename, password);
        } else {
            result = Keychain4.isValidDatabase(context, filename);
        }
        listener.DBResult(result);
    }

    private static void showEmptyPasswordAlert(Context context) {
        Builder alertDialogBuilder = new Builder(context);
        alertDialogBuilder.setCancelable(false).setMessage(R.string.wrong_password).setPositiveButton(R.string.ok, new 3());
        alertDialogBuilder.create().show();
    }

    public static Keychain openKeychain(Context context, String aFilename, char[] aPassword, DBValidationResult[] aResult) {
        DBValidationResult result;
        if (Utils.isDbTypeIsSqlCipher(aFilename)) {
            result = Keychain.isValidDatabase(context, aFilename, aPassword);
            if (result != DBValidationResult.DBResultPasswordOk) {
                aResult[0] = result;
                return null;
            }
            Keychain keycahin = Keychain.openOrCreate(context, aFilename, aPassword);
            aResult[0] = result;
            return keycahin;
        }
        result = Keychain4.isValidDatabase(context, aFilename);
        if (result == DBValidationResult.DBIsOlder || result == DBValidationResult.DBIsValid) {
            Keychain4 oldKeychain = Keychain4.openOrCreate(context, aFilename, new String(aPassword));
            if (oldKeychain == null) {
                aResult[0] = DBValidationResult.DBResultPasswordMismatch;
                return null;
            }
            oldKeychain.close();
            Keychain keychain = Keychain.upgrade(context, aFilename, aPassword);
            if (keychain == null) {
                aResult[0] = DBValidationResult.DBResultUpgradeFailed;
                return null;
            }
            aResult[0] = DBValidationResult.DBResultPasswordOk;
            return keychain;
        }
        aResult[0] = result;
        return null;
    }
}
