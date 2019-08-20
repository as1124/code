package com.as1124.touch_input.softinput.way1;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ExKey extends Keyboard.Key {

    public int backgroundResID;

    public ExKey(Resources res, Keyboard.Row parent, int x, int y, XmlResourceParser parser) {
        super(res, parent, x, y, parser);

        backgroundResID = parser.getAttributeResourceValue(null, KeyboardConstants.ATTR_KEY_BACKGROUND, -1);

    }

    @Override
    public boolean isInside(int x, int y) {
        return (x >= this.x && x <= (this.x + this.width) && y >= this.y && (y <= this.y + this.height));
    }

    @Override
    public int[] getCurrentDrawableState() {
//        if (this.modifier) {
//            if (pressed) {
//                return new int[]{
//                        android.R.attr.state_checkable,
//                        android.R.attr.state_checked
//                };
//            } else {
//                return new int[]{
//                        android.R.attr.state_checkable
//                };
//            }
//        } else {
        return super.getCurrentDrawableState();
//        }
    }
}
