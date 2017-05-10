package fnn.smirl.comptabilite.gui.adapters;
import android.content.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import fnn.smirl.comptabilite.*;
import fnn.smirl.comptabilite.ohada.operations.*;
import fnn.smirl.comptabilite.ohada.outils.*;
import java.util.*;
import fnn.smirl.comptabilite.gui.*;
import fnn.smirl.comptabilite.ohada.plan.*;

public class CompteExpandableAdapter extends
BaseExpandableListAdapter
{

	private Context ctx;
	private LayoutInflater inflater;
	private ArrayList<Transaction> parents;
	
	public CompteExpandableAdapter(Context ctx, ArrayList<Transaction> parents){
		this.ctx = ctx;
		this.parents = parents;
		setInflater();
		
	}
	
	private void setInflater(){
		inflater = (LayoutInflater)ctx.getSystemService(
		Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO: Implement this method
		final Entree entree = getChild(groupPosition, childPosition);
		if(convertView == null){
			convertView = inflater.inflate(R.layout.exp_list_row, null);
		}
		TextView exp_date = (TextView)convertView.findViewById(R.id.exp_list_row_ac_date);
		TextView exp_libele = (TextView)convertView.findViewById(R.id.exp_list_row_ac_libele);
		TextView exp_montant = (TextView)convertView.findViewById(R.id.exp_list_row_montant);
		TextView exp_cote = (TextView)convertView.findViewById(R.id.exp_list_row_cote);

	try{
		
			exp_date.setText(DateConverter.convertToString(entree.date, "dd/MM/yyyy"));
			exp_libele.setText(entree.libele);
			exp_montant.setText(entree.montant + "");
			exp_cote.setText(entree.cote.name());
	}catch(NullPointerException npe){
			Log.e("CompteExpandableAdapter >>", entree.toString());
		}
		
		convertView.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View p1) {
				// TODO: Implement this method
				PopupMenu popup = new PopupMenu(ctx, p1);
				popup.inflate(R.menu.ee_popup_menu);
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

					@Override
					public boolean onMenuItemClick(MenuItem p1) {
						// TODO: Implement this method
						switch (p1.getItemId()) {
							case R.id.ee_pop_new:
								
								break;
							case R.id.ee_pop_edit:


								break;
							case R.id.ee_pop_delete:
								MainActivity.compteActif.efface(entree);
								MainActivity.updateData();
								notifyDataSetChanged();
								break;
						}
						return false;
					}
				});

				popup.show();
				return false;
			}

			
		});
		
		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO: Implement this method
		int label = getGroup(groupPosition).getId();
		TextView tview = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.exp_list_group, null);
		}
		tview = (TextView)convertView.findViewById(R.id.explistgroup_tv1);
		tview.setText("Transaction " + label + "(" + getGroup(groupPosition).size() + ")");
		
//		convertView.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View p1) {
//				// TODO: Implement this method
//			
//			notifyDataSetChanged();
//			}
//
//		});
		
		return convertView;
	}


	@Override
	public int getGroupCount() {
		// TODO: Implement this method
		return parents.size();
	}

	@Override
	public int getChildrenCount(int p1) {
		// TODO: Implement this method
		return parents.get(p1).size();
	}

	@Override
	public Transaction getGroup(int p1) {
		// TODO: Implement this method
		return parents.get(p1);
	}

	@Override
	public Entree getChild(int p1, int p2) {
		// TODO: Implement this method
		return parents.get(p1).get(p2);
	}

	@Override
	public long getGroupId(int p1) {
		// TODO: Implement this method
		return p1;
	}

	@Override
	public long getChildId(int p1, int p2) {
		// TODO: Implement this method
		return p2;
	}

	@Override
	public boolean hasStableIds() {
		// TODO: Implement this method
		return true;
	}

	@Override
	public boolean isChildSelectable(int p1, int p2) {
		// TODO: Implement this method
		return true;
	}
	
	
}
