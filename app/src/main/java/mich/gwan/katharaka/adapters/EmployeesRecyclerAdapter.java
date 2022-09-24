package mich.gwan.katharaka.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mich.gwan.katharaka.R;
import mich.gwan.katharaka.model.Employee;

/**
 * Created by ownrestech on 21/12/2021.
 */

public class EmployeesRecyclerAdapter extends RecyclerView.Adapter<EmployeesRecyclerAdapter.EmployeeViewHolder> {

    private List<Employee> listEmployees;
    private int index = RecyclerView.NO_POSITION;

    public EmployeesRecyclerAdapter(List<Employee> listEmployees) {
        this.listEmployees = listEmployees;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_employee_recycler, parent, false);

        return new EmployeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        holder.textViewEmpName.setText(listEmployees.get(position).getEmp_name());
        holder.textViewEmpEmail.setText(listEmployees.get(position).getEmp_email());
        holder.textViewEmpPassword.setText(listEmployees.get(position).getEmp_password());
        holder.textviewEmpPhone.setText(listEmployees.get(position).getEmp_phone());
        holder.textviewEmpIdentity.setText(listEmployees.get(position).getEmp_id());
       // holder.textviewEmpUsertype.setText(listEmployees.get(position).getEmp_usertype());
        //holder.textviewEmpStation.setText(listEmployees.get(position).getEmp_station());

        holder.itemView.setSelected(index == position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                notifyItemChanged(index);
                index = holder.getLayoutPosition();
                notifyItemChanged(index);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(index);
                index = holder.getLayoutPosition();
                notifyItemChanged(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(EmployeesRecyclerAdapter.class.getSimpleName(),""+listEmployees.size());
        return listEmployees.size();
    }


    /**
     * ViewHolder class
     */
    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewEmpName;
        public AppCompatTextView textViewEmpEmail;
        public AppCompatTextView textViewEmpPassword;
        public AppCompatTextView textviewEmpPhone;
        public AppCompatTextView textviewEmpIdentity;
        public AppCompatTextView textviewEmpStation;
        public AppCompatTextView textviewEmpUsertype;

        public EmployeeViewHolder(View view) {
            super(view);
            textViewEmpName = (AppCompatTextView) view.findViewById(R.id.textViewEmpName);
            textViewEmpEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmpEmail);
            textViewEmpPassword = (AppCompatTextView) view.findViewById(R.id.textViewEmpPassword);
            textviewEmpPhone = (AppCompatTextView) view.findViewById(R.id.textViewEmpPhone);
            textviewEmpIdentity = (AppCompatTextView) view.findViewById(R.id.textViewIdNumber);
            textviewEmpStation = (AppCompatTextView) view.findViewById(R.id.textViewEmpStation);
            textviewEmpUsertype = (AppCompatTextView) view.findViewById(R.id.textViewUserType);
        }
    }


}
