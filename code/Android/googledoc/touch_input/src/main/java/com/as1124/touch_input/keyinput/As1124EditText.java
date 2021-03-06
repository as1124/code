package com.as1124.touch_input.keyinput;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * 自定义文本输入框.<br/>
 * Usually, you should use {@link #onKeyUp(int, KeyEvent)} if you want to be sure that you receive only one event.
 * If the user presses and holds the button, then {@link #onKeyDown(int, KeyEvent)} is called multiple times.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124EditText extends EditText {

    public As1124EditText(Context context) {
        super(context);
    }

    public As1124EditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public As1124EditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.i("As1124Input", "KeyCode == " + event.getKeyCode() + ", Shift state = " + event.isShiftPressed());
        switch (keyCode) {
            case KeyEvent.KEYCODE_D:
                Log.i("As1124Input", "D is taped!");
                return true;
            case KeyEvent.KEYCODE_F:
                Log.i("As1124Input", "F is taped!");
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }


    @Override
    public void dispatchWindowFocusChanged(boolean hasFocus) {
        Log.i("FOCUS_TEST", "View#dispatchWindowFocusChanged");
        super.dispatchWindowFocusChanged(hasFocus);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.i("FOCUS_TEST", "View#onWindowFocusChanged");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("FOCUS_TEST", "View#dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("FOCUS_TEST", "View#onTouchEvent");

        return super.onTouchEvent(event);


//        if (!this.isFocused()) {
//            requestFocus();
//        }
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
//        return true;
    }

}
