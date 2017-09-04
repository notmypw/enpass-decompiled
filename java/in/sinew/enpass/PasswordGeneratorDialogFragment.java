package in.sinew.enpass;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import com.github.clans.fab.BuildConfig;
import com.google.api.client.http.HttpStatusCodes;
import in.sinew.enpassengine.GeneratedPassword;
import io.enpass.app.R;
import java.util.ArrayList;
import java.util.List;
import zxcvbnjlib.PasswordStrengthInfo;

public class PasswordGeneratorDialogFragment extends DialogFragment {
    private static final int SEPERATOR_CODE_COMMA = 1;
    private static final int SEPERATOR_CODE_DOLLAR = 2;
    private static final int SEPERATOR_CODE_HASH = 3;
    private static final int SEPERATOR_CODE_HYPHEN = 4;
    private static final int SEPERATOR_CODE_PERIOD = 5;
    private static final int SEPERATOR_CODE_SPACE = 6;
    private static final int SEPERATOR_CODE_UNDERSCORE = 7;
    final int EXCLUDE_SYMBOL_LIMIT = 10;
    private final int TAB_WIDTH = LoginActivity.TAB_WIDTH;
    ScrollView aMainLayout;
    CheckBox checkBox_excludeText;
    CheckBox checkBox_includeDigit;
    TextView digitValue;
    View divider;
    EditText et_excludeValue;
    CheckBox mAmbiChar;
    boolean mAmbiguous;
    SeekBar mDigitBar;
    int mDigitLength = SEPERATOR_CODE_HYPHEN;
    StringBuilder mGeneratedPassword = new StringBuilder(BuildConfig.FLAVOR);
    TextView mGeneratedPasswordView;
    View mHistoryDivider;
    LinearLayout mHistoryList;
    boolean mIsFromEditPage = true;
    boolean mIsFromTab = false;
    boolean mIsRecipeClicked = false;
    String mLastCopyPassword = BuildConfig.FLAVOR;
    CheckBox mMixedCaseLetter;
    RelativeLayout mParentLayout;
    SeekBar mPasswordBar;
    private int mPasswordLength = 12;
    TextView mPasswordText;
    public final int[] mRealClearClipboardIntervals = new int[]{30, 60, 120, HttpStatusCodes.STATUS_CODE_MULTIPLE_CHOICES, Integer.MAX_VALUE};
    boolean mRecipe = false;
    View mRecipeDivider;
    TableLayout mSeprator;
    TextView mSepratorText;
    TextView mShowHistory;
    String mShuffleString;
    TextView mStrenthView;
    SeekBar mSymbolBar;
    int mSymbolLength = SEPERATOR_CODE_HASH;
    LinearLayout mToolbarLayout;
    int mUpperCaseLength = SEPERATOR_CODE_HASH;
    SeekBar mUppercaseBar;
    String mUrl = BuildConfig.FLAVOR;
    SeekBar mWordsBar;
    private int mWordsLength = SEPERATOR_CODE_DOLLAR;
    CheckBox pronounceablePassword;
    TextView recipe;
    Spinner sp_separator_list;
    TextView symbolText;
    TextView tv_exclude_text;
    TextView tv_wordLength;
    TextView tv_words_value;
    TextView uppercaseText;
    View view;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.password, container, false);
        this.mIsFromEditPage = getArguments().getBoolean("from_edit", false);
        this.mUrl = getArguments().getString("url_domain");
        this.mIsFromTab = getArguments().getBoolean("IsTab");
        setHasOptionsMenu(true);
        RelativeLayout aMainToolbarLayout = (RelativeLayout) this.view.findViewById(R.id.password_generator_main_toolbar_layout);
        aMainToolbarLayout.invalidate();
        this.mParentLayout = (RelativeLayout) this.view.findViewById(R.id.password_generator_parent_layout);
        View aShadowView = this.view.findViewById(R.id.password_generator_toolbar_shadow);
        if (VERSION.SDK_INT < 21) {
            aShadowView.setVisibility(0);
            aShadowView.bringToFront();
        }
        aMainToolbarLayout.bringToFront();
        this.aMainLayout = (ScrollView) this.view.findViewById(R.id.password_generator_main_layout);
        if (getResources().getConfiguration().smallestScreenWidthDp < LoginActivity.TAB_WIDTH) {
            this.aMainLayout.getLayoutParams().width = -1;
        }
        Toolbar aPasswordGenToolbar = (Toolbar) this.view.findViewById(R.id.password_generator_toolbar);
        if (this.mIsFromTab) {
            getDialog().setOnKeyListener(new 1(this));
            setCancelable(false);
            if (this.mIsFromEditPage) {
                aPasswordGenToolbar.setTitle(R.string.generate);
                aPasswordGenToolbar.setNavigationIcon(R.drawable.ic_action_done);
            } else {
                aPasswordGenToolbar.setTitle(R.string.generate);
                aPasswordGenToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            }
            aPasswordGenToolbar.setNavigationOnClickListener(new 2(this));
            aPasswordGenToolbar.inflateMenu(R.menu.refresh_generatepassword);
            aPasswordGenToolbar.setOnMenuItemClickListener(new 3(this));
        } else {
            ((AppCompatActivity) getActivity()).setSupportActionBar(aPasswordGenToolbar);
        }
        this.mGeneratedPasswordView = (TextView) this.view.findViewById(R.id.generated_password_view);
        this.mToolbarLayout = (LinearLayout) this.view.findViewById(R.id.password_generator_toolbar_layout);
        this.mGeneratedPasswordView.setTypeface(Typeface.MONOSPACE);
        ImageButton aCopyButton = (ImageButton) this.view.findViewById(R.id.password_generator_copy_btn);
        this.mStrenthView = (TextView) this.view.findViewById(R.id.password_generator_strength);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (!this.mIsFromTab) {
            if (this.mIsFromEditPage) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_action_done);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(R.string.generate);
                actionBar.setHomeButtonEnabled(true);
                aPasswordGenToolbar.setNavigationIcon(R.drawable.ic_action_done);
            } else {
                actionBar.setTitle(R.string.generate);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        this.mPasswordBar = (SeekBar) this.view.findViewById(R.id.password_length);
        this.mPasswordBar.setMax(50);
        this.mWordsBar = (SeekBar) this.view.findViewById(R.id.words_length);
        this.mWordsBar.setMax(10);
        this.mPasswordText = (TextView) this.view.findViewById(R.id.passwordValue);
        this.digitValue = (TextView) this.view.findViewById(R.id.digitValue);
        this.symbolText = (TextView) this.view.findViewById(R.id.symbolValue);
        this.uppercaseText = (TextView) this.view.findViewById(R.id.uppercaseValue);
        this.uppercaseText.setText(getResources().getString(R.string.default_uppercase_length));
        this.checkBox_excludeText = (CheckBox) this.view.findViewById(R.id.exclude_checkbox);
        this.et_excludeValue = (EditText) this.view.findViewById(R.id.exclude_value);
        this.checkBox_includeDigit = (CheckBox) this.view.findViewById(R.id.checkbox_add_digits);
        this.sp_separator_list = (Spinner) this.view.findViewById(R.id.spinner_separator_list);
        this.mAmbiChar = (CheckBox) this.view.findViewById(R.id.ambi_char);
        this.mShowHistory = (TextView) this.view.findViewById(R.id.show_history);
        this.mHistoryList = (LinearLayout) this.view.findViewById(R.id.history_list);
        this.mHistoryDivider = this.view.findViewById(R.id.show_history_bottom_divider);
        this.divider = this.view.findViewById(R.id.show_history_divider);
        this.mRecipeDivider = this.view.findViewById(R.id.show_recipe_bottom_divider);
        this.pronounceablePassword = (CheckBox) this.view.findViewById(R.id.pronounceablePassword);
        this.pronounceablePassword.setChecked(false);
        this.tv_words_value = (TextView) this.view.findViewById(R.id.wordsValue);
        this.tv_wordLength = (TextView) this.view.findViewById(R.id.words_pwdLenght);
        this.mUppercaseBar = (SeekBar) this.view.findViewById(R.id.uppercase_length);
        this.recipe = (TextView) this.view.findViewById(R.id.recipe);
        this.mDigitBar = (SeekBar) this.view.findViewById(R.id.digit_length);
        this.mSymbolBar = (SeekBar) this.view.findViewById(R.id.symbol_length);
        this.mSeprator = (TableLayout) this.view.findViewById(R.id.seprator_group);
        this.mSepratorText = (TextView) this.view.findViewById(R.id.seprator_text);
        this.tv_exclude_text = (TextView) this.view.findViewById(R.id.exclude_text);
        TextView textView = this.tv_exclude_text;
        String string = getString(R.string.exclude_text);
        Object[] objArr = new Object[SEPERATOR_CODE_COMMA];
        objArr[0] = Integer.valueOf(10);
        textView.setText(String.format(string, objArr));
        this.mMixedCaseLetter = (CheckBox) this.view.findViewById(R.id.mixed_case_letter);
        this.mPasswordText.setText(getResources().getString(R.string.default_password_length));
        this.digitValue.setText(getResources().getString(R.string.default_digit_length));
        this.symbolText.setText(getResources().getString(R.string.default_symbol_length));
        this.uppercaseText.setText(getResources().getString(R.string.default_uppercase_length));
        this.pronounceablePassword.setChecked(EnpassApplication.getInstance().getAppSettings().isPronounceable());
        this.mUppercaseBar.setMax(32);
        this.mDigitBar.setMax(32);
        this.mSymbolBar.setMax(32);
        this.mAmbiguous = RandomPasswordGenerator.getAmbiguousCharacterPref();
        this.mAmbiChar.setChecked(this.mAmbiguous);
        this.mPasswordLength = RandomPasswordGenerator.getPasswordLengthPref();
        this.mPasswordBar.setProgress(this.mPasswordLength);
        this.mPasswordText.setText(Integer.toString(this.mPasswordLength));
        this.mWordsLength = PronounceablePasswordGenerator.getPasswordWordsLengthPref();
        this.mWordsBar.setProgress(this.mWordsLength);
        this.tv_words_value.setText(BuildConfig.FLAVOR + this.mWordsLength);
        this.mDigitLength = RandomPasswordGenerator.getPasswordDigitLengthPref();
        this.mDigitBar.setProgress(this.mDigitLength);
        this.digitValue.setText(Integer.toString(this.mDigitLength));
        this.mSymbolLength = RandomPasswordGenerator.getPasswordSymbolLengthPref();
        this.mSymbolBar.setProgress(this.mSymbolLength);
        this.symbolText.setText(Integer.toString(this.mSymbolLength));
        this.mUpperCaseLength = RandomPasswordGenerator.getPasswordUppercaseLengthPref();
        this.mUppercaseBar.setProgress(this.mUpperCaseLength);
        this.uppercaseText.setText(Integer.toString(this.mUpperCaseLength));
        this.checkBox_excludeText.setChecked(RandomPasswordGenerator.getExcludeCase());
        this.et_excludeValue.setText(RandomPasswordGenerator.getExcludeCharacters());
        if (RandomPasswordGenerator.getExcludeCase()) {
            this.et_excludeValue.setVisibility(0);
            this.tv_exclude_text.setVisibility(0);
        }
        this.mMixedCaseLetter.setChecked(PronounceablePasswordGenerator.getUpperCase());
        this.et_excludeValue.setInputType(524288);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), 2130903056, 17367048);
        adapter.setDropDownViewResource(17367049);
        this.sp_separator_list.setAdapter(adapter);
        this.sp_separator_list.setSelection(SEPERATOR_CODE_HASH);
        if (PronounceablePasswordGenerator.getIncludeDigits()) {
            this.checkBox_includeDigit.setChecked(true);
        } else {
            this.checkBox_includeDigit.setChecked(false);
        }
        int aLastSeprator = PronounceablePasswordGenerator.getSeparatorType();
        if (aLastSeprator == SEPERATOR_CODE_COMMA) {
            this.sp_separator_list.setSelection(0);
        } else if (aLastSeprator == SEPERATOR_CODE_DOLLAR) {
            this.sp_separator_list.setSelection(SEPERATOR_CODE_COMMA);
        } else if (aLastSeprator == SEPERATOR_CODE_HASH) {
            this.sp_separator_list.setSelection(SEPERATOR_CODE_DOLLAR);
        } else if (aLastSeprator == SEPERATOR_CODE_HYPHEN) {
            this.sp_separator_list.setSelection(SEPERATOR_CODE_HASH);
        } else if (aLastSeprator == SEPERATOR_CODE_PERIOD) {
            this.sp_separator_list.setSelection(SEPERATOR_CODE_HYPHEN);
        } else if (aLastSeprator == SEPERATOR_CODE_SPACE) {
            this.sp_separator_list.setSelection(SEPERATOR_CODE_PERIOD);
        } else if (aLastSeprator == SEPERATOR_CODE_UNDERSCORE) {
            this.sp_separator_list.setSelection(SEPERATOR_CODE_SPACE);
        }
        this.checkBox_includeDigit.setOnCheckedChangeListener(new 4(this));
        showRecipe();
        generate();
        addHistoryView();
        this.mHistoryList.setVisibility(8);
        this.mMixedCaseLetter.setOnCheckedChangeListener(new 5(this));
        InputFilter filter = new 6(this);
        EditText editText = this.et_excludeValue;
        InputFilter[] inputFilterArr = new InputFilter[SEPERATOR_CODE_COMMA];
        inputFilterArr[0] = filter;
        editText.setFilters(inputFilterArr);
        this.et_excludeValue.setCustomSelectionActionModeCallback(new 7(this));
        this.et_excludeValue.addTextChangedListener(new 8(this));
        this.mShowHistory.setOnClickListener(new 9(this));
        aCopyButton.setOnClickListener(new 10(this));
        this.sp_separator_list.setOnItemSelectedListener(new 11(this));
        this.recipe.setOnClickListener(new 12(this));
        this.pronounceablePassword.setOnCheckedChangeListener(new 13(this));
        this.mWordsBar.setOnSeekBarChangeListener(new 14(this));
        this.mPasswordBar.setOnSeekBarChangeListener(new 15(this));
        this.mDigitBar.setOnSeekBarChangeListener(new 16(this));
        this.mSymbolBar.setOnSeekBarChangeListener(new 17(this));
        this.mUppercaseBar.setOnSeekBarChangeListener(new 18(this));
        this.mAmbiChar.setOnCheckedChangeListener(new 19(this));
        this.mParentLayout.setOnTouchListener(new 20(this));
        this.checkBox_excludeText.setOnCheckedChangeListener(new 21(this));
        return this.view;
    }

    public void generate() {
        this.mShuffleString = PasswordGeneratorActivity.generatePassword();
        ArrayList<String> userInputsList = new ArrayList();
        PasswordStrengthInfo strength = new PasswordStrengthInfo(getActivity());
        strength.calculateStrength(this.mShuffleString, userInputsList);
        int score = strength.getScore();
        int toolbarcolor = 0;
        if (score == 0) {
            this.mStrenthView.setText(getString(R.string.strength) + " : " + getString(R.string.very_weak));
            toolbarcolor = Color.rgb(183, SEPERATOR_CODE_COMMA, SEPERATOR_CODE_COMMA);
        } else if (score == SEPERATOR_CODE_COMMA) {
            this.mStrenthView.setText(getString(R.string.strength) + " : " + getString(R.string.weak));
            toolbarcolor = Color.rgb(239, 72, 0);
        } else if (score == SEPERATOR_CODE_DOLLAR) {
            this.mStrenthView.setText(getString(R.string.strength) + " : " + getString(R.string.fair));
            toolbarcolor = Color.rgb(240, 144, 9);
        } else if (score == SEPERATOR_CODE_HASH) {
            this.mStrenthView.setText(getString(R.string.strength) + " : " + getString(R.string.good));
            toolbarcolor = Color.rgb(106, 176, 20);
        } else if (score == SEPERATOR_CODE_HYPHEN) {
            this.mStrenthView.setText(getString(R.string.strength) + " : " + getString(R.string.strong));
            toolbarcolor = Color.rgb(0, 108, 36);
        }
        this.tv_wordLength.setTextColor(toolbarcolor);
        this.mToolbarLayout.setBackgroundColor(toolbarcolor);
        if (VERSION.SDK_INT >= 21 && !this.mIsFromTab) {
            float[] hsv = new float[SEPERATOR_CODE_HASH];
            Color.colorToHSV(toolbarcolor, hsv);
            hsv[SEPERATOR_CODE_DOLLAR] = hsv[SEPERATOR_CODE_DOLLAR] * 0.8f;
            int statusBarColor = Color.HSVToColor(hsv);
            getActivity().getWindow().setStatusBarColor(statusBarColor);
            this.tv_wordLength.setTextColor(statusBarColor);
        }
        this.mGeneratedPasswordView.setText(this.mShuffleString);
        if (this.pronounceablePassword.isChecked()) {
            this.tv_wordLength.setText(getString(R.string.password_length) + "-" + this.mShuffleString.length());
        }
    }

    public void changecolor(int value) {
        int aColor;
        if (value <= 30) {
            this.mToolbarLayout.setBackgroundResource(2131099795);
            if (VERSION.SDK_INT >= 21 && !this.mIsFromTab) {
                aColor = (int) (((double) value) * 2.5d);
                getActivity().getWindow().setStatusBarColor(Color.parseColor("#951F1F"));
                this.tv_wordLength.setTextColor(Color.parseColor("#951F1F"));
            }
        } else if (value <= 30 || value >= 80) {
            this.mToolbarLayout.setBackgroundColor(getResources().getColor(2131099796));
            if (VERSION.SDK_INT >= 21 && !this.mIsFromTab) {
                aColor = (int) (((double) value) * 2.5d);
                getActivity().getWindow().setStatusBarColor(Color.parseColor("#1E7E42"));
                this.tv_wordLength.setTextColor(Color.parseColor("#1E7E42"));
            }
        } else {
            int toolbarcolor = Color.rgb(255, (int) (((double) value) * 2.5d), 0);
            this.mToolbarLayout.setBackgroundColor(toolbarcolor);
            if (VERSION.SDK_INT >= 21 && !this.mIsFromTab) {
                float[] hsv = new float[SEPERATOR_CODE_HASH];
                Color.colorToHSV(toolbarcolor, hsv);
                hsv[SEPERATOR_CODE_DOLLAR] = hsv[SEPERATOR_CODE_DOLLAR] * 0.8f;
                int statusBarColor = Color.HSVToColor(hsv);
                getActivity().getWindow().setStatusBarColor(statusBarColor);
                this.tv_wordLength.setTextColor(statusBarColor);
            }
        }
    }

    public void showRecipe() {
        if (this.mRecipe) {
            this.mRecipe = false;
            if (this.pronounceablePassword.isChecked()) {
                ((LinearLayout) this.view.findViewById(R.id.digit_layout)).setVisibility(8);
                ((LinearLayout) this.view.findViewById(R.id.symbol_layout)).setVisibility(8);
                ((LinearLayout) this.view.findViewById(R.id.uppercase_layout)).setVisibility(8);
                ((LinearLayout) this.view.findViewById(R.id.exclude_layout)).setVisibility(8);
                ((LinearLayout) this.view.findViewById(R.id.password_layout)).setVisibility(8);
                ((LinearLayout) this.view.findViewById(R.id.words_layout)).setVisibility(0);
                this.checkBox_includeDigit.setVisibility(0);
                this.mMixedCaseLetter.setVisibility(0);
                this.mAmbiChar.setVisibility(8);
                this.mSeprator.setVisibility(8);
                this.mSepratorText.setVisibility(0);
                this.sp_separator_list.setVisibility(0);
                return;
            }
            this.checkBox_includeDigit.setVisibility(8);
            this.mSepratorText.setVisibility(8);
            this.mMixedCaseLetter.setVisibility(8);
            ((LinearLayout) this.view.findViewById(R.id.digit_layout)).setVisibility(0);
            ((LinearLayout) this.view.findViewById(R.id.symbol_layout)).setVisibility(0);
            ((LinearLayout) this.view.findViewById(R.id.uppercase_layout)).setVisibility(0);
            ((LinearLayout) this.view.findViewById(R.id.exclude_layout)).setVisibility(8);
            ((LinearLayout) this.view.findViewById(R.id.password_layout)).setVisibility(0);
            ((LinearLayout) this.view.findViewById(R.id.words_layout)).setVisibility(8);
            this.mAmbiChar.setVisibility(0);
            this.mSeprator.setVisibility(8);
            this.sp_separator_list.setVisibility(8);
            return;
        }
        this.mRecipe = true;
        ((LinearLayout) this.view.findViewById(R.id.digit_layout)).setVisibility(8);
        ((LinearLayout) this.view.findViewById(R.id.symbol_layout)).setVisibility(8);
        ((LinearLayout) this.view.findViewById(R.id.uppercase_layout)).setVisibility(8);
        ((LinearLayout) this.view.findViewById(R.id.exclude_layout)).setVisibility(8);
        if (EnpassApplication.getInstance().getAppSettings().isPronounceable()) {
            ((LinearLayout) this.view.findViewById(R.id.password_layout)).setVisibility(8);
            ((LinearLayout) this.view.findViewById(R.id.words_layout)).setVisibility(0);
        } else {
            ((LinearLayout) this.view.findViewById(R.id.password_layout)).setVisibility(0);
            ((LinearLayout) this.view.findViewById(R.id.words_layout)).setVisibility(8);
        }
        this.checkBox_includeDigit.setVisibility(8);
        this.mAmbiChar.setVisibility(8);
        this.mSeprator.setVisibility(8);
        this.mMixedCaseLetter.setVisibility(8);
        this.mSepratorText.setVisibility(8);
        this.sp_separator_list.setVisibility(8);
    }

    public void addHistoryView() {
        List<GeneratedPassword> aPasswordHistoryList = null;
        this.mHistoryList.removeAllViews();
        if (EnpassApplication.getInstance().getKeychain() != null) {
            aPasswordHistoryList = EnpassApplication.getInstance().getKeychain().getAllPasswordsFromHistory();
        }
        if (aPasswordHistoryList == null || aPasswordHistoryList.size() == 0) {
            this.mHistoryList.setVisibility(8);
            this.mShowHistory.setVisibility(8);
            this.mHistoryDivider.setVisibility(8);
            this.divider.setVisibility(8);
            return;
        }
        for (GeneratedPassword aGenPass : aPasswordHistoryList) {
            PasswordHistory passHistory = new PasswordHistory(getActivity(), aGenPass);
            this.mHistoryList.addView(passHistory);
            passHistory.setOnClickListener(new 22(this));
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!this.mIsFromTab) {
            inflater.inflate(R.menu.refresh_generatepassword, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                if (this.mIsFromEditPage) {
                    Intent intent = new Intent();
                    this.mLastCopyPassword = this.mShuffleString;
                    intent.putExtra("generatedPassword", this.mLastCopyPassword);
                    Activity activity = getActivity();
                    getActivity();
                    activity.setResult(-1, intent);
                }
                getActivity().finish();
                break;
            case R.id.menu_refreshPassword /*2131296749*/:
                generate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDetach() {
        super.onDetach();
        if (!this.mLastCopyPassword.isEmpty() && EnpassApplication.getInstance().getKeychain() != null) {
            EnpassApplication.getInstance().getKeychain().saveGeneratedPassword(this.mLastCopyPassword, this.mUrl);
        }
    }
}
