package xyz.santima.homepi.ui.impl.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;


public class CustomMaterialDialogBuilder extends MaterialDialog.Builder {
    private Context mContext;
    private List<EditText> mEditTexts;
    private List<InputValidator> mValidators;
    private List<CheckBox> mCheckboxes;
    private LinearLayout mRootView;

    public CustomMaterialDialogBuilder(@NonNull Context context) {
        super(context);
        mContext = context;
        mEditTexts = new ArrayList<>();
        mValidators = new ArrayList<>();
        mCheckboxes = new ArrayList<>();

        mRootView = new LinearLayout(context);
        mRootView.setOrientation(LinearLayout.VERTICAL);

        this.autoDismiss(false);
    }

    public CustomMaterialDialogBuilder addCheckbox(CharSequence title){
        return addCheckbox(title, false);
    }

    public CustomMaterialDialogBuilder addCheckbox(CharSequence title, boolean checked){
        CheckBox newCheckBox = new CheckBox(mContext);
        newCheckBox.setText(title);
        newCheckBox.setChecked(checked);

        mCheckboxes.add(newCheckBox);
        mRootView.addView(newCheckBox);

        this.customView(mRootView, true);
        return this;
    }

    public CustomMaterialDialogBuilder addInput(CharSequence preFill, CharSequence hint, @NonNull InputValidator validator) {
        EditText newEditText = new EditText(mContext);
        newEditText.setText(preFill);
        newEditText.setHint(hint);

        mEditTexts.add(newEditText);
        mValidators.add(validator);
        mRootView.addView(newEditText);

        this.customView(mRootView, true);
        return this;
    }

    public CustomMaterialDialogBuilder addInput(CharSequence preFill, CharSequence hint) {
        return addInput(preFill, hint, new InputValidator() {
            @Override
            public CharSequence validate(CharSequence input) {
                return null;
            }
        });
    }

    public CustomMaterialDialogBuilder addInput(@StringRes int preFill, @StringRes int hint, @NonNull InputValidator validator) {
        return addInput(
                preFill == 0 ? null : mContext.getString(preFill),
                hint == 0 ? null : mContext.getString(hint),
                validator
        );
    }

    public CustomMaterialDialogBuilder addInput(@StringRes int preFill, @StringRes int hint) {
        return addInput(preFill, hint, new InputValidator() {
            @Override
            public CharSequence validate(CharSequence input) {
                return null;
            }
        });
    }

    public CustomMaterialDialogBuilder inputs(@NonNull final CustomInputsCallback callback) {
        this.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);

                List<CharSequence> inputs = new ArrayList<>();
                for (EditText editText : mEditTexts) {
                    inputs.add(editText.getText());
                }

                boolean allInputsValidated = true;
                for (int i = 0; i < inputs.size(); i++) {
                    CharSequence input = inputs.get(i);
                    CharSequence errorMessage = mValidators.get(i).validate(input);
                    boolean validated = errorMessage == null;
                    if (!validated) {
                        mEditTexts.get(i).setError(errorMessage);
                        allInputsValidated = false;
                    }
                }

                callback.onInputs(dialog, inputs, allInputsValidated);

                if (allInputsValidated) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
                dialog.dismiss();
            }

            @Override
            public void onNeutral(MaterialDialog dialog) {
                super.onNeutral(dialog);
                dialog.dismiss();
            }
        });
        return this;
    }

    public CustomMaterialDialogBuilder checboxes(@NonNull final CustomCheckboxesCallback callback) {
        this.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);

                List<Boolean> checkboxes = new ArrayList<>();
                for (CheckBox checkBox : mCheckboxes) {
                    checkboxes.add(checkBox.isChecked());
                }

                callback.onCheckBoxes(dialog, checkboxes);

                dialog.dismiss();

            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
                dialog.dismiss();
            }

            @Override
            public void onNeutral(MaterialDialog dialog) {
                super.onNeutral(dialog);
                dialog.dismiss();
            }
        });
        return this;
    }

    public CustomMaterialDialogBuilder all(@NonNull final CustomAllCallback callback) {
        this.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);

                List<Boolean> checkboxes = new ArrayList<>();
                for (CheckBox checkBox : mCheckboxes) {
                    checkboxes.add(checkBox.isChecked());
                }

                List<CharSequence> inputs = new ArrayList<>();
                for (EditText editText : mEditTexts) {
                    inputs.add(editText.getText());
                }

                boolean allInputsValidated = true;
                for (int i = 0; i < inputs.size(); i++) {
                    CharSequence input = inputs.get(i);
                    CharSequence errorMessage = mValidators.get(i).validate(input);
                    boolean validated = errorMessage == null;
                    if (!validated) {
                        mEditTexts.get(i).setError(errorMessage);
                        allInputsValidated = false;
                    }
                }

                callback.onAll(dialog, checkboxes, inputs, allInputsValidated);

                if (allInputsValidated) {
                    dialog.dismiss();
                }

            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
                dialog.dismiss();
            }

            @Override
            public void onNeutral(MaterialDialog dialog) {
                super.onNeutral(dialog);
                dialog.dismiss();
            }
        });
        return this;
    }

    public interface CustomInputsCallback {
        void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated);
    }

    public interface CustomCheckboxesCallback {
        void onCheckBoxes(MaterialDialog dialog, List<Boolean> checkboxes);
    }

    public interface CustomAllCallback {
        void onAll(MaterialDialog dialog, List<Boolean> checkboxes, List<CharSequence> inputs, boolean allInputsValidated);

    }

    public interface InputValidator {
        /**
         * Validate text input
         *
         * @param input text to be validated
         * @return error message. Null if validated
         */
        CharSequence validate(CharSequence input);
    }

    public InputValidator NonEmptyInputValidator = new InputValidator() {
        @Override
        public CharSequence validate(CharSequence input) {
            return TextUtils.isEmpty(input) ? mContext.getString(android.R.string.no) : null;
        }
    };

}
