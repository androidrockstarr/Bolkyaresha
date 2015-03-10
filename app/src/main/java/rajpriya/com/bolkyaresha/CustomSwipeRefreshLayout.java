package rajpriya.com.bolkyaresha;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by rajkumar.waghmare on 2014/11/30.
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout{
    public CustomSwipeRefreshLayout(Context context) {
        super(context);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        return false;
    }
}
