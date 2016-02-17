package tomerbu.edu.recyclerviewhelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

class SwipeSupport {
    private OnItemSwipeListener mOnItemSwipeListener;

    private static SwipeSupport sharedInstance;
    private final RecyclerView mRecyclerView;

    //singleton our object for each recyclerView
    public static synchronized SwipeSupport addTo(RecyclerView view) {
        if (sharedInstance == null) {
            sharedInstance = new SwipeSupport(view); //call our private constructor
        }
        return sharedInstance;
    }

    private SwipeSupport(RecyclerView v) {
        this.mRecyclerView = v;
        RecyclerView.Adapter adapter = v.getAdapter();
        //Adding Item Touch Helper
        ItemTouchCallbacks cal = new ItemTouchCallbacks();
        ItemTouchHelper helper = new ItemTouchHelper(cal);
        helper.attachToRecyclerView(v);
    }

    public void setListener(OnItemSwipeListener onItemSwipeListener) {
        this.mOnItemSwipeListener = onItemSwipeListener;
    }

    //Interfaces:
    public interface OnItemSwipeListener {
        /**
         * @param recyclerView   Recycler
         * @param swipeDirection = ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT
         * @param position       The adapter position
         * @param holder         ViewHolder
         */
        void onItemSwiped(RecyclerView recyclerView, int swipeDirection, int position, RecyclerView.ViewHolder holder);
    }


    class ItemTouchCallbacks extends ItemTouchHelper.SimpleCallback {
        /*Define swipe and drag direction alowance, in here we only allow swipes*/
        public ItemTouchCallbacks() {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder
                viewHolder, RecyclerView.ViewHolder target) {
            return false; //Don't allow movement
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // remove from adapter
            //notify listeners
            int position = mRecyclerView.getChildAdapterPosition(viewHolder.itemView);
            if (position >= 0)
                mOnItemSwipeListener.onItemSwiped(mRecyclerView, direction, position, viewHolder);
        }
    }
}
