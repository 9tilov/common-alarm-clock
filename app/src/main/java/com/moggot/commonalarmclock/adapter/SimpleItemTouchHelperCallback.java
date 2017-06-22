package com.moggot.commonalarmclock.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.moggot.commonalarmclock.R;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter helper;
    private Context context;

    public SimpleItemTouchHelperCallback(Context context, ItemTouchHelperAdapter helper) {
        this.context = context;
        this.helper = helper;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int i) {
        showDialog(viewHolder);
    }

    private void showDialog(final RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context); //alert for confirm to delete
        builder.setMessage(context.getString(R.string.dialog_title_remove)).setCancelable(false);    //set message
        builder.setPositiveButton(context.getString(R.string.yes), (dialog, which) -> {
            helper.onItemDismiss(viewHolder.getAdapterPosition(), viewHolder.getLayoutPosition());
            return;
        }).setNegativeButton(context.getString(R.string.no), (dialog, which) -> {
            helper.onItemShow(viewHolder.getLayoutPosition());
            return;
        })
                .show();
    }

}