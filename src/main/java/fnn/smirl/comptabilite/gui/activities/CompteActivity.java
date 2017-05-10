package fnn.smirl.comptabilite.gui.activities;
import android.support.v7.app.*;
import android.os.*;
import android.opengl.*;
import fnn.smirl.comptabilite.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import fnn.smirl.comptabilite.ohada.plan.*;
import fnn.smirl.comptabilite.ohada.enums.*;
import fnn.smirl.comptabilite.gui.*;
import android.support.v4.app.*;
import java.util.*;
import fnn.smirl.comptabilite.gui.adapters.*;
import android.content.*;

public class CompteActivity extends AppCompatActivity {
	int id = 0;
	CompteExpandableAdapter ce_adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();

		setContentView(R.layout.compte);
		if (MainActivity.compteActif != null) {
			id = bundle.getInt("id");
			ce_adapter = new CompteExpandableAdapter(getApplicationContext(), MainActivity.compteActif.transactions());
		}
		init();
	}

	private void init() {

		final EditText cpt_ref = (EditText)findViewById(R.id.cpt_ref);
		final Spinner cpt_classe_id = (Spinner)findViewById(R.id.cpt_classe_id);
		final Spinner cpt_compte_id = (Spinner)findViewById(R.id.cpt_compt_id);
		final Spinner cpt_scompte_id = (Spinner)findViewById(R.id.cpt_scompte_id);
		final Spinner cpt_div_id = (Spinner)findViewById(R.id.cpt_div_id);
		final EditText cpt_titre = (EditText)findViewById(R.id.cpt_titre);
		final EditText cpt_div_titre = (EditText)findViewById(R.id.cpt_div_titre);
		final EditText cpt_id = (EditText)findViewById(R.id.cpt_id);
		final EditText cpt_intitule = (EditText)findViewById(R.id.cpt_intitule);
		final EditText cpt_solde_init = (EditText)findViewById(R.id.cpt_solde_init);
		final EditText cpt_solde_fin = (EditText)findViewById(R.id.cpt_solde_fin);
		final EditText cpt_poste_ref = (EditText)findViewById(R.id.cpt_poste_ref);
		final ExpandableListView cpt_exp_list = (ExpandableListView)findViewById(R.id.cpt_exp_list);

		ArrayAdapter<Integer> classe_id_adapter = getAdapter();
		classe_id_adapter.remove(0);
		cpt_classe_id.setAdapter(classe_id_adapter);
		ArrayAdapter<Integer> compte_id_adapter = getAdapter();
		cpt_compte_id.setAdapter(compte_id_adapter);
		ArrayAdapter<Integer> sCompte_id_adapter = getAdapter();
		cpt_scompte_id.setAdapter(sCompte_id_adapter);
		ArrayAdapter<Integer> div_id_adapter = getAdapter();
		cpt_div_id.setAdapter(div_id_adapter);

		if (MainActivity.compteActif != null) {
			cpt_ref.setText(MainActivity.compteActif.reference);
			cpt_classe_id.setSelection(classe_id_adapter.getPosition(MainActivity.compteActif.classe().getId())) ;
			cpt_compte_id.setSelection(compte_id_adapter.getPosition(MainActivity.compteActif.cptId()));
			cpt_scompte_id.setSelection(sCompte_id_adapter.getPosition(MainActivity.compteActif.sCptId()));
			cpt_div_id.setSelection(div_id_adapter.getPosition(MainActivity.compteActif.divId()));
			cpt_titre.setText(MainActivity.compteActif.sCptTitre());
			cpt_div_titre.setText(MainActivity.compteActif.divTitre());
			cpt_id.setText(MainActivity.compteActif.id() + "") ;
			cpt_intitule.setText(MainActivity.compteActif.titre());
			cpt_solde_init.setText(MainActivity.compteActif.soldeInit() + "");
			cpt_solde_fin.setText(MainActivity.compteActif.solde() + "");
			cpt_poste_ref.setText(MainActivity.compteActif.posteReference) ;

			cpt_exp_list.setAdapter(ce_adapter);
			ce_adapter.notifyDataSetChanged();
//			cpt_exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
//
//				@Override
//				public boolean onGroupClick(ExpandableListView p1, View p2, int p3, long p4) {
//					// TODO: Implement this method
//					if (!p1.isGroupExpanded(p3)) {
//						//	p1.expandGroup(p3);
//					} else {
//						//		p1.collapseGroup(p3);
//					}
//
//					return false;
//				}
//
//
//			});

		}

		// --- buttons --- //

		Button quitter_btn = (Button)findViewById(R.id.cpt_quitter);
		quitter_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				finish();
			}


		});
		Button save_btn = (Button)findViewById(R.id.cpt_enregistrer);
		save_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				String ref = cpt_ref.getText().toString();
				int clssId = cpt_classe_id.getSelectedItem();
				Classe classe = Classe.classeDu(clssId);
				int cptId = cpt_compte_id.getSelectedItem();
				int sCptId = cpt_scompte_id.getSelectedItem();
				int divId = cpt_div_id.getSelectedItem();
				String sCptTitre = cpt_titre.getText().toString();
				String divTitre = cpt_div_titre.getText().toString();
				double cptSoldeIn = Double.parseDouble(cpt_solde_init.getText().toString());

				if (MainActivity.compteActif == null) {
					MainActivity.compteActif = new Compte(classe, cptId, sCptId, divId,
					sCptTitre, divTitre);
					MainActivity.compteActif.metSoldeInitial(cptSoldeIn);
					MainActivity.compteActif.reference = ref;
					ce_adapter = new CompteExpandableAdapter(getApplicationContext(), MainActivity.compteActif.transactions());
					MainActivity.ohada.ajouterCompte();
				} else {
					MainActivity.compteActif.met(classe, cptId, sCptId, divId,
					sCptTitre, divTitre);
					MainActivity.compteActif.metSoldeInitial(cptSoldeIn);
					MainActivity.compteActif.reference = ref;
				}

				MainActivity.updateData();
				finish();
			}
		});
	}

	private ArrayAdapter<Integer> getAdapter() {
		ArrayList<Integer> al = new ArrayList<Integer>();
		al.add(0);
		al.add(1);
		al.add(2);
		al.add(3);
		al.add(4);
		al.add(5);
		al.add(6);
		al.add(7);
		al.add(8);
		al.add(9);
		return new ArrayAdapter<Integer>(this, R.layout.drop_down_model, al);
	}

}
