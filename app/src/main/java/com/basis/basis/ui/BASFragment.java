package com.basis.basis.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basis.basis.BAS;
import com.basis.basis.R;
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

            Call<List<Clientes>> call = authBasisService.getAllClients();
            call.enqueue(new Callback<List<Clientes>>() {
                @Override
                public void onResponse(Call<List<Clientes>> call, Response<List<Clientes>> response) {
                    if(response.isSuccessful()) {
                        basList = response.body();
                        adapter = new MyBASRecyclerViewAdapter(basList, mListener);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(),"Respuesta incorrecta", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<List<Clientes>> call, Throwable t) {
                    Toast.makeText(getActivity(),"Error en la conexion", Toast.LENGTH_LONG).show();
                }
            });


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
