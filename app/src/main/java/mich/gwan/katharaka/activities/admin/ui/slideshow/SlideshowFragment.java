package mich.gwan.katharaka.activities.admin.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import mich.gwan.katharaka.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        int copy = 169;
        String s = Character.toString((char) copy) ;
        binding.cardOpenSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder openSource = new AlertDialog.Builder(getView().getContext());
                openSource.setTitle("Android Open Source Project")
                        .setMessage("Copyright " + s + "2022 Android Open Source Project\n" +
                                "\n" +
                                "   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "   you may not use this file except in compliance with the License.\n" +
                                "   You may obtain a copy of the License at\n" +
                                "\n" +
                                "       http://www.apache.org/licenses/LICENSE-2.0\n" +
                                "\n" +
                                "   Unless required by applicable law or agreed to in writing, software\n" +
                                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "   See the License for the specific language governing permissions and\n" +
                                "   limitations under the License.")
                        .setPositiveButton("Link", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.apache.org/licenses/LICENSE-2.0"));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                return;
                            }
                        });
                AlertDialog alert = openSource.create();
                alert.setCanceledOnTouchOutside(true);
                alert.setCancelable(true);
                alert.show();
            }

        });

        binding.cardSupportLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                AlertDialog.Builder openSource = new AlertDialog.Builder(getView().getContext());
                openSource.setTitle("Android Support libraries")
                        .setMessage("Copyright "+ s +"2022 Android Support libraries\n" +
                                "\n" +
                                "   Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                "   you may not use this file except in compliance with the License.\n" +
                                "   You may obtain a copy of the License at\n" +
                                "\n" +
                                "     http://www.apache.org/licenses/LICENSE-2.0\n" +
                                "\n" +
                                "   Unless required by applicable law or agreed to in writing, software\n" +
                                "   distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                "   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                "   See the License for the specific language governing permissions and\n" +
                                "   limitations under the License.")
                        .setPositiveButton("Link", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://opensource.org/licenses/Apache-2.0"));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                return;
                            }
                        });
                AlertDialog alert = openSource.create();
                alert.setCanceledOnTouchOutside(true);
                alert.setCancelable(true);
                alert.show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}