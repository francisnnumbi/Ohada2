package fnn.smirl.comptabilite.gui.adapters;

import android.content.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import fnn.smirl.comptabilite.*;
import fnn.smirl.comptabilite.ohada.plan.*;
import java.util.*;
import fnn.smirl.comptabilite.gui.activities.*;
import fnn.smirl.comptabilite.gui.*;
import fnn.smirl.comptabilite.ohada.groupes.*;
import android.app.*;

public class ComptesListAdapter extends ArrayAdapter<Compte> {
	Activity ctx;

	public ComptesListAdapter(Activity ctx, ArrayList<Compte> list) {
		super(ctx, 0, list);
		this.ctx = ctx;
		setNotifyOnChange(true);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO: Implement this method
		final Compte cpt = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(ctx)
			.inflate(R.layout.touscomptesmodel, parent, false);
		}
		TextView tcm_tv_id = (TextView)convertView.findViewById(R.id.tcm_tv_id);
		tcm_tv_id.setText(cpt.id() + "");
		TextView tcm_tv_intitule = (TextView)convertView.findViewById(R.id.tcm_tv_intitule);
		tcm_tv_intitule.setText(cpt.titre());
		TextView tcm_tv_solde = (TextView)convertView.findViewById(R.id.tcm_tv_solde);
		tcm_tv_solde.setText(cpt.solde() + "");

		convertView.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View p1) {
				// TODO: Implement this method
				PopupMenu popup = new PopupMenu(ctx, p1);
				popup.inflate(R.menu.popup_menu);
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){

					@Override
					public boolean onMenuItemClick(MenuItem p1) {
						// TODO: Implement this method
						switch (p1.getItemId()) {
							case R.id.pop_new:
								MainActivity.startCompteActivity(null);
								break;
							case R.id.pop_edit:
								MainActivity.compteActif = cpt;
								MainActivity.startCompteActivity(cpt);
								break;
							case R.id.pop_poste_actif:
								deplacer(cpt, MainActivity.ohada.actif);
								break;
							case R.id.pop_poste_passif:
								deplacer(cpt, MainActivity.ohada.passif);
								break;
							case R.id.pop_poste_charge:
								deplacer(cpt, MainActivity.ohada.charge);
								break;
							case R.id.pop_poste_produit:
								deplacer(cpt, MainActivity.ohada.produit);
								break;
							case R.id.pop_delete:
								MainActivity.effacerCompte(cpt.id());
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


	private void deplacer(final Compte cpt, final Partie vers) {
		final Dialog dial = new Dialog(ctx);
		dial.setContentView(R.layout.partie_dialog);
		dial.setTitle(vers.categorie().name() + " : Choisir Poste");
		final Spinner dd_poste = (Spinner)dial.findViewById(R.id.partiedialog_spinner);
		ArrayAdapter<Poste> posting = new ArrayAdapter<Poste>(ctx, R.layout.drop_down_model_2, vers.postes());
		dd_poste.setAdapter(posting);


		Button ok_btn = (Button)dial.findViewById(R.id.partiedialog_ok_btn);
		ok_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
			Poste p = 	(Poste)dd_poste.getSelectedItem();
//				for (Poste po : vers.postes()) {
//					po.efface(cpt);
//				}
//				p.ajoute(cpt);
			cpt.posteReference = p.reference;
			cpt.categorie = p.categorie();
				MainActivity.updateData();
				MainActivity.ohada.refresh();
				dial.dismiss();
			}		
		});
		
		Button cancel_btn = (Button)dial.findViewById(R.id.partiedialog_cancel_btn);
		cancel_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1) {
				// TODO: Implement this method
				dial.dismiss();
			}
		});

		dial.show();

	}

}
