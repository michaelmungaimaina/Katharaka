package mich.gwan.katharaka.activities.admin.ui.developer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import mich.gwan.katharaka.databinding.KimemiaFragmentBinding;

public class KimemiaFragment extends Fragment {
    private KimemiaFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = KimemiaFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FloatingActionButton btn = binding.cv;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "The CV shall be loaded soon", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        FloatingActionButton call= binding.call;
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: 0799292048"));
                startActivity(callIntent);
            }
        });

        FloatingActionButton mail = binding.mail;
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailto = "mailto:michaelmungaimaina@gmail.com" +
                        "?cc=" +
                        "&subject=" + Uri.encode("SARENSA APP DEVELOPMENT") +
                        "&body=" + Uri.encode("");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                   Toast.makeText(getView().getContext(), "Error to open email app", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }
}
