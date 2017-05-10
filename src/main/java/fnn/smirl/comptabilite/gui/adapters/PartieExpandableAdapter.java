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
import fnn.smirl.comptabilite.ohada.groupes.*;
import android.text.method.*;

public class PartieExpandableAdapter extends
BaseExpandableListAdapter {
	private Context ctx;
	private LayoutInflater inflater;
	private Partie partie;
	private ArrayList<Poste> postes;

	public PartieExpandableAdapter(Context ctx, Partie partie) {
		this.ctx = ctx;
		this.partie = partie;
		postes = partie.postes();
		setInflater();

	}

	private void setInflater() {
		inflater = LayoutInflater.from(ctx);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO: Implement this method
		final Compte cpt =  getChild(groupPosition, childPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.exp_list_partie_row, null);
		}

		convertView.setClickable(true);
		TextView exp_1= (TextView)convertView.findViewById(R.id.exp_list_poste_row_ref);
		TextView exp_2 = (TextView)convertView.findViewById(R.id.exp_list_poste_row_id);
		TextView exp_3 = (TextView)convertView.findViewById(R.id.exp_list_poste_row_libele);
		TextView exp_4 = (TextView)convertView.findViewById(R.id.exp_list_poste_row_montant);

		exp_1.setText(cpt.reference);
		exp_2.setText(cpt.id());
		exp_3.setText(cpt.titre());
		exp_4.setText(cpt.solde() + "");

		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO: Implement this method
		Poste p = getGroup(groupPosition);
		final TextView tview1;
		final TextView tview2;
		final TextView tview3;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.exp_list_partie_post, null);
		}
		tview1 = (TextView)convertView.findViewById(R.id.exp_list_post_ref);
		tview1.setText(p.reference);
		tview2 = (TextView)convertView.findViewById(R.id.exp_list_post_designation);
		tview2.setMovementMethod(new ScrollingMovementMethod());
		tview2.setText(p.designation());


		tview3 = (TextView)convertView.findViewById(R.id.exp_list_post_montant);
		tview3.setText(p.somme() + "");

		convertView.setClickable(true);
		convertView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				if (!tview2.isSelected()) {
					tview2.setSelected(true);
				} else {
					tview2.setSelected(false);
				}
			}


		});


		return convertView;
	}


	@Override
	public int getGroupCount() {
		// TODO: Implement this method
		Log.e("EPA2", "try to expand ");
		Log.e("PEA", "postes size = " + postes.size());
		return postes.size();
	}

	@Override
	public int getChildrenCount(int p1) {
		// TODO: Implement this method
		int i = postes.get(p1).comptes().size();
		Log.e("PEA", "this poste has comptes = " + i);
		return i;
	}

	@Override
	public Poste getGroup(int p1) {
		// TODO: Implement this method
		Poste p = postes.get(p1);
		Log.e("PEA", "you touched this poste of categorie = " + p.categorie().name());
		Log.e("PEA", "poste designation = " + p.designation());
		return p;
	}

	@Override
	public Compte getChild(int p1, int p2) {
		// TODO: Implement this method
		Compte c = postes.get(p1).comptes().get(p2);
		Log.e("PEA", "Compte id = " + c.id());
		return c;
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

	@Override
	public void onGroupExpanded(int groupPosition) {
		// TODO: Implement this method
		Log.e("PEA -> onGroupExpanded", "try to expand at position = " + groupPosition);
		notifyDataSetChanged();
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		// TODO: Implement this method
		notifyDataSetChanged();
		super.onGroupCollapsed(groupPosition);
	}



}
