package fnn.smirl.comptabilite.gui.tabs;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import fnn.smirl.comptabilite.*;
import fnn.smirl.comptabilite.R;
import fnn.smirl.comptabilite.gui.*;
import android.support.v7.widget.*;
import android.graphics.*;
import fnn.smirl.comptabilite.ohada.plan.*;
import android.view.View.*;
import fnn.smirl.comptabilite.ohada.groupes.*;
import android.app.*;

public class TousComptesTab extends android.support.v4.app.Fragment {
	TousComptesRVAdapter adapter;
	TextView tv1;
	public TousComptesTab() {
		Bundle b = new Bundle();
		b.putString("title", "GRAND LIVRE");
		setArguments(b);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO: Implement this method
		return inflater.inflate(R.layout.touscomptestab, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onViewCreated(view, savedInstanceState);
		RecyclerView rv = (RecyclerView)view.findViewById(R.id.touscomptestab_rv);
		rv.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(getContext());
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		rv.setLayoutManager(llm);


		adapter = new TousComptesRVAdapter();
		rv.setAdapter(adapter);

	}

	public void setHeading(String titleHeader) {
		getArguments().putString("title", titleHeader);
	}

	public CharSequence getHeading() {
		return getArguments().getString("title");
	}

	// inner adapter

	class TousComptesRVAdapter extends RecyclerView.Adapter<TousComptesRVAdapter.TousComptesDataViewHolder> {

		@Override
		public TousComptesTab.TousComptesRVAdapter.TousComptesDataViewHolder onCreateViewHolder(ViewGroup p1, int p2) {
			// TODO: Implement this method
			View v = LayoutInflater.from(p1.getContext()).
			inflate(R.layout.touscomptesmodel, p1, false);
			TousComptesDataViewHolder dvh = new TousComptesDataViewHolder(v);
			return dvh;
		}

		@Override
		public void onBindViewHolder(TousComptesTab.TousComptesRVAdapter.TousComptesDataViewHolder p1, int p2) {
			// TODO: Implement this method
			Compte cpt =	MainActivity.ohada.comptes.get(p2);
			p1.tcm_tv_id.setText(cpt.id() + "");
			p1.tcm_tv_intitule.setText(cpt.titre());
			p1.tcm_tv_cat.setText("Type : " + cpt.categorie.name());
			p1.tcm_tv_solde.setText("Solde : " + cpt.solde());
		
			}

		@Override
		public int getItemCount() {
			// TODO: Implement this method
			return MainActivity.ohada.comptes.size();
		}

		@Override
		public void onAttachedToRecyclerView(RecyclerView recyclerView) {
			// TODO: Implement this method
			super.onAttachedToRecyclerView(recyclerView);
		}


		class TousComptesDataViewHolder extends RecyclerView.ViewHolder {
			CardView cv;
			TextView tcm_tv_id, tcm_tv_intitule, tcm_tv_cat, tcm_tv_solde;

			TousComptesDataViewHolder(View view) {
				super(view);
				cv = (CardView)itemView.findViewById(R.id.tcm_cv);
				cv.setShadowPadding(0, 0, 10, 10);
				cv.setCardElevation(10);
				cv.setCardBackgroundColor(Color.LTGRAY);

				tcm_tv_id = (TextView)view.findViewById(R.id.tcm_tv_id);
				tcm_tv_intitule = (TextView)view.findViewById(R.id.tcm_tv_intitule);
				tcm_tv_solde = (TextView)view.findViewById(R.id.tcm_tv_solde);
tcm_tv_cat = (TextView)view.findViewById(R.id.tcm_tv_cat);
				view.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1) {
						// TODO: Implement this method
						MainActivity.compteActif = MainActivity.ohada.comptes.get(getPosition());
						MainActivity.startCompteActivity(MainActivity.compteActif);
					}
				});
				view.setOnLongClickListener(new OnLongClickListener(){

					@Override
					public boolean onLongClick(View p1) {
						// TODO: Implement this method
						android.support.v7.widget.PopupMenu popup = new android.support.v7.widget.PopupMenu(getContext(), p1);
						popup.inflate(R.menu.popup_menu);
						popup.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener(){

							@Override
							public boolean onMenuItemClick(MenuItem p1) {
								// TODO: Implement this method
								switch (p1.getItemId()) {
									case R.id.pop_new:

										MainActivity.startCompteActivity(null);
										break;
									case R.id.pop_edit:
										MainActivity.compteActif = MainActivity.ohada.comptes.get(getPosition());

										MainActivity.startCompteActivity(MainActivity.compteActif);
										break;
									case R.id.pop_poste_actif:
										deplacer(MainActivity.compteActif, MainActivity.ohada.actif);
										break;
									case R.id.pop_poste_passif:
										deplacer(MainActivity.compteActif, MainActivity.ohada.passif);
										break;
									case R.id.pop_poste_charge:
										deplacer(MainActivity.compteActif, MainActivity.ohada.charge);
										break;
									case R.id.pop_poste_produit:
										deplacer(MainActivity.compteActif, MainActivity.ohada.produit);
										break;
									case R.id.pop_delete:
										MainActivity.effacerCompte(MainActivity.compteActif.id());
										break;
								}
								return false;
							}
						});

						popup.show();
						return false;
					}

				});


			}
		}
	}

	private void deplacer(final Compte cpt, final Partie vers) {
		final Dialog dial = new Dialog(getContext());
		dial.setContentView(R.layout.partie_dialog);
		dial.setTitle(vers.categorie().name() + " : Choisir Poste");
		final Spinner dd_poste = (Spinner)dial.findViewById(R.id.partiedialog_spinner);
		ArrayAdapter<Poste> posting = new ArrayAdapter<Poste>(getContext(), R.layout.drop_down_model_2, vers.postes());
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
