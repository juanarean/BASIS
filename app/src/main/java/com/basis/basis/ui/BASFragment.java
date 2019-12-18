package com.basis.basis.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basis.basis.BAS;
import com.basis.basis.R;
import com.basis.basis.data.BasisViewModel;
import com.basis.basis.retrofit.AuthBasisClient;
import com.basis.basis.retrofit.AuthBasisService;
import com.basis.basis.retrofit.Responses.Clientes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BASFragment extends Fragment {

    RecyclerView recyclerView;
    MyBASRecyclerViewAdapter adapter;
    List<Clientes> basList;
    AuthBasisService authBasisService;
    AuthBasisClient authBasisClient;
    BasisViewModel basisViewModel;

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BASFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basisViewModel = ViewModelProviders.of(getActivity())
                .get(BasisViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bas_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            basList = new ArrayList<>();
            retrofitInit();     //Inicio retrofit

            basisViewModel.getClientes().observe(getActivity(), new Observer<List<Clientes>>() {
                @Override
                public void onChanged(List<Clientes> clientes) {
                    basList = clientes;
                    adapter.setData(basList);
                }
            });
            adapter = new MyBASRecyclerViewAdapter(basList, mListener);
            recyclerView.setAdapter(adapter);

        }
        return view;
    }

    private void retrofitInit() {
        authBasisClient = AuthBasisClient.getInstance();
        authBasisService = authBasisClient.getAuthBasisService();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Clientes item);
    }
}
