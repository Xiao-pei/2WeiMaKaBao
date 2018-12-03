package com.a1466387944.a2weimakabao;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private MainActivityBarcodeManager FileManage;
    private ItemTouchAdapter MianListAdapter;

    public ItemTouchHelperCallback(MainActivityBarcodeManager FileManage, ItemTouchAdapter MianListAdapter) {
        this.FileManage = FileManage;
        this.MianListAdapter = MianListAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        FileManage.onItemDelete(viewHolder.getAdapterPosition());
        MianListAdapter.onItemDelete(viewHolder.getAdapterPosition());
    }
}
