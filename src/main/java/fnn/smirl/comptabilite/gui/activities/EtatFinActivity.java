package fnn.smirl.comptabilite.gui.activities;
import android.support.v7.app.*;
import android.os.*;
import fnn.smirl.comptabilite.*;
import fnn.smirl.comptabilite.gui.adapters.*;
import fnn.smirl.comptabilite.gui.*;
import android.content.*;
import android.widget.*;
import android.util.*;
import android.view.*;
import fnn.smirl.comptabilite.ohada.groupes.*;

public class EtatFinActivity extends AppCompatActivity
{
	PartieExpandableAdapter actif, passif;
Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.etat_fin);
		ctx = getApplicationContext();
		
		MainActivity.ohada.refresh();
		actif = new PartieExpandableAdapter(ctx,MainActivity.ohada.actif);
		passif = new PartieExpandableAdapter(ctx,MainActivity.ohada.passif);
		
		TextView tot_actif = (TextView)findViewById(R.id.tot_actif_tv);
		TextView tot_passif = (TextView)findViewById(R.id.tot_passif_tv);

		ExpandableListView elv_actif = (ExpandableListView)findViewById(R.id.exp_list_actif);
		elv_actif.setAdapter(actif);
		elv_actif.setGroupIndicator(null);
		actif.notifyDataSetChanged();
		ExpandableListView elv_passif = (ExpandableListView)findViewById(R.id.exp_list_passif);
		elv_passif.setAdapter(passif);
		elv_passif.setGroupIndicator(null);
		passif.notifyDataSetChanged();
		tot_actif.setText(MainActivity.ohada.actif.somme() + "");
		tot_passif.setText(MainActivity.ohada.passif.somme() + "");

		elv_actif.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
//
			@Override
			public boolean onGroupClick(ExpandableListView p1, View p2, int p3, long p4) {
				// TODO: Implement this method
				if (!p1.isGroupExpanded(p3)) {
					Log.e("BilanTab", "try to expand " + ((Poste) p1.getSelectedItem()).designation());
					p1.expandGroup(p3);
				} else {
					Log.e("BilanTab", "try to collapse " + ((Poste) p1.getSelectedItem()).designation());
					p1.collapseGroup(p3);
				}

				return false;
			}
		});
	}
	
	
}
