package com.sagetech.rxjavagoogleio2015;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.android.app.RxActivity;
import rx.android.lifecycle.LifecycleObservable;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Action1;
import rx.functions.Func3;


public class MainActivity extends RxActivity {
    @InjectView(R.id.txtName1)
    EditText txtName1;

    @InjectView(R.id.txtName2)
    EditText txtName2;

    @InjectView(R.id.txtName3)
    EditText txtName3;

    @InjectView(R.id.txtStatus)
    TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        startObservingFor3MandatoryFieldsBeingPopulated();
    }

    private void startObservingFor3MandatoryFieldsBeingPopulated() {
        Observable<OnTextChangeEvent> onTextChangeTxtName1Observable = createOnTextChangeEventObservable(txtName1);
        Observable<OnTextChangeEvent> onTextChangeTxtName2Observable = createOnTextChangeEventObservable(txtName2);
        Observable<OnTextChangeEvent> onTextChangeTxtName3Observable = createOnTextChangeEventObservable(txtName3);

        Observable
                .combineLatest(
                        onTextChangeTxtName1Observable, onTextChangeTxtName2Observable, onTextChangeTxtName3Observable, validateStrings())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        updateStatusText(aBoolean);
                    }
                });
    }

    private Func3<OnTextChangeEvent, OnTextChangeEvent, OnTextChangeEvent, Boolean> validateStrings() {
        return new Func3<OnTextChangeEvent, OnTextChangeEvent, OnTextChangeEvent, Boolean>() {
            @Override
            public Boolean call(OnTextChangeEvent onTextChangeEvent, OnTextChangeEvent onTextChangeEvent2, OnTextChangeEvent onTextChangeEvent3) {
                String txt1 = onTextChangeEvent.text().toString();
                String txt2 = onTextChangeEvent2.text().toString();
                String txt3 = onTextChangeEvent3.text().toString();

                return isValidText(txt1) && isValidText(txt2) && isValidText(txt3);

            }

            public boolean isValidText(String txt) {
                return txt != null && !"".equals(txt.trim());
            }
        };
    }

    private void updateStatusText(Boolean isValid) {
        if (isValid) {
            txtStatus.setText("Good");
        } else {
            txtStatus.setText("Bad");
        }
    }

    public Observable<OnTextChangeEvent> createOnTextChangeEventObservable(EditText txtName) {
        return LifecycleObservable.bindActivityLifecycle(lifecycle(), WidgetObservable.text(txtName));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
