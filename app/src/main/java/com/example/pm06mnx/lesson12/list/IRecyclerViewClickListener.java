package com.example.pm06mnx.lesson12.list;

import android.support.v7.widget.RecyclerView;

/**
 * Обработчик нажатия для элемента списка
 */
public interface IRecyclerViewClickListener {

    void onItemClick(RecyclerView.ViewHolder sender, int adapterPosition, int viewType);
}
