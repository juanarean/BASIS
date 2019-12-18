package com.basis.basis.ui;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.basis.basis.BAS;
import com.basis.basis.retrofit.Responses.Clientes;
import com.basis.basis.ui.BASFragment.OnListFragmentInteractionListener;
import com.basis.basis.R;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BAS} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBASRecyclerViewAdapter extends RecyclerView.Adapter<MyBASRecyclerViewAdapter.ViewHolder> {

    private List<Clientes> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyBASRecyclerViewAdapter(List<Clientes> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null) {
            holder.mItem = mValues.get(position);
            holder.mBasView.setText(mValues.get(position).getNombre());
            holder.mClientView.setText(mValues.get(position).getDireccion());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                    }
                }
            });
        }
    }

    public void setData(List<Clientes> clientesList) {
        this.mValues = clientesList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null)
            return mValues.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mBasView;
        public final TextView mClientView;
        public Clientes mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mBasView = (TextView) view.findViewById(R.id.tvBAS);
            mClientView = (TextView) view.findViewById(R.id.tvCliente);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mBasView.getText() + "'";
        }
    }
}
